package com.waylau.spring.cloud.weather.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.waylau.spring.cloud.weather.service.WeatherDataCollectionService;

@Service
public class WeatherDataCollectionServiceImpl implements WeatherDataCollectionService{
	
	private static final String WEATHER_URL = "http://wthrcdn.etouch.cn/weather_mini?";
	
	private static final long TIMEOUT = 1800L; //1800s
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public void syncDataByCityId(String cityId) {
		String url = WEATHER_URL+"citykey="+cityId;
		this.saveWeatherData(url);
	}
	/**
	 * 把天气数据放在缓存
	 * @param url
	 */
	private void saveWeatherData(String url) {
		String key = url;
		String strBoy = null;
		ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
		//缓存没有，再调用服务接口来获取
		ResponseEntity<String> respString =restTemplate.getForEntity(url, String.class);
		if(respString.getStatusCodeValue() == 200) {
			   strBoy = respString.getBody();
		}
		//数据写入缓存中
		ops.set(url, strBoy,TIMEOUT,TimeUnit.SECONDS);
	}
	
}
