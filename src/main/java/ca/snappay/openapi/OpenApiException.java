package ca.snappay.openapi;

/**
 * Exception thrown if anything goes wrong with OpenAPI invocation.
 *
 * @author shawndu
 * @version 1.0
 */
public class OpenApiException extends Exception {

    private String errCode;
    private String errMsg;

    public OpenApiException() {
        super();
    }

    public OpenApiException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

}