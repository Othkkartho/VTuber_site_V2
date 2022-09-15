package com.othkkartho.vtuber_site_v2.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Success<T> implements Result {
    private T data;
}
