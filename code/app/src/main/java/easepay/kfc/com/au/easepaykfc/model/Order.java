package easepay.kfc.com.au.easepaykfc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kodo on 23/07/15.
 */
public class Order {
    List<Product> products;
    String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    boolean isPaid;

    public Order(){
        products = new ArrayList<Product>();
        isPaid = false;
    }

    public Order(List<Product> products,String state){
        this.products = products;
        this.state = state;
    }


}
