package easepay.kfc.com.au.easepaykfc;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aevi.payment.PaymentRequest;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import easepay.kfc.com.au.easepaykfc.model.Product;

public class OrderConfirmationActivity extends ActionBarActivity {

    boolean isPaid = true;
    Double totalPrice = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        TextView list = (TextView) findViewById(R.id.product_list);
        TextView price = (TextView) findViewById(R.id.price);
        Button next = (Button) findViewById(R.id.next_step);
        String content = "";
        List<Product> products = inputOrderNumberActivity.order.getProducts();
        isPaid = inputOrderNumberActivity.order.isPaid();
        for(Product p:products){
            content += p.getName()+" $"+p.getPrice()+"\n";
            totalPrice+=p.getPrice();
        }
        list.setText(content);
        price.setText(""+totalPrice);

        if(isPaid){
            next.setText("Print Receipt");
        }else {
            next.setText("Pay Now");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_confirmation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void nextBtnClicked(View view){
        if(isPaid){
            //TODO:print receipt
        }else{

            PaymentRequest payment = new PaymentRequest(new BigDecimal(""+totalPrice));
            payment.setCurrency(Currency.getInstance("AUD"));

            // Launch the Payment app.
            startActivityForResult(payment.createIntent(), 0);
        }
    }
}
