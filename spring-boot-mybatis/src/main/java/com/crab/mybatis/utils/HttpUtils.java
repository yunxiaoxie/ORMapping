package com.crab.mybatis.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

public class HttpUtils {

  private static final int DEFAULT_CONNECT_TIMEOUT = 30000;

  public static String get(String url) throws Exception {
    return Request.Get(url).connectTimeout(DEFAULT_CONNECT_TIMEOUT).execute().returnContent().asString();
  }

  public static String get(String url, List<NameValuePair> params) throws Exception {
    return Request.Get(new URIBuilder(url).addParameters(params).build()).connectTimeout(DEFAULT_CONNECT_TIMEOUT).execute().returnContent().asString();
  }

  public static String post(String url, List<NameValuePair> params) throws IOException {
    return Request.Post(url).bodyForm(params, Consts.UTF_8).connectTimeout(DEFAULT_CONNECT_TIMEOUT).execute().returnContent().asString();
  }

  public static String post(String url, NameValuePair... params) throws IOException {
    return Request.Post(url).bodyForm(Arrays.asList(params), Consts.UTF_8).connectTimeout(DEFAULT_CONNECT_TIMEOUT).execute().returnContent().asString();
  }

  public static String post(String url, String bodyStr) throws IOException {
    return Request.Post(url).body(new StringEntity(bodyStr, Consts.UTF_8)).connectTimeout(DEFAULT_CONNECT_TIMEOUT).execute().returnContent().asString();
  }

  public static String postJson(String url, String body) throws IOException {
    return Request.Post(url).addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()).body(new StringEntity(body, Consts.UTF_8))
        .connectTimeout(DEFAULT_CONNECT_TIMEOUT).execute().returnContent().asString();
  }

  public static String uploadFile(String url, String fileFiled, String fileName, InputStream in) throws IOException {
    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
    multipartEntityBuilder = multipartEntityBuilder.addBinaryBody(fileFiled, in, ContentType.MULTIPART_FORM_DATA, fileName);
    multipartEntityBuilder.setCharset(Consts.UTF_8);
    multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    HttpEntity e = multipartEntityBuilder.build();
    return Request.Post(url).body(e).connectTimeout(DEFAULT_CONNECT_TIMEOUT).execute().returnContent().asString();
  }

  public static String getWithHeader(String url, Header... headers) throws IOException {
    return Request.Get(url).setHeaders(headers).connectTimeout(DEFAULT_CONNECT_TIMEOUT).execute().returnContent().asString();
  }

  public static String delete(String url) throws IOException {
    return Request.Delete(url).connectTimeout(DEFAULT_CONNECT_TIMEOUT).execute().returnContent().asString();
  }

  public static byte[] downloads(String url) throws Exception {
    InputStream in = Request.Get(url).connectTimeout(DEFAULT_CONNECT_TIMEOUT).execute().returnResponse().getEntity().getContent();
    return IOUtils.toByteArray(in);
  }

}
