package inf.ufes.repository;

import inf.ufes.domain.Quantitativa;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Quantitativa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuantitativaRepository extends JpaRepository<Quantitativa, Long> {

}
