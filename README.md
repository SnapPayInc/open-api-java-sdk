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
