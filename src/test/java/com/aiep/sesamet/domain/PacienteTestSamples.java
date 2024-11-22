package com.aiep.sesamet.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PacienteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Paciente getPacienteSample1() {
        return new Paciente()
            .id(1L)
            .rut_paciente("rut_paciente1")
            .nombres("nombres1")
            .apellido_paterno("apellido_paterno1")
            .apellido_materno("apellido_materno1")
            .telefono(1)
            .correo("correo1")
            .direccion("direccion1")
            .comuna("comuna1");
    }

    public static Paciente getPacienteSample2() {
        return new Paciente()
            .id(2L)
            .rut_paciente("rut_paciente2")
            .nombres("nombres2")
            .apellido_paterno("apellido_paterno2")
            .apellido_materno("apellido_materno2")
            .telefono(2)
            .correo("correo2")
            .direccion("direccion2")
            .comuna("comuna2");
    }

    public static Paciente getPacienteRandomSampleGenerator() {
        return new Paciente()
            .id(longCount.incrementAndGet())
            .rut_paciente(UUID.randomUUID().toString())
            .nombres(UUID.randomUUID().toString())
            .apellido_paterno(UUID.randomUUID().toString())
            .apellido_materno(UUID.randomUUID().toString())
            .telefono(intCount.incrementAndGet())
            .correo(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString())
            .comuna(UUID.randomUUID().toString());
    }
}
