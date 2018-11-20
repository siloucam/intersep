package inf.ufes.web.rest;

import com.codahale.metrics.annotation.Timed;
import inf.ufes.domain.Qualitativas;

import inf.ufes.repository.QualitativasRepository;
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
 * REST controller for managing Qualitativas.
 */
@RestController
@RequestMapping("/api")
public class QualitativasResource {

    private final Logger log = LoggerFactory.getLogger(QualitativasResource.class);

    private static final String ENTITY_NAME = "qualitativas";

    private final QualitativasRepository qualitativasRepository;

    public QualitativasResource(QualitativasRepository qualitativasRepository) {
        this.qualitativasRepository = qualitativasRepository;
    }

    /**
     * POST  /qualitativas : Create a new qualitativas.
     *
     * @param qualitativas the qualitativas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new qualitativas, or with status 400 (Bad Request) if the qualitativas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/qualitativas")
    @Timed
    public ResponseEntity<Qualitativas> createQualitativas(@RequestBody Qualitativas qualitativas) throws URISyntaxException {
        log.debug("REST request to save Qualitativas : {}", qualitativas);
        if (qualitativas.getId() != null) {
            throw new BadRequestAlertException("A new qualitativas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Qualitativas result = qualitativasRepository.save(qualitativas);
        return ResponseEntity.created(new URI("/api/qualitativas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /qualitativas : Updates an existing qualitativas.
     *
     * @param qualitativas the qualitativas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated qualitativas,
     * or with status 400 (Bad Request) if the qualitativas is not valid,
     * or with status 500 (Internal Server Error) if the qualitativas couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/qualitativas")
    @Timed
    public ResponseEntity<Qualitativas> updateQualitativas(@RequestBody Qualitativas qualitativas) throws URISyntaxException {
        log.debug("REST request to update Qualitativas : {}", qualitativas);
        if (qualitativas.getId() == null) {
            return createQualitativas(qualitativas);
        }
        Qualitativas result = qualitativasRepository.save(qualitativas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, qualitativas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /qualitativas : get all the qualitativas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of qualitativas in body
     */
    @GetMapping("/qualitativas")
    @Timed
    public List<Qualitativas> getAllQualitativas() {
        log.debug("REST request to get all Qualitativas");
        return qualitativasRepository.findAll();
        }

    /**
     * GET  /qualitativas/:id : get the "id" qualitativas.
     *
     * @param id the id of the qualitativas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the qualitativas, or with status 404 (Not Found)
     */
    @GetMapping("/qualitativas/{id}")
    @Timed
    public ResponseEntity<Qualitativas> getQualitativas(@PathVariable Long id) {
        log.debug("REST request to get Qualitativas : {}", id);
        Qualitativas qualitativas = qualitativasRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(qualitativas));
    }

    /**
     * DELETE  /qualitativas/:id : delete the "id" qualitativas.
     *
     * @param id the id of the qualitativas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/qualitativas/{id}")
    @Timed
    public ResponseEntity<Void> deleteQualitativas(@PathVariable Long id) {
        log.debug("REST request to delete Qualitativas : {}", id);
        qualitativasRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
