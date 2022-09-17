package ${package_name}.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.trips.service_framework.models.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "${entity_name}")
public class ${entity_name} extends BaseEntity {
    @Column(name = "name")
    private String ${entity_name}Name;
}
