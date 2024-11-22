package com.aiep.sesamet.service;

import com.aiep.sesamet.domain.Atencion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.aiep.sesamet.domain.Atencion}.
 */
public interface AtencionService {
    /**
     * Save a atencion.
     *
     * @param atencion the entity to save.
     * @return the persisted entity.
     */
    Atencion save(Atencion atencion);

    /**
     * Updates a atencion.
     *
     * @param atencion the entity to update.
     * @return the persisted entity.
     */
    Atencion update(Atencion atencion);

    /**
     * Partially updates a atencion.
     *
     * @param atencion the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Atencion> partialUpdate(Atencion atencion);

    /**
     * Get all the atencions.
     *
     * @return the list of entities.
     */
    List<Atencion> findAll();

    /**
     * Get all the atencions with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Atencion> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" atencion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Atencion> findOne(Long id);

    /**
     * Delete the "id" atencion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
