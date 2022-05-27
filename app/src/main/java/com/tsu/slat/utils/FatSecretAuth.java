package com.tsu.slat.utils;

public class FatSecretAuth {
    private String token, secret;

    public FatSecretAuth(String token, String secret){
        this.token = token;
        this.secret = secret;
    }

    public String getToken(){
        return token;
    }

    public String getSecret(){
        return secret;
    }
}
