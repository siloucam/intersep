package inf.ufes.web.rest;

import inf.ufes.IntersepHipsterApp;

import inf.ufes.domain.Qualitativas;
import inf.ufes.repository.QualitativasRepository;
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
 * Test class for the QualitativasResource REST controller.
 *
 * @see QualitativasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntersepHipsterApp.class)
public class QualitativasResourceIntTest {

    @Autowired
    private QualitativasRepository qualitativasRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQualitativasMockMvc;

    private Qualitativas qualitativas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QualitativasResource qualitativasResource = new QualitativasResource(qualitativasRepository);
        this.restQualitativasMockMvc = MockMvcBuilders.standaloneSetup(qualitativasResource)
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
    public static Qualitativas createEntity(EntityManager em) {
        Qualitativas qualitativas = new Qualitativas();
        return qualitativas;
    }

    @Before
    public void initTest() {
        qualitativas = createEntity(em);
    }

    @Test
    @Transactional
    public void createQualitativas() throws Exception {
        int databaseSizeBeforeCreate = qualitativasRepository.findAll().size();

        // Create the Qualitativas
        restQualitativasMockMvc.perform(post("/api/qualitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qualitativas)))
            .andExpect(status().isCreated());

        // Validate the Qualitativas in the database
        List<Qualitativas> qualitativasList = qualitativasRepository.findAll();
        assertThat(qualitativasList).hasSize(databaseSizeBeforeCreate + 1);
        Qualitativas testQualitativas = qualitativasList.get(qualitativasList.size() - 1);
    }

    @Test
    @Transactional
    public void createQualitativasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = qualitativasRepository.findAll().size();

        // Create the Qualitativas with an existing ID
        qualitativas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQualitativasMockMvc.perform(post("/api/qualitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qualitativas)))
            .andExpect(status().isBadRequest());

        // Validate the Qualitativas in the database
        List<Qualitativas> qualitativasList = qualitativasRepository.findAll();
        assertThat(qualitativasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQualitativas() throws Exception {
        // Initialize the database
        qualitativasRepository.saveAndFlush(qualitativas);

        // Get all the qualitativasList
        restQualitativasMockMvc.perform(get("/api/qualitativas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qualitativas.getId().intValue())));
    }

    @Test
    @Transactional
    public void getQualitativas() throws Exception {
        // Initialize the database
        qualitativasRepository.saveAndFlush(qualitativas);

        // Get the qualitativas
        restQualitativasMockMvc.perform(get("/api/qualitativas/{id}", qualitativas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(qualitativas.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingQualitativas() throws Exception {
        // Get the qualitativas
        restQualitativasMockMvc.perform(get("/api/qualitativas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQualitativas() throws Exception {
        // Initialize the database
        qualitativasRepository.saveAndFlush(qualitativas);
        int databaseSizeBeforeUpdate = qualitativasRepository.findAll().size();

        // Update the qualitativas
        Qualitativas updatedQualitativas = qualitativasRepository.findOne(qualitativas.getId());
        // Disconnect from session so that the updates on updatedQualitativas are not directly saved in db
        em.detach(updatedQualitativas);

        restQualitativasMockMvc.perform(put("/api/qualitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQualitativas)))
            .andExpect(status().isOk());

        // Validate the Qualitativas in the database
        List<Qualitativas> qualitativasList = qualitativasRepository.findAll();
        assertThat(qualitativasList).hasSize(databaseSizeBeforeUpdate);
        Qualitativas testQualitativas = qualitativasList.get(qualitativasList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingQualitativas() throws Exception {
        int databaseSizeBeforeUpdate = qualitativasRepository.findAll().size();

        // Create the Qualitativas

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQualitativasMockMvc.perform(put("/api/qualitativas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qualitativas)))
            .andExpect(status().isCreated());

        // Validate the Qualitativas in the database
        List<Qualitativas> qualitativasList = qualitativasRepository.findAll();
        assertThat(qualitativasList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQualitativas() throws Exception {
        // Initialize the database
        qualitativasRepository.saveAndFlush(qualitativas);
        int databaseSizeBeforeDelete = qualitativasRepository.findAll().size();

        // Get the qualitativas
        restQualitativasMockMvc.perform(delete("/api/qualitativas/{id}", qualitativas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Qualitativas> qualitativasList = qualitativasRepository.findAll();
        assertThat(qualitativasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Qualitativas.class);
        Qualitativas qualitativas1 = new Qualitativas();
        qualitativas1.setId(1L);
        Qualitativas qualitativas2 = new Qualitativas();
        qualitativas2.setId(qualitativas1.getId());
        assertThat(qualitativas1).isEqualTo(qualitativas2);
        qualitativas2.setId(2L);
        assertThat(qualitativas1).isNotEqualTo(qualitativas2);
        qualitativas1.setId(null);
        assertThat(qualitativas1).isNotEqualTo(qualitativas2);
    }
}
