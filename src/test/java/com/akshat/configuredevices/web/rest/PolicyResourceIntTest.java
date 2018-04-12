package com.akshat.configuredevices.web.rest;

import com.akshat.configuredevices.ConfiguredevicesApp;

import com.akshat.configuredevices.domain.Policy;
import com.akshat.configuredevices.repository.PolicyRepository;
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
 * Test class for the PolicyResource REST controller.
 *
 * @see PolicyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfiguredevicesApp.class)
public class PolicyResourceIntTest {

    private static final String DEFAULT_POLICY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_POLICY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRMWARE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_FIRMWARE_VERSION = "BBBBBBBBBB";

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPolicyMockMvc;

    private Policy policy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PolicyResource policyResource = new PolicyResource(policyRepository);
        this.restPolicyMockMvc = MockMvcBuilders.standaloneSetup(policyResource)
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
    public static Policy createEntity(EntityManager em) {
        Policy policy = new Policy()
            .policyName(DEFAULT_POLICY_NAME)
            .firmwareVersion(DEFAULT_FIRMWARE_VERSION);
        return policy;
    }

    @Before
    public void initTest() {
        policy = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolicy() throws Exception {
        int databaseSizeBeforeCreate = policyRepository.findAll().size();

        // Create the Policy
        restPolicyMockMvc.perform(post("/api/policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isCreated());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeCreate + 1);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getPolicyName()).isEqualTo(DEFAULT_POLICY_NAME);
        assertThat(testPolicy.getFirmwareVersion()).isEqualTo(DEFAULT_FIRMWARE_VERSION);
    }

    @Test
    @Transactional
    public void createPolicyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = policyRepository.findAll().size();

        // Create the Policy with an existing ID
        policy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyMockMvc.perform(post("/api/policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPolicies() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList
        restPolicyMockMvc.perform(get("/api/policies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policy.getId().intValue())))
            .andExpect(jsonPath("$.[*].policyName").value(hasItem(DEFAULT_POLICY_NAME.toString())))
            .andExpect(jsonPath("$.[*].firmwareVersion").value(hasItem(DEFAULT_FIRMWARE_VERSION.toString())));
    }

    @Test
    @Transactional
    public void getPolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get the policy
        restPolicyMockMvc.perform(get("/api/policies/{id}", policy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(policy.getId().intValue()))
            .andExpect(jsonPath("$.policyName").value(DEFAULT_POLICY_NAME.toString()))
            .andExpect(jsonPath("$.firmwareVersion").value(DEFAULT_FIRMWARE_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPolicy() throws Exception {
        // Get the policy
        restPolicyMockMvc.perform(get("/api/policies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Update the policy
        Policy updatedPolicy = policyRepository.findOne(policy.getId());
        // Disconnect from session so that the updates on updatedPolicy are not directly saved in db
        em.detach(updatedPolicy);
        updatedPolicy
            .policyName(UPDATED_POLICY_NAME)
            .firmwareVersion(UPDATED_FIRMWARE_VERSION);

        restPolicyMockMvc.perform(put("/api/policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPolicy)))
            .andExpect(status().isOk());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getPolicyName()).isEqualTo(UPDATED_POLICY_NAME);
        assertThat(testPolicy.getFirmwareVersion()).isEqualTo(UPDATED_FIRMWARE_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Create the Policy

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPolicyMockMvc.perform(put("/api/policies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(policy)))
            .andExpect(status().isCreated());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);
        int databaseSizeBeforeDelete = policyRepository.findAll().size();

        // Get the policy
        restPolicyMockMvc.perform(delete("/api/policies/{id}", policy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Policy.class);
        Policy policy1 = new Policy();
        policy1.setId(1L);
        Policy policy2 = new Policy();
        policy2.setId(policy1.getId());
        assertThat(policy1).isEqualTo(policy2);
        policy2.setId(2L);
        assertThat(policy1).isNotEqualTo(policy2);
        policy1.setId(null);
        assertThat(policy1).isNotEqualTo(policy2);
    }
}
