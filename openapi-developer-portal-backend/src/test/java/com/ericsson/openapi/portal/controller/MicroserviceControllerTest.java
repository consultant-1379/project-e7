package com.ericsson.openapi.portal.controller;

import com.ericsson.openapi.portal.exception.MicroserviceCollectionException;
import com.ericsson.openapi.portal.model.Microservice;
import com.ericsson.openapi.portal.service.MicroserviceService;
import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MicroserviceControllerTest
{
	@Mock
	private MicroserviceService microserviceService;

	@InjectMocks
	private MicroserviceController microserviceController;

	private ArrayList<Microservice> microservices = new ArrayList<>();

	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);

		Faker faker = new Faker();

		for(int i = 0; i < 10; i++)
		{
			Microservice microservice = new Microservice();
			microservice.setTitle(faker.hacker().abbreviation() + " " + faker.hacker().noun() + " API");
			microservice.setDescription(faker.gameOfThrones().quote() + " " + faker.lorem().paragraph());
			microservice.setCategory(faker.hacker().noun());
			microservice.setName(faker.name().fullName());
			microservice.setEmail(faker.internet().emailAddress());
			microservice.setVersion("3.0." + faker.number().numberBetween(0, 3));
			microservice.setSpecification(faker.internet().url());
			microservices.add(microservice);
		}
	}

	@Test
	public void TestCheckHealth()
	{
		ResponseEntity response = microserviceController.checkHealth();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Congratulations!! Open API Application is up and running..", response.getBody());
	}

	@Test
	public void TestGetAllMicroservices()
	{
		// Mock the behavior of the microserviceService.getAllMicroservices() method
		Mockito.when(microserviceService.getAllMicroservices()).thenReturn(microservices);

		// Call the controller method
		ResponseEntity response = microserviceController.getAllMicroservices();

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(microservices, response.getBody());

		// Verify that the microserviceService.getAllMicroservices() method was called
		Mockito.verify(microserviceService, Mockito.times(1)).getAllMicroservices();
	}

	@Test
	public void TestGetMicroserviceById_Success() throws MicroserviceCollectionException
	{
		// Mock the behavior of the microserviceService.getMicroserviceById(id) method
		Mockito.when(microserviceService.getMicroserviceById(ArgumentMatchers.anyString()))
				.thenReturn(microservices.get(0));

		// Call the controller method with a mock ID
		ResponseEntity response = microserviceController.getMicroserviceById("mockId");

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(microservices.get(0), response.getBody());

		// Verify that the microserviceService.getMicroserviceById(id) method was called with the correct ID
		Mockito.verify(microserviceService, Mockito.times(1)).getMicroserviceById("mockId");
	}

	@Test
	public void TestGetMicroserviceById_Failure() throws MicroserviceCollectionException
	{
		// Mock the behavior of the microserviceService.getMicroserviceById(id) method to throw an exception
		when(microserviceService.getMicroserviceById(anyString()))
				.thenThrow(new MicroserviceCollectionException("MICROSERVICE DOES NOT EXIST"));

		// Call the controller method with a mock id
		ResponseEntity response = microserviceController.getMicroserviceById("mockId");

		// Verify the response
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("MICROSERVICE DOES NOT EXIST", response.getBody());

		// Verify that the microserviceService.getMicroserviceById(id) method was called with the correct id
		verify(microserviceService, times(1)).getMicroserviceById("mockId");
	}

	@Test
	public void TestSearchMicroservicesByCategory_Success()
	{
		// Create a list of mock Microservice objects
		List<Microservice> microservices = new ArrayList<>();
		microservices.add(new Microservice(/* Initialize with required data */));
		// Add more mock Microservice objects as needed

		// Mock the behavior of the microserviceService.searchMicroservicesByCategory(category) method
		Mockito.when(microserviceService.searchMicroservicesByCategory(ArgumentMatchers.anyString())).thenReturn(
				microservices);

		// Call the controller method with a mock category
		ResponseEntity response = microserviceController.searchMicroservicesByCategory("mockCategory");

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(microservices, response.getBody());

		// Verify that the microserviceService.searchMicroservicesByCategory(category) method was called with the correct category
		Mockito.verify(microserviceService, Mockito.times(1)).searchMicroservicesByCategory("mockCategory");
	}

	@Test
	public void TestSearchMicroservicesByLead_Success()
	{
		// Create a list of mock Microservice objects
		List<Microservice> microservices = new ArrayList<>();
		microservices.add(new Microservice(/* Initialize with required data */));
		// Add more mock Microservice objects as needed

		// Mock the behavior of the microserviceService.searchMicroservicesByLead(leadName) method
		Mockito.when(microserviceService.searchMicroservicesByLead(ArgumentMatchers.anyString())).thenReturn(
				microservices);

		// Call the controller method with a mock lead name
		ResponseEntity response = microserviceController.searchMicroservicesByLead("mockLead");

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(microservices, response.getBody());

		// Verify that the microserviceService.searchMicroservicesByLead(leadName) method was called with the correct lead name
		Mockito.verify(microserviceService, Mockito.times(1)).searchMicroservicesByLead("mockLead");
	}

	@Test
	public void TestSearchMicroservicesByName_Success()
	{
		// Create a list of mock Microservice objects
		List<Microservice> microservices = new ArrayList<>();
		microservices.add(new Microservice(/* Initialize with required data */));
		// Add more mock Microservice objects as needed

		// Mock the behavior of the microserviceService.searchMicroservicesByName(name) method
		Mockito.when(microserviceService.searchMicroservicesByName(ArgumentMatchers.anyString())).thenReturn(
				microservices);

		// Call the controller method with a mock name
		ResponseEntity response = microserviceController.searchMicroservicesByName("mockName");

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(microservices, response.getBody());

		// Verify that the microserviceService.searchMicroservicesByName(name) method was called with the correct name
		Mockito.verify(microserviceService, Mockito.times(1)).searchMicroservicesByName("mockName");
	}

	@Test
	public void TestListMicroservicesInCategoryWithDependencies_Success()
	{
		// Create a list of mock Microservice objects
		List<Microservice> microservices = new ArrayList<>();
		microservices.add(new Microservice(/* Initialize with required data */));
		// Add more mock Microservice objects as needed

		// Mock the behavior of the microserviceService.listMicroservicesInCategoryWithDependencies(category, dependency) method
		Mockito.when(microserviceService.listMicroservicesInCategoryWithDependencies(ArgumentMatchers.anyString(),
		                                                                             ArgumentMatchers.anyString()))
				.thenReturn(
						microservices);

		// Call the controller method with mock category and dependency
		ResponseEntity response = microserviceController.listMicroservicesInCategoryWithDependencies("mockCategory",
		                                                                                             "mockDependency");

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(microservices, response.getBody());

		// Verify that the microserviceService.listMicroservicesInCategoryWithDependencies(category, dependency) method was called with the correct parameters
		Mockito.verify(microserviceService, Mockito.times(1))
				.listMicroservicesInCategoryWithDependencies("mockCategory",
				                                             "mockDependency");
	}

	@Test
	public void TestListMicroservicesWithNoDependencies_Success()
	{
		// Create a list of mock Microservice objects
		List<Microservice> microservices = new ArrayList<>();
		microservices.add(new Microservice(/* Initialize with required data */));
		// Add more mock Microservice objects as needed

		// Mock the behavior of the microserviceService.listMicroservicesWithNoDependencies() method
		Mockito.when(microserviceService.listMicroservicesWithNoDependencies()).thenReturn(microservices);

		// Call the controller method
		ResponseEntity response = microserviceController.listMicroservicesWithNoDependencies();

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(microservices, response.getBody());

		// Verify that the microserviceService.listMicroservicesWithNoDependencies() method was called
		Mockito.verify(microserviceService, Mockito.times(1)).listMicroservicesWithNoDependencies();
	}

	@Test
	public void TestCreateMicroservice_Success() throws MicroserviceCollectionException
	{
		// Mock the behavior of the microserviceService.createMicroservice(microservice) method
		Mockito.doNothing().when(microserviceService).createMicroservice(ArgumentMatchers.any(Microservice.class));

		// Call the controller method with mock parameters (since it doesn't take a single Microservice parameter)
		ResponseEntity response = microserviceController.createMicroservice(
				"mockTitle", "mockDescription", "mockCategory", "mockName", "mockEmail",
				"mockVersion", "", "mockSpecification");

		// Verify the response
		assertEquals("MICROSERVICE CREATED SUCCESSFULLY",
		             response.getBody());

		// Verify that the microserviceService.createMicroservice(microservice) method was called with the correct parameters
		Mockito.verify(microserviceService, Mockito.times(1))
				.createMicroservice(ArgumentMatchers.any(Microservice.class));
	}

	@Test
	public void TestCreateMicroservice_Random() throws MicroserviceCollectionException
	{

		// Mock the behavior of the microserviceService.createMicroservice(microservice) method
		Mockito.doNothing().when(microserviceService).createMicroservice(ArgumentMatchers.any(Microservice.class));

		// Call the controller method with mock parameters (since it doesn't take a single Microservice parameter)
		ResponseEntity response = microserviceController.createMicroservice();

		// Verify the response
		assertEquals("MICROSERVICE CREATED SUCCESSFULLY",
		             response.getBody());

		// Verify that the microserviceService.createMicroservice(microservice) method was called with the correct parameters
		Mockito.verify(microserviceService, Mockito.times(1))
				.createMicroservice(ArgumentMatchers.any(Microservice.class));

	}

	@Test
	public void TestCreateMicroservice_Failure() throws MicroserviceCollectionException
	{
		// Create a mock Microservice object
		Microservice microservice = new Microservice(
				"mockTitle",
				"mockDescription",
				"mockCategory",
				"mockName",
				"mockEmail",
				new Date(),
				"mockVersion",
				new ArrayList<>(),
				"mockSpecification");

		// Mock the behavior of the microserviceService.createMicroservice(microservice) method to throw an exception
		Mockito.doThrow(new MicroserviceCollectionException("Microservice already exists"))
				.when(microserviceService).createMicroservice(ArgumentMatchers.any(Microservice.class));

		// Call the controller method with mock parameters (since it doesn't take a single Microservice parameter)
		ResponseEntity response = microserviceController.createMicroservice(
				"mockTitle", "mockDescription", "mockCategory", "mockName", "mockEmail",
				"mockVersion", "", "mockSpecification");

		// Verify the response
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertEquals("Microservice already exists", response.getBody());

		// Verify that the microserviceService.createMicroservice(microservice) method was called with the correct parameters
		Mockito.verify(microserviceService, Mockito.times(1))
				.createMicroservice(ArgumentMatchers.any(Microservice.class));
	}

	@Test
	public void TestUpdateMicroserviceById_Success() throws MicroserviceCollectionException
	{

		Mockito.doNothing().when(microserviceService).updateMicroservice(ArgumentMatchers.anyString(),
		                                                                 ArgumentMatchers.anyString(),
		                                                                 ArgumentMatchers.anyString(),
		                                                                 ArgumentMatchers.anyString(),
		                                                                 ArgumentMatchers.anyString(),
		                                                                 ArgumentMatchers.anyString(),
		                                                                 ArgumentMatchers.anyString(),
		                                                                 ArgumentMatchers.anyString(),
		                                                                 ArgumentMatchers.anyString());

		// Call the controller method with a mock id and Microservice object
		ResponseEntity response = microserviceController.updateMicroservice("mockid", "mockTitle", "mockDescription",
		                                                                    "mockCategory", "mockName", "mockEmail",
		                                                                    "mockVersion", "", "mockSpecification");

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("UPDATE REQUEST FOR MICROSERVICE WITH ID SUCCESSFUL", response.getBody());

		// Verify that the microserviceService.updateMicroserviceById(id, microservice) method was called with the correct id and Microservice object
		Mockito.verify(microserviceService, Mockito.times(1)).updateMicroservice("mockid",
		                                                                         "mockTitle",
		                                                                         "mockDescription",
		                                                                         "mockCategory",
		                                                                         "mockName",
		                                                                         "mockEmail",
		                                                                         "mockVersion",
		                                                                         "",
		                                                                         "mockSpecification");
	}

	@Test
	public void TestUpdateMicroserviceById_Failure() throws MicroserviceCollectionException
	{
		// Create a mock Microservice object
		Microservice microservice = new Microservice(
				"mockTitle",
				"mockDescription",
				"mockCategory",
				"mockName",
				"mockEmail",
				new Date(),
				"mockVersion",
				new ArrayList<>(),
				"mockSpecification");

		// Mock the behavior of the microserviceService.updateMicroserviceBy(id, microservice) method
		Mockito.doThrow(new MicroserviceCollectionException(""))
				.when(microserviceService)
				.updateMicroservice(
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyString(),
						ArgumentMatchers.anyString()
				);

		ResponseEntity response = microserviceController.updateMicroservice("mockId", "mockTitle", "mockDescription",
		                                                                    "mockCategory", "mockName", "mockEmail",
		                                                                    "mockVersion", "", "mockSpecification");

		// Verify the response
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		// Verify that the microserviceService.updateMicroserviceById(id, microservice) method was called with the correct id and Microservice object
		Mockito.verify(microserviceService, Mockito.times(1)).updateMicroservice("mockId",
		                                                                         "mockTitle",
		                                                                         "mockDescription",
		                                                                         "mockCategory",
		                                                                         "mockName",
		                                                                         "mockEmail",
		                                                                         "mockVersion",
		                                                                         "",
		                                                                         "mockSpecification");
	}

	@Test
	public void TestDeleteMicroserviceById_Success() throws MicroserviceCollectionException
	{
		// Create a mock Microservice object
		Microservice microservice = new Microservice(
				"mockTitle",
				"mockDescription",
				"mockCategory",
				"mockName",
				"mockEmail",
				new Date(),
				"mockVersion",
				new ArrayList<>(),
				"mockSpecification");

		// Mock the behavior of the microserviceService.deleteMicroserviceById(id) method
		Mockito.doNothing().when(microserviceService).deleteMicroserviceById(ArgumentMatchers.anyString());

		// Call the controller method with a mock id
		ResponseEntity response = microserviceController.deleteMicroserviceById("mockId");

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("DELETE REQUEST FOR MICROSERVICE WITH ID SUCCESSFUL", response.getBody());

		// Verify that the microserviceService.deleteMicroserviceById(id) method was called with the correct id
		Mockito.verify(microserviceService, Mockito.times(1)).deleteMicroserviceById("mockId");
	}

	@Test
	public void TestDeleteMicroserviceById_Failure() throws MicroserviceCollectionException
	{
		// Create a mock Microservice object
		Microservice microservice = new Microservice(
				"mockTitle",
				"mockDescription",
				"mockCategory",
				"mockName",
				"mockEmail",
				new Date(),
				"mockVersion",
				new ArrayList<>(),
				"mockSpecification");

		// Mock the behavior of the microserviceService.deleteMicroserviceById(id) method
		Mockito.doThrow(new MicroserviceCollectionException("")).when(microserviceService).deleteMicroserviceById(
				ArgumentMatchers.anyString());

		// Call the controller method with a mock id
		ResponseEntity response = microserviceController.deleteMicroserviceById("mockId");

		// Verify the response
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		// Verify that the microserviceService.deleteMicroserviceById(id) method was called with the correct id
		Mockito.verify(microserviceService, Mockito.times(1)).deleteMicroserviceById("mockId");
	}

	@Test
	public void TestDeleteAllMicroservices_Success() throws MicroserviceCollectionException
	{
		// Create a mock Microservice object

		// Mock the behavior of the microserviceService.deleteAllMicroservices() method
		Mockito.doNothing().when(microserviceService).deleteAllMicroservices();

		// Call the controller method
		ResponseEntity response = microserviceController.deleteAllMicroservices();

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("ALL MICROSERVICES DELETED", response.getBody());

		// Verify that the microserviceService.deleteAllMicroservices() method was called
		Mockito.verify(microserviceService, Mockito.times(1)).deleteAllMicroservices();
	}

	@Test
	public void TestDeleteAllMicroservices_Failure() throws MicroserviceCollectionException
	{
		// Mock the behavior of the microserviceService.deleteAllMicroservices() method
		Mockito.doThrow(new MicroserviceCollectionException("mockMessage"))
				.when(microserviceService)
				.deleteAllMicroservices();

		// Call the controller method
		ResponseEntity response = microserviceController.deleteAllMicroservices();

		// Verify the response
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("mockMessage", response.getBody());

		// Verify that the microserviceService.deleteAllMicroservices() method was called
		Mockito.verify(microserviceService, Mockito.times(1)).deleteAllMicroservices();
	}

	@After
	public void tearDown() throws Exception
	{
		Mockito.reset(microserviceService);
	}
}