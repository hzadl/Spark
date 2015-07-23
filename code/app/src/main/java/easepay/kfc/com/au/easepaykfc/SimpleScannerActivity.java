package easepay.kfc.com.au.easepaykfc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Yun on 23/07/2015.
 */
public class SimpleScannerActivity extends BaseActivity implements ZXingScannerView.ResultHandler {
    public static final String SCAN_RESULT = "scanResult";
    public static final String FORMAT = "format";
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v("", rawResult.getText()); // Prints scan results
        Log.v("", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
//        Toast.makeText(this, "BarcodeFormat: " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_LONG).show();
//        Toast.makeText(this, "Text: " + rawResult.getText().toString(), Toast.LENGTH_LONG).show();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(SCAN_RESULT,rawResult.getText());

        resultIntent.putExtra(FORMAT,rawResult.getBarcodeFormat().toString());
        setResult(Activity.RESULT_OK,resultIntent);
        finish();
    }
}
