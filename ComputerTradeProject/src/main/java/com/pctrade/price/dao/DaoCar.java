package com.pctrade.price.dao;

import java.util.List;

import com.pctrade.price.entity.Car;

public interface DaoCar {

	List<Car> showAllCarsList() throws DaoException;

	List<Car> showCarsByStatus() throws DaoException;

	void createCar(Car car) throws DaoException;

	void deleteCar(Integer carId) throws DaoException; //-

	void clearTable() throws DaoException;

}
