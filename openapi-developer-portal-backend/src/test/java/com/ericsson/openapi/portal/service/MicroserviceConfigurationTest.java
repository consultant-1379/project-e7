package com.ericsson.openapi.portal.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.assertj.core.api.Assertions.assertThat;

public class MicroserviceConfigurationTest
{
	@Mock
	private MicroserviceConfiguration configuration;

	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);

		configuration = new MicroserviceConfiguration();
	}


	@Test
	public void TestAddCorsMappings()
	{
		CorsRegistry registry = new CorsRegistry();

		configuration.addCorsMappings(registry);

		assertThat(registry).isNotNull();
	}
}
