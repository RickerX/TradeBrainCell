package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class RequestWrapperComment {
    public Integer adsId;
    public Comment data;
    public String username;

    public RequestWrapperComment setAdsId(Integer adsId) {
        this.adsId = adsId;
        return this;
    }

    public RequestWrapperComment setData(Comment data) {
        this.data = data;
        return this;
    }

    public RequestWrapperComment setUsername(String username) {
        this.username = username;
        return this;
    }
}
