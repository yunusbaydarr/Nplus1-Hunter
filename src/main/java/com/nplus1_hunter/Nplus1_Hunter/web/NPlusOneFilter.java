package com.nplus1_hunter.Nplus1_Hunter.web;

import com.nplus1_hunter.Nplus1_Hunter.context.RequestContextHolder;
import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NPlusOneFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            RequestContextHolder.clear();
        }
    }
}
