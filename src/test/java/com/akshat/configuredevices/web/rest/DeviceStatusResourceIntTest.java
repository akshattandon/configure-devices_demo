package com.akshat.configuredevices.web.rest;

import com.akshat.configuredevices.ConfiguredevicesApp;

import com.akshat.configuredevices.domain.DeviceStatus;
import com.akshat.configuredevices.repository.DeviceStatusRepository;
import com.akshat.configuredevices.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.akshat.configuredevices.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DeviceStatusResource REST controller.
 *
 * @see DeviceStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfiguredevicesApp.class)
public class DeviceStatusResourceIntTest {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_THRESHOLD = "AAAAAAAAAA";
    private static final String UPDATED_THRESHOLD = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL = "BBBBBBBBBB";

    @Autowired
    private DeviceStatusRepository deviceStatusRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeviceStatusMockMvc;

    private DeviceStatus deviceStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeviceStatusResource deviceStatusResource = new DeviceStatusResource(deviceStatusRepository);
        this.restDeviceStatusMockMvc = MockMvcBuilders.standaloneSetup(deviceStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceStatus createEntity(EntityManager em) {
        DeviceStatus deviceStatus = new DeviceStatus()
            .status(DEFAULT_STATUS)
            .details(DEFAULT_DETAILS)
            .threshold(DEFAULT_THRESHOLD)
            .total(DEFAULT_TOTAL);
        return deviceStatus;
    }

    @Before
    public void initTest() {
        deviceStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeviceStatus() throws Exception {
        int databaseSizeBeforeCreate = deviceStatusRepository.findAll().size();

        // Create the DeviceStatus
        restDeviceStatusMockMvc.perform(post("/api/device-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceStatus)))
            .andExpect(status().isCreated());

        // Validate the DeviceStatus in the database
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceStatus testDeviceStatus = deviceStatusList.get(deviceStatusList.size() - 1);
        assertThat(testDeviceStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDeviceStatus.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testDeviceStatus.getThreshold()).isEqualTo(DEFAULT_THRESHOLD);
        assertThat(testDeviceStatus.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createDeviceStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceStatusRepository.findAll().size();

        // Create the DeviceStatus with an existing ID
        deviceStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceStatusMockMvc.perform(post("/api/device-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceStatus)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceStatus in the database
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeviceStatuses() throws Exception {
        // Initialize the database
        deviceStatusRepository.saveAndFlush(deviceStatus);

        // Get all the deviceStatusList
        restDeviceStatusMockMvc.perform(get("/api/device-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].threshold").value(hasItem(DEFAULT_THRESHOLD.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.toString())));
    }

    @Test
    @Transactional
    public void getDeviceStatus() throws Exception {
        // Initialize the database
        deviceStatusRepository.saveAndFlush(deviceStatus);

        // Get the deviceStatus
        restDeviceStatusMockMvc.perform(get("/api/device-statuses/{id}", deviceStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deviceStatus.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()))
            .andExpect(jsonPath("$.threshold").value(DEFAULT_THRESHOLD.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeviceStatus() throws Exception {
        // Get the deviceStatus
        restDeviceStatusMockMvc.perform(get("/api/device-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceStatus() throws Exception {
        // Initialize the database
        deviceStatusRepository.saveAndFlush(deviceStatus);
        int databaseSizeBeforeUpdate = deviceStatusRepository.findAll().size();

        // Update the deviceStatus
        DeviceStatus updatedDeviceStatus = deviceStatusRepository.findOne(deviceStatus.getId());
        // Disconnect from session so that the updates on updatedDeviceStatus are not directly saved in db
        em.detach(updatedDeviceStatus);
        updatedDeviceStatus
            .status(UPDATED_STATUS)
            .details(UPDATED_DETAILS)
            .threshold(UPDATED_THRESHOLD)
            .total(UPDATED_TOTAL);

        restDeviceStatusMockMvc.perform(put("/api/device-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeviceStatus)))
            .andExpect(status().isOk());

        // Validate the DeviceStatus in the database
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeUpdate);
        DeviceStatus testDeviceStatus = deviceStatusList.get(deviceStatusList.size() - 1);
        assertThat(testDeviceStatus.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDeviceStatus.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testDeviceStatus.getThreshold()).isEqualTo(UPDATED_THRESHOLD);
        assertThat(testDeviceStatus.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingDeviceStatus() throws Exception {
        int databaseSizeBeforeUpdate = deviceStatusRepository.findAll().size();

        // Create the DeviceStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeviceStatusMockMvc.perform(put("/api/device-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deviceStatus)))
            .andExpect(status().isCreated());

        // Validate the DeviceStatus in the database
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDeviceStatus() throws Exception {
        // Initialize the database
        deviceStatusRepository.saveAndFlush(deviceStatus);
        int databaseSizeBeforeDelete = deviceStatusRepository.findAll().size();

        // Get the deviceStatus
        restDeviceStatusMockMvc.perform(delete("/api/device-statuses/{id}", deviceStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeviceStatus> deviceStatusList = deviceStatusRepository.findAll();
        assertThat(deviceStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceStatus.class);
        DeviceStatus deviceStatus1 = new DeviceStatus();
        deviceStatus1.setId(1L);
        DeviceStatus deviceStatus2 = new DeviceStatus();
        deviceStatus2.setId(deviceStatus1.getId());
        assertThat(deviceStatus1).isEqualTo(deviceStatus2);
        deviceStatus2.setId(2L);
        assertThat(deviceStatus1).isNotEqualTo(deviceStatus2);
        deviceStatus1.setId(null);
        assertThat(deviceStatus1).isNotEqualTo(deviceStatus2);
    }
}
