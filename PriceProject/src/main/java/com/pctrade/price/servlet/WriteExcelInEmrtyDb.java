package com.pctrade.price.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pctrade.price.classes.ReadExcel;
import com.pctrade.price.dao.DaoProduct;
import com.pctrade.price.dao.DaoProductImpl;

@WebServlet("/parse")
public class WriteExcelInEmrtyDb extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String filePath = getServletContext().getInitParameter("file-upload")
				+ session.getAttribute("lastFileNameUpload");
		try {
			DaoProduct daoProductImpl = new DaoProductImpl();
			String date = (String) session.getAttribute("dateOfUpload");
			daoProductImpl.createProductTable(ReadExcel.readExcelFillProduct(filePath, date));

		} catch (Exception e) {
			session.setAttribute("exception", e);
			String encodingURL = response.encodeRedirectURL("/errorPage.jsp");
			request.getRequestDispatcher(encodingURL).forward(request, response);
		}
		String encodeURL = response.encodeURL("/excelWrite.jsp");
		request.getRequestDispatcher(encodeURL).forward(request, response);
	}
}
