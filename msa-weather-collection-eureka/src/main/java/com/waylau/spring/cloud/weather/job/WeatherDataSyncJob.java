package com.waylau.spring.cloud.weather.job;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.waylau.spring.cloud.weather.service.WeatherDataCollectionService;
import com.waylau.spring.cloud.weather.service.impl.WeatherDataCollectionServiceImpl;  
import com.waylau.spring.cloud.weather.vo.City;


public class WeatherDataSyncJob extends QuartzJobBean{
	
	private static final Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);
	
	@Autowired
	private WeatherDataCollectionService weatherDataCollectionService;
	@Override
	public void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("Weather Data Sync Job. Start!");
		//获取城市列表Id
		
		List<City> listCity = null;
		
		try { 
			//TODO 改为由城市数据API微服务来提供数据
			listCity = new ArrayList<>();
			City city = new City();
			city.setCityId("101280601");
			listCity.add(city);
		} catch (Exception e) {
			logger.info("Exception!",e);
		}
		//遍历城市Id  获取天气
		for(City city : listCity) {
			String cityId = city.getCityId();
			logger.info("Weather Data Sync Job, cityId : "+cityId);
			weatherDataCollectionService.syncDataByCityId(cityId);
		}
		logger.info("Weather Data Sync Job. End!");
	}

}
