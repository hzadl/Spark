package easepay.kfc.com.au.easepaykfc.model;

import java.util.List;

/**
 * Created by Kodo on 23/07/15.
 */
public class Order {
    List<Product> products;

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
    public Order(List<Product> products,boolean isPaid){
        this.products = products;
        this.isPaid = isPaid;
    }

}
