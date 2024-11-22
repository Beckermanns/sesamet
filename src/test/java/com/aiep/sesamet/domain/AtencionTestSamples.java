package com.aiep.sesamet.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AtencionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Atencion getAtencionSample1() {
        return new Atencion()
            .id(1L)
            .hora("hora1")
            .motivo_consulta("motivo_consulta1")
            .diagnostico("diagnostico1")
            .tratamiento("tratamiento1");
    }

    public static Atencion getAtencionSample2() {
        return new Atencion()
            .id(2L)
            .hora("hora2")
            .motivo_consulta("motivo_consulta2")
            .diagnostico("diagnostico2")
            .tratamiento("tratamiento2");
    }

    public static Atencion getAtencionRandomSampleGenerator() {
        return new Atencion()
            .id(longCount.incrementAndGet())
            .hora(UUID.randomUUID().toString())
            .motivo_consulta(UUID.randomUUID().toString())
            .diagnostico(UUID.randomUUID().toString())
            .tratamiento(UUID.randomUUID().toString());
    }
}
