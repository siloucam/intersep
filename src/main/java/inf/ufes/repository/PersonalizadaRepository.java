package inf.ufes.repository;

import inf.ufes.domain.Personalizada;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Personalizada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalizadaRepository extends JpaRepository<Personalizada, Long> {

}
