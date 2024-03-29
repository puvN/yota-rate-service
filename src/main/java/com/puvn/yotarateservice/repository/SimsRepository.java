package com.puvn.yotarateservice.repository;

import com.puvn.yotarateservice.entity.Sim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Eugeniy Lukin (eugeniylukin@gmail.com) created on 23.08.2019.
 */
public interface SimsRepository extends JpaRepository<Sim, Long> {

	Optional<Sim> findByIccidAndActive(long iccid, boolean active);

	Optional<Sim> findByIccid(Long iccid);

}
