package ca.snappay.openapi;

import ca.snappay.openapi.config.BasicConfigurationHolder;
import ca.snappay.openapi.constant.Language;
import ca.snappay.openapi.constant.SignType;
import ca.snappay.openapi.request.pay.BarCodePayRequest;
import ca.snappay.openapi.response.pay.BarCodePayResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultOpenApiClientTest {

    private DefaultOpenApiClient client;

    @BeforeEach
    public void setup() {
        BasicConfigurationHolder config = new BasicConfigurationHolder();
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

        BarCodePayResponse response = client.execute(request);
    }

}
