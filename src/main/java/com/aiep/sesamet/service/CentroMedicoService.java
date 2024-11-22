package com.aiep.sesamet.service;

import com.aiep.sesamet.domain.CentroMedico;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.sesamet.domain.CentroMedico}.
 */
public interface CentroMedicoService {
    /**
     * Save a centroMedico.
     *
     * @param centroMedico the entity to save.
     * @return the persisted entity.
     */
    CentroMedico save(CentroMedico centroMedico);

    /**
     * Updates a centroMedico.
     *
     * @param centroMedico the entity to update.
     * @return the persisted entity.
     */
    CentroMedico update(CentroMedico centroMedico);

    /**
     * Partially updates a centroMedico.
     *
     * @param centroMedico the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CentroMedico> partialUpdate(CentroMedico centroMedico);

    /**
     * Get all the centroMedicos.
     *
     * @return the list of entities.
     */
    List<CentroMedico> findAll();

    /**
     * Get the "id" centroMedico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CentroMedico> findOne(Long id);

    /**
     * Delete the "id" centroMedico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
