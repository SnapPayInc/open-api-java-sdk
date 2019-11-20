# SnapPay OpenAPI Java SDK

Java SDK for [SnapPay OpenAPI](http://developer.snappay.ca/openapi/doc/index.html)

## Configuration

This SDK supports 3 types of configuration: system property, environment variable, and profile file. The following table summarizes the name of configurations for each approach.

| Description | Required | System Property Key | Env Variable Key | Profile File Key | Default Value | Sample Value |
| --- | --- | --- | --- | --- | --- | --- |
| OpenApi Gateway Host | N | snappay.openapi.gateway-host | SNAPPAY_GATEWAY_HOST | openapi.gateway-host | open.snappay.ca | |
| Merchant Number | Y | snappay.openapi.merchant-no | SNAPPAY_MERCHANT_NO | openapi.merchant-no | | 901800000116 |
| App ID | Y | snappay.openapi.app-id | SNAPPAY_APP_ID | openapi.app-id | | 9f00cd9a873c511e |
| Language | N | snappay.openapi.language | SNAPPAY_LANGUAGE | openapi.language | en-US | en-US or zh-CN|
| Signature Type | N | snappay.openapi.sign-type | SNAPPAY_SIGN_TYPE | openapi.sign-type | MD5 | MD5 or RSA |
| RSA Public Key | Y (Only for RSA) | snappay.openapi.public-key | SNAPPAY_PUBLIC_KEY | openapi.public-key | | |
| Private Key | Y | snappay.openapi.private-key | SNAPPAY_PRIVATE_KEY | openapi.private-key | | 7e2083699dd510575faa1c72f9e35d43 |

### How to use property file

The SDK checks if there is a file located at `<user home>/.snappay` and with the name as `config`. This file needs to be a standard properties file, and the client will try to read all the properties with the keys listed in the above table to determine the configuration value.

## How to use the SDK

The SDK has an entry point interface `OpenApiClient` and its default implementation `DefaultOpenApiClient`. The user can create an instance of `DefaultOpenApiClient` using an instance of `ConfigurationHolder`; or if using default constructor, the configuration will be determined as the above section.

The interface has only one method `execute`, which takes a request and returns the response. For detailed information about the request and response fields, please see the developer documentation located [here](http://developer.snappay.ca/openapi/doc/index.html).

### Sample Code

**Setup configuration manually**

```java
BasicConfigurationHolder config = new BasicConfigurationHolder();
config.setXXX(...);

OpenApiClient client = new DefaultOpenApiClient(config);

BarCodePayRequest request = new BarCodePayRequest();
request.setXXX(...);

BarCodePayResponse response = client.execute(request);
```

**Get configuration from environment**

```java
OpenApiClient client = new DefaultOpenApiClient();

BarCodePayRequest request = new BarCodePayRequest();
request.setXXX(...);

BarCodePayResponse response = client.execute(request);
```