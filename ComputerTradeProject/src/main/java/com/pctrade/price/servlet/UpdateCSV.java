package com.pctrade.price.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pctrade.price.dao.DaoProduct;
import com.pctrade.price.dao.DaoProductImpl;
import com.pctrade.price.readers.ReadCsv;
import com.pctrade.price.utils.HttpUtils;

public class UpdateCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ENCODING_TYPE = "UTF-8";
	private static final String FORWARD_NAME = "/result.jsp";
	private static final String ERROR_NAME = "/errorPages/errorPage.jsp";	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpUtils.Encode(request, response, ENCODING_TYPE);
		HttpSession session = request.getSession();
		try {
			String date = (String) session.getAttribute("dateOfUpload");
			String filePath = getServletContext().getInitParameter("file-upload")
					+ session.getAttribute("lastFileNameUpload");

			DaoProduct daoProductImpl = new DaoProductImpl();
			daoProductImpl.updateProductTable(ReadCsv.readCsvFillProduct(filePath, date));			
						
		} catch (Exception e) {
			session.setAttribute("exception", e);
			HttpUtils.forward(ERROR_NAME, request, response);
		}
		HttpUtils.forward(FORWARD_NAME, request, response);		
	}
}
