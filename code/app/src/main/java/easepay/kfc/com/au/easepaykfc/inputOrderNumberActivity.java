package easepay.kfc.com.au.easepaykfc;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import easepay.kfc.com.au.easepaykfc.model.Order;
import easepay.kfc.com.au.easepaykfc.model.Product;

import easepay.kfc.com.au.easepaykfc.data.DataProviderImpl;

import easepay.kfc.com.au.easepaykfc.model.Order;
import easepay.kfc.com.au.easepaykfc.model.Product;



public class inputOrderNumberActivity extends ActionBarActivity {

    static Order order = new Order();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_order_number);
        EditText editText = (EditText) findViewById(R.id.order_number);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Done clicked");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input_order_number, menu);
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

    public void confirmBtnClicked(View view){
        System.out.println("Done clicked");
        EditText edit = (EditText) findViewById(R.id.order_number);
        DataProviderImpl dataProvider = new DataProviderImpl();

        String orderNumber = edit.getText().toString();
        order=dataProvider.getOrderByOrderNumber(orderNumber);
        if(order==null){
            
        }

        //TODO:send code to server and validate data, send order information back
        Intent i = new Intent(this, OrderConfirmationActivity.class);
        startActivity(i);
    }


}
