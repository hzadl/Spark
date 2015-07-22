package easepay.kfc.com.au.easepaykfc.easepay.kfc.com.au.easepaykfc.model;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;


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
    public static Product[] getProducts(){
        ArrayList<Product> products = new ArrayList<Product>();

        HttpClient httpClient = new DefaultHttpClient();

        Product[] productArray = new Product[products.size()];
        productArray = products.toArray(productArray);
        return productArray;
    }
}