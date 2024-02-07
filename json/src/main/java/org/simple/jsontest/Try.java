package org.simple.jsontest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Try {
	public	static void main(String[] args) {
		{
			StockList stl = new StockList();
			stl.add(new Stock("42", "apple", 100));
			stl.add(new Stock("43", "banana", 80));
			stl.add(new Stock("44", "orange", 40));
			System.out.println(stl.toJsonString());

			Gson gson = new Gson();
			String s = gson.toJson(stl);

			System.out.println(s);

			StockList stl2 = gson.fromJson(s, StockList.class);
			System.out.println(stl2.toJsonString());
		}
		{
			Map<String,String> map = new HashMap<>();
			map.put("x", "foo");
			map.put("y", "bar");

			Gson gson = new Gson();
			String s = gson.toJson(map);

			System.out.println(s);

			Type typeOfHashMap = new TypeToken<HashMap<String,String>>() {}.getType();
			Map<String,String> map2 = gson.fromJson(s, typeOfHashMap);
			System.out.println(map2.get("x"));
			System.out.println(map2.get("y"));

			Map<String,String> map3 = gson.fromJson(s, Map.class);
			System.out.println(map3.get("x"));
			System.out.println(map3.get("y"));
		}
		{
			List<Stock> list = new ArrayList<>();
			list.add(new Stock("42", "apple", 100));
			list.add(new Stock("43", "banana", 80));
			list.add(new Stock("44", "orange", 40));

			Gson gson = new Gson();
			String s = gson.toJson(list);

			System.out.println(s);

			Type listOfStockObject = new TypeToken<ArrayList<Stock>>() {}.getType();
			List<Stock> list2 = gson.fromJson(s, listOfStockObject);
			System.out.println(list2.get(0).toJsonString());
			System.out.println(list2.get(1).toJsonString());
			System.out.println(list2.get(2).toJsonString());
		}
	}
}

class StockList {
	private	List<Stock> stocks = new ArrayList<>();;
	public void add(Stock stock) {
		stocks.add(stock);
	}
	public String toJsonString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{stocks:[");
		for (Stock stock : stocks) {
			sb.append(stock.toJsonString()).append(",");
		}
		if (sb.lastIndexOf("[") == sb.length()-1) {
				sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]}");
		return sb.toString();
	}
}

class Stock {
	private String code;
	private String name;
	private Integer cnt;
	public Stock(String code, String name, Integer cnt) {
		this.code = code;
		this.name = name;
		this.cnt = cnt;
	}
	public String toJsonString() {
		return new StringBuffer()
				.append("{")
				.append("code").append(":").append("\"").append(code).append("\"").append(",")
				.append("name").append(":").append("\"").append(name).append("\"").append(",")
				.append("cnt").append(":").append(cnt)
				.append("}")
				.toString();
	}
}