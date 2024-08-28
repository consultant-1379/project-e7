package com.ericsson.openapi.portal;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OpenAPIDeveloperPortalApplicationTest
{

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setUp()
	{
		port = 8080;
		restTemplate = new TestRestTemplate();
	}

	@Test
	public void testMain_Failure()
	{
		OpenAPIDeveloperPortalApplication.main(new String[]{});

		assertEquals(404, this.restTemplate.getForEntity("http://localhost:" + port + "/",
		                                                 String.class).getStatusCodeValue());
	}

}
