package easepay.kfc.com.au.easepaykfc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import easepay.kfc.com.au.easepaykfc.easepay.kfc.com.au.easepaykfc.model.Product;


public class ProductActivity extends ActionBarActivity {

    Product[] products;

    ListView lvProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //load data and images from data base
        products = Product.getTestProducts();

        //get view handles
        lvProducts = (ListView)findViewById(R.id.lvProducts);
        ProductAdapter adapter = new ProductAdapter(this,products);
        lvProducts.setAdapter(adapter);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            View rowView = inflater.inflate(R.layout.product_row_layout, parent, false);
            Product product = products[position];

            TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
            textView.setText(product.getName());
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            UrlImageViewHelper.setUrlDrawable(imageView, product.getPicture());
//            imageView.setImageResource(R.drawable.kfc_test_product_1);
//            try {
//                URL url = new URL(product.getPicture());
//                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                imageView.setImageBitmap(bmp);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            //imageView.setImageDrawable(drawable);
            //UrlImageViewHelper.setUrlDrawable(imageView, product.getPicture());
            return rowView;
        }
    }
}
