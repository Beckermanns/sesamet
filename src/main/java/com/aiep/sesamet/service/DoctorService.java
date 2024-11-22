package com.aiep.sesamet.service;

import com.aiep.sesamet.domain.Doctor;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.sesamet.domain.Doctor}.
 */
public interface DoctorService {
    /**
     * Save a doctor.
     *
     * @param doctor the entity to save.
     * @return the persisted entity.
     */
    Doctor save(Doctor doctor);

    /**
     * Updates a doctor.
     *
     * @param doctor the entity to update.
     * @return the persisted entity.
     */
    Doctor update(Doctor doctor);

    /**
     * Partially updates a doctor.
     *
     * @param doctor the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Doctor> partialUpdate(Doctor doctor);

    /**
     * Get all the doctors.
     *
     * @return the list of entities.
     */
    List<Doctor> findAll();

    /**
     * Get the "id" doctor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Doctor> findOne(Long id);

    /**
     * Delete the "id" doctor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
