package ca.snappay.openapi;

import ca.snappay.openapi.request.misc.QueryExchangeRateRequest;
import ca.snappay.openapi.request.order.QueryOrderRequest;
import ca.snappay.openapi.request.order.RefundOrderRequest;
import ca.snappay.openapi.request.order.RevokeOrderRequest;
import ca.snappay.openapi.request.pay.BarCodePayRequest;
import ca.snappay.openapi.request.pay.H5PayRequest;
import ca.snappay.openapi.request.pay.MiniPayRequest;
import ca.snappay.openapi.request.pay.NativePayRequest;
import ca.snappay.openapi.request.pay.QRCodePayRequest;
import ca.snappay.openapi.request.pay.WebsitePayRequest;
import ca.snappay.openapi.response.misc.QueryExchangeRateResponse;
import ca.snappay.openapi.response.order.QueryOrderResponse;
import ca.snappay.openapi.response.order.RefundOrderResponse;
import ca.snappay.openapi.response.order.RevokeOrderResponse;
import ca.snappay.openapi.response.pay.BarCodePayResponse;
import ca.snappay.openapi.response.pay.H5PayResponse;
import ca.snappay.openapi.response.pay.MiniPayResponse;
import ca.snappay.openapi.response.pay.NativePayResponse;
import ca.snappay.openapi.response.pay.QRCodePayResponse;
import ca.snappay.openapi.response.pay.WebsitePayResponse;

/**
 * The OpenAPI client.
 *
 * @author shawndu
 * @version 1.0
 */
public interface OpenApiClient {

    /**
     * Make bar code payment.
     *
     * @param request the request.
     * @return the response.
     * @throws OpenApiException if any error occurred.
     * @see <a href="https://developer.snappay.ca/openapi.html#pay-apis-barcode-pay-api-post">
     *      API Documentation</a>
     */
    BarCodePayResponse barCodePay(BarCodePayRequest request) throws OpenApiException;

    /**
     * Make QR code payment.
     *
     * @param request the request.
     * @return the response.
     * @throws OpenApiException if any error occurred.
     * @see <a href="https://developer.snappay.ca/openapi.html#pay-apis-transaction-qr-code-pay-api-post">
     *      API Documentation</a>
     */
    QRCodePayResponse qrCodePay(QRCodePayRequest request) throws OpenApiException;

    /**
     * Make H5 payment.
     *
     * @param request the request.
     * @return the response.
     * @throws OpenApiException if any error occurred.
     * @see <a href="https://developer.snappay.ca/openapi.html#pay-apis-h5-pay-api-post">
     *      API Documentation</a>
     */
    H5PayResponse h5Pay(H5PayRequest request) throws OpenApiException;

    /**
     * Make native app payment.
     *
     * @param request the request.
     * @return the response.
     * @throws OpenApiException if any error occurred.
     * @see <a href="https://developer.snappay.ca/openapi.html#pay-apis-native-app-pay-api-post">
     *      API Documentation</a>
     */
    NativePayResponse nativePay(NativePayRequest request) throws OpenApiException;

    /**
     * Make website payment.
     *
     * @param request the request.
     * @return the response.
     * @throws OpenApiException if any error occurred.
     * @see <a href="https://developer.snappay.ca/openapi.html#pay-apis-website-pay-api-post">
     *      API Documentation</a>
     */
    WebsitePayResponse websitePay(WebsitePayRequest request) throws OpenApiException;

    /**
     * Make mini payment.
     *
     * @param request the request.
     * @return the response.
     * @throws OpenApiException if any error occurred.
     * @see <a href="https://developer.snappay.ca/openapi.html#pay-apis-mini-program-api-post">
     *      API Documentation</a>
     */
    MiniPayResponse miniPay(MiniPayRequest request) throws OpenApiException;

    /**
     * Query order status.
     *
     * @param request the request.
     * @return the response.
     * @throws OpenApiException if any error occurred.
     * @see <a href="https://developer.snappay.ca/openapi.html#order-apis-query-order-api-post">
     *      API Documentation</a>
     */
    QueryOrderResponse queryOrder(QueryOrderRequest request) throws OpenApiException;

    /**
     * Query order status.
     *
     * @param request the request.
     * @return the response.
     * @throws OpenApiException if any error occurred.
     * @see <a href="https://developer.snappay.ca/openapi.html#order-apis-revoke-order-api-post">
     *      API Documentation</a>
     */
    RevokeOrderResponse revokeOrder(RevokeOrderRequest request) throws OpenApiException;

    /**
     * Query refund status.
     *
     * @param request the request.
     * @return the response.
     * @throws OpenApiException if any error occurred.
     * @see <a href="https://developer.snappay.ca/openapi.html#order-apis-refund-api-post">
     *      API Documentation</a>
     */
    RefundOrderResponse refundOrder(RefundOrderRequest request) throws OpenApiException;

    /**
     * Query exchange rate.
     *
     * @param request the request.
     * @return the response.
     * @throws OpenApiException if any error occurred.
     * @see <a href="https://developer.snappay.ca/openapi.html#order-apis-query-exchange-rate-post">
     *      API Documentation</a>
     */
    QueryExchangeRateResponse queryExchangeRate(QueryExchangeRateRequest request) throws OpenApiException;

}
