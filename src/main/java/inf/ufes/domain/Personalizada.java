package inf.ufes.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Personalizada.
 */
@Entity
@Table(name = "personalizada")
public class Personalizada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "identificador")
    private String identificador;

    @Column(name = "query")
    private String query;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public Personalizada identificador(String identificador) {
        this.identificador = identificador;
        return this;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getQuery() {
        return query;
    }

    public Personalizada query(String query) {
        this.query = query;
        return this;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Personalizada personalizada = (Personalizada) o;
        if (personalizada.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personalizada.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Personalizada{" +
            "id=" + getId() +
            ", identificador='" + getIdentificador() + "'" +
            ", query='" + getQuery() + "'" +
            "}";
    }
}
