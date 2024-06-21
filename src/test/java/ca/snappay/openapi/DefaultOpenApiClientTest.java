/*
 * Copyright 2021 SnapPay Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.snappay.openapi;

import ca.snappay.openapi.config.BasicConfigurationHolder;
import ca.snappay.openapi.constant.*;
import ca.snappay.openapi.request.ExtensionParameters;
import ca.snappay.openapi.request.card.ActivateCardRequest;
import ca.snappay.openapi.request.card.QueryCardRequest;
import ca.snappay.openapi.request.card.RefundCardRequest;
import ca.snappay.openapi.request.misc.QueryExchangeRateRequest;
import ca.snappay.openapi.request.order.QueryOrderRequest;
import ca.snappay.openapi.request.order.RefundOrderRequest;
import ca.snappay.openapi.request.order.RevokeOrderRequest;
import ca.snappay.openapi.request.pay.*;
import ca.snappay.openapi.response.card.ActivateCardResponse;
import ca.snappay.openapi.response.card.QueryCardResponse;
import ca.snappay.openapi.response.card.RefundCardResponse;
import ca.snappay.openapi.response.misc.QueryExchangeRateResponse;
import ca.snappay.openapi.response.order.QueryOrderResponse;
import ca.snappay.openapi.response.order.RefundOrderResponse;
import ca.snappay.openapi.response.order.RevokeOrderResponse;
import ca.snappay.openapi.response.pay.*;
import com.google.gson.JsonObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        BarCodePayRequest request = new BarCodePayRequest("" + System.nanoTime(), 0.01, "test barcode",
                "134732675710524877");
        JsonObject attach = new JsonObject();
        attach.addProperty("test", "value");
        request.setAttach(attach);

        BarCodePayResponse response = client.barCodePay(request);

        assertNotNull(response, "API request should be successful");
        assertEquals("E062004", response.getCode(), "Error code should be correct");
        assertEquals(0, response.getTotal().intValue());
        assertNotNull(response.getPsn(), "PSN should be given");
    }

    @Test
    public void testQRCodePay() throws OpenApiException {
        QRCodePayRequest request = new QRCodePayRequest(PaymentMethod.WECHATPAY, "" + System.nanoTime(), 0.01,
                "test qr code");

        QRCodePayResponse response = client.qrCodePay(request);

        assertNotNull(response, "API request should be successful");
        assertEquals("0", response.getCode(), "Code should be 0");
        assertEquals(1, response.getTotal().intValue(), "1 result should be returned");
        assertNotNull(response.getResult(), "Result should not be null");
        assertEquals(config.getMerchantNo(), response.getResult().getMerchantNo(), "Merchant number should match");
        assertEquals(TransactionStatus.USERPAYING, response.getResult().getTransactionStatus(), "Transaction status should be correct");
    }

    @Test
    public void testQRCodePay_UnionPayQR() throws OpenApiException {
        QRCodePayRequest request = new QRCodePayRequest(PaymentMethod.UNIONPAY_QR, "" + System.nanoTime(), 0.01,
                "test qr code");
        ExtensionParameters params = new ExtensionParameters();
        params.setStoreNo("80521386");
        params.setQrCodeWidth(250);
        params.setQrCodeHeight(250);
        request.setExtensionParameters(params);

        QRCodePayResponse response = client.qrCodePay(request);

        assertNotNull(response, "API request should be successful");
        assertEquals("0", response.getCode(), "Code should be 0");
        assertEquals(1, response.getTotal().intValue(), "1 result should be returned");
        assertNotNull(response.getResult(), "Result should not be null");
        assertEquals(config.getMerchantNo(), response.getResult().getMerchantNo(), "Merchant number should match");
        assertEquals(TransactionStatus.USERPAYING, response.getResult().getTransactionStatus(), "Transaction status should be correct");
    }

    @Test
    public void testH5Pay() throws OpenApiException {
        H5PayRequest request = new H5PayRequest(PaymentMethod.WECHATPAY, "" + System.nanoTime(), 0.01, "test h5");
        request.setTradeType(PaymentChannelTradeType.MWEB);

        H5PayResponse response = client.h5Pay(request);

        assertNotNull(response, "API request should be successful");
        assertEquals("0", response.getCode(), "Code should be 0");
        assertEquals(1, response.getTotal().intValue(), "1 result should be returned");
        assertNotNull(response.getResult(), "Result should not be null");
        assertEquals(config.getMerchantNo(), response.getResult().getMerchantNo(), "Merchant number should match");
        assertNotNull(response.getResult().getH5PaymentUrl(), "Payment URL should not be null");
    }

    @Test
    public void testNativePay() throws OpenApiException {
        NativePayRequest request = new NativePayRequest(PaymentMethod.WECHATPAY, "" + System.nanoTime(), 0.01,
                "test native");
        request.setReferUrl("https://www.snappay.ca");

        NativePayResponse response = client.nativePay(request);

        assertNotNull(response, "API request should be successful");
        assertEquals("E045048", response.getCode(), "Error code should be correct");
        assertEquals(0, response.getTotal().intValue());
        assertNotNull(response.getPsn(), "PSN should be given");
    }

    @Test
    public void testWebsitePay() throws OpenApiException {
        WebsitePayRequest request = new WebsitePayRequest(PaymentMethod.ALIPAY, "" + System.nanoTime(), 0.01,
                "test web");
        request.setBrowserType(BrowserType.PC);

        WebsitePayResponse response = client.websitePay(request);

        assertNotNull(response, "API request should be successful");
        assertEquals("0", response.getCode(), "Code should be 0");
        assertEquals(1, response.getTotal().intValue(), "1 result should be returned");
        assertNotNull(response.getResult(), "Result should not be null");
        assertEquals(config.getMerchantNo(), response.getResult().getMerchantNo(), "Merchant number should match");
        assertNotNull(response.getResult().getWebpayUrl(), "Payment URL should not be null");
    }

    @Test
    public void testOrderQuery() throws OpenApiException {
        String orderNo = "" + System.nanoTime();
        QRCodePayRequest request = new QRCodePayRequest(PaymentMethod.WECHATPAY, orderNo, 0.01, "test qr code");

        client.qrCodePay(request);

        QueryOrderRequest queryRequest = new QueryOrderRequest();
        queryRequest.setOrderNo(orderNo);

        QueryOrderResponse queryResponse = client.queryOrder(queryRequest);

        assertNotNull(queryResponse, "API request should be successful");
        assertEquals("0", queryResponse.getCode(), "Code should be 0");
        assertEquals(1, queryResponse.getTotal().intValue(), "1 result should be returned");
        assertNotNull(queryResponse.getResult(), "Result should not be null");
        assertEquals(config.getMerchantNo(), queryResponse.getResult().getMerchantNo(), "Merchant number should match");
        assertEquals(TransactionStatus.USERPAYING, queryResponse.getResult().getTransactionStatus(), "Transaction status should be correct");
    }

    @Test
    public void testOrderRevoke() throws OpenApiException {
        RevokeOrderRequest request = new RevokeOrderRequest(String.valueOf(System.nanoTime()));

        RevokeOrderResponse response = client.revokeOrder(request);

        assertNotNull(response, "API request should be successful");
        assertEquals("E045025", response.getCode(), "Error code should be correct");
        assertEquals(0, response.getTotal().intValue());
        assertNotNull(response.getPsn(), "PSN should be given");
    }

    @Test
    public void testOrderRefund() throws OpenApiException {
        String orderNo = String.valueOf(System.nanoTime());
        RefundOrderRequest request = new RefundOrderRequest("REF-" + orderNo, 0.01);
        request.setOrderNo(orderNo);

        RefundOrderResponse response = client.refundOrder(request);

        assertNotNull(response, "API request should be successful");
        assertEquals("E045043", response.getCode(), "Error code should be correct");
        assertEquals(0, response.getTotal().intValue());
        assertNotNull(response.getPsn(), "PSN should be given");
    }

    @Test
    public void testExchangeRateQuery() throws OpenApiException {
        QueryExchangeRateRequest request = new QueryExchangeRateRequest(Currency.CAD, PaymentMethod.WECHATPAY);

        QueryExchangeRateResponse response = client.queryExchangeRate(request);

        assertNotNull(response, "API request should be successful");
        assertEquals("0", response.getCode(), "Code should be 0");
        assertEquals(1, response.getTotal().intValue(), "1 result should be returned");
        assertNotNull(response.getResult(), "Result should not be null");
        assertNotNull(response.getResult().getExchangeRate(), "Exchange rate should be returned");
    }

    private void replaceCardApiClient() {
        config.setAppId("c9f9a75d50b35d33");
        config.setMerchantNo("901951466157");
        config.setGatewayHost("open.snappay.ca");
        config.setLanguage(Language.ENGLISH);
        config.setSignType(SignType.MD5);
        config.setPrivateKey("70a43a6ead6cbb72ac171df672e9383d");
        client = new DefaultOpenApiClient(config);
    }

    @Test
    public void testActivateCard() throws OpenApiException {
        replaceCardApiClient();

        ActivateCardRequest request = new ActivateCardRequest("8010820000002271");

        ActivateCardResponse response = client.activateCard(request);

        assertNotNull(response, "API request should be successful");
        assertEquals("ERR_CARD_001", response.getCode(), "Code should be ERR_CARD_001");
    }

    @Test
    public void testQueryCard() throws OpenApiException {
        replaceCardApiClient();

        QueryCardRequest request = new QueryCardRequest("8010820000002263");

        QueryCardResponse response = client.queryCard(request);

        assertNotNull(response, "API request should be successful");
        assertEquals("0", response.getCode(), "Code should be 0");
        assertEquals(200, response.getData().get(0).getBalance());
        assertEquals(200, response.getData().get(0).getFaceValue());
        assertEquals(CardStatus.ACTIVATED, response.getData().get(0).getCardStatus());
        assertEquals("0002601", response.getData().get(0).getCardType());
    }

    @Test
    public void testRefundCard() throws OpenApiException {
        replaceCardApiClient();

        RefundCardRequest request = new RefundCardRequest("8010820000002271");

        RefundCardResponse response = client.refundCard(request);

        assertNotNull(response, "API request should be successful");
        assertEquals("ERR_CARD_003", response.getCode(), "Code should be ERR_CARD_003");
    }

}
