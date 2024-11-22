package com.aiep.sesamet.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CentroMedicoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CentroMedico getCentroMedicoSample1() {
        return new CentroMedico().id(1L).centro("centro1").region("region1").comuna("comuna1").direccion("direccion1");
    }

    public static CentroMedico getCentroMedicoSample2() {
        return new CentroMedico().id(2L).centro("centro2").region("region2").comuna("comuna2").direccion("direccion2");
    }

    public static CentroMedico getCentroMedicoRandomSampleGenerator() {
        return new CentroMedico()
            .id(longCount.incrementAndGet())
            .centro(UUID.randomUUID().toString())
            .region(UUID.randomUUID().toString())
            .comuna(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString());
    }
}
