package com.dac.spring.model.resp;

public class GetAddressResp {
    private GetAddressResponse info;

    public GetAddressResp() {
    }

    public GetAddressResp(GetAddressResponse info) {
        this.info = info;
    }

    public GetAddressResponse getInfo() {
        return info;
    }

    public void setInfo(GetAddressResponse info) {
        this.info = info;
    }
}
