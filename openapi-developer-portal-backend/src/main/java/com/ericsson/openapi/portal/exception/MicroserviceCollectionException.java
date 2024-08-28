package com.ericsson.openapi.portal.exception;

public class MicroserviceCollectionException extends Exception
{

	static final String[] errors = {"MICROSERVICE NOT FOUND", "TITLE ALREADY EXISTS",
			"MICROSERVICE" + " MISSING FIELD: "};

	public MicroserviceCollectionException(String message)
	{
		super(message);
	}

	public static String notFoundException()
	{
		return errors[0];
	}

	public static String titleAlreadyExists()
	{
		return errors[1];
	}

	public static String missingField(String fieldName)
	{
		return errors[2] + fieldName;
	}

}
