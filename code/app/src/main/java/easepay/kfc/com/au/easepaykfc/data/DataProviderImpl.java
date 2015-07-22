package easepay.kfc.com.au.easepaykfc.data;

import android.app.Activity;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.message.BasicNameValuePair;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import easepay.kfc.com.au.easepaykfc.easepay.kfc.com.au.easepaykfc.model.Product;


public class DataProviderImpl  {

	private static final String basePath = "http://10.201.38.138/public/rest?method=queryProduct&order_number=1000";




	public List<Product> getOrderByOrderNumber(String orderNumber) {
		List<Product> order = new ArrayList<Product>();
		String url = basePath ;
		try {
//			result = readStringFromUrl(url);
			// System.out.println("result"+result);
			JsonObject input = readJsonFromUrl(url);
			JsonArray entries = input.get("products").getAsJsonArray();
//			JsonArray entries = (JsonArray) new JsonParser().parse(result);
			for (int i = 0; i < entries.size(); i++) {
				String name = entries.get(i).getAsJsonObject().get("name")
						.getAsString();
				String price = entries.get(i).getAsJsonObject()
						.get("price").getAsString();
				Product a = new Product();
				a.setName(name);
				a.setPrice(Double.valueOf(price));
				order.add(a);
			}
			System.out.println("ordersize = "+order.size());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return order;
	}

	public Product getProduct(String productId) {
		JsonObject input = null;
		String path = basePath;
		try {
			input = readJsonFromUrl(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String name = input.get("name").getAsString();
		String description = input.get("description").getAsString();
		String price = input.get("price").getAsString();

		Product product = new Product();
		return product;
	}


	/**
	 * JSON utility method, read JSON string from URL
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private static String readStringFromUrl(String url) throws IOException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			return jsonText;
		} finally {
			is.close();
		}
	}

	/* JSON utility method */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	/* JSON utility method */
	private static JsonObject readJsonFromUrl(String url) throws IOException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JsonElement jelement = new JsonParser().parse(jsonText);
			JsonObject jobject = jelement.getAsJsonObject();
			return jobject;
		} finally {
			is.close();
		}
	}


}
