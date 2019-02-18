package inf.ufes.web.rest;

import com.codahale.metrics.annotation.Timed;
import inf.ufes.domain.Qualitativa;

import inf.ufes.repository.QualitativaRepository;
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
 * REST controller for managing Qualitativa.
 */
@RestController
@RequestMapping("/api")
public class QualitativaResource {

    private final Logger log = LoggerFactory.getLogger(QualitativaResource.class);

    private static final String ENTITY_NAME = "qualitativa";

    private final QualitativaRepository qualitativaRepository;

    public QualitativaResource(QualitativaRepository qualitativaRepository) {
        this.qualitativaRepository = qualitativaRepository;
    }

    /**
     * POST  /qualitativas : Create a new qualitativa.
     *
     * @param qualitativa the qualitativa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new qualitativa, or with status 400 (Bad Request) if the qualitativa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/qualitativas")
    @Timed
    public ResponseEntity<Qualitativa> createQualitativa(@RequestBody Qualitativa qualitativa) throws URISyntaxException {
        log.debug("REST request to save Qualitativa : {}", qualitativa);
        if (qualitativa.getId() != null) {
            throw new BadRequestAlertException("A new qualitativa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Qualitativa result = qualitativaRepository.save(qualitativa);
        return ResponseEntity.created(new URI("/api/qualitativas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /qualitativas : Updates an existing qualitativa.
     *
     * @param qualitativa the qualitativa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated qualitativa,
     * or with status 400 (Bad Request) if the qualitativa is not valid,
     * or with status 500 (Internal Server Error) if the qualitativa couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/qualitativas")
    @Timed
    public ResponseEntity<Qualitativa> updateQualitativa(@RequestBody Qualitativa qualitativa) throws URISyntaxException {
        log.debug("REST request to update Qualitativa : {}", qualitativa);
        if (qualitativa.getId() == null) {
            return createQualitativa(qualitativa);
        }
        Qualitativa result = qualitativaRepository.save(qualitativa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, qualitativa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /qualitativas : get all the qualitativas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of qualitativas in body
     */
    @GetMapping("/qualitativas")
    @Timed
    public List<Qualitativa> getAllQualitativas() {
        log.debug("REST request to get all Qualitativas");
        return qualitativaRepository.findAll();
        }

    /**
     * GET  /qualitativas/:id : get the "id" qualitativa.
     *
     * @param id the id of the qualitativa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the qualitativa, or with status 404 (Not Found)
     */
    @GetMapping("/qualitativas/{id}")
    @Timed
    public ResponseEntity<Qualitativa> getQualitativa(@PathVariable Long id) {
        log.debug("REST request to get Qualitativa : {}", id);
        Qualitativa qualitativa = qualitativaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(qualitativa));
    }

    /**
     * DELETE  /qualitativas/:id : delete the "id" qualitativa.
     *
     * @param id the id of the qualitativa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/qualitativas/{id}")
    @Timed
    public ResponseEntity<Void> deleteQualitativa(@PathVariable Long id) {
        log.debug("REST request to delete Qualitativa : {}", id);
        qualitativaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
