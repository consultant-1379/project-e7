package com.ericsson.openapi.portal.service;
import com.ericsson.openapi.portal.exception.MicroserviceCollectionException;
import com.ericsson.openapi.portal.model.Microservice;
import com.ericsson.openapi.portal.repository.MicroserviceRepository;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MicroserviceServiceTest
{

	@Mock
	private MicroserviceRepository microserviceRepository;
	@InjectMocks
	private MicroserviceService microserviceService;

	private ArrayList<Microservice> microservices = new ArrayList<>();

	@Before
	public void setUp() throws MicroserviceCollectionException
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
	public void TestGetAllMicroservices_Success()
	{

		when(microserviceRepository.findAll()).thenReturn(microservices);

		// Call the service method
		List<Microservice> result = microserviceService.getAllMicroservices();

		// Verify the result
		assertEquals(microservices, result);
	}

	@Test
	public void TestGetAllMicroservices_NoMicroservices()
	{
		// Mock an empty list of microservices
		when(microserviceRepository.findAll()).thenReturn(new ArrayList<>());

		// Call the service method
		List<Microservice> result = microserviceService.getAllMicroservices();

		// Verify the result
		assertTrue(result.isEmpty());
	}

	public void TestCreateMicroservice_Success() throws MicroserviceCollectionException
	{
		// Create a mock Microservice
		Microservice mockMicroservice = new Microservice();
		mockMicroservice.setTitle("Mock Title");
		// Set other properties...

		// Mock the repository's behavior
		when(microserviceRepository.findByTitle(any(String.class))).thenReturn(Optional.empty());
		when(microserviceRepository.save(any(Microservice.class))).thenReturn(mockMicroservice);

		// Call the service method
		microserviceService.createMicroservice(mockMicroservice);

		// Verify that repository methods were called
		verify(microserviceRepository, times(1)).findByTitle(any(String.class));
		verify(microserviceRepository, times(1)).save(any(Microservice.class));
	}

	@Test(expected = MicroserviceCollectionException.class)
	public void TestCreateMicroservice_TitleExists() throws MicroserviceCollectionException
	{
		// Create a mock Microservice with a title that already exists
		Microservice mockMicroservice = new Microservice();
		mockMicroservice.setTitle("Mock Title");

		// Mock the repository's behavior to return a Microservice with the same title
		when(microserviceRepository.findByTitle(any(String.class))).thenReturn(Optional.of(mockMicroservice));

		// Call the service method, which should throw an exception
		microserviceService.createMicroservice(mockMicroservice);
	}

	@Test
	public void TestUpdateMicroservice_Success() throws MicroserviceCollectionException
	{
		// Create an existing microservice and its updated version
		Microservice existingMicroservice = microservices.get(0);
		Microservice updatedMicroservice = new Microservice();
		updatedMicroservice.setId(existingMicroservice.getId());
		updatedMicroservice.setTitle("Updated Title");
		// Set other updated properties...

		// Mock the repository's behavior
		when(microserviceRepository.findById(existingMicroservice.getId())).thenReturn(Optional.of(existingMicroservice));
		when(microserviceRepository.findByTitle(any(String.class))).thenReturn(Optional.empty());
		when(microserviceRepository.save(any(Microservice.class))).thenReturn(updatedMicroservice);

		microserviceService.updateMicroservice(existingMicroservice.getId(), updatedMicroservice.getTitle(),
		                                       updatedMicroservice.getDescription(), updatedMicroservice.getCategory(),
		                                       updatedMicroservice.getName(), updatedMicroservice.getEmail(),
		                                       updatedMicroservice.getVersion(),
		                                      "",
		                                       updatedMicroservice.getSpecification());

		// Verify that repository methods were called
		verify(microserviceRepository, times(1)).findById(existingMicroservice.getId());
		verify(microserviceRepository, times(1)).findByTitle(any(String.class));
		verify(microserviceRepository, times(1)).save(any(Microservice.class));

		// Add your assertions here to verify the updatedMicroservice is properly updated
		assertEquals("Updated Title", updatedMicroservice.getTitle());
		// Add more assertions...
	}

	public void TestDeleteMicroserviceById_Success() throws MicroserviceCollectionException
	{
		// Create a mock microservice to delete
		microservices.get(0).setId("mockId");

		// Mock the repository's behavior
		when(microserviceRepository.findById(microservices.get(0)
				                                     .getId())).thenReturn(Optional.of(microservices.get(0)));

		// Call the service method#
		microserviceService.deleteMicroserviceById(microservices.get(0).getId());

		// Verify that repository method was called
		verify(microserviceRepository, times(1)).findById(microservices.get(0).getId());
		verify(microserviceRepository, times(1)).deleteById(microservices.get(0).getId());
	}

	@Test(expected = MicroserviceCollectionException.class)
	public void TestDeleteMicroserviceById_MicroserviceNotFound() throws MicroserviceCollectionException
	{
		// Create a mock microservice id
		String mockMicroserviceId = "nonExistentId";

		// Mock the repository's behavior
		when(microserviceRepository.findById(mockMicroserviceId)).thenReturn(Optional.empty());

		// Call the service method (this should throw an exception)
		microserviceService.deleteMicroserviceById(mockMicroserviceId);

		// Verify that repository method was called
		verify(microserviceRepository, times(1)).findById(mockMicroserviceId);
		verify(microserviceRepository, never()).deleteById(anyString());
	}

	@Test
	public void TestDeleteAllMicroservices_Success() throws MicroserviceCollectionException
	{

		// Mock the repository's behavior to return a list of microservices
		when(microserviceRepository.findAll()).thenReturn(microservices);

		// Call the service method
		microserviceService.deleteAllMicroservices();

		// Verify that repository method was called
		verify(microserviceRepository, times(1)).findAll();
		verify(microserviceRepository, times(1)).deleteAll();
	}

	@Test(expected = MicroserviceCollectionException.class)
	public void TestDeleteAllMicroservices_NoMicroservices() throws MicroserviceCollectionException
	{
		// Mock the repository's behavior to return an empty list of microservices
		when(microserviceRepository.findAll()).thenReturn(new ArrayList<>());

		// Call the service method (this should throw an exception)
		microserviceService.deleteAllMicroservices();

		// Verify that repository method was called
		verify(microserviceRepository, times(1)).findAll();
		verify(microserviceRepository, never()).deleteAll();
	}

	@Test
	public void TestGetMicroserviceById_Success() throws MicroserviceCollectionException
	{
		String microserviceId = "exampleId"; // Replace with an actual ID to search
		Microservice expectedMicroservice = new Microservice(); // Replace with an actual Microservice object
		// Mock the repository's behavior to return the expectedMicroservice when given the microserviceId
		when(microserviceRepository.findById(microserviceId)).thenReturn(Optional.of(expectedMicroservice));

		// Call the service method
		Microservice result = microserviceService.getMicroserviceById(microserviceId);

		// Verify that repository method was called
		verify(microserviceRepository, times(1)).findById(microserviceId);

		// Verify that the returned microservice matches the expected microservice
		assertEquals(expectedMicroservice, result);
	}

	@Test(expected = MicroserviceCollectionException.class)
	public void TestGetMicroserviceById_MicroserviceNotFound() throws MicroserviceCollectionException
	{
		String microserviceId = "nonExistentId"; // Replace with a non-existent ID
		// Mock the repository's behavior to return an empty optional
		when(microserviceRepository.findById(microserviceId)).thenReturn(Optional.empty());

		// Call the service method, expect an exception
		microserviceService.getMicroserviceById(microserviceId);
	}

	@Test
	public void TestSearchMicroservicesByCategory_Success()
	{
		String categoryToSearch = "exampleCategory"; // Replace with an actual category to search
		List<Microservice> expectedMicroservices = new ArrayList<>(); // Replace with actual list of Microservice objects
		// Mock the repository's behavior to return expectedMicroservices when given the categoryToSearch
		when(microserviceRepository.findByCategory(categoryToSearch)).thenReturn(expectedMicroservices);

		// Call the service method
		List<Microservice> result = microserviceService.searchMicroservicesByCategory(categoryToSearch);

		// Verify that repository method was called
		verify(microserviceRepository, times(1)).findByCategory(categoryToSearch);

		// Verify that the returned list matches the expected list
		assertEquals(expectedMicroservices, result);
	}

	@Test
	public void TestSearchMicroservicesByLead_Success()
	{
		String leadName = "exampleLead"; // Replace with an actual lead name to search
		List<Microservice> expectedMicroservices = new ArrayList<>(); // Replace with actual list of Microservice objects
		// Mock the repository's behavior to return expectedMicroservices when given the leadName
		when(microserviceRepository.findByName(leadName)).thenReturn(expectedMicroservices);

		// Call the service method
		List<Microservice> result = microserviceService.searchMicroservicesByLead(leadName);

		// Verify that repository method was called
		verify(microserviceRepository, times(1)).findByName(leadName);

		// Verify that the returned list matches the expected list
		assertEquals(expectedMicroservices, result);
	}

	@Test
	public void TestSearchMicroservicesByName_Success()
	{
		String microserviceName = "exampleName"; // Replace with an actual microservice name to search
		List<Microservice> expectedMicroservices = new ArrayList<>(); // Replace with actual list of Microservice objects
		// Mock the repository's behavior to return expectedMicroservices when given the microserviceName
		when(microserviceRepository.findByTitleIgnoreCaseContaining(microserviceName)).thenReturn(expectedMicroservices);

		// Call the service method
		List<Microservice> result = microserviceService.searchMicroservicesByName(microserviceName);

		// Verify that repository method was called
		verify(microserviceRepository, times(1)).findByTitleIgnoreCaseContaining(microserviceName);

		// Verify that the returned list matches the expected list
		assertEquals(expectedMicroservices, result);
	}

	@Test
	public void TestListMicroservicesInCategoryWithDependencies_Success()
	{
		String category = "exampleCategory"; // Replace with an actual category to search
		String dependency = "exampleDependency"; // Replace with an actual dependency to search
		List<Microservice> expectedMicroservices = new ArrayList<>(); // Replace with actual list of Microservice objects
		// Mock the repository's behavior to return expectedMicroservices when given the category and dependency
		when(microserviceRepository.findByCategoryAndDependenciesIn(category, dependency)).thenReturn(
				expectedMicroservices);

		// Call the service method
		List<Microservice> result = microserviceService.listMicroservicesInCategoryWithDependencies(category,
		                                                                                            dependency);

		// Verify that repository method was called
		verify(microserviceRepository, times(1)).findByCategoryAndDependenciesIn(category, dependency);

		// Verify that the returned list matches the expected list
		assertEquals(expectedMicroservices, result);
	}

	@Test
	public void TestListMicroservicesWithNoDependencies_Success()
	{
		// Mock the repository's behavior to return a list of microservices
		when(microserviceRepository.findByDependenciesIsEmpty()).thenReturn(microservices);

		// Call the service method
		List<Microservice> result = microserviceService.listMicroservicesWithNoDependencies();

		// Verify that repository method was called
		verify(microserviceRepository, times(1)).findByDependenciesIsEmpty();

		// Verify that the returned list matches the expected list
		assertEquals(microservices, result);
	}

	@Test
	public void TestListMicroservicesWithNoDependencies_NoMicroservices()
	{
		// Mock the repository's behavior to return an empty list of microservices
		when(microserviceRepository.findByDependenciesIsEmpty()).thenReturn(new ArrayList<>());

		// Call the service method
		List<Microservice> result = microserviceService.listMicroservicesWithNoDependencies();

		// Verify that repository method was called
		verify(microserviceRepository, times(1)).findByDependenciesIsEmpty();

		// Verify that the returned list is empty
		assertTrue(result.isEmpty());
	}
}
