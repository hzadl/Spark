package easepay.kfc.com.au.easepaykfc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aevi.helpers.services.AeviServiceConnectionCallback;
import com.aevi.payment.PaymentRequest;
import com.aevi.payment.TransactionResult;
import com.aevi.payment.TransactionStatus;
import com.aevi.printing.PrintService;
import com.aevi.printing.PrintServiceProvider;
import com.aevi.printing.model.Alignment;
import com.aevi.printing.model.FontStyle;
import com.aevi.printing.model.PrintPayload;
import com.aevi.printing.model.Underline;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;


public class ShowOrderDetailActivity extends ActionBarActivity {

    // create a new ServiceProvider instance and pass the Android context
    private PrintServiceProvider serviceProvider = new PrintServiceProvider(this);
    private PrintService printService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order_detail2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_order_detail, menu);
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

    public void onPayButtonClick(View view) {
        // Construct a new payment for $20.00.
        PaymentRequest payment = new PaymentRequest(new BigDecimal("20.00"));
        payment.setCurrency(Currency.getInstance("AUD"));

        // Launch the Payment app.
        //0 for paybutton
        startActivityForResult(payment.createIntent(), 0);
    }

    //if Cancel clicked
    public void onCancelButtonClick(View view) {
        // Construct a new payment for $20.00.

        // Launch the Payment app.
        //1 for Cancelbutton,transfer to main view
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0) {
            // Obtain the transaction result from the returned data.
            TransactionResult result = TransactionResult.fromIntent(data);
            // Use a toast to show the transaction result.
            Toast.makeText(this, "Transaction result: " + result.getTransactionStatus(), Toast.LENGTH_LONG).show();
            if(result.getTransactionStatus()== TransactionStatus.APPROVED) {
                //pay successful, transfer to wait view and print
                Intent intent=new Intent(this,wait.class);
                startActivity(intent);
                serviceProvider.connect(new AeviServiceConnectionCallback<PrintService>() {
                    @Override
                    public void onConnect(PrintService service) {

                        if (service == null) {
                            // Print service failed to open, please check the ADB log file for details.

                            return;
                        }

                        // service connected, have fun
                        printService = service;
                    }
                });
                // construct the printer payload
                PrintPayload printPayload = new PrintPayload();

// first line, hello world
                //printPayload.append("Hello world!").align(Alignment.Center);

// second line, the current date
                printPayload.append("KFC Adelaide").align(Alignment.CENTER);


                printPayload.append("Order detail: price").align(Alignment.CENTER);
                //printPayload.append("Emphasized").fontStyle(FontStyle.EMPHASIZED);
                //printPayload.append("Inverted").fontStyle(FontStyle.INVERTED);
                printPayload.appendEmptyLine();
                printPayload.append("*******************").underline(Underline.SINGLE).align(Alignment.CENTER);;
                printPayload.append("Total: Price").fontStyle(FontStyle.INVERTED_EMPHASIZED).align(Alignment.CENTER);;

               // printPayload.append("Double Underlined").underline(Underline.DOUBLE);

// send the request to the print service


                SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                Date date = new Date(System.currentTimeMillis());
                printPayload.append(String.format("The time is %s", dateFormatter.format(date))).align(Alignment.CENTER);
                Bitmap preview = this.printService.preview(printPayload, this.printService.getDefaultPrinterSettings());

// print the payload
                //printService.print(printPayload);

            }
            else{
                //unsuccessful, come back to showOrderDetailView
            }
        }
    }


}
