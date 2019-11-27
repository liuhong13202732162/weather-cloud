package com.waylau.spring.cloud.weather.service;

import com.waylau.spring.cloud.weather.vo.WeatherResponse;

/*
 * 天气数据接口
 */
public interface WeatherDataService {
	/*
	 * 根据城市Id查询天气信息
	 */
	WeatherResponse getDataByCityId(String cityId);
	/*
	 * 根据城市名称查询天气信息
	 */
	WeatherResponse getDataByCityName(String cityName);
}
