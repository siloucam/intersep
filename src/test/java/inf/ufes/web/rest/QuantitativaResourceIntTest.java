package inf.ufes.web.rest;

import inf.ufes.IntersepApp;

import inf.ufes.domain.Quantitativa;
import inf.ufes.repository.QuantitativaRepository;
import inf.ufes.web.rest.errors.ExceptionTranslator;

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

import static inf.ufes.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QuantitativaResource REST controller.
 *
 * @see QuantitativaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntersepApp.class)
public class QuantitativaResourceIntTest {

    @Autowired
    private QuantitativaRepository quantitativaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuantitativaMockMvc;

    private Quantitativa quantitativa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuantitativaResource quantitativaResource = new QuantitativaResource(quantitativaRepository);
        this.restQuantitativaMockMvc = MockMvcBuilders.standaloneSetup(quantitativaResource)
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
    public static Quantitativa createEntity(EntityManager em) {
        Quantitativa quantitativa = new Quantitativa();
        return quantitativa;
    }

    @Before
    public void initTest() {
        quantitativa = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuantitativa() throws Exception {
        int databaseSizeBeforeCreate = quantitativaRepository.findAll().size();

        // Create the Quantitativa
        restQuantitativaMockMvc.perform(post("/api/quantitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantitativa)))
            .andExpect(status().isCreated());

        // Validate the Quantitativa in the database
        List<Quantitativa> quantitativaList = quantitativaRepository.findAll();
        assertThat(quantitativaList).hasSize(databaseSizeBeforeCreate + 1);
        Quantitativa testQuantitativa = quantitativaList.get(quantitativaList.size() - 1);
    }

    @Test
    @Transactional
    public void createQuantitativaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quantitativaRepository.findAll().size();

        // Create the Quantitativa with an existing ID
        quantitativa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuantitativaMockMvc.perform(post("/api/quantitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantitativa)))
            .andExpect(status().isBadRequest());

        // Validate the Quantitativa in the database
        List<Quantitativa> quantitativaList = quantitativaRepository.findAll();
        assertThat(quantitativaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQuantitativas() throws Exception {
        // Initialize the database
        quantitativaRepository.saveAndFlush(quantitativa);

        // Get all the quantitativaList
        restQuantitativaMockMvc.perform(get("/api/quantitativas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quantitativa.getId().intValue())));
    }

    @Test
    @Transactional
    public void getQuantitativa() throws Exception {
        // Initialize the database
        quantitativaRepository.saveAndFlush(quantitativa);

        // Get the quantitativa
        restQuantitativaMockMvc.perform(get("/api/quantitativas/{id}", quantitativa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quantitativa.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingQuantitativa() throws Exception {
        // Get the quantitativa
        restQuantitativaMockMvc.perform(get("/api/quantitativas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuantitativa() throws Exception {
        // Initialize the database
        quantitativaRepository.saveAndFlush(quantitativa);
        int databaseSizeBeforeUpdate = quantitativaRepository.findAll().size();

        // Update the quantitativa
        Quantitativa updatedQuantitativa = quantitativaRepository.findOne(quantitativa.getId());
        // Disconnect from session so that the updates on updatedQuantitativa are not directly saved in db
        em.detach(updatedQuantitativa);

        restQuantitativaMockMvc.perform(put("/api/quantitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuantitativa)))
            .andExpect(status().isOk());

        // Validate the Quantitativa in the database
        List<Quantitativa> quantitativaList = quantitativaRepository.findAll();
        assertThat(quantitativaList).hasSize(databaseSizeBeforeUpdate);
        Quantitativa testQuantitativa = quantitativaList.get(quantitativaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingQuantitativa() throws Exception {
        int databaseSizeBeforeUpdate = quantitativaRepository.findAll().size();

        // Create the Quantitativa

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuantitativaMockMvc.perform(put("/api/quantitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantitativa)))
            .andExpect(status().isCreated());

        // Validate the Quantitativa in the database
        List<Quantitativa> quantitativaList = quantitativaRepository.findAll();
        assertThat(quantitativaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuantitativa() throws Exception {
        // Initialize the database
        quantitativaRepository.saveAndFlush(quantitativa);
        int databaseSizeBeforeDelete = quantitativaRepository.findAll().size();

        // Get the quantitativa
        restQuantitativaMockMvc.perform(delete("/api/quantitativas/{id}", quantitativa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Quantitativa> quantitativaList = quantitativaRepository.findAll();
        assertThat(quantitativaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quantitativa.class);
        Quantitativa quantitativa1 = new Quantitativa();
        quantitativa1.setId(1L);
        Quantitativa quantitativa2 = new Quantitativa();
        quantitativa2.setId(quantitativa1.getId());
        assertThat(quantitativa1).isEqualTo(quantitativa2);
        quantitativa2.setId(2L);
        assertThat(quantitativa1).isNotEqualTo(quantitativa2);
        quantitativa1.setId(null);
        assertThat(quantitativa1).isNotEqualTo(quantitativa2);
    }
}
