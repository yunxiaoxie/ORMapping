package com.crab.websocket.config;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class MessageWebSocketInterceptor implements HandshakeInterceptor {
	private static Logger logger = LogManager.getLogger(HandshakeInterceptor.class);
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		logger.info("beforeHandshake...");
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			String siteId = servletRequest.getServletRequest().getParameter("siteId");
			if (siteId == null) {
				return false;
			}
			attributes.put("siteId", siteId);
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		logger.info("afterHandshake...");
	}
}
