package com.pctrade.price.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pctrade.price.dao.DaoException;
import com.pctrade.price.dao.DaoProduct;
import com.pctrade.price.dao.DaoProductImpl;
import com.pctrade.price.entity.Product;
import com.pctrade.price.utils.HttpUtils;


public class ShowProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ENCODING_TYPE = "UTF-8";
	private static final String FORWARD_NAME = "showProducts.jsp";
	private static final String ERROR_NAME_DAO = "errorPages/errorDao.jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpUtils.Encode(request, response, ENCODING_TYPE);
		HttpSession session = request.getSession();
		DaoProduct daoProductImpl = new DaoProductImpl();
		
		List<Product> productsList;
		try {
			productsList = daoProductImpl.showAllProductList();
			session.setAttribute("productsList", productsList);
		} catch (DaoException e) {
			session.setAttribute("exception", e);
			HttpUtils.forward(ERROR_NAME_DAO, request, response);
		}				
		HttpUtils.forward(FORWARD_NAME, request, response);	
	}
}
