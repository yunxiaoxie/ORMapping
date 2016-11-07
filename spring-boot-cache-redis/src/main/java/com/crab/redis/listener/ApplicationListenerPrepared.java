package com.crab.redis.listener;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationListenerPrepared implements ApplicationListener<ApplicationPreparedEvent> {
	
	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event) {
		System.out.println(getClass().getSimpleName());
	}

}