package com.aiep.sesamet.repository;

import com.aiep.sesamet.domain.CentroMedico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CentroMedico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentroMedicoRepository extends JpaRepository<CentroMedico, Long> {}
