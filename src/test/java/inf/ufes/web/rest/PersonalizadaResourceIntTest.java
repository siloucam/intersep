package inf.ufes.web.rest;

import inf.ufes.IntersepApp;

import inf.ufes.domain.Personalizada;
import inf.ufes.repository.PersonalizadaRepository;
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
 * Test class for the PersonalizadaResource REST controller.
 *
 * @see PersonalizadaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntersepApp.class)
public class PersonalizadaResourceIntTest {

    private static final String DEFAULT_IDENTIFICADOR = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICADOR = "BBBBBBBBBB";

    private static final String DEFAULT_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_QUERY = "BBBBBBBBBB";

    @Autowired
    private PersonalizadaRepository personalizadaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonalizadaMockMvc;

    private Personalizada personalizada;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonalizadaResource personalizadaResource = new PersonalizadaResource(personalizadaRepository);
        this.restPersonalizadaMockMvc = MockMvcBuilders.standaloneSetup(personalizadaResource)
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
    public static Personalizada createEntity(EntityManager em) {
        Personalizada personalizada = new Personalizada()
            .identificador(DEFAULT_IDENTIFICADOR)
            .query(DEFAULT_QUERY);
        return personalizada;
    }

    @Before
    public void initTest() {
        personalizada = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonalizada() throws Exception {
        int databaseSizeBeforeCreate = personalizadaRepository.findAll().size();

        // Create the Personalizada
        restPersonalizadaMockMvc.perform(post("/api/personalizadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalizada)))
            .andExpect(status().isCreated());

        // Validate the Personalizada in the database
        List<Personalizada> personalizadaList = personalizadaRepository.findAll();
        assertThat(personalizadaList).hasSize(databaseSizeBeforeCreate + 1);
        Personalizada testPersonalizada = personalizadaList.get(personalizadaList.size() - 1);
        assertThat(testPersonalizada.getIdentificador()).isEqualTo(DEFAULT_IDENTIFICADOR);
        assertThat(testPersonalizada.getQuery()).isEqualTo(DEFAULT_QUERY);
    }

    @Test
    @Transactional
    public void createPersonalizadaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personalizadaRepository.findAll().size();

        // Create the Personalizada with an existing ID
        personalizada.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalizadaMockMvc.perform(post("/api/personalizadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalizada)))
            .andExpect(status().isBadRequest());

        // Validate the Personalizada in the database
        List<Personalizada> personalizadaList = personalizadaRepository.findAll();
        assertThat(personalizadaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonalizadas() throws Exception {
        // Initialize the database
        personalizadaRepository.saveAndFlush(personalizada);

        // Get all the personalizadaList
        restPersonalizadaMockMvc.perform(get("/api/personalizadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalizada.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificador").value(hasItem(DEFAULT_IDENTIFICADOR.toString())))
            .andExpect(jsonPath("$.[*].query").value(hasItem(DEFAULT_QUERY.toString())));
    }

    @Test
    @Transactional
    public void getPersonalizada() throws Exception {
        // Initialize the database
        personalizadaRepository.saveAndFlush(personalizada);

        // Get the personalizada
        restPersonalizadaMockMvc.perform(get("/api/personalizadas/{id}", personalizada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personalizada.getId().intValue()))
            .andExpect(jsonPath("$.identificador").value(DEFAULT_IDENTIFICADOR.toString()))
            .andExpect(jsonPath("$.query").value(DEFAULT_QUERY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonalizada() throws Exception {
        // Get the personalizada
        restPersonalizadaMockMvc.perform(get("/api/personalizadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonalizada() throws Exception {
        // Initialize the database
        personalizadaRepository.saveAndFlush(personalizada);
        int databaseSizeBeforeUpdate = personalizadaRepository.findAll().size();

        // Update the personalizada
        Personalizada updatedPersonalizada = personalizadaRepository.findOne(personalizada.getId());
        // Disconnect from session so that the updates on updatedPersonalizada are not directly saved in db
        em.detach(updatedPersonalizada);
        updatedPersonalizada
            .identificador(UPDATED_IDENTIFICADOR)
            .query(UPDATED_QUERY);

        restPersonalizadaMockMvc.perform(put("/api/personalizadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonalizada)))
            .andExpect(status().isOk());

        // Validate the Personalizada in the database
        List<Personalizada> personalizadaList = personalizadaRepository.findAll();
        assertThat(personalizadaList).hasSize(databaseSizeBeforeUpdate);
        Personalizada testPersonalizada = personalizadaList.get(personalizadaList.size() - 1);
        assertThat(testPersonalizada.getIdentificador()).isEqualTo(UPDATED_IDENTIFICADOR);
        assertThat(testPersonalizada.getQuery()).isEqualTo(UPDATED_QUERY);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonalizada() throws Exception {
        int databaseSizeBeforeUpdate = personalizadaRepository.findAll().size();

        // Create the Personalizada

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonalizadaMockMvc.perform(put("/api/personalizadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalizada)))
            .andExpect(status().isCreated());

        // Validate the Personalizada in the database
        List<Personalizada> personalizadaList = personalizadaRepository.findAll();
        assertThat(personalizadaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonalizada() throws Exception {
        // Initialize the database
        personalizadaRepository.saveAndFlush(personalizada);
        int databaseSizeBeforeDelete = personalizadaRepository.findAll().size();

        // Get the personalizada
        restPersonalizadaMockMvc.perform(delete("/api/personalizadas/{id}", personalizada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Personalizada> personalizadaList = personalizadaRepository.findAll();
        assertThat(personalizadaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personalizada.class);
        Personalizada personalizada1 = new Personalizada();
        personalizada1.setId(1L);
        Personalizada personalizada2 = new Personalizada();
        personalizada2.setId(personalizada1.getId());
        assertThat(personalizada1).isEqualTo(personalizada2);
        personalizada2.setId(2L);
        assertThat(personalizada1).isNotEqualTo(personalizada2);
        personalizada1.setId(null);
        assertThat(personalizada1).isNotEqualTo(personalizada2);
    }
}
