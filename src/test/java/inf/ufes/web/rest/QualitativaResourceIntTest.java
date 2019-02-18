package inf.ufes.web.rest;

import inf.ufes.IntersepApp;

import inf.ufes.domain.Qualitativa;
import inf.ufes.repository.QualitativaRepository;
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
 * Test class for the QualitativaResource REST controller.
 *
 * @see QualitativaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntersepApp.class)
public class QualitativaResourceIntTest {

    @Autowired
    private QualitativaRepository qualitativaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQualitativaMockMvc;

    private Qualitativa qualitativa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QualitativaResource qualitativaResource = new QualitativaResource(qualitativaRepository);
        this.restQualitativaMockMvc = MockMvcBuilders.standaloneSetup(qualitativaResource)
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
    public static Qualitativa createEntity(EntityManager em) {
        Qualitativa qualitativa = new Qualitativa();
        return qualitativa;
    }

    @Before
    public void initTest() {
        qualitativa = createEntity(em);
    }

    @Test
    @Transactional
    public void createQualitativa() throws Exception {
        int databaseSizeBeforeCreate = qualitativaRepository.findAll().size();

        // Create the Qualitativa
        restQualitativaMockMvc.perform(post("/api/qualitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qualitativa)))
            .andExpect(status().isCreated());

        // Validate the Qualitativa in the database
        List<Qualitativa> qualitativaList = qualitativaRepository.findAll();
        assertThat(qualitativaList).hasSize(databaseSizeBeforeCreate + 1);
        Qualitativa testQualitativa = qualitativaList.get(qualitativaList.size() - 1);
    }

    @Test
    @Transactional
    public void createQualitativaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = qualitativaRepository.findAll().size();

        // Create the Qualitativa with an existing ID
        qualitativa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQualitativaMockMvc.perform(post("/api/qualitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qualitativa)))
            .andExpect(status().isBadRequest());

        // Validate the Qualitativa in the database
        List<Qualitativa> qualitativaList = qualitativaRepository.findAll();
        assertThat(qualitativaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQualitativas() throws Exception {
        // Initialize the database
        qualitativaRepository.saveAndFlush(qualitativa);

        // Get all the qualitativaList
        restQualitativaMockMvc.perform(get("/api/qualitativas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qualitativa.getId().intValue())));
    }

    @Test
    @Transactional
    public void getQualitativa() throws Exception {
        // Initialize the database
        qualitativaRepository.saveAndFlush(qualitativa);

        // Get the qualitativa
        restQualitativaMockMvc.perform(get("/api/qualitativas/{id}", qualitativa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(qualitativa.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingQualitativa() throws Exception {
        // Get the qualitativa
        restQualitativaMockMvc.perform(get("/api/qualitativas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQualitativa() throws Exception {
        // Initialize the database
        qualitativaRepository.saveAndFlush(qualitativa);
        int databaseSizeBeforeUpdate = qualitativaRepository.findAll().size();

        // Update the qualitativa
        Qualitativa updatedQualitativa = qualitativaRepository.findOne(qualitativa.getId());
        // Disconnect from session so that the updates on updatedQualitativa are not directly saved in db
        em.detach(updatedQualitativa);

        restQualitativaMockMvc.perform(put("/api/qualitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQualitativa)))
            .andExpect(status().isOk());

        // Validate the Qualitativa in the database
        List<Qualitativa> qualitativaList = qualitativaRepository.findAll();
        assertThat(qualitativaList).hasSize(databaseSizeBeforeUpdate);
        Qualitativa testQualitativa = qualitativaList.get(qualitativaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingQualitativa() throws Exception {
        int databaseSizeBeforeUpdate = qualitativaRepository.findAll().size();

        // Create the Qualitativa

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQualitativaMockMvc.perform(put("/api/qualitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qualitativa)))
            .andExpect(status().isCreated());

        // Validate the Qualitativa in the database
        List<Qualitativa> qualitativaList = qualitativaRepository.findAll();
        assertThat(qualitativaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQualitativa() throws Exception {
        // Initialize the database
        qualitativaRepository.saveAndFlush(qualitativa);
        int databaseSizeBeforeDelete = qualitativaRepository.findAll().size();

        // Get the qualitativa
        restQualitativaMockMvc.perform(delete("/api/qualitativas/{id}", qualitativa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Qualitativa> qualitativaList = qualitativaRepository.findAll();
        assertThat(qualitativaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Qualitativa.class);
        Qualitativa qualitativa1 = new Qualitativa();
        qualitativa1.setId(1L);
        Qualitativa qualitativa2 = new Qualitativa();
        qualitativa2.setId(qualitativa1.getId());
        assertThat(qualitativa1).isEqualTo(qualitativa2);
        qualitativa2.setId(2L);
        assertThat(qualitativa1).isNotEqualTo(qualitativa2);
        qualitativa1.setId(null);
        assertThat(qualitativa1).isNotEqualTo(qualitativa2);
    }
}
