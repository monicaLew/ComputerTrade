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

public class WriteCsvInEmptyDb extends HttpServlet {
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
		String uploadDate = (String) session.getAttribute("dateOfUpload");
		
		try {
			DaoProduct daoProductImpl = new DaoProductImpl();			
			List<Product> productsList = new ArrayList<Product>();
			String csvFile = getServletContext().getInitParameter("file-upload")
					+ session.getAttribute("lastFileNameUpload");

			CSVReader reader = null;

			reader = new CSVReader(new FileReader(csvFile));
			String[] arr;
			while ((arr = reader.readNext()) != null) {				
				Product product = new Product();				
					product.setArticleCode(Integer.valueOf(arr[0].trim()));
					product.setArticle(arr[1]);
					product.setPrice(Integer.parseInt(arr[2].trim()));
					product.setDate(uploadDate);	
					productsList.add(product);
			}
			daoProductImpl.createProductTable(productsList);
			reader.close();
		} catch (Exception e) {
			session.setAttribute("exception", e);
			String encodingURL = response.encodeRedirectURL("/errorPage.jsp");
			request.getRequestDispatcher(encodingURL).forward(request, response);
		}
		String encodingURL = response.encodeRedirectURL("/csvWrite.jsp");
		request.getRequestDispatcher(encodingURL).forward(request, response);
	}
}
