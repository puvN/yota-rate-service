package com.puvn.yotarateservice.dataJpa;

import com.puvn.yotarateservice.builder.SimBuilder;
import com.puvn.yotarateservice.repository.SimsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Eugeniy Lukin (eugeniylukin@gmail.com) created on 25.08.2019.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class SimsRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private SimsRepository simsRepository;

	@Before
	public void setUp() {
		var iccid1 = 6246125898765872320L;
		var iccid2 = 9146125898765872320L;
		var sim1 = new SimBuilder().iccid(iccid1).active(true).build();
		var sim2 = new SimBuilder().iccid(iccid2).active(false).build();
		entityManager.persist(sim1);
		entityManager.persist(sim2);
		entityManager.flush();
	}

	@Test
	public void testFindSimByIccid() {
		var foundSim = simsRepository.findByIccid(6246125898765872320L);
		assertNotNull(foundSim);
		assertTrue(foundSim.isPresent());
		assertNotNull(foundSim.get());
		assertEquals(6246125898765872320L, (long) foundSim.get().getIccid());
	}

	@Test
	public void testFindSimByIccidAndActive() {
		var foundSim1 = simsRepository.findByIccidAndActive(6246125898765872320L, true);
		assertNotNull(foundSim1);
		assertTrue(foundSim1.isPresent());
		assertNotNull(foundSim1.get());
		assertEquals(6246125898765872320L, (long) foundSim1.get().getIccid());
		var notFoundSim2 = simsRepository.findByIccidAndActive(9146125898765872320L, true);
		assertTrue(notFoundSim2.isEmpty());
		var foundSim2 = simsRepository.findByIccidAndActive(9146125898765872320L, false);
		assertNotNull(foundSim2);
		assertTrue(foundSim2.isPresent());
		assertNotNull(foundSim2.get());
		assertEquals(9146125898765872320L, (long) foundSim2.get().getIccid());
	}

}
