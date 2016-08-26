package com.pctrade.price.servlet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opencsv.CSVReader;
import com.pctrade.price.dao.DaoProductImpl;
import com.pctrade.price.entity.Product;

public class WriteCSV extends HttpServlet {
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

		int count = 1;
	//	int incorr = 0;
		String uploadDate = (String) session.getAttribute("dateOfUpload");

		//try {

//			BufferedReader fileOut = new BufferedReader(new InputStreamReader(new FileInputStream(
//					getServletContext().getInitParameter("file-upload") + session.getAttribute("lastFileNameUpload")),
//					"utf-8"));
//			for (String line; (line = fileOut.readLine()) != null;) {
//				System.out.println(count + ": " + line);
//				count++;
//				String[] arr = line.split(",");
//				for (int i = 0; i < arr.length; i++) {
//
//					DaoProductImpl daoProductImpl = new DaoProductImpl();
//					Product product = new Product();
//					product.setArticleCode(Integer.parseInt(arr[0]));
//					product.setArticle(arr[1]);
//					product.setPrice(Integer.parseInt(arr[2]));
//					product.setDate(uploadDate);
//
//					daoProductImpl.createProduct(product);
//					// if (arr.length < 3) {
//					// incorr++;
//					// }
//				}
//			}
//			session.setAttribute("incorr", incorr);
//			fileOut.close();
			
			
			
			
//		} catch (Exception ex) {
//			System.out.println(ex);
//			session.setAttribute("ex", ex.getClass());
//		}
		
String csvFile = "C:\\Users\\Monika\\Desktop\\csv.txt";
		
		CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] arr;
            while ((arr = reader.readNext()) != null) {
                System.out.println( arr[0] + " " + arr[1] + " " + arr[2] );
            	DaoProductImpl daoProductImpl = new DaoProductImpl();
				Product product = new Product();
				product.setArticleCode(Integer.parseInt(arr[0]));
				product.setArticle(arr[1]);
				product.setPrice(Integer.parseInt(arr[2]));
				product.setDate(uploadDate);

				daoProductImpl.createProduct(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		

		String encodingURL = response.encodeRedirectURL("/csvReader.jsp");
		request.getRequestDispatcher(encodingURL).forward(request, response);
	}
}
