package inf.ufes.web.rest;

import com.codahale.metrics.annotation.Timed;
import inf.ufes.domain.Personalizada;

import inf.ufes.repository.PersonalizadaRepository;
import inf.ufes.web.rest.errors.BadRequestAlertException;
import inf.ufes.web.rest.util.HeaderUtil;
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
 * REST controller for managing Personalizada.
 */
@RestController
@RequestMapping("/api")
public class PersonalizadaResource {

    private final Logger log = LoggerFactory.getLogger(PersonalizadaResource.class);

    private static final String ENTITY_NAME = "personalizada";

    private final PersonalizadaRepository personalizadaRepository;

    public PersonalizadaResource(PersonalizadaRepository personalizadaRepository) {
        this.personalizadaRepository = personalizadaRepository;
    }

    /**
     * POST  /personalizadas : Create a new personalizada.
     *
     * @param personalizada the personalizada to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personalizada, or with status 400 (Bad Request) if the personalizada has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personalizadas")
    @Timed
    public ResponseEntity<Personalizada> createPersonalizada(@RequestBody Personalizada personalizada) throws URISyntaxException {
        log.debug("REST request to save Personalizada : {}", personalizada);
        if (personalizada.getId() != null) {
            throw new BadRequestAlertException("A new personalizada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Personalizada result = personalizadaRepository.save(personalizada);
        return ResponseEntity.created(new URI("/api/personalizadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personalizadas : Updates an existing personalizada.
     *
     * @param personalizada the personalizada to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personalizada,
     * or with status 400 (Bad Request) if the personalizada is not valid,
     * or with status 500 (Internal Server Error) if the personalizada couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personalizadas")
    @Timed
    public ResponseEntity<Personalizada> updatePersonalizada(@RequestBody Personalizada personalizada) throws URISyntaxException {
        log.debug("REST request to update Personalizada : {}", personalizada);
        if (personalizada.getId() == null) {
            return createPersonalizada(personalizada);
        }
        Personalizada result = personalizadaRepository.save(personalizada);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personalizada.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personalizadas : get all the personalizadas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personalizadas in body
     */
    @GetMapping("/personalizadas")
    @Timed
    public List<Personalizada> getAllPersonalizadas() {
        log.debug("REST request to get all Personalizadas");
        return personalizadaRepository.findAll();
        }

    /**
     * GET  /personalizadas/:id : get the "id" personalizada.
     *
     * @param id the id of the personalizada to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personalizada, or with status 404 (Not Found)
     */
    @GetMapping("/personalizadas/{id}")
    @Timed
    public ResponseEntity<Personalizada> getPersonalizada(@PathVariable Long id) {
        log.debug("REST request to get Personalizada : {}", id);
        Personalizada personalizada = personalizadaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personalizada));
    }

    /**
     * DELETE  /personalizadas/:id : delete the "id" personalizada.
     *
     * @param id the id of the personalizada to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personalizadas/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonalizada(@PathVariable Long id) {
        log.debug("REST request to delete Personalizada : {}", id);
        personalizadaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
