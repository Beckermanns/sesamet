package com.aiep.sesamet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "rut_paciente")
    private String rut_paciente;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellido_paterno")
    private String apellido_paterno;

    @Column(name = "apellido_materno")
    private String apellido_materno;

    @Column(name = "telefono")
    private Integer telefono;

    @Column(name = "correo")
    private String correo;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "comuna")
    private String comuna;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paciente")
    @JsonIgnoreProperties(value = { "centroMedico", "doctor", "paciente" }, allowSetters = true)
    private Set<Atencion> atencions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paciente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut_paciente() {
        return this.rut_paciente;
    }

    public Paciente rut_paciente(String rut_paciente) {
        this.setRut_paciente(rut_paciente);
        return this;
    }

    public void setRut_paciente(String rut_paciente) {
        this.rut_paciente = rut_paciente;
    }

    public String getNombres() {
        return this.nombres;
    }

    public Paciente nombres(String nombres) {
        this.setNombres(nombres);
        return this;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellido_paterno() {
        return this.apellido_paterno;
    }

    public Paciente apellido_paterno(String apellido_paterno) {
        this.setApellido_paterno(apellido_paterno);
        return this;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return this.apellido_materno;
    }

    public Paciente apellido_materno(String apellido_materno) {
        this.setApellido_materno(apellido_materno);
        return this;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public Integer getTelefono() {
        return this.telefono;
    }

    public Paciente telefono(Integer telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return this.correo;
    }

    public Paciente correo(String correo) {
        this.setCorreo(correo);
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Paciente direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getComuna() {
        return this.comuna;
    }

    public Paciente comuna(String comuna) {
        this.setComuna(comuna);
        return this;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public Set<Atencion> getAtencions() {
        return this.atencions;
    }

    public void setAtencions(Set<Atencion> atencions) {
        if (this.atencions != null) {
            this.atencions.forEach(i -> i.setPaciente(null));
        }
        if (atencions != null) {
            atencions.forEach(i -> i.setPaciente(this));
        }
        this.atencions = atencions;
    }

    public Paciente atencions(Set<Atencion> atencions) {
        this.setAtencions(atencions);
        return this;
    }

    public Paciente addAtencion(Atencion atencion) {
        this.atencions.add(atencion);
        atencion.setPaciente(this);
        return this;
    }

    public Paciente removeAtencion(Atencion atencion) {
        this.atencions.remove(atencion);
        atencion.setPaciente(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paciente)) {
            return false;
        }
        return getId() != null && getId().equals(((Paciente) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            ", rut_paciente='" + getRut_paciente() + "'" +
            ", nombres='" + getNombres() + "'" +
            ", apellido_paterno='" + getApellido_paterno() + "'" +
            ", apellido_materno='" + getApellido_materno() + "'" +
            ", telefono=" + getTelefono() +
            ", correo='" + getCorreo() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", comuna='" + getComuna() + "'" +
            "}";
    }
}
