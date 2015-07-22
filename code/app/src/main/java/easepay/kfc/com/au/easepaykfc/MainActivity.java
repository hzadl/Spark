package easepay.kfc.com.au.easepaykfc;

import android.content.Intent;
<<<<<<< Updated upstream
=======
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
>>>>>>> Stashed changes
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void collectBtnClicked(View view){
        System.out.println("collect clicked");
        Intent i = new Intent(this, inputOrderNumberActivity.class);
        startActivity(i);
    }

    public void newBtnClicked(View view){
        System.out.println("new clicked");
        Intent i = new Intent(this, ShowOrderDetailActivity.class);
        startActivity(i);
    }

}
