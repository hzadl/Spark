package easepay.kfc.com.au.easepaykfc.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import easepay.kfc.com.au.easepaykfc.model.ApiException;
import easepay.kfc.com.au.easepaykfc.model.Product;

/**
 * Created by Yun on 22/07/2015.
 */
public class ModelUtil {

    private static final String baseUrl = "http://10.201.38.138/public/rest?";
    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String convertToString(Set<Long> values) {
        if (values == null || values.size() == 0){
            return "";
        }else{
            StringBuffer sb = new StringBuffer();
            Long[] array = (Long[]) values.toArray();
            for (int i = 0; i < array.length; i++) {
                Long value = array[i];
                sb.append(value);
                if (i != array.length - 1){
                   sb.append(",");
                }
            }
            return sb.toString();
        }
    }

    public static String executeJson(String methodName, String paramStr) {
        StringBuffer sb = new StringBuffer();
        sb.append(baseUrl).append(methodName);
        if (paramStr != null && paramStr.length() != 0){
            sb.append(methodName).append("&").append(paramStr);
        }
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(sb.toString());
        HttpResponse response;
        String str = null;

        try {
            response = client.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null){
                InputStream instream = entity.getContent();
                str = ModelUtil.convertStreamToString(instream);
                return str;
            }
        } catch (IOException e) {
            throw new ApiException("Fail to get json string");
        }

        return null;
    }
}
