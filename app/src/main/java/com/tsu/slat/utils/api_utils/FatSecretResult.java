package com.tsu.slat.utils.api_utils;

public class FatSecretResult {
    private String _signature;
    private String _signatureBase;
    private String _normalizedUrl;
    private String _normalizedRequestParameters;

    public FatSecretResult() {
    }

    public void setSignature(String _signature) {
        this._signature = _signature;
    }

    public String getSignature() {
        return this._signature;
    }

    public void setSignatureBase(String _signatureBase) {
        this._signatureBase = _signatureBase;
    }

    public String getSignatureBase() {
        return this._signatureBase;
    }

    public void setNormalizedUrl(String _normalizedUrl) {
        this._normalizedUrl = _normalizedUrl;
    }

    public String getNormalizedUrl() {
        return this._normalizedUrl;
    }

    public void setNormalizedRequestParameters(String _normalizedRequestParameters) {
        this._normalizedRequestParameters = _normalizedRequestParameters;
    }

    public String getNormalizedRequestParameters() {
        return this._normalizedRequestParameters;
    }
}
