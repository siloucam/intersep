package inf.ufes.web.rest;

import com.codahale.metrics.annotation.Timed;
import inf.ufes.domain.Quantitativas;

import inf.ufes.repository.QuantitativasRepository;
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
 * REST controller for managing Quantitativas.
 */
@RestController
@RequestMapping("/api")
public class QuantitativasResource {

    private final Logger log = LoggerFactory.getLogger(QuantitativasResource.class);

    private static final String ENTITY_NAME = "quantitativas";

    private final QuantitativasRepository quantitativasRepository;

    public QuantitativasResource(QuantitativasRepository quantitativasRepository) {
        this.quantitativasRepository = quantitativasRepository;
    }

    /**
     * POST  /quantitativas : Create a new quantitativas.
     *
     * @param quantitativas the quantitativas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quantitativas, or with status 400 (Bad Request) if the quantitativas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/quantitativas")
    @Timed
    public ResponseEntity<Quantitativas> createQuantitativas(@RequestBody Quantitativas quantitativas) throws URISyntaxException {
        log.debug("REST request to save Quantitativas : {}", quantitativas);
        if (quantitativas.getId() != null) {
            throw new BadRequestAlertException("A new quantitativas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Quantitativas result = quantitativasRepository.save(quantitativas);
        return ResponseEntity.created(new URI("/api/quantitativas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quantitativas : Updates an existing quantitativas.
     *
     * @param quantitativas the quantitativas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quantitativas,
     * or with status 400 (Bad Request) if the quantitativas is not valid,
     * or with status 500 (Internal Server Error) if the quantitativas couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/quantitativas")
    @Timed
    public ResponseEntity<Quantitativas> updateQuantitativas(@RequestBody Quantitativas quantitativas) throws URISyntaxException {
        log.debug("REST request to update Quantitativas : {}", quantitativas);
        if (quantitativas.getId() == null) {
            return createQuantitativas(quantitativas);
        }
        Quantitativas result = quantitativasRepository.save(quantitativas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quantitativas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quantitativas : get all the quantitativas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of quantitativas in body
     */
    @GetMapping("/quantitativas")
    @Timed
    public List<Quantitativas> getAllQuantitativas() {
        log.debug("REST request to get all Quantitativas");
        return quantitativasRepository.findAll();
        }

    /**
     * GET  /quantitativas/:id : get the "id" quantitativas.
     *
     * @param id the id of the quantitativas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quantitativas, or with status 404 (Not Found)
     */
    @GetMapping("/quantitativas/{id}")
    @Timed
    public ResponseEntity<Quantitativas> getQuantitativas(@PathVariable Long id) {
        log.debug("REST request to get Quantitativas : {}", id);
        Quantitativas quantitativas = quantitativasRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quantitativas));
    }

    /**
     * DELETE  /quantitativas/:id : delete the "id" quantitativas.
     *
     * @param id the id of the quantitativas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/quantitativas/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuantitativas(@PathVariable Long id) {
        log.debug("REST request to delete Quantitativas : {}", id);
        quantitativasRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
