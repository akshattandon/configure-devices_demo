package com.akshat.configuredevices.repository;

import com.akshat.configuredevices.domain.DeviceStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DeviceStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long> {

}
