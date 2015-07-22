package easepay.kfc.com.au.easepaykfc.model;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import easepay.kfc.com.au.easepaykfc.util.ModelUtil;


public class Product extends Item {

    private String name;

    private String description;

    private double price;

    private String picture;

    public String getName() {
        return name;
    }

    /** @param newName **/
    public void setName(String newName) {
        name = newName;
    }

    public String getDescription() {
        return description;
    }

    /** @param newDescription**/
    public void setDescription(String newDescription) {
        description = newDescription;
    }

    public double getPrice() {
        return price;
    }

    /** @param newPrice**/

    public void setPrice(double newPrice) {
        price = newPrice;
    }

    public String getPicture() {
        return picture;
    }

    /** @param newPicture*/
    public void setPicture(String newPicture) {
        picture = newPicture;
    }

    public static Product[] getTestProducts(){
        ArrayList<Product> products = new ArrayList<Product>();
        Product product = new Product();
        products.add(product);
        product.setId(1L);
        product.setName("KFC Product 1");
        product.setDescription("amazing nutrious food\n well come");
        product.setPrice(9.9d);
        product.setPicture("http://10.201.38.138/kfc_test_product_1.jpg");

        product = new Product();
        products.add(product);
        product.setId(2L);
        product.setName("KFC Product 2");
        product.setDescription("amazing nutrious food\n well come");
        product.setPrice(10.9d);
        product.setPicture("http://10.201.38.138/kfc_test_product_1.jpg");
        Product[] productArray = new Product[products.size()];
        productArray = products.toArray(productArray);
        return productArray;
    }

    @SuppressWarnings("deprecation")
    public static Product[] getProducts() throws ApiException {
        String jsonUrl = "http://10.201.38.138/public/rest?method=queryProduct";
        ArrayList<Product> products = new ArrayList<Product>();

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(jsonUrl);
        HttpResponse response;
        String str = null;

        try {
            response = client.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null){
                InputStream instream = entity.getContent();
                str = ModelUtil.convertStreamToString(instream);
                JSONArray jsonArray = new JSONArray(str);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Product product = new Product();
                    products.add(product);
                    product.setId(jsonObject.getLong("id"));
                    product.setName(jsonObject.getString("name"));
                    product.setDescription(jsonObject.getString("description"));
                    product.setPrice(jsonObject.getDouble("price"));
                    product.setPicture(jsonObject.getString("picture"));
                }
            }
        } catch (IOException e) {
            throw new ApiException("Fail to get json string");
        } catch (JSONException e) {
            throw new ApiException("Fail to parse json string");
        }

        Product[] productArray = new Product[products.size()];
        productArray = products.toArray(productArray);
        return productArray;
    }

}