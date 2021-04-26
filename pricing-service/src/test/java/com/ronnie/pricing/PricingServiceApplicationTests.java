package com.ronnie.pricing;

import com.ronnie.pricing.domain.price.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricingServiceApplicationTests {
	
	@LocalServerPort
	private int port;

	@Autowired
	TestRestTemplate restTemplate;
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void getPrice() {
		Random random = new Random();
		long vehicleId = 1 + random.nextInt(20);
		ResponseEntity<Price> response =
				this.restTemplate.getForEntity("http://localhost:"
				+ port + "/services/price/" + vehicleId, Price.class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}
}
