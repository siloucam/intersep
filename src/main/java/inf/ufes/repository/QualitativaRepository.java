package inf.ufes.repository;

import inf.ufes.domain.Qualitativa;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Qualitativa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QualitativaRepository extends JpaRepository<Qualitativa, Long> {

}
