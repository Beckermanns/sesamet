package com.aiep.sesamet.service.impl;

import com.aiep.sesamet.domain.Atencion;
import com.aiep.sesamet.repository.AtencionRepository;
import com.aiep.sesamet.service.AtencionService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.sesamet.domain.Atencion}.
 */
@Service
@Transactional
public class AtencionServiceImpl implements AtencionService {

    private static final Logger LOG = LoggerFactory.getLogger(AtencionServiceImpl.class);

    private final AtencionRepository atencionRepository;

    public AtencionServiceImpl(AtencionRepository atencionRepository) {
        this.atencionRepository = atencionRepository;
    }

    @Override
    public Atencion save(Atencion atencion) {
        LOG.debug("Request to save Atencion : {}", atencion);
        return atencionRepository.save(atencion);
    }

    @Override
    public Atencion update(Atencion atencion) {
        LOG.debug("Request to update Atencion : {}", atencion);
        return atencionRepository.save(atencion);
    }

    @Override
    public Optional<Atencion> partialUpdate(Atencion atencion) {
        LOG.debug("Request to partially update Atencion : {}", atencion);

        return atencionRepository
            .findById(atencion.getId())
            .map(existingAtencion -> {
                if (atencion.getFecha() != null) {
                    existingAtencion.setFecha(atencion.getFecha());
                }
                if (atencion.getHora() != null) {
                    existingAtencion.setHora(atencion.getHora());
                }
                if (atencion.getMotivo_consulta() != null) {
                    existingAtencion.setMotivo_consulta(atencion.getMotivo_consulta());
                }
                if (atencion.getDiagnostico() != null) {
                    existingAtencion.setDiagnostico(atencion.getDiagnostico());
                }
                if (atencion.getTratamiento() != null) {
                    existingAtencion.setTratamiento(atencion.getTratamiento());
                }

                return existingAtencion;
            })
            .map(atencionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Atencion> findAll() {
        LOG.debug("Request to get all Atencions");
        return atencionRepository.findAll();
    }

    public Page<Atencion> findAllWithEagerRelationships(Pageable pageable) {
        return atencionRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Atencion> findOne(Long id) {
        LOG.debug("Request to get Atencion : {}", id);
        return atencionRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Atencion : {}", id);
        atencionRepository.deleteById(id);
    }
}
