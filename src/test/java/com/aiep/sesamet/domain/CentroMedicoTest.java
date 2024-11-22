package com.aiep.sesamet.domain;

import static com.aiep.sesamet.domain.AtencionTestSamples.*;
import static com.aiep.sesamet.domain.CentroMedicoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.sesamet.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CentroMedicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentroMedico.class);
        CentroMedico centroMedico1 = getCentroMedicoSample1();
        CentroMedico centroMedico2 = new CentroMedico();
        assertThat(centroMedico1).isNotEqualTo(centroMedico2);

        centroMedico2.setId(centroMedico1.getId());
        assertThat(centroMedico1).isEqualTo(centroMedico2);

        centroMedico2 = getCentroMedicoSample2();
        assertThat(centroMedico1).isNotEqualTo(centroMedico2);
    }

    @Test
    void atencionTest() {
        CentroMedico centroMedico = getCentroMedicoRandomSampleGenerator();
        Atencion atencionBack = getAtencionRandomSampleGenerator();

        centroMedico.addAtencion(atencionBack);
        assertThat(centroMedico.getAtencions()).containsOnly(atencionBack);
        assertThat(atencionBack.getCentroMedico()).isEqualTo(centroMedico);

        centroMedico.removeAtencion(atencionBack);
        assertThat(centroMedico.getAtencions()).doesNotContain(atencionBack);
        assertThat(atencionBack.getCentroMedico()).isNull();

        centroMedico.atencions(new HashSet<>(Set.of(atencionBack)));
        assertThat(centroMedico.getAtencions()).containsOnly(atencionBack);
        assertThat(atencionBack.getCentroMedico()).isEqualTo(centroMedico);

        centroMedico.setAtencions(new HashSet<>());
        assertThat(centroMedico.getAtencions()).doesNotContain(atencionBack);
        assertThat(atencionBack.getCentroMedico()).isNull();
    }
}
