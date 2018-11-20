package inf.ufes.repository;

import inf.ufes.domain.Qualitativas;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Qualitativas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QualitativasRepository extends JpaRepository<Qualitativas, Long> {

}
