package com.aiep.sesamet.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DoctorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Doctor getDoctorSample1() {
        return new Doctor()
            .id(1L)
            .rut_doctor("rut_doctor1")
            .nombre("nombre1")
            .apellido_paterno("apellido_paterno1")
            .apellido_materno("apellido_materno1")
            .telefono(1)
            .correo("correo1")
            .especialidad("especialidad1");
    }

    public static Doctor getDoctorSample2() {
        return new Doctor()
            .id(2L)
            .rut_doctor("rut_doctor2")
            .nombre("nombre2")
            .apellido_paterno("apellido_paterno2")
            .apellido_materno("apellido_materno2")
            .telefono(2)
            .correo("correo2")
            .especialidad("especialidad2");
    }

    public static Doctor getDoctorRandomSampleGenerator() {
        return new Doctor()
            .id(longCount.incrementAndGet())
            .rut_doctor(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .apellido_paterno(UUID.randomUUID().toString())
            .apellido_materno(UUID.randomUUID().toString())
            .telefono(intCount.incrementAndGet())
            .correo(UUID.randomUUID().toString())
            .especialidad(UUID.randomUUID().toString());
    }
}
