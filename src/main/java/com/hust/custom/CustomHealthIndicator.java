package com.hust.custom;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
@Component
public class CustomHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		// TODO Auto-generated method stub
		int errorCode=check();
		if (errorCode==0) {
			return Health.up()
					.withDetail("Status", "UP")
					.withDetail("Error code", errorCode)
					.withDetail("Description", "Your Custom health indicator point is Up")
					.build();
		}
		return Health.up().build();
	}
	public int check() {
		return 0;
	}
}
