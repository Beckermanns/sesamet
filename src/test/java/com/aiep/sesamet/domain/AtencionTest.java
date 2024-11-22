package com.aiep.sesamet.domain;

import static com.aiep.sesamet.domain.AtencionTestSamples.*;
import static com.aiep.sesamet.domain.CentroMedicoTestSamples.*;
import static com.aiep.sesamet.domain.DoctorTestSamples.*;
import static com.aiep.sesamet.domain.PacienteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.sesamet.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AtencionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Atencion.class);
        Atencion atencion1 = getAtencionSample1();
        Atencion atencion2 = new Atencion();
        assertThat(atencion1).isNotEqualTo(atencion2);

        atencion2.setId(atencion1.getId());
        assertThat(atencion1).isEqualTo(atencion2);

        atencion2 = getAtencionSample2();
        assertThat(atencion1).isNotEqualTo(atencion2);
    }

    @Test
    void centroMedicoTest() {
        Atencion atencion = getAtencionRandomSampleGenerator();
        CentroMedico centroMedicoBack = getCentroMedicoRandomSampleGenerator();

        atencion.setCentroMedico(centroMedicoBack);
        assertThat(atencion.getCentroMedico()).isEqualTo(centroMedicoBack);

        atencion.centroMedico(null);
        assertThat(atencion.getCentroMedico()).isNull();
    }

    @Test
    void doctorTest() {
        Atencion atencion = getAtencionRandomSampleGenerator();
        Doctor doctorBack = getDoctorRandomSampleGenerator();

        atencion.setDoctor(doctorBack);
        assertThat(atencion.getDoctor()).isEqualTo(doctorBack);

        atencion.doctor(null);
        assertThat(atencion.getDoctor()).isNull();
    }

    @Test
    void pacienteTest() {
        Atencion atencion = getAtencionRandomSampleGenerator();
        Paciente pacienteBack = getPacienteRandomSampleGenerator();

        atencion.setPaciente(pacienteBack);
        assertThat(atencion.getPaciente()).isEqualTo(pacienteBack);

        atencion.paciente(null);
        assertThat(atencion.getPaciente()).isNull();
    }
}
