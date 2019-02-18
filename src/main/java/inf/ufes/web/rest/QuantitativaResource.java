package inf.ufes.web.rest;

import com.codahale.metrics.annotation.Timed;
import inf.ufes.domain.Quantitativa;

import inf.ufes.repository.QuantitativaRepository;
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
 * REST controller for managing Quantitativa.
 */
@RestController
@RequestMapping("/api")
public class QuantitativaResource {

    private final Logger log = LoggerFactory.getLogger(QuantitativaResource.class);

    private static final String ENTITY_NAME = "quantitativa";

    private final QuantitativaRepository quantitativaRepository;

    public QuantitativaResource(QuantitativaRepository quantitativaRepository) {
        this.quantitativaRepository = quantitativaRepository;
    }

    /**
     * POST  /quantitativas : Create a new quantitativa.
     *
     * @param quantitativa the quantitativa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quantitativa, or with status 400 (Bad Request) if the quantitativa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/quantitativas")
    @Timed
    public ResponseEntity<Quantitativa> createQuantitativa(@RequestBody Quantitativa quantitativa) throws URISyntaxException {
        log.debug("REST request to save Quantitativa : {}", quantitativa);
        if (quantitativa.getId() != null) {
            throw new BadRequestAlertException("A new quantitativa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Quantitativa result = quantitativaRepository.save(quantitativa);
        return ResponseEntity.created(new URI("/api/quantitativas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quantitativas : Updates an existing quantitativa.
     *
     * @param quantitativa the quantitativa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quantitativa,
     * or with status 400 (Bad Request) if the quantitativa is not valid,
     * or with status 500 (Internal Server Error) if the quantitativa couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/quantitativas")
    @Timed
    public ResponseEntity<Quantitativa> updateQuantitativa(@RequestBody Quantitativa quantitativa) throws URISyntaxException {
        log.debug("REST request to update Quantitativa : {}", quantitativa);
        if (quantitativa.getId() == null) {
            return createQuantitativa(quantitativa);
        }
        Quantitativa result = quantitativaRepository.save(quantitativa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quantitativa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quantitativas : get all the quantitativas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of quantitativas in body
     */
    @GetMapping("/quantitativas")
    @Timed
    public List<Quantitativa> getAllQuantitativas() {
        log.debug("REST request to get all Quantitativas");
        return quantitativaRepository.findAll();
        }

    /**
     * GET  /quantitativas/:id : get the "id" quantitativa.
     *
     * @param id the id of the quantitativa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quantitativa, or with status 404 (Not Found)
     */
    @GetMapping("/quantitativas/{id}")
    @Timed
    public ResponseEntity<Quantitativa> getQuantitativa(@PathVariable Long id) {
        log.debug("REST request to get Quantitativa : {}", id);
        Quantitativa quantitativa = quantitativaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quantitativa));
    }

    /**
     * DELETE  /quantitativas/:id : delete the "id" quantitativa.
     *
     * @param id the id of the quantitativa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/quantitativas/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuantitativa(@PathVariable Long id) {
        log.debug("REST request to delete Quantitativa : {}", id);
        quantitativaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
