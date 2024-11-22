package com.aiep.sesamet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CentroMedico.
 */
@Entity
@Table(name = "centro_medico")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CentroMedico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "centro")
    private String centro;

    @Column(name = "region")
    private String region;

    @Column(name = "comuna")
    private String comuna;

    @Column(name = "direccion")
    private String direccion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "centroMedico")
    @JsonIgnoreProperties(value = { "centroMedico", "doctor", "paciente" }, allowSetters = true)
    private Set<Atencion> atencions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CentroMedico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCentro() {
        return this.centro;
    }

    public CentroMedico centro(String centro) {
        this.setCentro(centro);
        return this;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getRegion() {
        return this.region;
    }

    public CentroMedico region(String region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getComuna() {
        return this.comuna;
    }

    public CentroMedico comuna(String comuna) {
        this.setComuna(comuna);
        return this;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public CentroMedico direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Set<Atencion> getAtencions() {
        return this.atencions;
    }

    public void setAtencions(Set<Atencion> atencions) {
        if (this.atencions != null) {
            this.atencions.forEach(i -> i.setCentroMedico(null));
        }
        if (atencions != null) {
            atencions.forEach(i -> i.setCentroMedico(this));
        }
        this.atencions = atencions;
    }

    public CentroMedico atencions(Set<Atencion> atencions) {
        this.setAtencions(atencions);
        return this;
    }

    public CentroMedico addAtencion(Atencion atencion) {
        this.atencions.add(atencion);
        atencion.setCentroMedico(this);
        return this;
    }

    public CentroMedico removeAtencion(Atencion atencion) {
        this.atencions.remove(atencion);
        atencion.setCentroMedico(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CentroMedico)) {
            return false;
        }
        return getId() != null && getId().equals(((CentroMedico) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CentroMedico{" +
            "id=" + getId() +
            ", centro='" + getCentro() + "'" +
            ", region='" + getRegion() + "'" +
            ", comuna='" + getComuna() + "'" +
            ", direccion='" + getDireccion() + "'" +
            "}";
    }
}
