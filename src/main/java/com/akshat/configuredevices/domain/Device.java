package com.akshat.configuredevices.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Device Entity
 */
@ApiModel(description = "Device Entity")
@Entity
@Table(name = "device")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "device_name", nullable = false)
    private String deviceName;

    @OneToMany(mappedBy = "device")
    @JsonIgnore
    private Set<Policy> policies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Device deviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Set<Policy> getPolicies() {
        return policies;
    }

    public Device policies(Set<Policy> policies) {
        this.policies = policies;
        return this;
    }

    public Device addPolicy(Policy policy) {
        this.policies.add(policy);
        policy.setDevice(this);
        return this;
    }

    public Device removePolicy(Policy policy) {
        this.policies.remove(policy);
        policy.setDevice(null);
        return this;
    }

    public void setPolicies(Set<Policy> policies) {
        this.policies = policies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Device device = (Device) o;
        if (device.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), device.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Device{" +
            "id=" + getId() +
            ", deviceName='" + getDeviceName() + "'" +
            "}";
    }
}
