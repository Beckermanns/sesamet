package com.aiep.sesamet.web.rest;

import static com.aiep.sesamet.domain.CentroMedicoAsserts.*;
import static com.aiep.sesamet.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.sesamet.IntegrationTest;
import com.aiep.sesamet.domain.CentroMedico;
import com.aiep.sesamet.repository.CentroMedicoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CentroMedicoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CentroMedicoResourceIT {

    private static final String DEFAULT_CENTRO = "AAAAAAAAAA";
    private static final String UPDATED_CENTRO = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_COMUNA = "AAAAAAAAAA";
    private static final String UPDATED_COMUNA = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/centro-medicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CentroMedicoRepository centroMedicoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCentroMedicoMockMvc;

    private CentroMedico centroMedico;

    private CentroMedico insertedCentroMedico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentroMedico createEntity() {
        return new CentroMedico().centro(DEFAULT_CENTRO).region(DEFAULT_REGION).comuna(DEFAULT_COMUNA).direccion(DEFAULT_DIRECCION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentroMedico createUpdatedEntity() {
        return new CentroMedico().centro(UPDATED_CENTRO).region(UPDATED_REGION).comuna(UPDATED_COMUNA).direccion(UPDATED_DIRECCION);
    }

    @BeforeEach
    public void initTest() {
        centroMedico = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCentroMedico != null) {
            centroMedicoRepository.delete(insertedCentroMedico);
            insertedCentroMedico = null;
        }
    }

    @Test
    @Transactional
    void createCentroMedico() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CentroMedico
        var returnedCentroMedico = om.readValue(
            restCentroMedicoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroMedico)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CentroMedico.class
        );

        // Validate the CentroMedico in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCentroMedicoUpdatableFieldsEquals(returnedCentroMedico, getPersistedCentroMedico(returnedCentroMedico));

        insertedCentroMedico = returnedCentroMedico;
    }

    @Test
    @Transactional
    void createCentroMedicoWithExistingId() throws Exception {
        // Create the CentroMedico with an existing ID
        centroMedico.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentroMedicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroMedico)))
            .andExpect(status().isBadRequest());

        // Validate the CentroMedico in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCentroMedicos() throws Exception {
        // Initialize the database
        insertedCentroMedico = centroMedicoRepository.saveAndFlush(centroMedico);

        // Get all the centroMedicoList
        restCentroMedicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centroMedico.getId().intValue())))
            .andExpect(jsonPath("$.[*].centro").value(hasItem(DEFAULT_CENTRO)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].comuna").value(hasItem(DEFAULT_COMUNA)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)));
    }

    @Test
    @Transactional
    void getCentroMedico() throws Exception {
        // Initialize the database
        insertedCentroMedico = centroMedicoRepository.saveAndFlush(centroMedico);

        // Get the centroMedico
        restCentroMedicoMockMvc
            .perform(get(ENTITY_API_URL_ID, centroMedico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(centroMedico.getId().intValue()))
            .andExpect(jsonPath("$.centro").value(DEFAULT_CENTRO))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.comuna").value(DEFAULT_COMUNA))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION));
    }

    @Test
    @Transactional
    void getNonExistingCentroMedico() throws Exception {
        // Get the centroMedico
        restCentroMedicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCentroMedico() throws Exception {
        // Initialize the database
        insertedCentroMedico = centroMedicoRepository.saveAndFlush(centroMedico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centroMedico
        CentroMedico updatedCentroMedico = centroMedicoRepository.findById(centroMedico.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCentroMedico are not directly saved in db
        em.detach(updatedCentroMedico);
        updatedCentroMedico.centro(UPDATED_CENTRO).region(UPDATED_REGION).comuna(UPDATED_COMUNA).direccion(UPDATED_DIRECCION);

        restCentroMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCentroMedico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCentroMedico))
            )
            .andExpect(status().isOk());

        // Validate the CentroMedico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCentroMedicoToMatchAllProperties(updatedCentroMedico);
    }

    @Test
    @Transactional
    void putNonExistingCentroMedico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroMedico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentroMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centroMedico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centroMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroMedico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCentroMedico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroMedico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centroMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroMedico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCentroMedico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroMedico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroMedicoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroMedico)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentroMedico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCentroMedicoWithPatch() throws Exception {
        // Initialize the database
        insertedCentroMedico = centroMedicoRepository.saveAndFlush(centroMedico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centroMedico using partial update
        CentroMedico partialUpdatedCentroMedico = new CentroMedico();
        partialUpdatedCentroMedico.setId(centroMedico.getId());

        partialUpdatedCentroMedico.centro(UPDATED_CENTRO).direccion(UPDATED_DIRECCION);

        restCentroMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentroMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCentroMedico))
            )
            .andExpect(status().isOk());

        // Validate the CentroMedico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCentroMedicoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCentroMedico, centroMedico),
            getPersistedCentroMedico(centroMedico)
        );
    }

    @Test
    @Transactional
    void fullUpdateCentroMedicoWithPatch() throws Exception {
        // Initialize the database
        insertedCentroMedico = centroMedicoRepository.saveAndFlush(centroMedico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centroMedico using partial update
        CentroMedico partialUpdatedCentroMedico = new CentroMedico();
        partialUpdatedCentroMedico.setId(centroMedico.getId());

        partialUpdatedCentroMedico.centro(UPDATED_CENTRO).region(UPDATED_REGION).comuna(UPDATED_COMUNA).direccion(UPDATED_DIRECCION);

        restCentroMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentroMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCentroMedico))
            )
            .andExpect(status().isOk());

        // Validate the CentroMedico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCentroMedicoUpdatableFieldsEquals(partialUpdatedCentroMedico, getPersistedCentroMedico(partialUpdatedCentroMedico));
    }

    @Test
    @Transactional
    void patchNonExistingCentroMedico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroMedico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentroMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, centroMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(centroMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroMedico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCentroMedico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroMedico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(centroMedico))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroMedico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCentroMedico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroMedico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroMedicoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(centroMedico)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentroMedico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCentroMedico() throws Exception {
        // Initialize the database
        insertedCentroMedico = centroMedicoRepository.saveAndFlush(centroMedico);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the centroMedico
        restCentroMedicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, centroMedico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return centroMedicoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected CentroMedico getPersistedCentroMedico(CentroMedico centroMedico) {
        return centroMedicoRepository.findById(centroMedico.getId()).orElseThrow();
    }

    protected void assertPersistedCentroMedicoToMatchAllProperties(CentroMedico expectedCentroMedico) {
        assertCentroMedicoAllPropertiesEquals(expectedCentroMedico, getPersistedCentroMedico(expectedCentroMedico));
    }

    protected void assertPersistedCentroMedicoToMatchUpdatableProperties(CentroMedico expectedCentroMedico) {
        assertCentroMedicoAllUpdatablePropertiesEquals(expectedCentroMedico, getPersistedCentroMedico(expectedCentroMedico));
    }
}
