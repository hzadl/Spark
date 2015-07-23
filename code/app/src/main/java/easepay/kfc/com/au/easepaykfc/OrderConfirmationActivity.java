package easepay.kfc.com.au.easepaykfc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aevi.helpers.services.AeviServiceConnectionCallback;
import com.aevi.payment.PaymentRequest;
import com.aevi.payment.TransactionResult;
import com.aevi.payment.TransactionStatus;
import com.aevi.printing.PrintService;
import com.aevi.printing.PrintServiceProvider;
import com.aevi.printing.PrinterSettings;
import com.aevi.printing.model.Alignment;
import com.aevi.printing.model.FontStyle;
import com.aevi.printing.model.PrintPayload;
import com.aevi.printing.model.Underline;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import easepay.kfc.com.au.easepaykfc.data.DataProviderImpl;
import easepay.kfc.com.au.easepaykfc.model.Order;
import easepay.kfc.com.au.easepaykfc.model.Product;

public class OrderConfirmationActivity extends ActionBarActivity {

    private PrintServiceProvider serviceProvider = new PrintServiceProvider(this);
    private PrintService printService;
    String content;
    boolean isPaid = true;
    Double totalPrice = 0.0;
    Button next;
    TextView thankMessage;
    List<Product> products;
    String orderNumber;
    private Order order;
    DataProviderImpl dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        Bundle bundle=this.getIntent().getExtras();
        orderNumber=bundle.getString("orderNumber");
        TextView list = (TextView) findViewById(R.id.product_list);
        TextView price = (TextView) findViewById(R.id.price);
        thankMessage=(TextView) findViewById(R.id.textView4);
         next = (Button) findViewById(R.id.next_step);
         content = "";

        //query products
        dataProvider = new DataProviderImpl();
        order=dataProvider.getOrderByOrderNumber(orderNumber);

        if(order==null){
            Toast.makeText(this,"The order " + orderNumber + " does not exist",Toast.LENGTH_LONG).show();
            finish();
        }
        TextView tvOrderNumber = (TextView)findViewById(R.id.tvOrderNumber);
        tvOrderNumber.setText("Order # " + orderNumber);
        products = order.getProducts();
        isPaid = order.isPaid();
        for(Product p:products){
            content += p.getName()+" $"+p.getPrice()+"\n";
            totalPrice+=p.getPrice();
        }
        list.setText(content);
        price.setText("" + totalPrice);

        if(isPaid){
            next.setText("OK");
            thankMessage.setVisibility(View.VISIBLE);
        }else {
            next.setText("Pay Now");
            thankMessage.setVisibility(View.INVISIBLE);
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
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            this.finish();
            serviceProvider.connect(new AeviServiceConnectionCallback<PrintService>() {
                @Override
                public void onConnect(PrintService service) {

                    if (service == null) {
                        // Print service failed to open, please check the ADB log file for details.

                        return;
                    }

                    // service connected, have fun
                    printService = service;
                    if(null!=printService)
                    {
                        printReceipt();
                    }
                }
            });
        }else{

            PaymentRequest payment = new PaymentRequest(new BigDecimal(""+totalPrice));
            payment.setCurrency(Currency.getInstance("AUD"));

            // Launch the Payment app.
            startActivityForResult(payment.createIntent(), 0);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0) {
            // Obtain the transaction result from the returned data.
            TransactionResult result = TransactionResult.fromIntent(data);
            // Use a toast to show the transaction result.
            Toast.makeText(this, "Transaction result: " + result.getTransactionStatus(), Toast.LENGTH_LONG).show();
            if(result.getTransactionStatus()== TransactionStatus.APPROVED) {
                //pay successful, transfer to wait view and print
               next.setText("OK");
                thankMessage.setVisibility(View.VISIBLE);
                isPaid=true;
                dataProvider.payOrderByOrderNumber(orderNumber);







            }
            else{
                //unsuccessful, come back to currentView
            }
        }
    }

    void printReceipt()
    {// construct the printer payload
        PrintPayload printPayload = getPayload(printService.getDefaultPrinterSettings());

// first line, hello world
        //printPayload.append("Hello world!").align(Alignment.Center);

// second line, the current date

        //Bitmap preview = this.printService.preview(printPayload, this.printService.getDefaultPrinterSettings());

// print the payload
         printService.print(printPayload);
    }



    private PrintPayload getPayload(PrinterSettings printerSettings) {

        PrintPayload payload = new PrintPayload();
        // blank line
//        payload.appendEmptyLine();
//        payload.append("KFC Adelaide").align(Alignment.CENTER);
//
//
//        payload.append("Order detail: price").align(Alignment.CENTER);
//        //printPayload.append("Emphasized").fontStyle(FontStyle.EMPHASIZED);
//        //printPayload.append("Inverted").fontStyle(FontStyle.INVERTED);
//        payload.appendEmptyLine();
//        payload.append("*******************").underline(Underline.SINGLE).align(Alignment.CENTER);;
//      payload.append("Total: Price").fontStyle(FontStyle.INVERTED_EMPHASIZED).align(Alignment.CENTER);;

        // printPayload.append("Double Underlined").underline(Underline.DOUBLE);

// send the request to the print service



        // logo
        // graphic lines
        BitmapFactory.Options bitmapFactoryOptions = printerSettings.asBitmapFactoryOptions();

        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.kfc_logo, bitmapFactoryOptions);
        payload.appendEmptyLine();
        payload.append(logo).align(Alignment.CENTER);
        // blank line
        payload.appendEmptyLine();
        // contact no.
        payload.append("Order#: "+orderNumber).align(Alignment.CENTER);
        // address
        payload.append("Address: KFC RundleMall ");
        // // append a table
        payload.append("#   Name        Price ")
                .underline(Underline.DOUBLE)
                .align(Alignment.CENTER)
                .fontStyle(FontStyle.EMPHASIZED);

        payload.append(content).align(Alignment.CENTER);
//        payload.append("2   Beer        $6.00 ").align(Alignment.CENTER);
        payload.appendEmptyLine();
        payload.append("    Total       $"+totalPrice).align(Alignment.CENTER).fontStyle(FontStyle.INVERTED_EMPHASIZED);
        payload.append("Congratulations! You are rewarded with a barcode coupon! ").align(Alignment.CENTER);
        Bitmap barcode = BitmapFactory.decodeResource(getResources(), R.drawable.staticbarcode, bitmapFactoryOptions);
        payload.appendEmptyLine();
        payload.append(barcode).align(Alignment.CENTER);
        payload.appendEmptyLine();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = new Date(System.currentTimeMillis());
        payload.append(String.format("The time is %s", dateFormatter.format(date))).align(Alignment.CENTER);
        payload.appendEmptyLine();
        payload.appendEmptyLine();
        return payload;
    }
}
