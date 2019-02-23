package com.crab.common.jwt;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken {

    // 密钥
    private String token;

    private String ipHost;

    public JwtToken(String token, String ipHost) {
        this.token = token;
        this.ipHost = ipHost;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
