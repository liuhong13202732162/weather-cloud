package com.waylau.spring.cloud.weather.service;

/*
 * 天气数据采集接口
 */
public interface WeatherDataCollectionService {
	
	/**
	 * 根据城市Id  同步天气数据
	 * @param cityId
	 */
	void syncDataByCityId(String cityId);
}
