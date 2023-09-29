package com.kopyshov.weatherwebapplication.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public interface MappingCookies {
    default Map<String, String> mapCookies(HttpServletRequest request) {
        Map<String, String> cookies = new HashMap<>();
        Cookie[] requestCookies = request.getCookies();
        if (requestCookies != null) {
            for (Cookie aCookie : requestCookies) {
                cookies.put(aCookie.getName(), aCookie.getValue());
            }
        }
        return cookies;
    }
}
