package com.aiep.sesamet.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AtencionAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAtencionAllPropertiesEquals(Atencion expected, Atencion actual) {
        assertAtencionAutoGeneratedPropertiesEquals(expected, actual);
        assertAtencionAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAtencionAllUpdatablePropertiesEquals(Atencion expected, Atencion actual) {
        assertAtencionUpdatableFieldsEquals(expected, actual);
        assertAtencionUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAtencionAutoGeneratedPropertiesEquals(Atencion expected, Atencion actual) {
        assertThat(expected)
            .as("Verify Atencion auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAtencionUpdatableFieldsEquals(Atencion expected, Atencion actual) {
        assertThat(expected)
            .as("Verify Atencion relevant properties")
            .satisfies(e -> assertThat(e.getFecha()).as("check fecha").isEqualTo(actual.getFecha()))
            .satisfies(e -> assertThat(e.getHora()).as("check hora").isEqualTo(actual.getHora()))
            .satisfies(e -> assertThat(e.getMotivo_consulta()).as("check motivo_consulta").isEqualTo(actual.getMotivo_consulta()))
            .satisfies(e -> assertThat(e.getDiagnostico()).as("check diagnostico").isEqualTo(actual.getDiagnostico()))
            .satisfies(e -> assertThat(e.getTratamiento()).as("check tratamiento").isEqualTo(actual.getTratamiento()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAtencionUpdatableRelationshipsEquals(Atencion expected, Atencion actual) {
        assertThat(expected)
            .as("Verify Atencion relationships")
            .satisfies(e -> assertThat(e.getCentroMedico()).as("check centroMedico").isEqualTo(actual.getCentroMedico()))
            .satisfies(e -> assertThat(e.getDoctor()).as("check doctor").isEqualTo(actual.getDoctor()))
            .satisfies(e -> assertThat(e.getPaciente()).as("check paciente").isEqualTo(actual.getPaciente()));
    }
}
