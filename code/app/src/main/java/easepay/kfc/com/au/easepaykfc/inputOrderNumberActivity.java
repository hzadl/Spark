package easepay.kfc.com.au.easepaykfc;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import easepay.kfc.com.au.easepaykfc.data.DataProviderImpl;
import easepay.kfc.com.au.easepaykfc.model.Order;



public class inputOrderNumberActivity extends BaseActivity {

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

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
        if (id == android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
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
            Toast.makeText(this,"The order # " + orderNumber + " does not exist!",Toast.LENGTH_LONG).show();
        }else{
            //TODO:send code to server and validate data, send order information back
            Intent i = new Intent(this, OrderConfirmationActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString("orderNumber", orderNumber);
            //bundle.putBoolean("Ismale", true);


            i.putExtras(bundle);
            startActivity(i);
        }
    }


}
