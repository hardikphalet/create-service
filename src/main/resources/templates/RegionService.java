package ${package_name}.services;

import ${package_name}.entities.Region;
import ${package_name}.mappers.RegionMapper;
import ${package_name}.repositories.RegionRepository;
import org.springframework.stereotype.Service;
import org.trips.service_framework.services.BaseService;

import java.util.List;

@Service
public class ${entity_name}Service extends BaseService<Region> {
    public ${entity_name}Service(${entity_name}Repository repository) {
        super(repository, Region.class);
    }

    @Override
    protected ${entity_name} merge(${entity_name} src, ${entity_name} target) {
        return ${entity_name}Mapper.INSTANCE.merge(src, target);
    }

    @Override
    public ${entity_name}Repository getRepository() {
        return (${entity_name}Repository) this.repository;
    }

    public List<${entity_name}> getChildRegions(Long ${entity_name}Id) {
        return getRepository().findByParentId(${entity_name}Id);
    }
}
