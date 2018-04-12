package com.akshat.configuredevices.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.akshat.configuredevices.domain.Policy;

import com.akshat.configuredevices.repository.PolicyRepository;
import com.akshat.configuredevices.web.rest.errors.BadRequestAlertException;
import com.akshat.configuredevices.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Policy.
 */
@RestController
@RequestMapping("/api")
public class PolicyResource {

    private final Logger log = LoggerFactory.getLogger(PolicyResource.class);

    private static final String ENTITY_NAME = "policy";

    private final PolicyRepository policyRepository;

    public PolicyResource(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    /**
     * POST  /policies : Create a new policy.
     *
     * @param policy the policy to create
     * @return the ResponseEntity with status 201 (Created) and with body the new policy, or with status 400 (Bad Request) if the policy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/policies")
    @Timed
    public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy) throws URISyntaxException {
        log.debug("REST request to save Policy : {}", policy);
        if (policy.getId() != null) {
            throw new BadRequestAlertException("A new policy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Policy result = policyRepository.save(policy);
        return ResponseEntity.created(new URI("/api/policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /policies : Updates an existing policy.
     *
     * @param policy the policy to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated policy,
     * or with status 400 (Bad Request) if the policy is not valid,
     * or with status 500 (Internal Server Error) if the policy couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/policies")
    @Timed
    public ResponseEntity<Policy> updatePolicy(@RequestBody Policy policy) throws URISyntaxException {
        log.debug("REST request to update Policy : {}", policy);
        if (policy.getId() == null) {
            return createPolicy(policy);
        }
        Policy result = policyRepository.save(policy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, policy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /policies : get all the policies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of policies in body
     */
    @GetMapping("/policies")
    @Timed
    public List<Policy> getAllPolicies() {
        log.debug("REST request to get all Policies");
        return policyRepository.findAll();
        }

    /**
     * GET  /policies/:id : get the "id" policy.
     *
     * @param id the id of the policy to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the policy, or with status 404 (Not Found)
     */
    @GetMapping("/policies/{id}")
    @Timed
    public ResponseEntity<Policy> getPolicy(@PathVariable Long id) {
        log.debug("REST request to get Policy : {}", id);
        Policy policy = policyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(policy));
    }

    /**
     * DELETE  /policies/:id : delete the "id" policy.
     *
     * @param id the id of the policy to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/policies/{id}")
    @Timed
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        log.debug("REST request to delete Policy : {}", id);
        policyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
