package com.ericsson.openapi.portal.exception;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class MicroserviceCollectionExceptionTest
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
	public void TestNotFoundException()
	{
		String id = "123";
		String expectedMessage = "MICROSERVICE NOT FOUND";
		String actualMessage = MicroserviceCollectionException.notFoundException();
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void TestTitleAlreadyExists()
	{
		String expectedMessage = "TITLE ALREADY EXISTS";
		String actualMessage = MicroserviceCollectionException.titleAlreadyExists();
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void TestMissingField()
	{
		String fieldName = "name";
		String expectedMessage = "MICROSERVICE MISSING FIELD: " + fieldName;
		String actualMessage = MicroserviceCollectionException.missingField(fieldName);
		assertEquals(expectedMessage, actualMessage);
	}
}