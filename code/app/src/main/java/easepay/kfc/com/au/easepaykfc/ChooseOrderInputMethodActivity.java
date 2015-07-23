package easepay.kfc.com.au.easepaykfc;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.URIParsedResult;

import easepay.kfc.com.au.easepaykfc.data.DataProviderImpl;
import easepay.kfc.com.au.easepaykfc.model.Order;
import easepay.kfc.com.au.easepaykfc.util.ModelUtil;


public class ChooseOrderInputMethodActivity extends ActionBarActivity {
    private final static String TAG = "ChooseOrderInputMethod";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_order_input_method);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_order_input_method, menu);
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

    public void onScanBarCodeClick(View v){
//        Intent i = new Intent(this, ScanBarCodeActivity.class);
//        startActivity(i);
        //IntentIntegrator scanIntegrator = new IntentIntegrator(this);
       // scanIntegrator.initiateScan();
        startActivityForResult(new Intent(this, SimpleScannerActivity.class), 0);
    }

    public void onTypeOrderNo(View v){
        Intent i = new Intent(this, inputOrderNumberActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        String barcode = intent.getStringExtra(SimpleScannerActivity.SCAN_RESULT);

        String orderNumber = ModelUtil.barCodeToOrderNumber(barcode);
        DataProviderImpl dataProvider = new DataProviderImpl();
        Order order=dataProvider.getOrderByOrderNumber(orderNumber);
        if(order==null){
            Toast.makeText(this,"The order # " + orderNumber + " does not exist!",Toast.LENGTH_LONG).show();
        }else{
            Intent i = new Intent(this, OrderConfirmationActivity.class);
            Bundle bundle = new Bundle();

            Log.d(TAG,"orderNumber = " + orderNumber);

            bundle.putString("orderNumber", orderNumber);
            //bundle.putBoolean("Ismale", true);

            i.putExtras(bundle);
            startActivity(i);
        }
    }

    public String getCodeURI(ParsedResult parsedResult) {
        if (parsedResult != null && parsedResult instanceof URIParsedResult) {
            return ((URIParsedResult) parsedResult).getURI();
        }
        return null;
    }
}
