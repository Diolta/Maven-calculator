/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hello;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.web.server.LocalServerPort;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class HelloWorldConfigurationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test

	public void testGreeting() throws Exception {
		String url = "http://localhost:" + this.port + "/api/calculate?expression=1+1";

		ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
	}

	private final Controller calculatorController = new Controller();

	@Test
	void testValidExpression() {
		// Arrange
		String expression = "1+1";

		// Act
		Map<String, String> response = calculatorController.calculateExpression(expression);

		// Assert
		assertNotNull(response);
		assertTrue(response.containsKey("result"));
		assertEquals("2.0", response.get("result"));
	}

//	@Test
//	void testInvalidExpression() {
//		// Arrange
//		String expression = "5-1"; // Цей тест спеціально некоректний, ми очікуємо "2"
//
//		// Act
//		Map<String, String> response = calculatorController.calculateExpression(expression);
//
//		// Assert
//		assertNotNull(response);
//		assertTrue(response.containsKey("result"));
//		assertEquals("2.0", response.get("result")); // Завжди провалиться
//	}

	@Test
	void testSyntaxErrorExpression() {
		// Arrange
		String expression = "1+";

		// Act
		Map<String, String> response = calculatorController.calculateExpression(expression);

		// Assert
		assertNotNull(response);
		assertTrue(response.containsKey("error"));
		assertEquals("Invalid expression!", response.get("error"));
	}

}
