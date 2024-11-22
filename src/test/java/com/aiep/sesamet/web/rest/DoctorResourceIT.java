package com.aiep.sesamet.web.rest;

import static com.aiep.sesamet.domain.DoctorAsserts.*;
import static com.aiep.sesamet.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.sesamet.IntegrationTest;
import com.aiep.sesamet.domain.Doctor;
import com.aiep.sesamet.repository.DoctorRepository;
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
 * Integration tests for the {@link DoctorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DoctorResourceIT {

    private static final String DEFAULT_RUT_DOCTOR = "AAAAAAAAAA";
    private static final String UPDATED_RUT_DOCTOR = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_PATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_PATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_MATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_MATERNO = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEFONO = 1;
    private static final Integer UPDATED_TELEFONO = 2;

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_ESPECIALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIALIDAD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/doctors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoctorMockMvc;

    private Doctor doctor;

    private Doctor insertedDoctor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctor createEntity() {
        return new Doctor()
            .rut_doctor(DEFAULT_RUT_DOCTOR)
            .nombre(DEFAULT_NOMBRE)
            .apellido_paterno(DEFAULT_APELLIDO_PATERNO)
            .apellido_materno(DEFAULT_APELLIDO_MATERNO)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO)
            .especialidad(DEFAULT_ESPECIALIDAD);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctor createUpdatedEntity() {
        return new Doctor()
            .rut_doctor(UPDATED_RUT_DOCTOR)
            .nombre(UPDATED_NOMBRE)
            .apellido_paterno(UPDATED_APELLIDO_PATERNO)
            .apellido_materno(UPDATED_APELLIDO_MATERNO)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .especialidad(UPDATED_ESPECIALIDAD);
    }

    @BeforeEach
    public void initTest() {
        doctor = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDoctor != null) {
            doctorRepository.delete(insertedDoctor);
            insertedDoctor = null;
        }
    }

    @Test
    @Transactional
    void createDoctor() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Doctor
        var returnedDoctor = om.readValue(
            restDoctorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doctor)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Doctor.class
        );

        // Validate the Doctor in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDoctorUpdatableFieldsEquals(returnedDoctor, getPersistedDoctor(returnedDoctor));

        insertedDoctor = returnedDoctor;
    }

    @Test
    @Transactional
    void createDoctorWithExistingId() throws Exception {
        // Create the Doctor with an existing ID
        doctor.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doctor)))
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDoctors() throws Exception {
        // Initialize the database
        insertedDoctor = doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList
        restDoctorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].rut_doctor").value(hasItem(DEFAULT_RUT_DOCTOR)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido_paterno").value(hasItem(DEFAULT_APELLIDO_PATERNO)))
            .andExpect(jsonPath("$.[*].apellido_materno").value(hasItem(DEFAULT_APELLIDO_MATERNO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].especialidad").value(hasItem(DEFAULT_ESPECIALIDAD)));
    }

    @Test
    @Transactional
    void getDoctor() throws Exception {
        // Initialize the database
        insertedDoctor = doctorRepository.saveAndFlush(doctor);

        // Get the doctor
        restDoctorMockMvc
            .perform(get(ENTITY_API_URL_ID, doctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doctor.getId().intValue()))
            .andExpect(jsonPath("$.rut_doctor").value(DEFAULT_RUT_DOCTOR))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellido_paterno").value(DEFAULT_APELLIDO_PATERNO))
            .andExpect(jsonPath("$.apellido_materno").value(DEFAULT_APELLIDO_MATERNO))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.especialidad").value(DEFAULT_ESPECIALIDAD));
    }

    @Test
    @Transactional
    void getNonExistingDoctor() throws Exception {
        // Get the doctor
        restDoctorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDoctor() throws Exception {
        // Initialize the database
        insertedDoctor = doctorRepository.saveAndFlush(doctor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doctor
        Doctor updatedDoctor = doctorRepository.findById(doctor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDoctor are not directly saved in db
        em.detach(updatedDoctor);
        updatedDoctor
            .rut_doctor(UPDATED_RUT_DOCTOR)
            .nombre(UPDATED_NOMBRE)
            .apellido_paterno(UPDATED_APELLIDO_PATERNO)
            .apellido_materno(UPDATED_APELLIDO_MATERNO)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .especialidad(UPDATED_ESPECIALIDAD);

        restDoctorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDoctor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDoctor))
            )
            .andExpect(status().isOk());

        // Validate the Doctor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDoctorToMatchAllProperties(updatedDoctor);
    }

    @Test
    @Transactional
    void putNonExistingDoctor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(put(ENTITY_API_URL_ID, doctor.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doctor)))
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDoctor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDoctor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(doctor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doctor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDoctorWithPatch() throws Exception {
        // Initialize the database
        insertedDoctor = doctorRepository.saveAndFlush(doctor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doctor using partial update
        Doctor partialUpdatedDoctor = new Doctor();
        partialUpdatedDoctor.setId(doctor.getId());

        partialUpdatedDoctor.apellido_paterno(UPDATED_APELLIDO_PATERNO).correo(UPDATED_CORREO);

        restDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoctor))
            )
            .andExpect(status().isOk());

        // Validate the Doctor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoctorUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDoctor, doctor), getPersistedDoctor(doctor));
    }

    @Test
    @Transactional
    void fullUpdateDoctorWithPatch() throws Exception {
        // Initialize the database
        insertedDoctor = doctorRepository.saveAndFlush(doctor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the doctor using partial update
        Doctor partialUpdatedDoctor = new Doctor();
        partialUpdatedDoctor.setId(doctor.getId());

        partialUpdatedDoctor
            .rut_doctor(UPDATED_RUT_DOCTOR)
            .nombre(UPDATED_NOMBRE)
            .apellido_paterno(UPDATED_APELLIDO_PATERNO)
            .apellido_materno(UPDATED_APELLIDO_MATERNO)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .especialidad(UPDATED_ESPECIALIDAD);

        restDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDoctor))
            )
            .andExpect(status().isOk());

        // Validate the Doctor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDoctorUpdatableFieldsEquals(partialUpdatedDoctor, getPersistedDoctor(partialUpdatedDoctor));
    }

    @Test
    @Transactional
    void patchNonExistingDoctor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, doctor.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDoctor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(doctor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDoctor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        doctor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(doctor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doctor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDoctor() throws Exception {
        // Initialize the database
        insertedDoctor = doctorRepository.saveAndFlush(doctor);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the doctor
        restDoctorMockMvc
            .perform(delete(ENTITY_API_URL_ID, doctor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return doctorRepository.count();
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

    protected Doctor getPersistedDoctor(Doctor doctor) {
        return doctorRepository.findById(doctor.getId()).orElseThrow();
    }

    protected void assertPersistedDoctorToMatchAllProperties(Doctor expectedDoctor) {
        assertDoctorAllPropertiesEquals(expectedDoctor, getPersistedDoctor(expectedDoctor));
    }

    protected void assertPersistedDoctorToMatchUpdatableProperties(Doctor expectedDoctor) {
        assertDoctorAllUpdatablePropertiesEquals(expectedDoctor, getPersistedDoctor(expectedDoctor));
    }
}
