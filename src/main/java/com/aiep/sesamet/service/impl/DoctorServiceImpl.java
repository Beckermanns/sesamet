package com.aiep.sesamet.service.impl;

import com.aiep.sesamet.domain.Doctor;
import com.aiep.sesamet.repository.DoctorRepository;
import com.aiep.sesamet.service.DoctorService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.sesamet.domain.Doctor}.
 */
@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private static final Logger LOG = LoggerFactory.getLogger(DoctorServiceImpl.class);

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor save(Doctor doctor) {
        LOG.debug("Request to save Doctor : {}", doctor);
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor update(Doctor doctor) {
        LOG.debug("Request to update Doctor : {}", doctor);
        return doctorRepository.save(doctor);
    }

    @Override
    public Optional<Doctor> partialUpdate(Doctor doctor) {
        LOG.debug("Request to partially update Doctor : {}", doctor);

        return doctorRepository
            .findById(doctor.getId())
            .map(existingDoctor -> {
                if (doctor.getRut_doctor() != null) {
                    existingDoctor.setRut_doctor(doctor.getRut_doctor());
                }
                if (doctor.getNombre() != null) {
                    existingDoctor.setNombre(doctor.getNombre());
                }
                if (doctor.getApellido_paterno() != null) {
                    existingDoctor.setApellido_paterno(doctor.getApellido_paterno());
                }
                if (doctor.getApellido_materno() != null) {
                    existingDoctor.setApellido_materno(doctor.getApellido_materno());
                }
                if (doctor.getTelefono() != null) {
                    existingDoctor.setTelefono(doctor.getTelefono());
                }
                if (doctor.getCorreo() != null) {
                    existingDoctor.setCorreo(doctor.getCorreo());
                }
                if (doctor.getEspecialidad() != null) {
                    existingDoctor.setEspecialidad(doctor.getEspecialidad());
                }

                return existingDoctor;
            })
            .map(doctorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Doctor> findAll() {
        LOG.debug("Request to get all Doctors");
        return doctorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Doctor> findOne(Long id) {
        LOG.debug("Request to get Doctor : {}", id);
        return doctorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Doctor : {}", id);
        doctorRepository.deleteById(id);
    }
}
