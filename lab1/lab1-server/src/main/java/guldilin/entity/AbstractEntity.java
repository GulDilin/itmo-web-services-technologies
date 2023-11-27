package guldilin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.sql.Timestamp;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Abstract entity class for other entities.
 * Contains basic fields.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public abstract class AbstractEntity implements Mappable {
    /**
     * Entity id. Autogenerated.
     */
    @FilterableField
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Creation timestamp. Autogenerated.
     */
    @FilterableField
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    /**
     * Last Update timestamp. Autogenerated.
     */
    @FilterableField
    @Column(name = "updated_at", nullable = false, updatable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;

    /**
     * Autogenerated equals.
     *
     * @param o object to compare
     * @return equals
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt);
    }

    /**
     * Autogenerated hashCode.
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, updatedAt);
    }
}
