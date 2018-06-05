package com.company.service;

import com.company.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doAnswer;

public class LocationServiceTest {

	@Mock
	private GoogleService googleService;

	@InjectMocks
	private LocationService locationService;

	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testMockCreation() {
		assertNotNull(locationService);
	}

	@Test(expected=Exception.class)
	public void findLocalTimeWithWrongTimeFormat() throws Exception {
		final List<Location> locations = new ArrayList<>();
		final Location auckland = new Location();
		auckland.setUtcTime("2013-07-10");
		auckland.setLatitude("-44.490947");
		auckland.setLongitude("171.220966");
		locations.add(auckland);

		doAnswer(
				(InvocationOnMock invocation) -> CompletableFuture.completedFuture("Pacific/Auckland")
		).when(googleService).getLocalTime(auckland);

		locationService.findLocalTime(locations);
	}

	@Test
	public void findLocalTimeWithProperTimeFormat() throws Exception {
		final List<Location> locations = new ArrayList<>();
		final Location auckland = new Location();
		auckland.setUtcTime("2013-07-10 02:52:49");
		auckland.setLatitude("-44.490947");
		auckland.setLongitude("171.220966");
		locations.add(auckland);

		doAnswer(
				(InvocationOnMock invocation) -> CompletableFuture.completedFuture("Pacific/Auckland")
		).when(googleService).getLocalTime(auckland);

		List<Location> result = locationService.findLocalTime(locations);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(result.get(0).getTimeZone(), "Pacific/Auckland");
		assertEquals(result.get(0).getLocalTime(), "2013-07-10T14:52:49");
	}

}
