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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ca.snappay.openapi.config.BasicConfigurationHolder;
import ca.snappay.openapi.constant.Constants;
import ca.snappay.openapi.constant.Currency;
import ca.snappay.openapi.constant.Language;
import ca.snappay.openapi.constant.PaymentMethod;
import ca.snappay.openapi.constant.PaymentOperationMethod;
import ca.snappay.openapi.constant.SignType;
import ca.snappay.openapi.constant.TransactionStatus;
import ca.snappay.openapi.request.ExtensionParameters;
import ca.snappay.openapi.request.order.RefundOrderRequest;
import ca.snappay.openapi.request.pay.BarCodePayRequest;
import ca.snappay.openapi.response.order.RefundOrderResponse;
import ca.snappay.openapi.response.order.RefundOrderResponseData;
import ca.snappay.openapi.response.pay.BarCodePayResponse;
import ca.snappay.openapi.response.pay.BarCodePayResponseData;

@ExtendWith(MockitoExtension.class)
public class DefaultOpenApiClientMockTest {

    @Mock
    private HttpClientBuilder builder;
    @Mock
    private CloseableHttpClient httpClient;
    @Mock
    private CloseableHttpResponse httpResponse;
    @Mock
    private StatusLine statusLine;

    @Captor
    private ArgumentCaptor<HttpPost> postCaptor;

    private MockedStatic<HttpClients> httpClientsMock;

    private BasicConfigurationHolder config = new BasicConfigurationHolder();
    private DefaultOpenApiClient client;

    @BeforeEach
    public void setup() {
        httpClientsMock = Mockito.mockStatic(HttpClients.class);
        when(HttpClients.custom()).thenReturn(builder);
        when(builder.setDefaultRequestConfig(any())).thenReturn(builder);
        when(builder.build()).thenReturn(httpClient);

        config.setAppId("9f00cd9a873c511e");
        config.setMerchantNo("901800000116");
        config.setGatewayHost("open.snappay.ca");
        config.setLanguage(Language.ENGLISH);
        config.setSignType(SignType.MD5);
        config.setPrivateKey("7e2083699dd510575faa1c72f9e35d43");
        client = new DefaultOpenApiClient(config);
    }

    @AfterEach
    public void teardown() {
        httpClientsMock.close();
    }
    
    @Test
    public void testBarCodePay_FailRequest() throws ClientProtocolException, IOException {
        when(httpClient.execute(any())).thenReturn(httpResponse);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(500);

        BarCodePayRequest request = new BarCodePayRequest("test1", 123.45, "desc", "1234567890");
        try {
            client.barCodePay(request);
        } catch (OpenApiException e) {
            assertEquals("-102", e.getErrCode());
            return;
        }
        fail("request should fail");
    }

    @Test
    public void testBarCodePay_FailPayment() throws OpenApiException, ClientProtocolException, IOException {
        HttpEntity entity = new StringEntity("{\"code\":\"E000001\",\"msg\":\"payment failed\",\"psn\":\"12345\",\"sign\":\"8888fa81407c46edf9cab10e1df4bba8\",\"data\":[]}", ContentType.APPLICATION_JSON);
        when(httpClient.execute(any())).thenReturn(httpResponse);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(200);
        when(httpResponse.getEntity()).thenReturn(entity);

        BarCodePayRequest request = new BarCodePayRequest("test1", 123.45, "desc", "1234567890");
        ExtensionParameters extensionParams = new ExtensionParameters();
        extensionParams.setStoreNo("800123");
        request.setExtensionParameters(extensionParams);
        BarCodePayResponse response = client.barCodePay(request);

        Mockito.verify(httpClient).execute(postCaptor.capture());
        HttpPost post = postCaptor.getValue();
        String postStr = EntityUtils.toString(post.getEntity(), Constants.CHARSET_UTF8);
        JsonObject postJson = JsonParser.parseString(postStr).getAsJsonObject();

        verifyBasicFields(postJson, "pay.barcodepay");
        assertEquals("test1", postJson.get("out_order_no").getAsString());
        assertEquals(123.45, postJson.get("trans_amount").getAsDouble());
        assertEquals("desc", postJson.get("description").getAsString());
        assertEquals("{\"store_no\":\"800123\"}", postJson.get("extension_parameters").getAsString());

        assertEquals("E000001", response.getCode());
        assertEquals("payment failed", response.getMessage());
        assertEquals("12345", response.getPsn());
        assertEquals("8888fa81407c46edf9cab10e1df4bba8", response.getSign());
        assertEquals(0, response.getData().size());
    }

    @Test
    public void testBarCodePay_Success() throws OpenApiException, ClientProtocolException, IOException {
        String data = "{\"trans_no\":\"TRANS12345\","
            + "\"out_order_no\":\"test1\","
            + "\"merchant_no\":\"" + config.getMerchantNo() + "\","
            + "\"trans_status\":\"SUCCESS\","
            + "\"payment_method\":\"ALIPAY\","
            + "\"pay_operation_method\":5,"
            + "\"pay_user_account_id\":\"2088101117955611\","
            + "\"pay_user_account_name\":\"15900000000\","
            + "\"trans_currency\":\"CAD\","
            + "\"exchange_rate\":5.21,"
            + "\"trans_amount\":123.45,"
            + "\"c_trans_fee\":2.5,"
            + "\"customer_paid_amount\":524.09,"
            + "\"discount_bmopc\":0.0,"
            + "\"discount_bpc\":0.0,"
            + "\"trans_end_time\":\"2021-03-02 15:16:55\"}";
        HttpEntity entity = new StringEntity("{\"code\":\"0\",\"msg\":\"payment successful\",\"total\":1,\"psn\":\"12345\",\"sign\":\"5259982d84faab397e758e82b0b4324e\",\"data\":[" + data + "]}", ContentType.APPLICATION_JSON);
        when(httpClient.execute(any())).thenReturn(httpResponse);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(200);
        when(httpResponse.getEntity()).thenReturn(entity);

        BarCodePayRequest request = new BarCodePayRequest("test1", 123.45, "desc", "1234567890");
        ExtensionParameters extensionParams = new ExtensionParameters();
        extensionParams.setStoreNo("800123");
        request.setExtensionParameters(extensionParams);
        BarCodePayResponse response = client.barCodePay(request);

        verify(httpClient).execute(postCaptor.capture());
        HttpPost post = postCaptor.getValue();
        String postStr = EntityUtils.toString(post.getEntity(), Constants.CHARSET_UTF8);
        JsonObject postJson = JsonParser.parseString(postStr).getAsJsonObject();

        verifyBasicFields(postJson, "pay.barcodepay");
        assertEquals("test1", postJson.get("out_order_no").getAsString());
        assertEquals(123.45, postJson.get("trans_amount").getAsDouble());
        assertEquals("desc", postJson.get("description").getAsString());
        assertEquals("{\"store_no\":\"800123\"}", postJson.get("extension_parameters").getAsString());

        assertEquals("0", response.getCode());
        assertEquals("payment successful", response.getMessage());
        assertEquals("12345", response.getPsn());
        assertEquals("5259982d84faab397e758e82b0b4324e", response.getSign());
        assertEquals(1, response.getData().size());
        BarCodePayResponseData responseData = response.getData().get(0);
        assertEquals("TRANS12345", responseData.getTransactionNo());
        assertEquals(config.getMerchantNo(), responseData.getMerchantNo());
        assertEquals("test1", responseData.getOrderNo());
        assertEquals(TransactionStatus.SUCCESS, responseData.getTransactionStatus());
        assertEquals(PaymentMethod.ALIPAY, responseData.getPaymentMethod());
        assertEquals(PaymentOperationMethod.BAR_CODE_PAY, responseData.getPaymentOperationMethod());
        assertEquals("2088101117955611", responseData.getUserAccountId());
        assertEquals("15900000000", responseData.getUserAccountName());
        assertEquals(Currency.CAD, responseData.getCurrency());
        assertEquals(BigDecimal.valueOf(5.21), responseData.getExchangeRate());
        assertEquals(123.45, responseData.getTransactionAmount());
        assertEquals(2.5, responseData.getTransactionFee());
        assertEquals(524.09, responseData.getPaidAmount());
        assertEquals(0, responseData.getMerchantDiscount());
        assertEquals(0, responseData.getChannelDiscount());
        assertEquals(LocalDateTime.of(2021, 3, 2, 15, 16, 55), responseData.getCompletionTime());
    }

    @Test
    public void testBarCodePay_PartialPayment() throws OpenApiException, ClientProtocolException, IOException {
        config.setPartialPaymentSupported(true);
        config.setAlternativeOrderNumberPrefix("new-");
        String data = "{\"trans_no\":\"TRANS12345\","
            + "\"out_order_no\":\"test1\","
            + "\"merchant_no\":\"" + config.getMerchantNo() + "\","
            + "\"trans_status\":\"SUCCESS\","
            + "\"payment_method\":\"ALIPAY\","
            + "\"pay_operation_method\":5,"
            + "\"pay_user_account_id\":\"2088101117955611\","
            + "\"pay_user_account_name\":\"15900000000\","
            + "\"trans_currency\":\"CAD\","
            + "\"exchange_rate\":5.21,"
            + "\"trans_amount\":50.24,"
            + "\"c_trans_fee\":2.5,"
            + "\"customer_paid_amount\":524.09,"
            + "\"discount_bmopc\":0.0,"
            + "\"discount_bpc\":0.0,"
            + "\"trans_end_time\":\"2021-03-02 15:16:55\"}";
        HttpEntity entity1 = new StringEntity("{\"code\":\"E066006\",\"msg\":\"Insufficient balance, current balance is (50.24).\",\"total\":0,\"psn\":\"12345\",\"sign\":\"6b5d0c4ed27f90da5dd485588dbcfcc0\",\"data\":[]}", ContentType.APPLICATION_JSON);
        HttpEntity entity2 = new StringEntity("{\"code\":\"0\",\"msg\":\"payment successful\",\"total\":1,\"psn\":\"12345\",\"sign\":\"f512d47248fe697b29f72d20e2f36d56\",\"data\":[" + data + "]}", ContentType.APPLICATION_JSON);
        when(httpClient.execute(any())).thenReturn(httpResponse);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(200);
        when(httpResponse.getEntity()).thenReturn(entity1, entity2);

        BarCodePayRequest request = new BarCodePayRequest("test1", 123.45, "desc", "881234567890123456");
        ExtensionParameters extensionParams = new ExtensionParameters();
        extensionParams.setStoreNo("800123");
        request.setExtensionParameters(extensionParams);
        BarCodePayResponse response = client.barCodePay(request);

        verify(httpClient, times(2)).execute(postCaptor.capture());
        List<HttpPost> posts = postCaptor.getAllValues();
        String postStr = EntityUtils.toString(posts.get(0).getEntity(), Constants.CHARSET_UTF8);
        JsonObject postJson = JsonParser.parseString(postStr).getAsJsonObject();

        verifyBasicFields(postJson, "pay.barcodepay");
        assertEquals("test1", postJson.get("out_order_no").getAsString());
        assertEquals(123.45, postJson.get("trans_amount").getAsDouble());
        assertEquals("desc", postJson.get("description").getAsString());
        assertEquals("{\"store_no\":\"800123\"}", postJson.get("extension_parameters").getAsString());

        postStr = EntityUtils.toString(posts.get(1).getEntity(), Constants.CHARSET_UTF8);
        postJson = JsonParser.parseString(postStr).getAsJsonObject();
        verifyBasicFields(postJson, "pay.barcodepay");
        assertEquals("new-test1", postJson.get("out_order_no").getAsString());
        assertEquals(50.24, postJson.get("trans_amount").getAsDouble());
        assertEquals("desc", postJson.get("description").getAsString());
        assertEquals("{\"store_no\":\"800123\"}", postJson.get("extension_parameters").getAsString());

        assertEquals("0", response.getCode());
        assertEquals("payment successful", response.getMessage());
        assertEquals("12345", response.getPsn());
        assertEquals("f512d47248fe697b29f72d20e2f36d56", response.getSign());
        assertEquals(1, response.getData().size());
        BarCodePayResponseData responseData = response.getData().get(0);
        assertEquals("TRANS12345", responseData.getTransactionNo());
        assertEquals(config.getMerchantNo(), responseData.getMerchantNo());
        assertEquals("test1", responseData.getOrderNo());
        assertEquals(TransactionStatus.SUCCESS, responseData.getTransactionStatus());
        assertEquals(PaymentMethod.ALIPAY, responseData.getPaymentMethod());
        assertEquals(PaymentOperationMethod.BAR_CODE_PAY, responseData.getPaymentOperationMethod());
        assertEquals("2088101117955611", responseData.getUserAccountId());
        assertEquals("15900000000", responseData.getUserAccountName());
        assertEquals(Currency.CAD, responseData.getCurrency());
        assertEquals(BigDecimal.valueOf(5.21), responseData.getExchangeRate());
        assertEquals(50.24, responseData.getTransactionAmount());
        assertEquals(2.5, responseData.getTransactionFee());
        assertEquals(524.09, responseData.getPaidAmount());
        assertEquals(0, responseData.getMerchantDiscount());
        assertEquals(0, responseData.getChannelDiscount());
        assertEquals(LocalDateTime.of(2021, 3, 2, 15, 16, 55), responseData.getCompletionTime());
        assertTrue(responseData.isPartialPayment());
        assertEquals(123.45, responseData.getTotalAmount());
        assertEquals(123.45 - 50.24, responseData.getOutstandingAmount());
    }

    @Test
    public void testRefundOrder_OrderNo() throws OpenApiException, ClientProtocolException, IOException {
        String data = "{\"trans_no\": \"TRANS12345\","
            + "\"out_order_no\": \"test1\","
            + "\"out_refund_no\": \"refund1\","
            + "\"trans_status\": \"SUCCESS\","
            + "\"refund_trans_no\": \"TRANS67890\","
            + "\"refund_trans_end_time\": \"2021-03-02 15:32:45\"}";
        HttpEntity entity = new StringEntity("{\"code\":\"0\",\"msg\":\"refund successful\",\"total\":1,\"psn\":\"12345\",\"sign\":\"ebcabe64d25c747e88240bf3c0ae450e\",\"data\":[" + data + "]}", ContentType.APPLICATION_JSON);
        when(httpClient.execute(any())).thenReturn(httpResponse);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(200);
        when(httpResponse.getEntity()).thenReturn(entity);

        RefundOrderRequest request = new RefundOrderRequest("refund1", 23.45);
        request.setOrderNo("test1");
        RefundOrderResponse response = client.refundOrder(request);

        verify(httpClient).execute(postCaptor.capture());
        HttpPost post = postCaptor.getValue();
        String postStr = EntityUtils.toString(post.getEntity(), Constants.CHARSET_UTF8);
        JsonObject postJson = JsonParser.parseString(postStr).getAsJsonObject();

        verifyBasicFields(postJson, "pay.orderrefund");
        assertEquals("test1", postJson.get("out_order_no").getAsString());
        assertEquals("refund1", postJson.get("out_refund_no").getAsString());
        assertEquals(23.45, postJson.get("refund_amount").getAsDouble());

        assertEquals("0", response.getCode());
        assertEquals("refund successful", response.getMessage());
        assertEquals("12345", response.getPsn());
        assertEquals("ebcabe64d25c747e88240bf3c0ae450e", response.getSign());
        assertEquals(1, response.getData().size());
        RefundOrderResponseData responseData = response.getData().get(0);
        assertEquals("TRANS12345", responseData.getTransactionNo());
        assertEquals("TRANS67890", responseData.getRefundTransactionNo());
        assertEquals("test1", responseData.getOrderNo());
        assertEquals("refund1", responseData.getRefundOrderNo());
        assertEquals(TransactionStatus.SUCCESS, responseData.getTransactionStatus());
        assertEquals(LocalDateTime.of(2021, 3, 2, 15, 32, 45), responseData.getRefundCompletionTime());
    }

    @Test
    public void testRefundOrder_TransactionNo() throws OpenApiException, ClientProtocolException, IOException {
        String data1 = "{\"trans_no\":\"TRANS12345\","
            + "\"out_order_no\":\"test1\","
            + "\"merchant_no\":\"" + config.getMerchantNo() + "\","
            + "\"trans_status\":\"SUCCESS\","
            + "\"payment_method\":\"ALIPAY\","
            + "\"pay_operation_method\":5,"
            + "\"pay_user_account_id\":\"2088101117955611\","
            + "\"pay_user_account_name\":\"15900000000\","
            + "\"trans_currency\":\"CAD\","
            + "\"exchange_rate\":5.21,"
            + "\"trans_amount\":50.24,"
            + "\"c_trans_fee\":2.5,"
            + "\"customer_paid_amount\":524.09,"
            + "\"discount_bmopc\":0.0,"
            + "\"discount_bpc\":0.0,"
            + "\"trans_end_time\":\"2021-03-02 15:16:55\","
            + "\"attach\":\"{\\\"test\\\":\\\"value\\\"}\"}";
        HttpEntity entity1 = new StringEntity("{\"code\":\"0\",\"msg\":\"query successful\",\"total\":1,\"psn\":\"12345\",\"sign\":\"87946cde03ebce206c4e22b423a06cfe\",\"data\":[" + data1 + "]}", ContentType.APPLICATION_JSON);
        String data2 = "{\"trans_no\": \"TRANS12345\","
            + "\"out_order_no\": \"test1\","
            + "\"out_refund_no\": \"refund1\","
            + "\"trans_status\": \"SUCCESS\","
            + "\"refund_trans_no\": \"TRANS67890\","
            + "\"refund_trans_end_time\": \"2021-03-02 15:32:45\"}";
        HttpEntity entity2 = new StringEntity("{\"code\":\"0\",\"msg\":\"refund successful\",\"total\":1,\"psn\":\"12345\",\"sign\":\"ebcabe64d25c747e88240bf3c0ae450e\",\"data\":[" + data2 + "]}", ContentType.APPLICATION_JSON);
        when(httpClient.execute(any())).thenReturn(httpResponse);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(200);
        when(httpResponse.getEntity()).thenReturn(entity1, entity2);

        RefundOrderRequest request = new RefundOrderRequest("refund1", 23.45);
        request.setTransactionNo("TRANS12345");
        RefundOrderResponse response = client.refundOrder(request);

        verify(httpClient, times(2)).execute(postCaptor.capture());
        List<HttpPost> posts = postCaptor.getAllValues();
        String postStr = EntityUtils.toString(posts.get(0).getEntity(), Constants.CHARSET_UTF8);
        JsonObject postJson = JsonParser.parseString(postStr).getAsJsonObject();

        verifyBasicFields(postJson, "pay.orderquery");
        assertEquals("TRANS12345", postJson.get("trans_no").getAsString());

        postStr = EntityUtils.toString(posts.get(1).getEntity(), Constants.CHARSET_UTF8);
        postJson = JsonParser.parseString(postStr).getAsJsonObject();

        verifyBasicFields(postJson, "pay.orderrefund");
        assertEquals("test1", postJson.get("out_order_no").getAsString());
        assertEquals("refund1", postJson.get("out_refund_no").getAsString());
        assertEquals(23.45, postJson.get("refund_amount").getAsDouble());

        assertEquals("0", response.getCode());
        assertEquals("refund successful", response.getMessage());
        assertEquals("12345", response.getPsn());
        assertEquals("ebcabe64d25c747e88240bf3c0ae450e", response.getSign());
        assertEquals(1, response.getData().size());
        RefundOrderResponseData responseData = response.getData().get(0);
        assertEquals("TRANS12345", responseData.getTransactionNo());
        assertEquals("TRANS67890", responseData.getRefundTransactionNo());
        assertEquals("test1", responseData.getOrderNo());
        assertEquals("refund1", responseData.getRefundOrderNo());
        assertEquals(TransactionStatus.SUCCESS, responseData.getTransactionStatus());
        assertEquals(LocalDateTime.of(2021, 3, 2, 15, 32, 45), responseData.getRefundCompletionTime());
    }

    private void verifyBasicFields(JsonObject json, String method) {
        assertEquals(config.getAppId(), json.get("app_id").getAsString());
        assertEquals(method, json.get("method").getAsString());
        assertEquals(config.getMerchantNo(), json.get("merchant_no").getAsString());
        assertEquals("JSON", json.get("format").getAsString());
        assertEquals("UTF-8", json.get("charset").getAsString());
        assertEquals("1.0", json.get("version").getAsString());
        assertEquals("MD5", json.get("sign_type").getAsString());
    }
}
