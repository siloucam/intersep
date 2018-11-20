package inf.ufes.repository;

import inf.ufes.domain.Quantitativas;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Quantitativas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuantitativasRepository extends JpaRepository<Quantitativas, Long> {

}
