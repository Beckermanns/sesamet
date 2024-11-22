package com.aiep.sesamet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Atencion.
 */
@Entity
@Table(name = "atencion")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Atencion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    private String hora;

    @Column(name = "motivo_consulta")
    private String motivo_consulta;

    @Column(name = "diagnostico")
    private String diagnostico;

    @Column(name = "tratamiento")
    private String tratamiento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "atencions" }, allowSetters = true)
    private CentroMedico centroMedico;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "atencions" }, allowSetters = true)
    private Doctor doctor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "atencions" }, allowSetters = true)
    private Paciente paciente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Atencion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Atencion fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return this.hora;
    }

    public Atencion hora(String hora) {
        this.setHora(hora);
        return this;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivo_consulta() {
        return this.motivo_consulta;
    }

    public Atencion motivo_consulta(String motivo_consulta) {
        this.setMotivo_consulta(motivo_consulta);
        return this;
    }

    public void setMotivo_consulta(String motivo_consulta) {
        this.motivo_consulta = motivo_consulta;
    }

    public String getDiagnostico() {
        return this.diagnostico;
    }

    public Atencion diagnostico(String diagnostico) {
        this.setDiagnostico(diagnostico);
        return this;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return this.tratamiento;
    }

    public Atencion tratamiento(String tratamiento) {
        this.setTratamiento(tratamiento);
        return this;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public CentroMedico getCentroMedico() {
        return this.centroMedico;
    }

    public void setCentroMedico(CentroMedico centroMedico) {
        this.centroMedico = centroMedico;
    }

    public Atencion centroMedico(CentroMedico centroMedico) {
        this.setCentroMedico(centroMedico);
        return this;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Atencion doctor(Doctor doctor) {
        this.setDoctor(doctor);
        return this;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Atencion paciente(Paciente paciente) {
        this.setPaciente(paciente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Atencion)) {
            return false;
        }
        return getId() != null && getId().equals(((Atencion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Atencion{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", hora='" + getHora() + "'" +
            ", motivo_consulta='" + getMotivo_consulta() + "'" +
            ", diagnostico='" + getDiagnostico() + "'" +
            ", tratamiento='" + getTratamiento() + "'" +
            "}";
    }
}
