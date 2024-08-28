package com.ericsson.openapi.portal.mongo;
import com.ericsson.openapi.portal.repository.MicroserviceRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class MongoDatabaseSeederTest
{

	@Mock
	private MicroserviceRepository microserviceRepository;

	@InjectMocks
	private MongoDatabaseSeeder mongoDatabaseSeeder;

	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSeedData_Success()
	{
		// Mock the behavior of the MicroserviceRepository
		when(microserviceRepository.saveAll(anyList())).thenReturn(null);

		// Call the seedData method
		mongoDatabaseSeeder.seedData();

		// Verify that the MicroserviceRepository.deleteAll() and saveAll() methods were called
		verify(microserviceRepository, times(1)).deleteAll();
		verify(microserviceRepository, times(1)).saveAll(anyList());
	}

	@Test
	public void testSeedData_Failure()
	{
		// Mock the behavior of the MicroserviceRepository
		when(microserviceRepository.saveAll(anyList())).thenReturn(null);

		// Call the seedData method
		mongoDatabaseSeeder.seedData();

		// Verify that the MicroserviceRepository.deleteAll() and saveAll() methods were called
		verify(microserviceRepository, times(1)).deleteAll();
		verify(microserviceRepository, times(1)).saveAll(anyList());
	}
}