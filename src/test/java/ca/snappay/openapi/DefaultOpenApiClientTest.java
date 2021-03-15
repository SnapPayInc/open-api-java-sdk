package ca.snappay.openapi;

import ca.snappay.openapi.config.BasicConfigurationHolder;
import ca.snappay.openapi.constant.BrowserType;
import ca.snappay.openapi.constant.Constants;
import ca.snappay.openapi.constant.Currency;
import ca.snappay.openapi.constant.Language;
import ca.snappay.openapi.constant.PaymentChannelTradeType;
import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.constant.SignType;
import ca.snappay.openapi.constant.TransactionStatus;
import ca.snappay.openapi.request.ExtensionParameters;
import ca.snappay.openapi.request.misc.QueryExchangeRateRequest;
import ca.snappay.openapi.request.order.QueryOrderRequest;
import ca.snappay.openapi.request.order.RefundOrderRequest;
import ca.snappay.openapi.request.order.RevokeOrderRequest;
import ca.snappay.openapi.request.pay.BarCodePayRequest;
import ca.snappay.openapi.request.pay.H5PayRequest;
import ca.snappay.openapi.request.pay.NativePayRequest;
import ca.snappay.openapi.request.pay.QRCodePayRequest;
import ca.snappay.openapi.request.pay.UnitedPayRequest;
import ca.snappay.openapi.request.pay.WebsitePayRequest;
import ca.snappay.openapi.response.misc.QueryExchangeRateResponse;
import ca.snappay.openapi.response.order.QueryOrderResponse;
import ca.snappay.openapi.response.order.RefundOrderResponse;
import ca.snappay.openapi.response.order.RevokeOrderResponse;
import ca.snappay.openapi.response.pay.BarCodePayResponse;
import ca.snappay.openapi.response.pay.H5PayResponse;
import ca.snappay.openapi.response.pay.NativePayResponse;
import ca.snappay.openapi.response.pay.QRCodePayResponse;
import ca.snappay.openapi.response.pay.UnitedPayResponse;
import ca.snappay.openapi.response.pay.WebsitePayResponse;
import ca.snappay.openapi.sign.MD5;
import ca.snappay.openapi.sign.SignHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultOpenApiClientTest {

  private BasicConfigurationHolder config = new BasicConfigurationHolder();
  private DefaultOpenApiClient client;

  @BeforeEach
  public void setup() {
    config4J();
    config.setGatewayHost("open.dev.snappay.ca");
//    config.setGatewayHost("open.snappay.ca");
    config.setLanguage(Language.ENGLISH);
    config.setSignType(SignType.MD5);
    client = new DefaultOpenApiClient(config);
  }

  private void config4J() {
    config.setAppId("813912b87893e2a9");
    config.setMerchantNo("902100000694");
    config.setPrivateKey("555c45e3a5bef9e256e15529e98d44ae");
//    config.setAppId("ff781eca08e0f6cf");
//    config.setMerchantNo("902000001433");
//    config.setPrivateKey("abec813b53889d50bd5d67aeecd860aa");
  }

  @Test
  public void testBarCodePay() throws OpenApiException {
    BarCodePayRequest request = new BarCodePayRequest();
    request.setPaymentMethod(PaymentMethod.WECHATPAY);
    request.setOrderNo("" + System.nanoTime());
    request.setAmount(0.01);
    request.setAuthCode("134732675710524877");
    request.setDescription("test barcode");
    JsonObject attach = new JsonObject();
    attach.addProperty("test", "value");
    request.setAttach(attach);

    BarCodePayResponse response = client.execute(request);

    Assertions.assertNotNull(response, "API request should be successful");
    Assertions.assertEquals("E062004", response.getCode(), "Error code should be correct");
    Assertions.assertEquals(0, response.getTotal().intValue());
    Assertions.assertNotNull(response.getPsn(), "PSN should be given");
  }

  /** */
  @Test
  public void testBarCodePaySnapLii() throws OpenApiException {
    /*
     * AuthCode & account balance For test
     * 888014893983999512    250
     * 888014893983999513    2000
     * 888014893983999514    100
     */
    BarCodePayRequest request = new BarCodePayRequest();
    request.setPaymentMethod(PaymentMethod.SNAPLII);
    request.setOrderNo("" + System.nanoTime());
    request.setAmount(0.01);
    request.setAuthCode("888014893983999512");
    request.setDescription("test murong barcode");
    JsonObject attach = new JsonObject();
    attach.addProperty("test", "value");
    request.setAttach(attach);

    BarCodePayResponse response = client.execute(request);
    System.out.println(response.toString());
    Assertions.assertNotNull(response, "API request should be successful");
    Assertions.assertEquals("E062004", response.getCode(), "Error code should be correct");
    Assertions.assertEquals(0, response.getTotal().intValue());
    Assertions.assertNotNull(response.getPsn(), "PSN should be given");
  }

  @Test
  public void testWeChatPayBodyLength(){

    String body ="Store: sichuanimpressions -- Customer: Carrie Xiang  | xiangfei0525@hotmail.com | 9497356265 -- Order ID: 1209 -- Total: USD 245";
    System.out.println(body.getBytes(StandardCharsets.UTF_8).length);
  }

  @Test
  public void testSign(){
    String linkString = "app_id=db06a5c8231dc20b&c_trans_fee=0.0000&charset=UTF-8&customer_paid_amount=0.0100&discount_bmopc=0.0000&discount_bpc=0.0000&exchange_rate=0&format=JSON&merchant_no=902000105286&out_order_no=202101262305412551&pay_code=snappay&pay_user_account_id=2088202288456805&pay_user_account_name=wan***@163.com&payment_method=ALIPAY&timestamp=2021-01-26 15:06:15&trans_amount=0.0100&trans_currency=CAD&trans_end_time=2021-01-26 15:06:13&trans_no=TA120001052210126000002&trans_status=SUCCESS&version=1.0";
    String signatureKey = "6a6e9dd6542661b7fb76703ade0a7946";
    System.out.println(MD5.md5sign(linkString, signatureKey, Constants.CHARSET_UTF8));
  }

  @Test
  public void testMD5SignByJson(){
    String json = "{\"trans_no\":\"TA120001052210126000002\",\"charset\":\"UTF-8\",\"merchant_no\":\"902000105286\",\"trans_end_time\":\"2021-01-26 15:06:13\",\"exchange_rate\":\"0\",\"pay_user_account_id\":\"2088202288456805\",\"format\":\"JSON\",\"sign\":\"7bf78f06dd1af7c9e0a0a2435e6a1da4\",\"out_order_no\":\"202101262305412551\",\"pay_user_account_name\":\"wan***@163.com\",\"trans_amount\":\"0.0100\",\"version\":\"1.0\",\"customer_paid_amount\":\"0.0100\",\"discount_bpc\":\"0.0000\",\"trans_currency\":\"CAD\",\"app_id\":\"db06a5c8231dc20b\",\"sign_type\":\"MD5\",\"trans_status\":\"SUCCESS\",\"payment_method\":\"ALIPAY\",\"c_trans_fee\":\"0.0000\",\"timestamp\":\"2021-01-26 15:06:15\",\"discount_bmopc\":\"0.0000\"}";
    JsonObject resultJson = JsonParser.parseString(json).getAsJsonObject();
    String signatureKey = "6a6e9dd6542661b7fb76703ade0a7946";
    String prestr = SignHandler.createLinkString(resultJson);

    String mysign = MD5.md5sign(prestr, signatureKey, Constants.CHARSET_UTF8);
    System.out.println(mysign);
  }

  @Test
  public void testQRCodePay() throws OpenApiException {
    QRCodePayRequest request = new QRCodePayRequest();
    request.setPaymentMethod(PaymentMethod.WECHATPAY);
    request.setOrderNo("" + System.nanoTime());
    request.setAmount(0.01);
    request.setDescription("this is a description.");
    ExtensionParameters extensionParameters = new ExtensionParameters();

    extensionParameters.setQrcodePicSize("240,240");
//    extensionParameters.setStoreNo("80002204");
    request.setExtensionParameters(extensionParameters);

    QRCodePayResponse response = client.execute(request);

    Assertions.assertNotNull(response, "API request should be successful");
    Assertions.assertEquals("0", response.getCode(), "Code should be 0");
    Assertions.assertEquals(1, response.getTotal().intValue(), "1 result should be returned");
    Assertions.assertNotNull(response.getResult(), "Result should not be null");
    Assertions.assertEquals(
        config.getMerchantNo(),
        response.getResult().getMerchantNo(),
        "Merchant number should match");
    Assertions.assertEquals(
        TransactionStatus.USERPAYING,
        response.getResult().getTransactionStatus(),
        "Transaction status should be correct");
  }

  @Test
  public void testH5Pay() throws OpenApiException {
    H5PayRequest request = new H5PayRequest();
    request.setPaymentMethod(PaymentMethod.WECHATPAY);
    request.setTradeType(PaymentChannelTradeType.MWEB);
    request.setOrderNo("" + System.nanoTime());
    request.setAmount(0.01);
    request.setDescription("test h5");

    H5PayResponse response = client.execute(request);

    Assertions.assertNotNull(response, "API request should be successful");
    Assertions.assertEquals("0", response.getCode(), "Code should be 0");
    Assertions.assertEquals(1, response.getTotal().intValue(), "1 result should be returned");
    Assertions.assertNotNull(response.getResult(), "Result should not be null");
    Assertions.assertEquals(
        config.getMerchantNo(),
        response.getResult().getMerchantNo(),
        "Merchant number should match");
    Assertions.assertNotNull(
        response.getResult().getH5PaymentUrl(), "Payment URL should not be null");
  }

  @Test
  public void testNativePay() throws OpenApiException {
    NativePayRequest request = new NativePayRequest();
    request.setPaymentMethod(PaymentMethod.WECHATPAY);
    request.setOrderNo("" + System.nanoTime());
    request.setAmount(0.01);
    request.setDescription("test native");
    request.setReferUrl("https://www.snappay.ca");

    NativePayResponse response = client.execute(request);

    Assertions.assertNotNull(response, "API request should be successful");
    Assertions.assertEquals("E082003", response.getCode(), "Error code should be correct");
    Assertions.assertEquals(0, response.getTotal().intValue());
    Assertions.assertNotNull(response.getPsn(), "PSN should be given");
  }

  @Test
  public void testWebsitePay() throws OpenApiException {
    WebsitePayRequest request = new WebsitePayRequest();
    request.setPaymentMethod(PaymentMethod.ALIPAY);
    request.setBrowserType(BrowserType.PC);
    request.setOrderNo("" + System.nanoTime());
    request.setAmount(0.01);
    request.setDescription("test web");

    WebsitePayResponse response = client.execute(request);

    Assertions.assertNotNull(response, "API request should be successful");
    Assertions.assertEquals("0", response.getCode(), "Code should be 0");
    Assertions.assertEquals(1, response.getTotal().intValue(), "1 result should be returned");
    Assertions.assertNotNull(response.getResult(), "Result should not be null");
    Assertions.assertEquals(
        config.getMerchantNo(),
        response.getResult().getMerchantNo(),
        "Merchant number should match");
    Assertions.assertNotNull(response.getResult().getWebpayUrl(), "Payment URL should not be null");
  }

  @Test
  public void testUnitedPay() throws OpenApiException {
    UnitedPayRequest request = new UnitedPayRequest();
    request.setOrderNo("" + System.nanoTime());
    request.setAmount(0.01);
    request.setDescription("test unitedpay");
    request.setEffectiveMinutes(60);
    request.setReturnUrl("Missing required field returnUrl");

    UnitedPayResponse response = client.execute(request);

    Assertions.assertNotNull(response, "API request should be successful");
    Assertions.assertEquals("0", response.getCode(), "Code should be 0");
    Assertions.assertEquals(1, response.getTotal().intValue(), "1 result should be returned");
    Assertions.assertNotNull(response.getResult(), "Result should not be null");
    Assertions.assertEquals(
        config.getMerchantNo(),
        response.getResult().getMerchantNo(),
        "Merchant number should match");
    Assertions.assertNotNull(response.getResult().getRedirectUrl(), "Payment URL should not be null");
    System.out.println(response.getResult().getRedirectUrl());
  }

  @Test
  public void testOrderQuery() throws OpenApiException {
//    String orderNo = "" + System.nanoTime();
//    QRCodePayRequest request = new QRCodePayRequest();
//    request.setPaymentMethod(PaymentMethod.WECHATPAY);
//    request.setOrderNo(orderNo);
//    request.setAmount(0.01);
//    request.setDescription("test qr code");
//
//    client.execute(request);

    QueryOrderRequest queryRequest = new QueryOrderRequest();
    queryRequest.setOrderNo("10028120210118");
    //20210118 21:29:31
    queryRequest.setTimestamp(LocalDateTime.of(2021, Month.JANUARY, 18, 21, 29, 31));

    QueryOrderResponse queryResponse = client.execute(queryRequest);

    Assertions.assertNotNull(queryResponse, "API request should be successful");
    Assertions.assertEquals("0", queryResponse.getCode(), "Code should be 0");
    Assertions.assertEquals(1, queryResponse.getTotal().intValue(), "1 result should be returned");
    Assertions.assertNotNull(queryResponse.getResult(), "Result should not be null");
    Assertions.assertEquals(
        config.getMerchantNo(),
        queryResponse.getResult().getMerchantNo(),
        "Merchant number should match");
    Assertions.assertEquals(
        TransactionStatus.USERPAYING,
        queryResponse.getResult().getTransactionStatus(),
        "Transaction status should be correct");
  }

  @Test
  public void testOrderRevoke() throws OpenApiException {
    RevokeOrderRequest request = new RevokeOrderRequest();
    request.setOrderNo("" + System.nanoTime());

    RevokeOrderResponse response = client.execute(request);

    Assertions.assertNotNull(response, "API request should be successful");
    Assertions.assertEquals("E045025", response.getCode(), "Error code should be correct");
    Assertions.assertEquals(0, response.getTotal().intValue());
    Assertions.assertNotNull(response.getPsn(), "PSN should be given");
  }

  @Test
  public void testOrderRefund() throws OpenApiException {
    RefundOrderRequest request = new RefundOrderRequest();
    request.setOrderNo("" + System.nanoTime());
    request.setRefundOrderNo("REF-" + request.getOrderNo());
    request.setRefundAmount(0.01);

    RefundOrderResponse response = client.execute(request);

    Assertions.assertNotNull(response, "API request should be successful");
    Assertions.assertEquals("E045043", response.getCode(), "Error code should be correct");
    Assertions.assertEquals(0, response.getTotal().intValue());
    Assertions.assertNotNull(response.getPsn(), "PSN should be given");
  }

  @Test
  public void testExchangeRateQuery() throws OpenApiException {
    QueryExchangeRateRequest request = new QueryExchangeRateRequest();
    request.setCurrency(Currency.CAD);
    request.setPaymentMethod(PaymentMethod.WECHATPAY);

    QueryExchangeRateResponse response = client.execute(request);

    Assertions.assertNotNull(response, "API request should be successful");
    Assertions.assertEquals("0", response.getCode(), "Code should be 0");
    Assertions.assertEquals(1, response.getTotal().intValue(), "1 result should be returned");
    Assertions.assertNotNull(response.getResult(), "Result should not be null");
    Assertions.assertNotNull(
        response.getResult().getExchangeRate(), "Exchange rate should be returned");
  }
}
