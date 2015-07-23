package easepay.kfc.com.au.easepaykfc.data;

import easepay.kfc.com.au.easepaykfc.model.Order;
import android.app.Activity;
import android.os.Message;
import android.util.Log;

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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import easepay.kfc.com.au.easepaykfc.model.Order;
import easepay.kfc.com.au.easepaykfc.model.Product;


public class DataProviderImpl  {

	private static final String basePath = "http://10.201.38.138/public/rest";
	private Order order;



	public Order getOrderByOrderNumber(String orderNumber) {
		List<Product> products = new ArrayList<Product>();
		boolean isPaid = true;
		String url = basePath +"?method=queryOrder&order_number="+orderNumber;
		try {

			JSONObject input = readJsonFromUrl(url);
			String state = input.getString("state");
			JSONArray entries = input.getJSONArray("products");
			if(entries==null)return null;

			for (int i = 0; i < entries.length(); i++) {
				String name = entries.getJSONObject(i).getString("name");
				String price = entries.getJSONObject(i).getString("price");
				Product a = new Product();
				a.setName(name);
				a.setPrice(Double.valueOf(price));
				products.add(a);
			}
			order = new Order(products,state);
			System.out.println("ordersize = " + products.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return order;
	}

	public boolean payOrderByOrderNumber(String orderNumber) {

		boolean isSuccess = true;
		String state = "";
		String url = basePath +"?method=payOrder&order_number="+orderNumber;
		try {
			JSONObject input = readJsonFromUrl(url);
			state = input.getString("state");

		} catch (Exception e) {
			e.printStackTrace();
		}

		if(state.equals("failed")){
			return false;
		}else {
			return true;
		}

	}

	public void finishOrder(String orderNumber) {

		boolean isSuccess = true;
		String state = "";
		String url = basePath +"?method=deliverOrder&order_number="+orderNumber;
		try {
			JSONObject input = readJsonFromUrl(url);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	public Product getProduct(String productId) {
		JSONObject input = null;
		String path = basePath;
		try {
			input = readJsonFromUrl(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			String name = input.getString("name");
			String description = input.getString("description");
			String price = input.getString("price");
		} catch (JSONException e) {
			e.printStackTrace();
		}


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
	private static JSONObject readJsonFromUrl(String url) throws IOException {
		InputStream inputStream = null;
		String result = "";
		try {

			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// convert inputstream to string
			if(inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("result="+result);
		JSONObject json = null;
		try {
			json = new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}


}

