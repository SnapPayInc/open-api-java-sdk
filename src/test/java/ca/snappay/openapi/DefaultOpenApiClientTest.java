package ca.snappay.openapi;

import ca.snappay.openapi.config.BasicConfigurationHolder;
import ca.snappay.openapi.constant.Language;
import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.constant.SignType;
import ca.snappay.openapi.constant.TransactionStatus;
import ca.snappay.openapi.request.pay.BarCodePayRequest;
import ca.snappay.openapi.request.pay.QRCodePayRequest;
import ca.snappay.openapi.response.pay.BarCodePayResponse;
import ca.snappay.openapi.response.pay.QRCodePayResponse;
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

}
