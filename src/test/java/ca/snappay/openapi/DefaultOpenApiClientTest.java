package ca.snappay.openapi;

import ca.snappay.openapi.config.BasicConfigurationHolder;
import ca.snappay.openapi.constant.*;
import ca.snappay.openapi.request.misc.QueryExchangeRateRequest;
import ca.snappay.openapi.request.order.QueryOrderRequest;
import ca.snappay.openapi.request.order.RefundOrderRequest;
import ca.snappay.openapi.request.order.RevokeOrderRequest;
import ca.snappay.openapi.request.pay.*;
import ca.snappay.openapi.response.misc.QueryExchangeRateResponse;
import ca.snappay.openapi.response.order.QueryOrderResponse;
import ca.snappay.openapi.response.order.RefundOrderResponse;
import ca.snappay.openapi.response.order.RevokeOrderResponse;
import ca.snappay.openapi.response.pay.*;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultOpenApiClientTest {

    private BasicConfigurationHolder config = new BasicConfigurationHolder();
    private DefaultOpenApiClient client;

    @BeforeEach
    public void setup() {
        config.setAppId("9f00cd9a873c511e");
        config.setMerchantNo("901800000116");
        config.setGatewayHost("open.snappay.ca");
        config.setLanguage(Language.ENGLISH);
        config.setSignType(SignType.MD5);
        config.setPrivateKey("7e2083699dd510575faa1c72f9e35d43");
        client = new DefaultOpenApiClient(config);
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

    @Test
    public void testQRCodePay() throws OpenApiException {
        QRCodePayRequest request = new QRCodePayRequest();
        request.setPaymentMethod(PaymentMethod.WECHATPAY);
        request.setOrderNo("" + System.nanoTime());
        request.setAmount(0.01);
        request.setDescription("test qr code");

        QRCodePayResponse response = client.execute(request);

        Assertions.assertNotNull(response, "API request should be successful");
        Assertions.assertEquals("0", response.getCode(), "Code should be 0");
        Assertions.assertEquals(1, response.getTotal().intValue(), "1 result should be returned");
        Assertions.assertNotNull(response.getResult(), "Result should not be null");
        Assertions.assertEquals(config.getMerchantNo(), response.getResult().getMerchantNo(), "Merchant number should match");
        Assertions.assertEquals(TransactionStatus.USERPAYING, response.getResult().getTransactionStatus(), "Transaction status should be correct");
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
        Assertions.assertEquals(config.getMerchantNo(), response.getResult().getMerchantNo(), "Merchant number should match");
        Assertions.assertNotNull(response.getResult().getH5PaymentUrl(), "Payment URL should not be null");
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
        Assertions.assertEquals(config.getMerchantNo(), response.getResult().getMerchantNo(), "Merchant number should match");
        Assertions.assertNotNull(response.getResult().getWebpayUrl(), "Payment URL should not be null");
    }

    @Test
    public void testOrderQuery() throws OpenApiException {
        String orderNo = "" + System.nanoTime();
        QRCodePayRequest request = new QRCodePayRequest();
        request.setPaymentMethod(PaymentMethod.WECHATPAY);
        request.setOrderNo(orderNo);
        request.setAmount(0.01);
        request.setDescription("test qr code");

        client.execute(request);

        QueryOrderRequest queryRequest = new QueryOrderRequest();
        queryRequest.setOrderNo(orderNo);

        QueryOrderResponse queryResponse = client.execute(queryRequest);

        Assertions.assertNotNull(queryResponse, "API request should be successful");
        Assertions.assertEquals("0", queryResponse.getCode(), "Code should be 0");
        Assertions.assertEquals(1, queryResponse.getTotal().intValue(), "1 result should be returned");
        Assertions.assertNotNull(queryResponse.getResult(), "Result should not be null");
        Assertions.assertEquals(config.getMerchantNo(), queryResponse.getResult().getMerchantNo(), "Merchant number should match");
        Assertions.assertEquals(TransactionStatus.USERPAYING, queryResponse.getResult().getTransactionStatus(), "Transaction status should be correct");
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
        Assertions.assertNotNull(response.getResult().getExchangeRate(), "Exchange rate should be returned");
    }

}
