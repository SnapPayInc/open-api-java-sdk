# SnapPay OpenAPI Java SDK

Java SDK for [SnapPay OpenAPI](https://developer.snappay.ca/openapi.html)

## Add SDK to your project

**Maven**

If you use Apache Maven, just add the following dependency to pom.xml.

```xml
<dependency>
    <groupId>ca.snappay</groupId>
    <artifactId>openapi-java-sdk</artifactId>
    <version>1.2.0</version>
</dependency>
```

## Configuration

In order to register as merchant with SnapPay, please contact [SnapPay Customer Service](https://www.snappay.ca/contact-us/). You will get the following values from SnapPay: merchant number, app ID, signature type, signing keys.

Besides setting up manually, this SDK supports 3 types of configuration: system property, environment variable, and profile file. The following table summarizes the name of configurations for each approach.

| Description | Required | System Property Key | Env Variable Key | Profile File Key | Default Value | Sample Value |
| --- | --- | --- | --- | --- | --- | --- |
| OpenApi Gateway Host | N | snappay.openapi.gateway-host | SNAPPAY_GATEWAY_HOST | openapi.gateway-host | open.snappay.ca | |
| Merchant Number | Y | snappay.openapi.merchant-no | SNAPPAY_MERCHANT_NO | openapi.merchant-no | | 901800000116 |
| App ID | Y | snappay.openapi.app-id | SNAPPAY_APP_ID | openapi.app-id | | 9f00cd9a873c511e |
| Language | N | snappay.openapi.language | SNAPPAY_LANGUAGE | openapi.language | en-US | en-US or zh-CN|
| Signature Type | N | snappay.openapi.sign-type | SNAPPAY_SIGN_TYPE | openapi.sign-type | MD5 | MD5 or RSA |
| RSA Public Key | N (required for RSA only) | snappay.openapi.public-key | SNAPPAY_PUBLIC_KEY | openapi.public-key | | |
| Private Key | Y | snappay.openapi.private-key | SNAPPAY_PRIVATE_KEY | openapi.private-key | | 7e2083699dd510575faa1c72f9e35d43 |

### How to use property file

The SDK checks if there is a file located at `<user home>/.snappay` and with the name as `config`. This file needs to be a standard properties file, and the SDK will try to read all the properties with the keys listed in the above table to determine the configuration value. Here is a sample property file.

```
openapi.merchant-no=901800000116
openapi.app-id=9f00cd9a873c511e
openapi.sign-type=MD5
openapi.private-key=7e2083699dd510575faa1c72f9e35d43
```

## How to use the SDK

The SDK has an entry point interface `OpenApiClient` and its default implementation `DefaultOpenApiClient`. The user can create an instance of `DefaultOpenApiClient` using an instance of `ConfigurationHolder`; or if using default constructor, the configuration will be determined as described above.

The interface has different methods for each API operation, which takes a request and returns the corresponding response. For detailed information about the request and response fields, please see the developer documentation located [here](https://developer.snappay.ca/openapi.html).

## Sample Code

**Setup configuration manually**

```java
// create BasicConfigurationHolder and set all necessary values
BasicConfigurationHolder config = new BasicConfigurationHolder();
config.setMerchantNo("901800000116");
config.setAppId("9f00cd9a873c511e");
config.setSignType(SignType.MD5);
config.setPrivateKey("7e2083699dd510575faa1c72f9e35d43");

OpenApiClient client = new DefaultOpenApiClient(config);
```

**Get configuration from environment**

```java
// no need to create ConfigurationHolder since the configuration will be retrieved from environment
OpenApiClient client = new DefaultOpenApiClient();
```

**Make payment using barcode [API doc](https://developer.snappay.ca/openapi.html#pay-apis-barcode-pay-api-post)**

```java
OpenApiClient client = new DefaultOpenApiClient();

// order number should be unique, and auth code is from the payment mobile app (AliPay, WeChat, etc.)
BarCodePayRequest request = new BarCodePayRequest("testorder001", 123.45, "this is a test order", "134512345678901234");
BarCodePayResponse response = client.barCodePay(request);
```

**Make payment using QR code [API doc](https://developer.snappay.ca/openapi.html#pay-apis-transaction-qr-code-pay-api-post)**

```java
OpenApiClient client = new DefaultOpenApiClient();

// order number should be unique, payment method can be either of ALIPAY, WECHAT, and UNIONPAY_QR
QRCodePayRequest request = new QRCodePayRequest(PaymentMethod.WECHAT, "testorder002", 123.45, "this is a test order");
QRCodePayResponse response = client.qrCodePay(request);
```

**Make payment through H5 page [API doc](https://developer.snappay.ca/openapi.html#pay-apis-h5-pay-api-post)**

```java
OpenApiClient client = new DefaultOpenApiClient();

// order number should be unique, payment method can be either ALIPAY or WECHAT
H5PayRequest request = new H5PayRequest(PaymentMethod.WECHAT, "testorder003", 123.45, "this is a test order");
H5PayResponse response = client.h5Pay(request);
```

**Make payment through native app [API doc](https://developer.snappay.ca/openapi.html#pay-apis-native-app-pay-api-post)**

```java
OpenApiClient client = new DefaultOpenApiClient();

// order number should be unique, payment method can be either ALIPAY or WECHAT
NativePayRequest request = new NativePayRequest(PaymentMethod.WECHAT, "testorder004", 123.45, "this is a test order");
NativePayResponse response = client.nativePay(request);
```

**Make payment through web page [API doc](https://developer.snappay.ca/openapi.html#pay-apis-website-pay-api-post)**

```java
OpenApiClient client = new DefaultOpenApiClient();

// order number should be unique, payment method can be either of ALIPAY, UNIONPAY, or CREDITCARD_PAYBYTOKEN
WebsitePayRequest request = new WebsitePayRequest(PaymentMethod.ALIPAY, "testorder005", 123.45, "this is a test order");
WebsitePayResponse response = client.websitePay(request);
```

**Make payment through WeChat mini app [API doc](https://developer.snappay.ca/openapi.html#pay-apis-mini-program-api-post)**

```java
OpenApiClient client = new DefaultOpenApiClient();

// order number should be unique, payment method can only be WECHAT
MiniPayRequest request = new MiniPayRequest(PaymentMethod.WECHAT, "testorder006", 123.45, "this is a test order");
MiniPayResponse response = client.websitePay(request);
```

**Query an existing order [API doc](https://developer.snappay.ca/openapi.html#order-apis-query-order-api-post)**

```java
OpenApiClient client = new DefaultOpenApiClient();

QueryOrderRequest request = new QueryOrderRequest();
// use order number or transaction number, but not both
request.setOrderNo("testorder001");
// request.setTransactionNo("transaction001");
QueryOrderResponse response = client.queryOrder(request);
```

**Revoke an existing order [API doc](https://developer.snappay.ca/openapi.html#order-apis-revoke-order-api-post)**

```java
OpenApiClient client = new DefaultOpenApiClient();

RevokeOrderRequest request = new RevokeOrderRequest("testorder001");
RevokeOrderResponse response = client.revokeOrder(request);
```

**Refund an existing order [API doc](https://developer.snappay.ca/openapi.html#order-apis-refund-api-post)**

```java
OpenApiClient client = new DefaultOpenApiClient();

// order number is the original order number, refund order number is for the refund transaction, and has to be unique
RefundOrderRequest request = new RefundOrderRequest("testorder001", "testorder100", 50);
RevokeOrderResponse response = client.refundOrder(request);
```

**Query exchange rate [API doc](https://developer.snappay.ca/openapi.html#order-apis-query-exchange-rate-post)**

```java
OpenApiClient client = new DefaultOpenApiClient();

// only ALIPAY and WECHAT are supported, exchange rate is for reference only
QueryExchangeRateRequest request = new QueryExchangeRateRequest(Currency.CAD, PaymentMethod.WECHAT);
QueryExchangeRateResponse response = client.queryExchangeRate(request);
```

**Payment workflow demo (barcode payment)**

The following code snippet demonstrates the general payment workflow using SnapPay OpenAPI. It uses barcode payment, but the other payment methods are similar. 

```java
// ClientOrder represents the order information in client's system
ClientOrder order = new ClientOrder();
// client needs to initialize the order with any required information, and very likely persists the order to database

// create the API client
OpenApiClient client = new DefaultOpenApiClient();

// make barcode payment
BarCodePayRequest request = new BarCodePayRequest(order.getOrderNo(), order.getAmount(), order.getDescription(), "134512345678901234");
BarCodePayResponse response = client.barCodePay(request);

// if returned code is not 0, meaning the payment is failed
if (!"0".equals(response.getCode()) {
    // error handling and stop
    order.setStatus("failed");
    order.setErrorCode(response.getCode());
    return;
}

// there should be only 1 data item returned
BarCodePayResponseData data = response.getData().get(0);
switch (data.getTransactionStatus()) {
case CLOSE:
    // the transaction is closed directly, meaning the payment is not completed, consider this is an error
    order.setStatus("failed");
    order.setErrorCode("CLOSE");
    break;
case SUCCESS:
    // the transaction is successful, update local order status
    order.setStatus("success");
    break;
case USERPAYING:
    // the payment is waiting user interaction, query the order status periodically to determine the final status of the payment
    // see below example
    break;
}
```

**How to deal with USERPAYING status**

Very often, the payment requires user confirmation or PIN verification. In such cases, the payment API will return USERPAYING status, meaning the payment is not finished. It is recommended to query order status periodically in order to finalize the payment. Depending on the client's order system, the order may need to be cancelled before it is confirmed.

The below example uses very simple loop. Normally, some more sophisticated approach (thread pool, reactive, etc.) should be used, which is out of scope of this demonstration.

```java
OpenApiClient client = new DefaultOpenApiClient();

// construct a quest which can be reused
QueryOrderRequest request = new QueryOrderRequest();
request.setOrderNo(order.getOrderNo());

// flag to keep querying
boolean keepQuery = true;
// stop after 10 queries
int maxQueries =  10;
int currentQuery = 0;
while (keepQuery && currentQuery < maxQueries) {
    QueryOrderResponse response = client.queryOrder(request);
    // only need to handle successful response
    if ("0".equals(response.getCode()) {
        QueryOrderResponseData data = response.getData().get(0);
        switch (data.getTransactionStatus()) {
        case CLOSE:
            // the transaction is closed directly, meaning the payment is not completed, consider this is an error
            order.setStatus("failed");
            order.setErrorCode("CLOSE");
            keepQuery = false;
            break;
        case SUCCESS:
            // the transaction is successful, update local order status and break the loop
            order.setStatus("success");
            keepQuery = false;
            break;
        case USERPAYING:
            // the payment is still waiting for user input, do nothing here, just keep waiting
            break;
        }
    }
    // wait for 5 seconds before another query
    sleep(5000);
    currentQuery++;
}
```

**Payment status notification [API doc](https://developer.snappay.ca/openapi.html#order-apis-asynchronous-notification-post)**

SnapPay payment platform will send notification to client's system once the payment is completed. The client's system needs to expose an endpoint in order to receive such notification. The exact implementation can be anything which can serve as HTTP endpoint. Below is some demonstration implementation using basic HTTP Servlet.

```java
public class SnapPayNotificationServlet extends HttpServlet {

    public void init() throws ServletException {
        // do required initialization
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject body = JsonParser.parseReader(request.getReader()).getAsJsonObject();
        // verify signature returned from SnapPay
        boolean verified = SignHandler.verifySign(config, body);
        if (verified) {
            String status = body.get("trans_status").getAsString();
            if ("SUCCESS".equals(status)) {
                String orderNo = body.get("out_order_no").getAsString();
                // find client order using the order number
                ClientOrder order = ...;
                order.setStatus("success");
            }

            // return successful processing
            PrintWriter out = response.getWriter();
            out.println("{\"code\": \"0\"}");
            return;
        }

        // return error
        PrintWriter out = response.getWriter();
        out.println("{\"code\": \"ERROR\"}");
    }
}
```

**Automatic partial payment (applicable for Snaplii only)**
If the user is making payment using Snaplii app, it is possible that the card balance is not sufficient for the whole order. In such case, this SDK is capable to make partial payment to use the full remaining balance in Snaplii and return the result to client. For example, if the user is trying to make a payment of $100 but the remaining balance in Snaplii is only $40, the payment will be successful and the actual paid amount will be $40, from Snaplii. And the payment response will indicate that this is partial payment with outstanding amount of $60.

Internally, the SDK makes one additional payment request in case the balance is not sufficient. So the initial request will get to know the balance in Snaplii, then the SDK automatically adjusts the request and send it again. Since the acquiring system does not allow duplicate order number, the second request cannot use the same order number as the first request. The SDK includes an extension point where the client can specify how the second order number can be generated. One default implementation is provided which simply add prefix and/or suffix to the original order number. Clients can provide more sophisticated implementations based on the order management system they use.

In order to enable this feature, the client needs to set BasicConfigurationHolder.partialPaymentSupported to true (by default, this is disabled), and can optionally provide an implementation of AlternativeOrderNumberGenerator. The provided DefaultAlternativeOrderNumberGenerator simply use the configured prefix and suffix to make the new order number. Code example as follows.

*Use DefaultAlternativeOrderNumberGenerator*

```java
BasicConfigurationHolder config = new BasicConfigurationHolder();
config.setPartialPaymentSupported(true);
config.setAlternativeOrderNumberPrefix("new-");
config.setAlternativeOrderNumberSuffix("-1");

OpenApiClient client = new DefaultOpenApiClient(config);

BarCodePayRequest request = new BarCodePayRequest("partial001", 123.45, "this is a test order", "134512345678901234");
BarCodePayResponse response = client.barCodePay(request);

// this becomes partial payment
response.isPartialPayment(); // = true
response.getOrderNo(); // = new-partial001-1
response.getTransactionAmount(); // = user's Snaplii balance
response.getTotalAmount(); // = initial payment amount which is 123.45
response.getOutstandingAmount(); // = 123.45 - user's Snaplii balance
```

*Create custom AlternativeOrderNumberGenerator*

```java
public class MyAlternativeOrderNumberGenerator implements AlternativeOrderNumberGenerator {
    // this generator just replace the last 3 digits from original order number with a random number
    public String generato(ConfigurationHolder config, String orderNo) {
        Random random = new Random();
        int randomInt = random.nextInt(1000);
        return orderNo.substring(0, orderNo.length() - 3) + String.format("%03d", randomInt);
    }
}
```
