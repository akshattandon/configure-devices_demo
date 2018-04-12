package com.akshat.configuredevices.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.akshat.configuredevices.domain.DeviceStatus;

import com.akshat.configuredevices.repository.DeviceStatusRepository;
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
 * REST controller for managing DeviceStatus.
 */
@RestController
@RequestMapping("/api")
public class DeviceStatusResource {

    private final Logger log = LoggerFactory.getLogger(DeviceStatusResource.class);

    private static final String ENTITY_NAME = "deviceStatus";

    private final DeviceStatusRepository deviceStatusRepository;

    public DeviceStatusResource(DeviceStatusRepository deviceStatusRepository) {
        this.deviceStatusRepository = deviceStatusRepository;
    }

    /**
     * POST  /device-statuses : Create a new deviceStatus.
     *
     * @param deviceStatus the deviceStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deviceStatus, or with status 400 (Bad Request) if the deviceStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/device-statuses")
    @Timed
    public ResponseEntity<DeviceStatus> createDeviceStatus(@RequestBody DeviceStatus deviceStatus) throws URISyntaxException {
        log.debug("REST request to save DeviceStatus : {}", deviceStatus);
        if (deviceStatus.getId() != null) {
            throw new BadRequestAlertException("A new deviceStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceStatus result = deviceStatusRepository.save(deviceStatus);
        return ResponseEntity.created(new URI("/api/device-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /device-statuses : Updates an existing deviceStatus.
     *
     * @param deviceStatus the deviceStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deviceStatus,
     * or with status 400 (Bad Request) if the deviceStatus is not valid,
     * or with status 500 (Internal Server Error) if the deviceStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/device-statuses")
    @Timed
    public ResponseEntity<DeviceStatus> updateDeviceStatus(@RequestBody DeviceStatus deviceStatus) throws URISyntaxException {
        log.debug("REST request to update DeviceStatus : {}", deviceStatus);
        if (deviceStatus.getId() == null) {
            return createDeviceStatus(deviceStatus);
        }
        DeviceStatus result = deviceStatusRepository.save(deviceStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deviceStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /device-statuses : get all the deviceStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of deviceStatuses in body
     */
    @GetMapping("/device-statuses")
    @Timed
    public List<DeviceStatus> getAllDeviceStatuses() {
        log.debug("REST request to get all DeviceStatuses");
        return deviceStatusRepository.findAll();
        }

    /**
     * GET  /device-statuses/:id : get the "id" deviceStatus.
     *
     * @param id the id of the deviceStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deviceStatus, or with status 404 (Not Found)
     */
    @GetMapping("/device-statuses/{id}")
    @Timed
    public ResponseEntity<DeviceStatus> getDeviceStatus(@PathVariable Long id) {
        log.debug("REST request to get DeviceStatus : {}", id);
        DeviceStatus deviceStatus = deviceStatusRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(deviceStatus));
    }

    /**
     * DELETE  /device-statuses/:id : delete the "id" deviceStatus.
     *
     * @param id the id of the deviceStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/device-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeviceStatus(@PathVariable Long id) {
        log.debug("REST request to delete DeviceStatus : {}", id);
        deviceStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
