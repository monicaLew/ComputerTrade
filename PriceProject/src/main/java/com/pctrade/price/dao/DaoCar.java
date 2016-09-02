package com.pctrade.price.dao;

import java.util.List;

import com.pctrade.price.entity.Car;

public interface DaoCar {

	List<Car> showAllCarsList();

	Car showCarById(Integer carId);

	void createCar(Car car); 

	void updateCar(Car car);

	void deleteCar(Integer carId);

	void clearTable();

}
