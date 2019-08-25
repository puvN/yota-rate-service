package com.puvn.yotarateservice.integration;

import com.puvn.yotarateservice.builder.SimBuilder;
import com.puvn.yotarateservice.entity.Sim;
import com.puvn.yotarateservice.exception.BusinessError;
import com.puvn.yotarateservice.exception.BusinessException;
import com.puvn.yotarateservice.repository.SimsRepository;
import com.puvn.yotarateservice.service.ActionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Eugeniy Lukin (eugeniylukin@gmail.com) created on 25.08.2019.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ActionServiceImplIntegrationTest {

	@Spy
	private SimsRepository simsRepository;

	@InjectMocks
	private ActionServiceImpl actionService;

	private final long testIccid = 6246125898765872320L;

	private Sim activeTestSim;

	@BeforeEach
	void setUp() {
		activeTestSim = new SimBuilder()
				.id(1L)
				.iccid(testIccid)
				.active(true)
				.minutes(90)
				.gigabytes(10)
				.minutesExpire(LocalDateTime.of(2019, 2, 1, 12, 0))
				.gigabytesExpire(LocalDateTime.of(2019, 2, 1, 13, 0))
				.build();
		when(simsRepository.findByIccidAndActive(eq(testIccid), eq(true)))
				.thenReturn(Optional.of(activeTestSim));
		when(simsRepository.findByIccid(eq(testIccid))).thenReturn(Optional.of(activeTestSim));
	}

	@Test
	void testAddMinutesToSim() throws BusinessException {
		var result = actionService.addMinutes(testIccid, 30,
				LocalDateTime.of(2019, 3, 1, 12, 0));
		assertEquals(120, result);
		verify(simsRepository, times(1))
				.findByIccidAndActive(testIccid, true);
		verify(simsRepository, times(1)).save(activeTestSim);
	}

	@Test
	void testAddGigsToSim() throws BusinessException {
		var result = actionService.addGigabytes(testIccid, 3,
				LocalDateTime.of(2019, 3, 1, 12, 0));
		assertEquals(13, result);
		verify(simsRepository, times(1))
				.findByIccidAndActive(testIccid, true);
		verify(simsRepository, times(1)).save(activeTestSim);
	}

	@Test
	void testGetMinutes() throws BusinessException {
		var result = actionService.getMinutesByIccid(testIccid);
		assertEquals(90, result);
		verify(simsRepository, times(1))
				.findByIccidAndActive(testIccid, true);
	}

	@Test
	void testGetGigs() throws BusinessException {
		var result = actionService.getGigsByIccid(testIccid);
		assertEquals(10, result);
		verify(simsRepository, times(1))
				.findByIccidAndActive(testIccid, true);
	}

	@Test
	void testTTLExpiredSpendMinutes() {
		var exception = assertThrows(BusinessException.class,
				() -> actionService.spendMinutes(testIccid, 3));
		assertEquals(BusinessError.B001.getMessage(), exception.getMessage());
	}

	@Test
	void testSpendMinutes() throws BusinessException {
		var testSim = new SimBuilder()
				.iccid(testIccid)
				.active(true)
				.minutes(90)
				.minutesExpire(LocalDateTime.of(2100, 2, 1, 12, 0))
				.build();
		when(simsRepository.findByIccidAndActive(eq(testIccid), eq(true)))
				.thenReturn(Optional.of(testSim));
		var result = actionService.spendMinutes(testIccid, 3);
		assertEquals(87, result);
	}

	@Test
	void testSpendGigs() throws BusinessException {
		var testSim = new SimBuilder()
				.iccid(testIccid)
				.active(true)
				.gigabytes(3)
				.gigabytesExpire(LocalDateTime.of(2100, 2, 1, 12, 0))
				.build();
		when(simsRepository.findByIccidAndActive(eq(testIccid), eq(true)))
				.thenReturn(Optional.of(testSim));
		var result = actionService.spendGigabytes(testIccid, 1);
		assertEquals(2, result);
	}

	@Test
	void testSimCardSetActive() throws BusinessException {
		var result = actionService.setSimCardActive(testIccid, false);
		assertFalse(result);
		result = actionService.setSimCardActive(testIccid, true);
		assertTrue(result);
	}

	@Test
	void testTTLExpiredSpendGigs() {
		var exception = assertThrows(BusinessException.class,
				() -> actionService.spendGigabytes(testIccid, 3));
		assertEquals(BusinessError.B001.getMessage(), exception.getMessage());
	}

	@Test
	void testNoActiveSimCardByIccid() {
		var exception = assertThrows(BusinessException.class,
				() -> actionService.addMinutes(1111125898765872320L, 30,
						LocalDateTime.of(2019, 3, 1, 12, 0)));
		assertEquals(BusinessError.B002.getMessage(), exception.getMessage());
	}

}
