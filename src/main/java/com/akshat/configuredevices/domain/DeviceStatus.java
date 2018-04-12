package com.akshat.configuredevices.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DeviceStatus.
 */
@Entity
@Table(name = "device_status")
public class DeviceStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "details")
    private String details;

    @Column(name = "threshold")
    private String threshold;

    @Column(name = "total")
    private String total;

    @OneToOne
    @JoinColumn(unique = true)
    private Device device;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public DeviceStatus status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public DeviceStatus details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getThreshold() {
        return threshold;
    }

    public DeviceStatus threshold(String threshold) {
        this.threshold = threshold;
        return this;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getTotal() {
        return total;
    }

    public DeviceStatus total(String total) {
        this.total = total;
        return this;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Device getDevice() {
        return device;
    }

    public DeviceStatus device(Device device) {
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
        DeviceStatus deviceStatus = (DeviceStatus) o;
        if (deviceStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deviceStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeviceStatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", details='" + getDetails() + "'" +
            ", threshold='" + getThreshold() + "'" +
            ", total='" + getTotal() + "'" +
            "}";
    }
}
