package com.aiep.sesamet.service.impl;

import com.aiep.sesamet.domain.Paciente;
import com.aiep.sesamet.repository.PacienteRepository;
import com.aiep.sesamet.service.PacienteService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.sesamet.domain.Paciente}.
 */
@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    private static final Logger LOG = LoggerFactory.getLogger(PacienteServiceImpl.class);

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente save(Paciente paciente) {
        LOG.debug("Request to save Paciente : {}", paciente);
        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente update(Paciente paciente) {
        LOG.debug("Request to update Paciente : {}", paciente);
        return pacienteRepository.save(paciente);
    }

    @Override
    public Optional<Paciente> partialUpdate(Paciente paciente) {
        LOG.debug("Request to partially update Paciente : {}", paciente);

        return pacienteRepository
            .findById(paciente.getId())
            .map(existingPaciente -> {
                if (paciente.getRut_paciente() != null) {
                    existingPaciente.setRut_paciente(paciente.getRut_paciente());
                }
                if (paciente.getNombres() != null) {
                    existingPaciente.setNombres(paciente.getNombres());
                }
                if (paciente.getApellido_paterno() != null) {
                    existingPaciente.setApellido_paterno(paciente.getApellido_paterno());
                }
                if (paciente.getApellido_materno() != null) {
                    existingPaciente.setApellido_materno(paciente.getApellido_materno());
                }
                if (paciente.getTelefono() != null) {
                    existingPaciente.setTelefono(paciente.getTelefono());
                }
                if (paciente.getCorreo() != null) {
                    existingPaciente.setCorreo(paciente.getCorreo());
                }
                if (paciente.getDireccion() != null) {
                    existingPaciente.setDireccion(paciente.getDireccion());
                }
                if (paciente.getComuna() != null) {
                    existingPaciente.setComuna(paciente.getComuna());
                }

                return existingPaciente;
            })
            .map(pacienteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> findAll() {
        LOG.debug("Request to get all Pacientes");
        return pacienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> findOne(Long id) {
        LOG.debug("Request to get Paciente : {}", id);
        return pacienteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Paciente : {}", id);
        pacienteRepository.deleteById(id);
    }
}
