package com.pctrade.price.servlet;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


/**
 * Servlet implementation class DBconnect
 */
public class DBconnect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DBconnect() {
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
		final Statement st; 
		final Connection cn;
		
		final String url = "jdbc:mysql://localhost/SAKILA";
		final String user = "root";
		final String password = "root";		
					
		HttpSession session = request.getSession();
				ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();				
			try {
				Class.forName("com.mysql.jdbc.Driver");	
				
				System.out.println("Driver Found");
			} catch (ClassNotFoundException e) {
				
				System.out.println("Driver not Found:" + e);
			}
			int t=0;
			try {
				cn = (Connection) DriverManager.getConnection(url, user, password);
				System.out.println("create statement * * *");
				st = (Statement) cn.createStatement(); 
				System.out.println("Connection Succesefully");
				String sql;
				sql = "SELECT * FROM CITY";
				//ResultSet rs = st.executeQuery(sql);		
				String sq  = "SELECT COUNT(CITY) FROM CITY WHERE `CITY` = 'IFE'";
				PreparedStatement preparedStatement = cn.prepareStatement(sq);
				//String sq  = "SELECT COUNT(CITY) FROM CITY WHERE `CITY` = 'MINSK'";
				ResultSet re = preparedStatement.executeQuery();
				
				while(re.next()){
				int r = re.getInt(1);			
				
				t = t + r;
				System.out.println(t);
				}
//					for (int i = 0; rs.next(); i++) {
//						ArrayList<String> ar = new ArrayList<String>();
//					int cityId = rs.getInt("CITY_ID");
//					ar.add(String.valueOf(cityId));			
//					String city = rs.getString("CITY");
//					ar.add(city);
//					int countryID = rs.getInt("COUNTRY_ID");
//					ar.add(String.valueOf(countryID));
//					String lastUpdate = rs.getString("LAST_UPDATE");
//					ar.add(lastUpdate);
//					int c = 0;
//					
//					if(rs.getString("CITY").equals("Minsk")){						
//						System.out.println(" Есть Минск  - ----   ----   ----    -----   --       ---   ----  --  - !");             // UPDATE 
//					}else{						
//						System.out.println(" Проверка на отсутствие колонки со значением X ");                        // неееее перед пробегом запрос на каунт если 0 = то инсерт
//					}
//					
//					System.out.print(cityId + ", ");
//					System.out.print(city + ", ");
//					System.out.print(countryID + ", ");
//					System.out.println(lastUpdate);
//					list.add(ar);
//					session.setAttribute("array_history" + i, ar);
//				}														
				
				//rs.close();
				st.close();
				cn.close();			
				System.out.println("*****");
				System.out.println(list);
				System.out.println("*****");
				//session.setAttribute("array_history", list);			
				
				
				
			} catch (SQLException e) {
				System.out.println("Connection Failed:" + e);
			} 
					System.out.println("GoooodBye !!!");
					String encodeURL = response.encodeURL("/db.jsp");
					request.getRequestDispatcher(encodeURL).forward(request, response);					
		}
}
