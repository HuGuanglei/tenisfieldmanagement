package com.cff.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component("authenticationDetailsSource")
public class SimpleAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new AdditionUserDetails(context);
    }


}
