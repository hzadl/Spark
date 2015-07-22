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
//			result = readStringFromUrl(url);
			// System.out.println("result"+result);


//			JSONObject input = new JSONObject("{'id':2,'order_number':'1000','state':3,'store_id':1,'order_time':1437550174,'payment_time':1437553951,'deliver_time':1437554113,'product_id':'1,3','price':20,'user_id':1,'products':[{'id':1,'name':'Beef burger','description':'yummy beef burger','price':10.5,'picture':'https:\\/\\/encrypted-tbn0.gstatic.com\\/images?q=tbn:ANd9GcQNuHABYLdlRKBVjAbUcepRALRyIpwtD0c-4e_xzzRouQofqYGvgQ'},{'id':3,'name':'chicken burger','description':'disgusting chicken burger','price':4.99,'picture':'http:\\/\\/img4.wikia.nocookie.net\\/__cb20131222143935\\/glee\\/images\\/6\\/6d\\/Chicken_Burger.jpg'}]}");
//			System.out.println(input.toString());
			JSONObject input = readJsonFromUrl(url);
			int state = input.getInt("state");
			JSONArray entries = input.getJSONArray("products");

//			JsonArray entries = (JsonArray) new JsonParser().parse(result);
			for (int i = 0; i < entries.length(); i++) {
				String name = entries.getJSONObject(i).getString("name");
				String price = entries.getJSONObject(i).getString("price");
				Product a = new Product();
				a.setName(name);
				a.setPrice(Double.valueOf(price));
				products.add(a);
			}
			if(state==1){
				isPaid = false;
			}
			order = new Order(products,isPaid);
			System.out.println("ordersize = " + products.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return order;
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

