package guldilin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private Timestamp createdAt;

    /**
     * Last Update timestamp. Autogenerated.
     */
    @FilterableField
    @Column(name = "updated_at", nullable = false, updatable = false)
    private Timestamp updatedAt;

    /**
     * Set creation timestamp.
     */
    @PrePersist
    public void onCreate() {
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = this.createdAt;
    }

    /**
     * Set timestamp on updated.
     */
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, updatedAt);
    }
}
