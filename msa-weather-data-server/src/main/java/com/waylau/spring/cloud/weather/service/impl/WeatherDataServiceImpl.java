package com.waylau.spring.cloud.weather.service.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waylau.spring.cloud.weather.service.WeatherDataService;
import com.waylau.spring.cloud.weather.vo.WeatherResponse;

@Service
public class WeatherDataServiceImpl implements WeatherDataService{
	
	private static final Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);
	
	private static final String WEATHER_URL = "http://wthrcdn.etouch.cn/weather_mini?";
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
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
		String key = url;
		WeatherResponse resp = null;
		String strBoy = null;
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		//从字符串中读取JSON  
		ObjectMapper mapper = new ObjectMapper();
		//先查询缓存，缓存有的先从缓存取数据
		if(stringRedisTemplate.hasKey(key)) {
			logger.info("Redis has data");
			strBoy = ops.get(key);
		}else {  
			logger.info("Redis don't has data");
			//缓存没有，
			throw new RuntimeException("Don't has data!");
			
		}
		try {
			//从字符串中读取JSON readValue()
			resp = mapper.readValue(strBoy, WeatherResponse.class);
		}catch (Exception e) {
			//e.printStackTrace();
			logger.error("ERROR!",e );
		}
		return resp;
	}

}
