package com.crab;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.crab.websocket.SimpleApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SimpleApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebsocketTest {
	private final Logger logger = LoggerFactory.getLogger(WebsocketTest.class);
	// private final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
	final CountDownLatch latch = new CountDownLatch(1);
	final AtomicReference<Throwable> failure = new AtomicReference<>();
	private SockJsClient sockJsClient;

	@Before
	public void setup() {
		List<Transport> transports = new ArrayList<>();
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		transports.add(new RestTemplateXhrTransport());
		this.sockJsClient = new SockJsClient(transports);
	}

	@Test
	public void getGreeting() throws Exception {

		this.sockJsClient.doHandshake(new TestWebSocketHandler(failure),
				"ws://localhost:" + String.valueOf(80) + "/echo?siteId=webtrn&userId=lucy");
		if (latch.await(5, TimeUnit.SECONDS)) {
			if (failure.get() != null) {
				throw new AssertionError("", failure.get());
			}
		} else {
			Assert.fail("Greeting not received");
		}

	}

	private class TestWebSocketHandler implements WebSocketHandler {

//		private final AtomicReference<Throwable> failure;

		public TestWebSocketHandler(AtomicReference<Throwable> failure) {
//			this.failure = failure;
		};

		@Override
		public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			logger.info("client connection established");
			session.sendMessage(new TextMessage("hello websocket server!"));
		}

		@Override
		public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
			String payload = (String) message.getPayload();
			logger.info("client handle message: " + payload);
			if (payload.equals("hello websocket client! webtrn lucy")) {
				latch.countDown();
			}

			if (payload.equals("web socket notify")) {
				latch.countDown();
			}
		}

		@Override
		public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
			logger.info("client transport error");
		}

		@Override
		public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
			logger.info("client connection closed");
		}

		@Override
		public boolean supportsPartialMessages() {
			return false;
		}
	}

}