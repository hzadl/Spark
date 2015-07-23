package easepay.kfc.com.au.easepaykfc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;


import java.util.HashSet;
import java.util.Set;

import easepay.kfc.com.au.easepaykfc.model.ApiException;
import easepay.kfc.com.au.easepaykfc.model.Params;
import easepay.kfc.com.au.easepaykfc.model.Product;
import easepay.kfc.com.au.easepaykfc.util.ModelUtil;


@SuppressWarnings("deprecation")
public class ProductActivity extends ActionBarActivity {

    Product[] products;

    ListView lvProducts;

    private double totalPrice = 0.00d;

    private Set<Long> selectedProductIds = new HashSet<Long>();

    private void updateTotalPrice(){
        double totalPrice = 0.00d;
        for (int i = 0; i < products.length; i++) {
            if (selectedProductIds.contains(products[i].getId())){
                totalPrice += products[i].getPrice();
            }
        }
        this.totalPrice = totalPrice;
        setTitle("Total: $" + String.valueOf(totalPrice));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //load data and images from data base
        //products = Product.getTestProducts();
        try {
            products = Product.getProducts();
            //products = Product.getTestProducts();
            //get view handles
            lvProducts = (ListView)findViewById(R.id.lvProducts);
            ProductAdapter adapter = new ProductAdapter(this,products);
            lvProducts.setAdapter(adapter);
        } catch (ApiException e) {
            Toast.makeText(this,"Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_order) {
            Toast.makeText(this,"order put",Toast.LENGTH_LONG).show();
            placeOrder();
        }

        return super.onOptionsItemSelected(item);
    }

    private void placeOrder() {
        //get price
        //get product_ids
        //
        if (selectedProductIds.size() == 0){
            Toast.makeText(this,"Please choose products you need",Toast.LENGTH_LONG).show();
        }else{
            String methodName = "createOrder";
            Params params = new Params();
            params.addParam("price", totalPrice);
            params.addParam("product_id", ModelUtil.convertToString(selectedProductIds));
            String paramStr = params.toString();
            try {
                String jsonResult = ModelUtil.executeJson(methodName, paramStr);
                JSONObject jsonObject = new JSONObject(jsonResult);
                String state = jsonObject.getString("state");
                if (state.equals("successed")){
                    String orderNo = jsonObject.getString("order_number");
                    showMessage("order successfully, order no: " + orderNo);
                }else{
                    throw new ApiException("Fail to place order in database");
                }
            }catch (Exception e){
                Toast.makeText(this,"Error: " + e.toString(),Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showMessage(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG);
    }

    private class ProductAdapter extends ArrayAdapter<Product>{
        private final Context context;
        private Product[] products;
        public ProductAdapter(Context context, Product[] aProducts) {
            super(context, -1, aProducts);
            this.context = context;
            this.products = aProducts;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder")
            View rowView = inflater.inflate(R.layout.product_row_layout, parent, false);
            Product product = products[position];

            //checkbox
            CheckBox checkBox = (CheckBox)rowView.findViewById(R.id.product_checkbox);
            checkBox.setTag(new Long(product.getId()));
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( ((CheckBox)v).isChecked() ){
                        selectedProductIds.add((Long)v.getTag());
                    }else{
                        selectedProductIds.remove((Long)v.getTag());
                    }
                    updateTotalPrice();
                }


            });


            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            UrlImageViewHelper.setUrlDrawable(imageView, product.getPicture());

            TextView tvProductName = (TextView) rowView.findViewById(R.id.product_name);
            tvProductName.setText(product.getName());

            TextView tvPrice = (TextView) rowView.findViewById(R.id.price);
            tvPrice.setText("$" + product.getPrice());

            TextView tvSecondLine = (TextView)rowView.findViewById(R.id.secondLine);
            tvSecondLine.setText(product.getDescription());
            return rowView;
        }

    }
}
