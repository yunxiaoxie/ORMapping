package com.crab.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class ApiResult<T> {
  private String result;
  private T content;
  private List<String> errors;
  private List<String> messages;
  private Integer code;

  public ApiResult() {
  }

  public ApiResult(String result) {
    this.result = result;
    this.code = HttpStatus.BAD_REQUEST.value();
    this.errors = Lists.newArrayList();
    this.messages = Lists.newArrayList();
  }

  public ApiResult(String result, T content) {
    this.result = result;
    this.code = HttpStatus.OK.value();
    this.content = content;
    this.errors = Lists.newArrayList();
    this.messages = Lists.newArrayList();
  }

  public ApiResult(String result, String message) {
    this.result = result;
    this.code = HttpStatus.BAD_REQUEST.value();
    this.errors = Lists.newArrayList();
    this.messages = Lists.newArrayList(message);
  }

  public ApiResult(String result, List<String> messages) {
    this.result = result;
    this.code = HttpStatus.BAD_REQUEST.value();
    this.errors = Lists.newArrayList();
    this.messages = messages;
  }

  public static ApiResult success(Object context) {
    return new ApiResult(ResultCode.SUCCESS, context);
  }

  public static ApiResult success() {
    return success(Maps.newHashMap());
  }

  public static ApiResult error() {
    return new ApiResult(ResultCode.ERROR);
  }

  public static ApiResult error(String message) {
    return new ApiResult(ResultCode.ERROR, message);
  }

  public static ApiResult errors(List<String> messages) {
    return new ApiResult(ResultCode.ERROR, messages);
  }
}
