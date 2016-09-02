package com.pctrade.price.servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pctrade.price.dao.DaoProduct;
import com.pctrade.price.dao.DaoProductImpl;
import com.pctrade.price.entity.Product;

public class ReadOutputFromDB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		DaoProduct daoProduct = new DaoProductImpl();
		List<Product> productsList = daoProduct.showAllProductList();

		File file = new File("C:\\priceList.txt");
		FileWriter fileWriter = new FileWriter(file);
		Writer writer = new BufferedWriter(fileWriter);

		for (Product product : productsList) {
			writer.write(product.toString() + System.getProperty("line.separator"));
		}
		writer.close();

		request.getRequestDispatcher("/output.jsp").forward(request, response);

	}
}
