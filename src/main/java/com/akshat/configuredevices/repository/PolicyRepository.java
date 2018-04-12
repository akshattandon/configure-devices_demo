package com.akshat.configuredevices.repository;

import com.akshat.configuredevices.domain.Policy;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Policy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

}
