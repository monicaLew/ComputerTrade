package com.pctrade.price.servlet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateCSV
 */
public class UpdateCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCSV() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8"); 		
		HttpSession session = request.getSession(); 
		
		
		int incorr = 0;
		ArrayList<String> justOpenList = new ArrayList<String>(); 
		try{ 
			//BufferedReader fileOut = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\Monika\\Desktop\\priceSampleCSV.csv"), "UTF-8")); 
			
			BufferedReader fileOut = new BufferedReader(new InputStreamReader(new FileInputStream(getServletContext().getInitParameter("file-upload") + session.getAttribute("lastFileNameUpload")), "utf-8")); 
				for(String line; (line = fileOut.readLine()) != null; ){ 
					String[] arr = line.split(";"); 
					//System.out.println(count + ": " + line);
		
					if (arr.length < 3) {
						incorr++;						
					}					

					justOpenList.add(line); 		
					
					}
				fileOut.close();
		}catch(Exception ex) {				 
			System.out.println(ex); 					
			session.setAttribute("ex", ex.getClass());
		}
				session.setAttribute("incorr", incorr);
				session.setAttribute("justfileList", justOpenList);	
				
		
		Statement st = null; 
		Connection cn = null; 
		PreparedStatement prst = null; 
		ResultSet rs = null;

		final String url = "jdbc:mysql://localhost/PC_TRADE"; 
		final String user = "root"; 
		final String password = "root"; 

			try { 
				Class.forName("com.mysql.jdbc.Driver"); 

				System.out.println("Driver Found"); 
			} catch (ClassNotFoundException e) { 

				System.out.println("Driver not Found:" + e); 
			} 

			try { 
				cn = (Connection) DriverManager.getConnection(url, user, password); 
				System.out.println("create statement * * *"); 
				st = (Statement) cn.createStatement(); 
				System.out.println("Connection Succesefully"); 
				st.execute("UPDATE PRICE_LIST SET STATUS = 'NOT AVAILABLE'");
				String sql; 
				sql = "SELECT * FROM PRICE_LIST";
				rs = st.executeQuery(sql);
				//prst = cn.prepareStatement(sql2); // ! подгот запрос 	
				String article_code;				
				String date = (String) session.getAttribute("dateOfUpload");
				
		while(rs.next()){					
			st = (Statement) cn.createStatement();         // changes 'NOT AVAILABLE' - 'AVAILABLE'
				article_code = rs.getString(1);					
			
			for(String s : justOpenList){
				if(s.startsWith(article_code)){
					st.executeUpdate("UPDATE PRICE_LIST SET STATUS = 'AVAILABLE' WHERE ARTICLE_CODE ='" + article_code+"'");
					st.executeUpdate("UPDATE PRICE_LIST SET DATE = '" + date + "' WHERE ARTICLE_CODE ='" + article_code+"'");
					System.out.println(" как-то сделать апдейт ");					
				}
			}
		}
			} catch (SQLException e) { 
				System.out.println("Connection Failed:" + e); 
			} 					
						
	}

}
