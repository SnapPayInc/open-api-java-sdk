
package com.wiseasy.openapi.sign;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA {

	
	public static final String  SIGN_ALGORITHMS = "SHA256WithRSA";
	
	/**
	* RSA签名 RSA sign
	* @param content 待签名数据 pre-sign string
	* @param privateKey RSA私钥 private key
	* @param input_charset 编码格式 charset
	* @return 签名值 sign
	*/
	public static String sign(String content, String privateKey, String input_charset) {
        try {
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(input_charset) );

            byte[] signed = signature.sign();
            
            return Base64.encode(signed);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        return null;
    }
	
	/**
	* RSA验签名检查 RSA sign verification
	* @param content 待签名数据 pre-sign string
	* @param sign 签名值 sign
	* @param ali_public_key RSA公钥 Alipay's public key
	* @param input_charset 编码格式 charset
	* @return 布尔值 boolean
	*/
	public static boolean verify(String content, String sign, String ali_public_key, String input_charset) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(ali_public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
		
			boolean bverify = signature.verify( Base64.decode(sign) );
			return bverify;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
