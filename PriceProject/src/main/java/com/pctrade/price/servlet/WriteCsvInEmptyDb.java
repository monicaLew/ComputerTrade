package com.pctrade.price.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pctrade.price.classes.ReadCsv;
import com.pctrade.price.dao.DaoProduct;
import com.pctrade.price.dao.DaoProductImpl;


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
			
			String csvFile = getServletContext().getInitParameter("file-upload")
					+ session.getAttribute("lastFileNameUpload");				
			daoProductImpl.createProductTable(ReadCsv.readCsvFillProtuct(csvFile, uploadDate));
			
		} catch (Exception e) {
			session.setAttribute("exception", e);
			String encodingURL = response.encodeRedirectURL("/errorPage.jsp");
			request.getRequestDispatcher(encodingURL).forward(request, response);
		}		
		String encodingURL = response.encodeRedirectURL("/csvWrite.jsp");
		request.getRequestDispatcher(encodingURL).forward(request, response);
	}
}
