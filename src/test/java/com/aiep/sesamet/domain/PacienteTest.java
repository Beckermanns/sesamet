package com.aiep.sesamet.domain;

import static com.aiep.sesamet.domain.AtencionTestSamples.*;
import static com.aiep.sesamet.domain.PacienteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.sesamet.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PacienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paciente.class);
        Paciente paciente1 = getPacienteSample1();
        Paciente paciente2 = new Paciente();
        assertThat(paciente1).isNotEqualTo(paciente2);

        paciente2.setId(paciente1.getId());
        assertThat(paciente1).isEqualTo(paciente2);

        paciente2 = getPacienteSample2();
        assertThat(paciente1).isNotEqualTo(paciente2);
    }

    @Test
    void atencionTest() {
        Paciente paciente = getPacienteRandomSampleGenerator();
        Atencion atencionBack = getAtencionRandomSampleGenerator();

        paciente.addAtencion(atencionBack);
        assertThat(paciente.getAtencions()).containsOnly(atencionBack);
        assertThat(atencionBack.getPaciente()).isEqualTo(paciente);

        paciente.removeAtencion(atencionBack);
        assertThat(paciente.getAtencions()).doesNotContain(atencionBack);
        assertThat(atencionBack.getPaciente()).isNull();

        paciente.atencions(new HashSet<>(Set.of(atencionBack)));
        assertThat(paciente.getAtencions()).containsOnly(atencionBack);
        assertThat(atencionBack.getPaciente()).isEqualTo(paciente);

        paciente.setAtencions(new HashSet<>());
        assertThat(paciente.getAtencions()).doesNotContain(atencionBack);
        assertThat(atencionBack.getPaciente()).isNull();
    }
}
