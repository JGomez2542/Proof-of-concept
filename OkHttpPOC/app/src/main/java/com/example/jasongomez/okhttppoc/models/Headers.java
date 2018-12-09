
package com.example.jasongomez.okhttppoc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Headers {

    @SerializedName("Accept")
    @Expose
    private String accept;
    @SerializedName("Accept-Encoding")
    @Expose
    private String acceptEncoding;
    @SerializedName("Accept-Language")
    @Expose
    private String acceptLanguage;
    @SerializedName("Connection")
    @Expose
    private String connection;
    @SerializedName("Content-Length")
    @Expose
    private String contentLength;
    @SerializedName("Cookie")
    @Expose
    private String cookie;
    @SerializedName("Host")
    @Expose
    private String host;
    @SerializedName("Origin")
    @Expose
    private String origin;
    @SerializedName("Referer")
    @Expose
    private String referer;
    @SerializedName("User-Agent")
    @Expose
    private String userAgent;

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public void setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getContentLength() {
        return contentLength;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

}
