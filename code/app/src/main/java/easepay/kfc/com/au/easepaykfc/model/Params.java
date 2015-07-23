package easepay.kfc.com.au.easepaykfc.model;

import java.util.HashMap;

/**
 * Created by Yun on 23/07/2015.
 */
public class Params {
    private HashMap<String,String> keyValues = new HashMap<String,String>();

    public void addParam(String name, double value) {
        keyValues.put(name, String.valueOf(value));
    }
    public void addParam(String name, String value) {
        keyValues.put(name, value);
    }

    @Override
    public String toString() {
        int count = 0;
        StringBuffer sb = new StringBuffer();
        for(String key : keyValues.keySet()){
            if (count++ != 0){
                sb.append("&");
            }
            sb.append(key).append("=").append(keyValues.get(key));
        }
        return sb.toString();
    }
}
