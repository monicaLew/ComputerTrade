package com.pctrade.price.provider;

import java.net.SocketTimeoutException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.pctrade.price.dao.DaoCar;
import com.pctrade.price.dao.DaoCarImpl;
import com.pctrade.price.dao.DaoException;
import com.pctrade.price.entity.Car;
import com.pctrade.price.parser.HtmlParser;
import com.pctrade.price.utils.UrlReconnections;
import com.pctrade.price.utils.ThreadSetUtils;

public class CarModelProvider implements Runnable {
	private Queue<UrlReconnections> urls = new ConcurrentLinkedQueue<UrlReconnections>();
	private static ThreadSetUtils threadSet = null;
	public CarModelProvider(Queue<UrlReconnections> urls) {
		this.urls = urls;
	}

	public void run() {
		threadSet = ThreadSetUtils.getInst();
		threadSet.addId(Thread.currentThread().getId());
		DaoCar daoCar = new DaoCarImpl();
		UrlReconnections r;
		while ((r = urls.poll()) != null) {
			System.out.println("Проверка 1 - " + r.getIdPage() + " " + r.getNumberOfReconnect());
			Car car = new Car();
			try {
				car = HtmlParser.extractCarInfo(r.getIdPage());
			} catch (Exception ex) {
				System.out.println("Проверка 2 - " + r.getIdPage() + " " + r.getNumberOfReconnect());
				if ((ex instanceof SocketTimeoutException) && (r.getNumberOfReconnect() < 3)) {
					System.out.println("Проверка . . 3 - " + r.getIdPage() + "        " + r.getNumberOfReconnect());
					r.setNumberOfReconnect(r.getNumberOfReconnect() + 1);
					urls.add(r);
					continue;
				}
				car = Car.createCar(ex, r.getIdPage());
			}
			try {
				daoCar.createCar(car);
			} catch (DaoException e) {
				System.out.println("DaoException   DaoException   DaoException   CREATEcar");
			}
		}
		threadSet.remove(Thread.currentThread().getId());
	}	
}
