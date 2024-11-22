package com.aiep.sesamet.domain;

import static com.aiep.sesamet.domain.AtencionTestSamples.*;
import static com.aiep.sesamet.domain.DoctorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.sesamet.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DoctorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Doctor.class);
        Doctor doctor1 = getDoctorSample1();
        Doctor doctor2 = new Doctor();
        assertThat(doctor1).isNotEqualTo(doctor2);

        doctor2.setId(doctor1.getId());
        assertThat(doctor1).isEqualTo(doctor2);

        doctor2 = getDoctorSample2();
        assertThat(doctor1).isNotEqualTo(doctor2);
    }

    @Test
    void atencionTest() {
        Doctor doctor = getDoctorRandomSampleGenerator();
        Atencion atencionBack = getAtencionRandomSampleGenerator();

        doctor.addAtencion(atencionBack);
        assertThat(doctor.getAtencions()).containsOnly(atencionBack);
        assertThat(atencionBack.getDoctor()).isEqualTo(doctor);

        doctor.removeAtencion(atencionBack);
        assertThat(doctor.getAtencions()).doesNotContain(atencionBack);
        assertThat(atencionBack.getDoctor()).isNull();

        doctor.atencions(new HashSet<>(Set.of(atencionBack)));
        assertThat(doctor.getAtencions()).containsOnly(atencionBack);
        assertThat(atencionBack.getDoctor()).isEqualTo(doctor);

        doctor.setAtencions(new HashSet<>());
        assertThat(doctor.getAtencions()).doesNotContain(atencionBack);
        assertThat(atencionBack.getDoctor()).isNull();
    }
}
