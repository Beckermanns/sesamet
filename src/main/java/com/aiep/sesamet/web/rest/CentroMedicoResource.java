package com.aiep.sesamet.web.rest;

import com.aiep.sesamet.domain.CentroMedico;
import com.aiep.sesamet.repository.CentroMedicoRepository;
import com.aiep.sesamet.service.CentroMedicoService;
import com.aiep.sesamet.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.aiep.sesamet.domain.CentroMedico}.
 */
@RestController
@RequestMapping("/api/centro-medicos")
public class CentroMedicoResource {

    private static final Logger LOG = LoggerFactory.getLogger(CentroMedicoResource.class);

    private static final String ENTITY_NAME = "centroMedico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CentroMedicoService centroMedicoService;

    private final CentroMedicoRepository centroMedicoRepository;

    public CentroMedicoResource(CentroMedicoService centroMedicoService, CentroMedicoRepository centroMedicoRepository) {
        this.centroMedicoService = centroMedicoService;
        this.centroMedicoRepository = centroMedicoRepository;
    }

    /**
     * {@code POST  /centro-medicos} : Create a new centroMedico.
     *
     * @param centroMedico the centroMedico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centroMedico, or with status {@code 400 (Bad Request)} if the centroMedico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CentroMedico> createCentroMedico(@RequestBody CentroMedico centroMedico) throws URISyntaxException {
        LOG.debug("REST request to save CentroMedico : {}", centroMedico);
        if (centroMedico.getId() != null) {
            throw new BadRequestAlertException("A new centroMedico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        centroMedico = centroMedicoService.save(centroMedico);
        return ResponseEntity.created(new URI("/api/centro-medicos/" + centroMedico.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, centroMedico.getId().toString()))
            .body(centroMedico);
    }

    /**
     * {@code PUT  /centro-medicos/:id} : Updates an existing centroMedico.
     *
     * @param id the id of the centroMedico to save.
     * @param centroMedico the centroMedico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centroMedico,
     * or with status {@code 400 (Bad Request)} if the centroMedico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centroMedico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CentroMedico> updateCentroMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CentroMedico centroMedico
    ) throws URISyntaxException {
        LOG.debug("REST request to update CentroMedico : {}, {}", id, centroMedico);
        if (centroMedico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centroMedico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centroMedicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        centroMedico = centroMedicoService.update(centroMedico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centroMedico.getId().toString()))
            .body(centroMedico);
    }

    /**
     * {@code PATCH  /centro-medicos/:id} : Partial updates given fields of an existing centroMedico, field will ignore if it is null
     *
     * @param id the id of the centroMedico to save.
     * @param centroMedico the centroMedico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centroMedico,
     * or with status {@code 400 (Bad Request)} if the centroMedico is not valid,
     * or with status {@code 404 (Not Found)} if the centroMedico is not found,
     * or with status {@code 500 (Internal Server Error)} if the centroMedico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CentroMedico> partialUpdateCentroMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CentroMedico centroMedico
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CentroMedico partially : {}, {}", id, centroMedico);
        if (centroMedico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centroMedico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centroMedicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CentroMedico> result = centroMedicoService.partialUpdate(centroMedico);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centroMedico.getId().toString())
        );
    }

    /**
     * {@code GET  /centro-medicos} : get all the centroMedicos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centroMedicos in body.
     */
    @GetMapping("")
    public List<CentroMedico> getAllCentroMedicos() {
        LOG.debug("REST request to get all CentroMedicos");
        return centroMedicoService.findAll();
    }

    /**
     * {@code GET  /centro-medicos/:id} : get the "id" centroMedico.
     *
     * @param id the id of the centroMedico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centroMedico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CentroMedico> getCentroMedico(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CentroMedico : {}", id);
        Optional<CentroMedico> centroMedico = centroMedicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centroMedico);
    }

    /**
     * {@code DELETE  /centro-medicos/:id} : delete the "id" centroMedico.
     *
     * @param id the id of the centroMedico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCentroMedico(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CentroMedico : {}", id);
        centroMedicoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
