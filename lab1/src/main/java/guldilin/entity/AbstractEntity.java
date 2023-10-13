package guldilin.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@MappedSuperclass
public abstract class AbstractEntity implements Mappable, Filterable {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FilterableField
    private Integer id;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @FilterableField
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, updatable = false)
    @UpdateTimestamp
    @FilterableField
    private Timestamp updatedAt;
}
