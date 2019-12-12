package com.waylau.spring.cloud.weather.service;

import java.util.List;

import com.waylau.spring.cloud.weather.vo.City;

public interface CityDataService {
	/*
	 * 获取city列表
	 */
	List<City> listCity() throws Exception;
}
