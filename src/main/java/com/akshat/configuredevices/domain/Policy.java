package com.akshat.configuredevices.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Policy.
 */
@Entity
@Table(name = "policy")
public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "policy_name")
    private String policyName;

    @Column(name = "firmware_version")
    private String firmwareVersion;

    @ManyToOne
    private Device device;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyName() {
        return policyName;
    }

    public Policy policyName(String policyName) {
        this.policyName = policyName;
        return this;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public Policy firmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
        return this;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public Device getDevice() {
        return device;
    }

    public Policy device(Device device) {
        this.device = device;
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
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
        Policy policy = (Policy) o;
        if (policy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), policy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Policy{" +
            "id=" + getId() +
            ", policyName='" + getPolicyName() + "'" +
            ", firmwareVersion='" + getFirmwareVersion() + "'" +
            "}";
    }
}
