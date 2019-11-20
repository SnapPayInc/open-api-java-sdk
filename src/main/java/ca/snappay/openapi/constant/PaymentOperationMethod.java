package ca.snappay.openapi.constant;

/**
 * The payment operation method.
 *
 * @author shawndu
 * @version 1.0
 */
public enum PaymentOperationMethod {

    QR_CODE_PAY,

    BAR_CODE_PAY,

    H5_PAY,

    NATIVE_APP_PAY,

    WEB_PAY;

    /**
     * Gets a payment operation method from the given code.
     *
     * @param code the code.
     * @return the corresponding payment operation method.
     */
    public static PaymentOperationMethod getFromCode(int code) {
        switch (code) {
            case 4:
                return QR_CODE_PAY;
            case 5:
                return BAR_CODE_PAY;
            case 6:
                return H5_PAY;
            case 8:
                return NATIVE_APP_PAY;
            case 9:
                return WEB_PAY;
            default:
                return null;
        }
    }

}
