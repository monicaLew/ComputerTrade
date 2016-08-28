package com.pctrade.price.servlet;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opencsv.CSVReader;
import com.pctrade.price.dao.DaoProduct;
import com.pctrade.price.dao.DaoProductImpl;
import com.pctrade.price.entity.Product;

public class UpdateCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		try {
			String date = (String) session.getAttribute("dateOfUpload");
			String filePath = getServletContext().getInitParameter("file-upload")
					+ session.getAttribute("lastFileNameUpload");

			DaoProduct daoProductImpl = new DaoProductImpl();

			List<Product> justOpenList = new ArrayList<Product>();

			CSVReader csvReader = null;
			csvReader = new CSVReader(new FileReader(filePath));
			String[] arr;
			while ((arr = csvReader.readNext()) != null) {
				Product product = new Product();
				product.setArticleCode(Integer.parseInt(arr[0]));
				product.setArticle(arr[1]);
				product.setPrice(Integer.parseInt(arr[2]));
				product.setDate(date);

				justOpenList.add(product);				
			}
			daoProductImpl.updateProductTable(justOpenList);
			csvReader.close();
			session.setAttribute("justfileList", justOpenList);
		} catch (Exception e) {
			session.setAttribute("exception", e);
			String encodingURL = response.encodeRedirectURL("/errorPage.jsp");
			request.getRequestDispatcher(encodingURL).forward(request, response);
		}
		String encodeURL = response.encodeURL("/result.jsp");
		request.getRequestDispatcher(encodeURL).forward(request, response);
	}
}
