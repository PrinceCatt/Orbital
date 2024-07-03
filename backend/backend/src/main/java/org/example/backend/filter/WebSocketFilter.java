package org.example.backend.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@Component
@WebFilter(filterName = "WebsocketFilter", urlPatterns = "/ws/**")
public class WebSocketFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = ((HttpServletRequest) servletRequest).getHeader("Sec-Websocket-Protocol");
        // 解决 Sent non-empty 'Sec-WebSocket-Protocol' header but no response was received
        response.setHeader("Sec-Websocket-Protocol",token);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}
