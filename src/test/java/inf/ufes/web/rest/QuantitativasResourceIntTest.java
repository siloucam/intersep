package inf.ufes.web.rest;

import inf.ufes.IntersepHipsterApp;

import inf.ufes.domain.Quantitativas;
import inf.ufes.repository.QuantitativasRepository;
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
 * Test class for the QuantitativasResource REST controller.
 *
 * @see QuantitativasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntersepHipsterApp.class)
public class QuantitativasResourceIntTest {

    @Autowired
    private QuantitativasRepository quantitativasRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuantitativasMockMvc;

    private Quantitativas quantitativas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuantitativasResource quantitativasResource = new QuantitativasResource(quantitativasRepository);
        this.restQuantitativasMockMvc = MockMvcBuilders.standaloneSetup(quantitativasResource)
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
    public static Quantitativas createEntity(EntityManager em) {
        Quantitativas quantitativas = new Quantitativas();
        return quantitativas;
    }

    @Before
    public void initTest() {
        quantitativas = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuantitativas() throws Exception {
        int databaseSizeBeforeCreate = quantitativasRepository.findAll().size();

        // Create the Quantitativas
        restQuantitativasMockMvc.perform(post("/api/quantitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantitativas)))
            .andExpect(status().isCreated());

        // Validate the Quantitativas in the database
        List<Quantitativas> quantitativasList = quantitativasRepository.findAll();
        assertThat(quantitativasList).hasSize(databaseSizeBeforeCreate + 1);
        Quantitativas testQuantitativas = quantitativasList.get(quantitativasList.size() - 1);
    }

    @Test
    @Transactional
    public void createQuantitativasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quantitativasRepository.findAll().size();

        // Create the Quantitativas with an existing ID
        quantitativas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuantitativasMockMvc.perform(post("/api/quantitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantitativas)))
            .andExpect(status().isBadRequest());

        // Validate the Quantitativas in the database
        List<Quantitativas> quantitativasList = quantitativasRepository.findAll();
        assertThat(quantitativasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQuantitativas() throws Exception {
        // Initialize the database
        quantitativasRepository.saveAndFlush(quantitativas);

        // Get all the quantitativasList
        restQuantitativasMockMvc.perform(get("/api/quantitativas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quantitativas.getId().intValue())));
    }

    @Test
    @Transactional
    public void getQuantitativas() throws Exception {
        // Initialize the database
        quantitativasRepository.saveAndFlush(quantitativas);

        // Get the quantitativas
        restQuantitativasMockMvc.perform(get("/api/quantitativas/{id}", quantitativas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quantitativas.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingQuantitativas() throws Exception {
        // Get the quantitativas
        restQuantitativasMockMvc.perform(get("/api/quantitativas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuantitativas() throws Exception {
        // Initialize the database
        quantitativasRepository.saveAndFlush(quantitativas);
        int databaseSizeBeforeUpdate = quantitativasRepository.findAll().size();

        // Update the quantitativas
        Quantitativas updatedQuantitativas = quantitativasRepository.findOne(quantitativas.getId());
        // Disconnect from session so that the updates on updatedQuantitativas are not directly saved in db
        em.detach(updatedQuantitativas);

        restQuantitativasMockMvc.perform(put("/api/quantitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuantitativas)))
            .andExpect(status().isOk());

        // Validate the Quantitativas in the database
        List<Quantitativas> quantitativasList = quantitativasRepository.findAll();
        assertThat(quantitativasList).hasSize(databaseSizeBeforeUpdate);
        Quantitativas testQuantitativas = quantitativasList.get(quantitativasList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingQuantitativas() throws Exception {
        int databaseSizeBeforeUpdate = quantitativasRepository.findAll().size();

        // Create the Quantitativas

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuantitativasMockMvc.perform(put("/api/quantitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quantitativas)))
            .andExpect(status().isCreated());

        // Validate the Quantitativas in the database
        List<Quantitativas> quantitativasList = quantitativasRepository.findAll();
        assertThat(quantitativasList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuantitativas() throws Exception {
        // Initialize the database
        quantitativasRepository.saveAndFlush(quantitativas);
        int databaseSizeBeforeDelete = quantitativasRepository.findAll().size();

        // Get the quantitativas
        restQuantitativasMockMvc.perform(delete("/api/quantitativas/{id}", quantitativas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Quantitativas> quantitativasList = quantitativasRepository.findAll();
        assertThat(quantitativasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quantitativas.class);
        Quantitativas quantitativas1 = new Quantitativas();
        quantitativas1.setId(1L);
        Quantitativas quantitativas2 = new Quantitativas();
        quantitativas2.setId(quantitativas1.getId());
        assertThat(quantitativas1).isEqualTo(quantitativas2);
        quantitativas2.setId(2L);
        assertThat(quantitativas1).isNotEqualTo(quantitativas2);
        quantitativas1.setId(null);
        assertThat(quantitativas1).isNotEqualTo(quantitativas2);
    }
}
