package com.ericsson.openapi.portal.mongo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;
public class MongoValidationConfigTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void TestValidatingMongoEventListener()
	{
		// Create an instance of MongoValidationConfig
		MongoValidationConfig config = new MongoValidationConfig();

		// Call the validatingMongoEventListener method
		ValidatingMongoEventListener listener = config.validatingMongoEventListener();

		// Verify that the listener is not null
		assertNotNull(listener);
	}
	@Test
	public void TestValidator() {
		// Create an instance of MongoValidationConfig
		MongoValidationConfig config = new MongoValidationConfig();

		// Call the validator method
		LocalValidatorFactoryBean validator = config.validator();

		// Verify that the validator is not null
		assertNotNull(validator);
	}
}