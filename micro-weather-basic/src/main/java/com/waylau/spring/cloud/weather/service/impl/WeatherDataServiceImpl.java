package com.waylau.spring.cloud.weather.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waylau.spring.cloud.weather.service.WeatherDataService;
import com.waylau.spring.cloud.weather.vo.WeatherResponse;

@Service
public class WeatherDataServiceImpl implements WeatherDataService{
	
	public static final String WEATHER_URL = "http://wthrcdn.etouch.cn/weather_mini?";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public WeatherResponse getDataByCityId(String cityId) {
		String url = WEATHER_URL+"citykey="+cityId;
		return this.doGetWeather(url);
	}

	@Override
	public WeatherResponse getDataByCityName(String cityName) {
		String url = WEATHER_URL+"city="+cityName;
		return this.doGetWeather(url);
	}
	
	@SuppressWarnings("unused")
	private WeatherResponse doGetWeather(String url) {
		ResponseEntity<String> respString =restTemplate.getForEntity(url, String.class);
		ObjectMapper mapper = new ObjectMapper();
		WeatherResponse resp = null;
		String strBoy = null;
		if(respString.getStatusCodeValue() == 200) {
		    strBoy = respString.getBody();
		}
		try {
			resp = mapper.readValue(strBoy, WeatherResponse.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
}
