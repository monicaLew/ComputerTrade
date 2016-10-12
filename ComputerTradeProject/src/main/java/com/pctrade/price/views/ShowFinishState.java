package com.pctrade.price.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pctrade.price.dao.DaoCar;
import com.pctrade.price.dao.DaoCarImpl;
import com.pctrade.price.dao.DaoException;
import com.pctrade.price.entity.Car;
import com.pctrade.price.utils.HttpUtils;
import com.pctrade.price.utils.ThreadSetUtils;

public class ShowFinishState extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SUCCESS_VIEW_NAME = "showCars/finishCars.jsp";
	private static final String INPUT_VIEW_NAME = "mainMenu.jsp";
	private static final String ERROR_NAME_DAO = "errorPages/errorDao.jsp";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		DaoCar daoCar = new DaoCarImpl();
		List<Car> carList = new ArrayList<Car>();
		List<Car> carListAll = new ArrayList<Car>();
		try {
			carList = daoCar.showCarsByStatus();
			carListAll = daoCar.showAllCarsList();
			session.setAttribute("numberOfPages", carListAll.size());
			session.setAttribute("carList", carList);
			session.setAttribute("numberOfCars", carList.size());

			if (carListAll.size() == 0 || !(ThreadSetUtils.getInst().isEmpty())) {
				HttpUtils.forward(INPUT_VIEW_NAME, request, response);
			} else {
				session.setAttribute("doneMessage", "Process is Сompleted. Finish Result : ");
				HttpUtils.forward(SUCCESS_VIEW_NAME, request, response);
			}
		} catch (DaoException e) {
			session.setAttribute("exception", e);
			HttpUtils.forward(ERROR_NAME_DAO, request, response);
		}
	}
}