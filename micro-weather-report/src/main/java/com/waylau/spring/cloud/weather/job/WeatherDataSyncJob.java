package com.waylau.spring.cloud.weather.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.core.QuartzScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.waylau.spring.cloud.weather.service.CityDataService;
import com.waylau.spring.cloud.weather.service.WeatherDataService;
import com.waylau.spring.cloud.weather.service.impl.WeatherDataServiceImpl;
import com.waylau.spring.cloud.weather.vo.City;
import com.waylau.spring.cloud.weather.vo.CityList;

public class WeatherDataSyncJob extends QuartzJobBean{
	
	private static final Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);
	
	@Autowired
	private CityDataService cityDataService;
	@Autowired
	private WeatherDataService weatherDataService;
	@Override
	public void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("Weather Data Sync Job. Start!");
		//获取城市列表Id
		List<City> listCity = null;
		try {
			listCity = cityDataService.listCity();
		} catch (Exception e) {
			logger.info("Exception!",e);
		}
		//遍历城市Id  获取天气
		for(City city : listCity) {
			String cityId = city.getCityId();
			logger.info("Weather Data Sync Job, cityId : "+cityId);
			weatherDataService.syncDataByCityId(cityId);
		}
		logger.info("Weather Data Sync Job. End!");
	}

}
