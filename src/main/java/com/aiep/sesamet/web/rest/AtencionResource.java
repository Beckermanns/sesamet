package com.aiep.sesamet.web.rest;

import com.aiep.sesamet.domain.Atencion;
import com.aiep.sesamet.repository.AtencionRepository;
import com.aiep.sesamet.service.AtencionService;
import com.aiep.sesamet.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.aiep.sesamet.domain.Atencion}.
 */
@RestController
@RequestMapping("/api/atencions")
public class AtencionResource {

    private static final Logger LOG = LoggerFactory.getLogger(AtencionResource.class);

    private static final String ENTITY_NAME = "atencion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AtencionService atencionService;

    private final AtencionRepository atencionRepository;

    public AtencionResource(AtencionService atencionService, AtencionRepository atencionRepository) {
        this.atencionService = atencionService;
        this.atencionRepository = atencionRepository;
    }

    /**
     * {@code POST  /atencions} : Create a new atencion.
     *
     * @param atencion the atencion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new atencion, or with status {@code 400 (Bad Request)} if the atencion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Atencion> createAtencion(@Valid @RequestBody Atencion atencion) throws URISyntaxException {
        LOG.debug("REST request to save Atencion : {}", atencion);
        if (atencion.getId() != null) {
            throw new BadRequestAlertException("A new atencion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        atencion = atencionService.save(atencion);
        return ResponseEntity.created(new URI("/api/atencions/" + atencion.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, atencion.getId().toString()))
            .body(atencion);
    }

    /**
     * {@code PUT  /atencions/:id} : Updates an existing atencion.
     *
     * @param id the id of the atencion to save.
     * @param atencion the atencion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atencion,
     * or with status {@code 400 (Bad Request)} if the atencion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the atencion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Atencion> updateAtencion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Atencion atencion
    ) throws URISyntaxException {
        LOG.debug("REST request to update Atencion : {}, {}", id, atencion);
        if (atencion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, atencion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!atencionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        atencion = atencionService.update(atencion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, atencion.getId().toString()))
            .body(atencion);
    }

    /**
     * {@code PATCH  /atencions/:id} : Partial updates given fields of an existing atencion, field will ignore if it is null
     *
     * @param id the id of the atencion to save.
     * @param atencion the atencion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atencion,
     * or with status {@code 400 (Bad Request)} if the atencion is not valid,
     * or with status {@code 404 (Not Found)} if the atencion is not found,
     * or with status {@code 500 (Internal Server Error)} if the atencion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Atencion> partialUpdateAtencion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Atencion atencion
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Atencion partially : {}, {}", id, atencion);
        if (atencion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, atencion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!atencionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Atencion> result = atencionService.partialUpdate(atencion);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, atencion.getId().toString())
        );
    }

    /**
     * {@code GET  /atencions} : get all the atencions.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of atencions in body.
     */
    @GetMapping("")
    public List<Atencion> getAllAtencions(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        LOG.debug("REST request to get all Atencions");
        return atencionService.findAll();
    }

    /**
     * {@code GET  /atencions/:id} : get the "id" atencion.
     *
     * @param id the id of the atencion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the atencion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Atencion> getAtencion(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Atencion : {}", id);
        Optional<Atencion> atencion = atencionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(atencion);
    }

    /**
     * {@code DELETE  /atencions/:id} : delete the "id" atencion.
     *
     * @param id the id of the atencion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtencion(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Atencion : {}", id);
        atencionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
