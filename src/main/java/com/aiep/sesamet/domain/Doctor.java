package com.aiep.sesamet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Doctor.
 */
@Entity
@Table(name = "doctor")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "rut_doctor")
    private String rut_doctor;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido_paterno")
    private String apellido_paterno;

    @Column(name = "apellido_materno")
    private String apellido_materno;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "correo")
    private String correo;

    @Column(name = "especialidad")
    private String especialidad;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    @JsonIgnoreProperties(value = { "centroMedico", "doctor", "paciente" }, allowSetters = true)
    private Set<Atencion> atencions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Doctor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut_doctor() {
        return this.rut_doctor;
    }

    public Doctor rut_doctor(String rut_doctor) {
        this.setRut_doctor(rut_doctor);
        return this;
    }

    public void setRut_doctor(String rut_doctor) {
        this.rut_doctor = rut_doctor;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Doctor nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_paterno() {
        return this.apellido_paterno;
    }

    public Doctor apellido_paterno(String apellido_paterno) {
        this.setApellido_paterno(apellido_paterno);
        return this;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return this.apellido_materno;
    }

    public Doctor apellido_materno(String apellido_materno) {
        this.setApellido_materno(apellido_materno);
        return this;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public Integer getTelefono() {
        return this.telefono;
    }

    public Doctor telefono(Integer telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return this.correo;
    }

    public Doctor correo(String correo) {
        this.setCorreo(correo);
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEspecialidad() {
        return this.especialidad;
    }

    public Doctor especialidad(String especialidad) {
        this.setEspecialidad(especialidad);
        return this;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Set<Atencion> getAtencions() {
        return this.atencions;
    }

    public void setAtencions(Set<Atencion> atencions) {
        if (this.atencions != null) {
            this.atencions.forEach(i -> i.setDoctor(null));
        }
        if (atencions != null) {
            atencions.forEach(i -> i.setDoctor(this));
        }
        this.atencions = atencions;
    }

    public Doctor atencions(Set<Atencion> atencions) {
        this.setAtencions(atencions);
        return this;
    }

    public Doctor addAtencion(Atencion atencion) {
        this.atencions.add(atencion);
        atencion.setDoctor(this);
        return this;
    }

    public Doctor removeAtencion(Atencion atencion) {
        this.atencions.remove(atencion);
        atencion.setDoctor(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doctor)) {
            return false;
        }
        return getId() != null && getId().equals(((Doctor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Doctor{" +
            "id=" + getId() +
            ", rut_doctor='" + getRut_doctor() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido_paterno='" + getApellido_paterno() + "'" +
            ", apellido_materno='" + getApellido_materno() + "'" +
            ", telefono=" + getTelefono() +
            ", correo='" + getCorreo() + "'" +
            ", especialidad='" + getEspecialidad() + "'" +
            "}";
    }
}
