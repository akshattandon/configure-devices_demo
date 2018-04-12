package com.akshat.configuredevices.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

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

    public String getCountryName() {
        return countryName;
    }

    public Location countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Location streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public Location city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public Location stateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public Device getDevice() {
        return device;
    }

    public Location device(Device device) {
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
        Location location = (Location) o;
        if (location.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            "}";
    }
}
