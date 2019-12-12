package com.waylau.spring.cloud.weather.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.waylau.spring.cloud.weather.service.WeatherReportService;
import com.waylau.spring.cloud.weather.vo.City;
import com.waylau.spring.cloud.weather.vo.WeatherResponse;

/*
 * 天气Controller
 */
@RestController
@RequestMapping("/report")
public class WeatherReportController {
	
	private static final Logger logger = LoggerFactory.getLogger(WeatherReportController.class);
	@Autowired
	private WeatherReportService weatherReportService;
	
	@GetMapping("/cityId/{cityId}")
	public ModelAndView getReportByCityId(@PathVariable("cityId") String cityId,Model model) throws Exception {
		
		//改为由城市数据API微服务来提供数据
		//获取城市列表Id
		
		List<City> listCity = null;
				
		try { 
			//TODO 改为由城市数据API微服务来提供数据
			listCity = new ArrayList<>();
			City city = new City();
			city.setCityId("101280601");
			city.setCityName("深圳");
			listCity.add(city);
		} catch (Exception e) {
			logger.info("Exception!",e);
		}
		model.addAttribute("title", "小宏天气实时预测");
		model.addAttribute("cityId", cityId);
		model.addAttribute("cityList", listCity);
		model.addAttribute("report", weatherReportService.getDataByCityId(cityId));
		return new ModelAndView("weather/report","reportModel",model);
	}
}
