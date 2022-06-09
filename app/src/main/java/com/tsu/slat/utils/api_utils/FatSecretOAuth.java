package com.tsu.slat.utils.api_utils;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

class FatSecretOAuth {
    public static final String OAUTH_VERSION_NUMBER = "1.0";
    public static final String OAUTH_PARAMETER_PREFIX = "oauth_";
    public static final String XOAUTH_PARAMETER_PREFIX = "xoauth_";
    public static final String OPEN_SOCIAL_PARAMETER_PREFIX = "opensocial_";
    public static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    public static final String OAUTH_CALLBACK = "oauth_callback";
    public static final String OAUTH_VERSION = "oauth_version";
    public static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
    public static final String OAUTH_SIGNATURE = "oauth_signature";
    public static final String OAUTH_TIMESTAMP = "oauth_timestamp";
    public static final String OAUTH_NONCE = "oauth_nonce";
    public static final String OAUTH_TOKEN = "oauth_token";
    public static final String OAUTH_TOKEN_SECRET = "oauth_token_secret";
    protected String unreservedChars;

    private FatSecretOAuth() {
        this.unreservedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~";
    }

    public void generateSignature(URL url, String consumerKey, String consumerSecret, String token, String tokenSecret, FatSecretResult result) {
        this.GenerateSignatureBase(url, consumerKey, token, "POST", this.generateTimeStamp(), this.generateNonce(), "HMAC-SHA1", result);
        String secret = consumerSecret + "&";
        if (tokenSecret != null) {
            secret = secret + tokenSecret;
        }

        result.setSignature(this.getHMACSHA1(secret, result.getSignatureBase()));
    }

    private void GenerateSignatureBase(URL url, String consumerKey, String token, String httpMethod, String timeStamp, String nonce, String signatureType, FatSecretResult result) {
        Map<String, String> parameters = new HashMap();
        //Map<String, String>
        parameters = this.getQueryParameters(url.getQuery(), parameters);
        parameters.put("oauth_version", "1.0");
        parameters.put("oauth_nonce", nonce);
        parameters.put("oauth_timestamp", timeStamp);
        parameters.put("oauth_signature_method", signatureType);
        parameters.put("oauth_consumer_key", consumerKey);
        if (!this.IsNullOrEmpty(token)) {
            parameters.put("oauth_token", token);
        }

        String normalizedUrl = url.getProtocol() + "://" + url.getHost();
        if (url.getPort() != -1 && (url.getProtocol() != "http" || url.getPort() != 80) && (url.getProtocol() != "https" || url.getPort() != 443)) {
            normalizedUrl = normalizedUrl + ":" + url.getPort();
        }

        normalizedUrl = normalizedUrl + url.getPath();
        String normalizedRequestParameters = this.normalizeRequestParameters(parameters);
        result.setNormalizedUrl(normalizedUrl);
        result.setNormalizedRequestParameters(normalizedRequestParameters);
        result.setSignatureBase(httpMethod + "&" + this.encode(normalizedUrl) + "&" + this.encode(normalizedRequestParameters));
    }

    private boolean IsNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    private Map<String, String> getQueryParameters(String parameters, Map<String, String> result) {
        if (parameters.startsWith("?")) {
            parameters = parameters.substring(1);
        }

        if (!this.IsNullOrEmpty(parameters)) {
            String[] p = parameters.split("&");
            String[] var7 = p;
            int var6 = p.length;

            for(int var5 = 0; var5 < var6; ++var5) {
                String s = var7[var5];
                if (!this.IsNullOrEmpty(s) && !s.startsWith("oauth_") && !s.startsWith("xoauth_") && !s.startsWith("opensocial_")) {
                    if (s.indexOf(61) > -1) {
                        String[] temp = s.split("=");
                        result.put(temp[0], temp[1]);
                    } else {
                        result.put(s, "");
                    }
                }
            }
        }

        return result;
    }

    private String encode(String value) {
        if (value == null) {
            return "";
        } else {
            try {
                return URLEncoder.encode(value, "utf-8").replace("+", "%20").replace("!", "%21").replace("*", "%2A").replace("\\", "%27").replace("(", "%28").replace(")", "%29");
            } catch (UnsupportedEncodingException var3) {
                throw new RuntimeException(var3.getMessage(), var3);
            }
        }
    }

    private String normalizeRequestParameters(Map<String, String> parameters) {
        List<String> parameterList = new ArrayList();
        Iterator var4 = parameters.keySet().iterator();

        while(var4.hasNext()) {
            String key = (String)var4.next();
            String parameter = key + "=" + this.encode((String)parameters.get(key));
            parameterList.add(parameter);
        }

        Collections.sort(parameterList);
        StringBuilder s = new StringBuilder();

        for(int i = 0; i < parameterList.size(); ++i) {
            s.append((String)parameterList.get(i));
            if (i != parameterList.size() - 1) {
                s.append("&");
            }
        }

        return s.toString();
    }

    private String getHMACSHA1(String key, String data) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HMAC-SHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            return Base64Util.encodeBytes(rawHmac);
        } catch (Exception var6) {
            throw new RuntimeException("Unable to generate HMAC-SHA1", var6);
        }
    }

    private String generateTimeStamp() {
        long timestamp = System.currentTimeMillis() / 1000L;
        return Long.toString(timestamp);
    }

    private String generateNonce() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
