package com.waylau.spring.cloud.weather.service;

import com.waylau.spring.cloud.weather.vo.Weather;

public interface WeatherReportService {
	/**
	 * 根据城市Id 获取天气数据
	 */
	Weather getDataByCityId(String cityId);
}
