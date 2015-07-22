package easepay.kfc.com.au.easepaykfc;

import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aevi.payment.PaymentRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import easepay.kfc.com.au.easepaykfc.R;
import easepay.kfc.com.au.easepaykfc.easepay.kfc.com.au.easepaykfc.model.Product;

public class OrderConfirmationActivity extends ActionBarActivity {

    boolean paid = false;
    Double totalPrice = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        TextView list = (TextView) findViewById(R.id.product_list);
        TextView price = (TextView) findViewById(R.id.price);
        String content = "";

        for(Product p:inputOrderNumberActivity.products){
            content += p.getName()+" $"+p.getPrice()+"\n";
            totalPrice+=p.getPrice();
        }
        list.setText(content);
        price.setText(""+totalPrice);
        boolean paid = true;
        if(paid){

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
        if(paid){

        }else{
            PaymentRequest payment = new PaymentRequest(new BigDecimal(totalPrice));
            payment.setCurrency(Currency.getInstance("AUD"));

            // Launch the Payment app.
            startActivityForResult(payment.createIntent(), 0);
        }
    }
}
