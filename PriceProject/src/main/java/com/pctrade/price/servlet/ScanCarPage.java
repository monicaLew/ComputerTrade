package com.pctrade.price.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.pctrade.price.dao.DaoCar;
import com.pctrade.price.dao.DaoCarImpl;
import com.pctrade.price.entity.Car;

public class ScanCarPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String urlBase = "http://www.abw.by/allpublic/sell/";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Integer idFrom = Integer.parseInt(request.getParameter("idFrom").trim());
		Integer idTill = Integer.parseInt(request.getParameter("idTill").trim());		
		while (idFrom <= idTill) {
			try {
				StringBuilder s = new StringBuilder(urlBase);
				s.append(idFrom);
				String url = s.toString();
				Document doc = Jsoup.connect(url).get();

				Elements divWithCarCost = doc.select("span.cost-eur");
				String carCostEuroS = divWithCarCost.last().text().replaceAll(" ", "");
				Integer carCostEuro = Integer.parseInt(carCostEuroS.replaceAll("€", ""));
				Elements divWithCarCostByr = doc.select("span.cost-byr");
				String carCostByr = divWithCarCostByr.last().text().replaceAll(" ", "");
				Double carCostBy = Double.parseDouble(carCostByr.replaceAll("р.", ""));

				Elements divStrong = doc.select("strong");
				String annonceId = divStrong.first().text().replaceAll("\u00a0", "").trim();
				Integer i = Integer.parseInt(annonceId);
				
				String link = doc.select("a.see_link_mod").text();				
				Elements el = doc.select("div[style=font-weight:bold;font-size:1.4em;]");
				Integer year = Integer.parseInt(el.text().substring(0, 4));				

				Car car = new Car();
				car.setPageCode(i);
				car.setArticle(link);
				car.setPriceByn(carCostBy);
				car.setPriceEuro(carCostEuro);
				car.setYear(year);
				DaoCar daoCar = new DaoCarImpl();
				daoCar.createCar(car);

			} catch (HttpStatusException e) {
				
			} catch (Exception ex) {
				session.setAttribute("exception", ex);
				// String encodingURL =
				// response.encodeRedirectURL("/errorPage.jsp");
				// request.getRequestDispatcher(encodingURL).forward(request,
				// response);
			}
			idFrom++;
		}
		List<Car> carList = new ArrayList<Car>();
		DaoCar daoCar = new DaoCarImpl();
		carList = daoCar.showAllCarsList();
		session.setAttribute("carList", carList);

		String encodeUrl = response.encodeRedirectURL("/inputCars.jsp");
		request.getRequestDispatcher(encodeUrl).forward(request, response);
	}
}
