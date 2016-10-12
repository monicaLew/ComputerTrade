package com.pctrade.price.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.pctrade.price.entity.Car;

public class HtmlParser {
	private static final String urlBase = "http://www.abw.by/allpublic/sell/";
	
	public static Car extractCarInfo(Integer idPage) throws Exception {
		Car car = new Car();

		StringBuilder s = new StringBuilder(urlBase);
		s.append(idPage);
		String url = s.toString();
		//Document doc = Jsoup.connect(url).get();
		Document doc = Jsoup.connect(url).timeout(1000).get();

		Elements divWithCarCost = doc.select("span.cost-eur");
		String carCostEuroS = divWithCarCost.last().text().replaceAll(" ", "");
		Integer carCostEuro = Integer.parseInt(carCostEuroS.replaceAll("€", ""));
		Elements divWithCarCostByr = doc.select("span.cost-byrn");
		String carCostByr = divWithCarCostByr.last().text().replaceAll(" ", "");
		Double carCostBy = Double.parseDouble(carCostByr.replaceAll("руб.", ""));

		Elements divStrong = doc.select("strong");
		String annonceId = divStrong.first().text().replaceAll("\u00a0", "").trim();
		Integer i = Integer.parseInt(annonceId);

		String link = doc.select("a.see_link_mod").text();
		Elements el = doc.select("div[style=font-weight:bold;font-size:1.4em;]");
		Integer year = Integer.parseInt(el.text().substring(0, 4));

		car.setPageCode(i);
		car.setArticle(link);
		car.setPriceByn(carCostBy);
		car.setPriceEuro(carCostEuro);
		car.setYear(year);
		car.setStatus("AVAILABLE");

		return car;
	}
}
