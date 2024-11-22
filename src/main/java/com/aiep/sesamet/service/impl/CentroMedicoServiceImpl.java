package com.aiep.sesamet.service.impl;

import com.aiep.sesamet.domain.CentroMedico;
import com.aiep.sesamet.repository.CentroMedicoRepository;
import com.aiep.sesamet.service.CentroMedicoService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.sesamet.domain.CentroMedico}.
 */
@Service
@Transactional
public class CentroMedicoServiceImpl implements CentroMedicoService {

    private static final Logger LOG = LoggerFactory.getLogger(CentroMedicoServiceImpl.class);

    private final CentroMedicoRepository centroMedicoRepository;

    public CentroMedicoServiceImpl(CentroMedicoRepository centroMedicoRepository) {
        this.centroMedicoRepository = centroMedicoRepository;
    }

    @Override
    public CentroMedico save(CentroMedico centroMedico) {
        LOG.debug("Request to save CentroMedico : {}", centroMedico);
        return centroMedicoRepository.save(centroMedico);
    }

    @Override
    public CentroMedico update(CentroMedico centroMedico) {
        LOG.debug("Request to update CentroMedico : {}", centroMedico);
        return centroMedicoRepository.save(centroMedico);
    }

    @Override
    public Optional<CentroMedico> partialUpdate(CentroMedico centroMedico) {
        LOG.debug("Request to partially update CentroMedico : {}", centroMedico);

        return centroMedicoRepository
            .findById(centroMedico.getId())
            .map(existingCentroMedico -> {
                if (centroMedico.getCentro() != null) {
                    existingCentroMedico.setCentro(centroMedico.getCentro());
                }
                if (centroMedico.getRegion() != null) {
                    existingCentroMedico.setRegion(centroMedico.getRegion());
                }
                if (centroMedico.getComuna() != null) {
                    existingCentroMedico.setComuna(centroMedico.getComuna());
                }
                if (centroMedico.getDireccion() != null) {
                    existingCentroMedico.setDireccion(centroMedico.getDireccion());
                }

                return existingCentroMedico;
            })
            .map(centroMedicoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentroMedico> findAll() {
        LOG.debug("Request to get all CentroMedicos");
        return centroMedicoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CentroMedico> findOne(Long id) {
        LOG.debug("Request to get CentroMedico : {}", id);
        return centroMedicoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete CentroMedico : {}", id);
        centroMedicoRepository.deleteById(id);
    }
}
