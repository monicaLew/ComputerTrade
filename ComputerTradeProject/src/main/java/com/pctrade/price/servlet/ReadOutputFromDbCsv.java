package com.pctrade.price.servlet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

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

public class ReadOutputFromDbCsv extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ENCODING_TYPE = "UTF-8";
	private static final String ERROR_NAME_DAO = "/errorPages/errorDao.jsp";
	private static final String CONTENT_TYPE = "application/octet-stream";	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		HttpUtils.Encode(request, response, ENCODING_TYPE);
		
		DaoProduct daoProduct = new DaoProductImpl();
		List<Product> productsList;
		try {
			productsList = daoProduct.showAllProductList();
		// try {
		// File file = new File("C:/priceList.txt"); //на свой комп
		// FileWriter fileWriter = new FileWriter(file);
		// Writer writer = new BufferedWriter(fileWriter);
		//
		// for (Product product : productsList) {
		// writer.write(product.toString() +
		// System.getProperty("line.separator"));
		// }
		// writer.close();
		// fileWriter.close();
		// } catch (FileNotFoundException e) {
		// HttpUtils.forward(ERROR_NAME, request, response);
		// }
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename=priceListCSV.txt");

		BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
		for (Product product : productsList) {
			writer2.write(product.toString() + System.getProperty("line.separator"));
		}		
		writer2.close();
		} catch (DaoException e) {
			session.setAttribute("exception", e);
			HttpUtils.forward(ERROR_NAME_DAO, request, response);
		}
		// HttpUtils.forward(FORWARD_NAME, request, response);
	}
}
