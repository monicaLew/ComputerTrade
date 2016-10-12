package com.pctrade.price.servlet;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pctrade.price.dao.DaoCar;
import com.pctrade.price.dao.DaoCarImpl;
import com.pctrade.price.dao.DaoException;
import com.pctrade.price.entity.DefaultValues;
import com.pctrade.price.provider.CarModelProvider;
import com.pctrade.price.utils.HttpUtils;
import com.pctrade.price.utils.UrlReconnections;

public class ScanCarPageDefaultValue extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String INPUT_VIEW_NAME = "mainMenu.jsp";
	private static final String ERROR_NAME_DAO = "errorPages/errorDao.jsp";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
		DefaultValues dValues = (DefaultValues) context.getBean("defaultValues");

		Integer idFrom = dValues.getIdFrom();
		Integer idTill = dValues.getIdTill();
		Integer poolCapacity = dValues.getPoolCapacity();

		Queue<UrlReconnections> urls = new ConcurrentLinkedQueue<UrlReconnections>();
		for (int id = idFrom; id <= idTill; id++) {
			UrlReconnections urlReconnections = new UrlReconnections();
			urlReconnections.fill(id);
			urls.add(urlReconnections);
		}
		session.setAttribute("totalPages", urls.size());
		session.setAttribute("urls", urls);
		DaoCar daoCar = new DaoCarImpl();
		try {
			daoCar.clearTable();
		} catch (DaoException ex) {
			session.setAttribute("exception", ex);
			HttpUtils.forward(ERROR_NAME_DAO, request, response);
		}

		ExecutorService pool = Executors.newFixedThreadPool(poolCapacity);
		for (int id = idFrom; id <= idTill; id++) {
			Runnable worker = new CarModelProvider(urls);
			pool.execute(worker);
		}
		pool.shutdown();
		HttpUtils.forward(INPUT_VIEW_NAME, request, response);
	}
}
