package easepay.kfc.com.au.easepaykfc.model;

/**
 * Created by Yun on 22/07/2015.
 */
public class ApiException extends RuntimeException{
    public ApiException(String detailMessage) {
        super(detailMessage);
    }
}
