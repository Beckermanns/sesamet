package com.aiep.sesamet.web.rest;

import static com.aiep.sesamet.domain.AtencionAsserts.*;
import static com.aiep.sesamet.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.sesamet.IntegrationTest;
import com.aiep.sesamet.domain.Atencion;
import com.aiep.sesamet.domain.CentroMedico;
import com.aiep.sesamet.domain.Doctor;
import com.aiep.sesamet.domain.Paciente;
import com.aiep.sesamet.repository.AtencionRepository;
import com.aiep.sesamet.service.AtencionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AtencionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AtencionResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HORA = "AAAAAAAAAA";
    private static final String UPDATED_HORA = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIVO_CONSULTA = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO_CONSULTA = "BBBBBBBBBB";

    private static final String DEFAULT_DIAGNOSTICO = "AAAAAAAAAA";
    private static final String UPDATED_DIAGNOSTICO = "BBBBBBBBBB";

    private static final String DEFAULT_TRATAMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_TRATAMIENTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/atencions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AtencionRepository atencionRepository;

    @Mock
    private AtencionRepository atencionRepositoryMock;

    @Mock
    private AtencionService atencionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAtencionMockMvc;

    private Atencion atencion;

    private Atencion insertedAtencion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Atencion createEntity(EntityManager em) {
        Atencion atencion = new Atencion()
            .fecha(DEFAULT_FECHA)
            .hora(DEFAULT_HORA)
            .motivo_consulta(DEFAULT_MOTIVO_CONSULTA)
            .diagnostico(DEFAULT_DIAGNOSTICO)
            .tratamiento(DEFAULT_TRATAMIENTO);
        // Add required entity
        CentroMedico centroMedico;
        if (TestUtil.findAll(em, CentroMedico.class).isEmpty()) {
            centroMedico = CentroMedicoResourceIT.createEntity();
            em.persist(centroMedico);
            em.flush();
        } else {
            centroMedico = TestUtil.findAll(em, CentroMedico.class).get(0);
        }
        atencion.setCentroMedico(centroMedico);
        // Add required entity
        Doctor doctor;
        if (TestUtil.findAll(em, Doctor.class).isEmpty()) {
            doctor = DoctorResourceIT.createEntity();
            em.persist(doctor);
            em.flush();
        } else {
            doctor = TestUtil.findAll(em, Doctor.class).get(0);
        }
        atencion.setDoctor(doctor);
        // Add required entity
        Paciente paciente;
        if (TestUtil.findAll(em, Paciente.class).isEmpty()) {
            paciente = PacienteResourceIT.createEntity();
            em.persist(paciente);
            em.flush();
        } else {
            paciente = TestUtil.findAll(em, Paciente.class).get(0);
        }
        atencion.setPaciente(paciente);
        return atencion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Atencion createUpdatedEntity(EntityManager em) {
        Atencion updatedAtencion = new Atencion()
            .fecha(UPDATED_FECHA)
            .hora(UPDATED_HORA)
            .motivo_consulta(UPDATED_MOTIVO_CONSULTA)
            .diagnostico(UPDATED_DIAGNOSTICO)
            .tratamiento(UPDATED_TRATAMIENTO);
        // Add required entity
        CentroMedico centroMedico;
        if (TestUtil.findAll(em, CentroMedico.class).isEmpty()) {
            centroMedico = CentroMedicoResourceIT.createUpdatedEntity();
            em.persist(centroMedico);
            em.flush();
        } else {
            centroMedico = TestUtil.findAll(em, CentroMedico.class).get(0);
        }
        updatedAtencion.setCentroMedico(centroMedico);
        // Add required entity
        Doctor doctor;
        if (TestUtil.findAll(em, Doctor.class).isEmpty()) {
            doctor = DoctorResourceIT.createUpdatedEntity();
            em.persist(doctor);
            em.flush();
        } else {
            doctor = TestUtil.findAll(em, Doctor.class).get(0);
        }
        updatedAtencion.setDoctor(doctor);
        // Add required entity
        Paciente paciente;
        if (TestUtil.findAll(em, Paciente.class).isEmpty()) {
            paciente = PacienteResourceIT.createUpdatedEntity();
            em.persist(paciente);
            em.flush();
        } else {
            paciente = TestUtil.findAll(em, Paciente.class).get(0);
        }
        updatedAtencion.setPaciente(paciente);
        return updatedAtencion;
    }

    @BeforeEach
    public void initTest() {
        atencion = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAtencion != null) {
            atencionRepository.delete(insertedAtencion);
            insertedAtencion = null;
        }
    }

    @Test
    @Transactional
    void createAtencion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Atencion
        var returnedAtencion = om.readValue(
            restAtencionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(atencion)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Atencion.class
        );

        // Validate the Atencion in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAtencionUpdatableFieldsEquals(returnedAtencion, getPersistedAtencion(returnedAtencion));

        insertedAtencion = returnedAtencion;
    }

    @Test
    @Transactional
    void createAtencionWithExistingId() throws Exception {
        // Create the Atencion with an existing ID
        atencion.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtencionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(atencion)))
            .andExpect(status().isBadRequest());

        // Validate the Atencion in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAtencions() throws Exception {
        // Initialize the database
        insertedAtencion = atencionRepository.saveAndFlush(atencion);

        // Get all the atencionList
        restAtencionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atencion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].hora").value(hasItem(DEFAULT_HORA)))
            .andExpect(jsonPath("$.[*].motivo_consulta").value(hasItem(DEFAULT_MOTIVO_CONSULTA)))
            .andExpect(jsonPath("$.[*].diagnostico").value(hasItem(DEFAULT_DIAGNOSTICO)))
            .andExpect(jsonPath("$.[*].tratamiento").value(hasItem(DEFAULT_TRATAMIENTO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAtencionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(atencionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAtencionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(atencionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAtencionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(atencionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAtencionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(atencionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAtencion() throws Exception {
        // Initialize the database
        insertedAtencion = atencionRepository.saveAndFlush(atencion);

        // Get the atencion
        restAtencionMockMvc
            .perform(get(ENTITY_API_URL_ID, atencion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(atencion.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.hora").value(DEFAULT_HORA))
            .andExpect(jsonPath("$.motivo_consulta").value(DEFAULT_MOTIVO_CONSULTA))
            .andExpect(jsonPath("$.diagnostico").value(DEFAULT_DIAGNOSTICO))
            .andExpect(jsonPath("$.tratamiento").value(DEFAULT_TRATAMIENTO));
    }

    @Test
    @Transactional
    void getNonExistingAtencion() throws Exception {
        // Get the atencion
        restAtencionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAtencion() throws Exception {
        // Initialize the database
        insertedAtencion = atencionRepository.saveAndFlush(atencion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the atencion
        Atencion updatedAtencion = atencionRepository.findById(atencion.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAtencion are not directly saved in db
        em.detach(updatedAtencion);
        updatedAtencion
            .fecha(UPDATED_FECHA)
            .hora(UPDATED_HORA)
            .motivo_consulta(UPDATED_MOTIVO_CONSULTA)
            .diagnostico(UPDATED_DIAGNOSTICO)
            .tratamiento(UPDATED_TRATAMIENTO);

        restAtencionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAtencion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAtencion))
            )
            .andExpect(status().isOk());

        // Validate the Atencion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAtencionToMatchAllProperties(updatedAtencion);
    }

    @Test
    @Transactional
    void putNonExistingAtencion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atencion.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtencionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, atencion.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(atencion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Atencion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAtencion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atencion.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtencionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(atencion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Atencion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAtencion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atencion.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtencionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(atencion)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Atencion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAtencionWithPatch() throws Exception {
        // Initialize the database
        insertedAtencion = atencionRepository.saveAndFlush(atencion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the atencion using partial update
        Atencion partialUpdatedAtencion = new Atencion();
        partialUpdatedAtencion.setId(atencion.getId());

        partialUpdatedAtencion
            .fecha(UPDATED_FECHA)
            .hora(UPDATED_HORA)
            .motivo_consulta(UPDATED_MOTIVO_CONSULTA)
            .diagnostico(UPDATED_DIAGNOSTICO)
            .tratamiento(UPDATED_TRATAMIENTO);

        restAtencionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAtencion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAtencion))
            )
            .andExpect(status().isOk());

        // Validate the Atencion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAtencionUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAtencion, atencion), getPersistedAtencion(atencion));
    }

    @Test
    @Transactional
    void fullUpdateAtencionWithPatch() throws Exception {
        // Initialize the database
        insertedAtencion = atencionRepository.saveAndFlush(atencion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the atencion using partial update
        Atencion partialUpdatedAtencion = new Atencion();
        partialUpdatedAtencion.setId(atencion.getId());

        partialUpdatedAtencion
            .fecha(UPDATED_FECHA)
            .hora(UPDATED_HORA)
            .motivo_consulta(UPDATED_MOTIVO_CONSULTA)
            .diagnostico(UPDATED_DIAGNOSTICO)
            .tratamiento(UPDATED_TRATAMIENTO);

        restAtencionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAtencion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAtencion))
            )
            .andExpect(status().isOk());

        // Validate the Atencion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAtencionUpdatableFieldsEquals(partialUpdatedAtencion, getPersistedAtencion(partialUpdatedAtencion));
    }

    @Test
    @Transactional
    void patchNonExistingAtencion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atencion.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtencionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, atencion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(atencion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Atencion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAtencion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atencion.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtencionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(atencion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Atencion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAtencion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atencion.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtencionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(atencion)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Atencion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAtencion() throws Exception {
        // Initialize the database
        insertedAtencion = atencionRepository.saveAndFlush(atencion);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the atencion
        restAtencionMockMvc
            .perform(delete(ENTITY_API_URL_ID, atencion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return atencionRepository.count();
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

    protected Atencion getPersistedAtencion(Atencion atencion) {
        return atencionRepository.findById(atencion.getId()).orElseThrow();
    }

    protected void assertPersistedAtencionToMatchAllProperties(Atencion expectedAtencion) {
        assertAtencionAllPropertiesEquals(expectedAtencion, getPersistedAtencion(expectedAtencion));
    }

    protected void assertPersistedAtencionToMatchUpdatableProperties(Atencion expectedAtencion) {
        assertAtencionAllUpdatablePropertiesEquals(expectedAtencion, getPersistedAtencion(expectedAtencion));
    }
}
