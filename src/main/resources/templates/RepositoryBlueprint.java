package ${package_name}.repositories;


import ${package_name}.entities.Region;
import org.springframework.data.jpa.repository.Query;
import org.trips.service_framework.models.repositories.BaseRepository;

public interface ${entity_name}Repository extends BaseRepository<${entity_name}> {
    List<${entity_name}> findByParentId(Long ${entity_name}Id);
}
