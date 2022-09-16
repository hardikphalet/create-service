package ${package_name}.mappers;

import ${package_name}.entities.Region;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.trips.service_framework.mappers.BaseMapper;
import org.trips.service_framework.utils.ProtoMapperUtil;

@Mapper(uses = ProtoMapperUtil.class,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ${entity_name}Mapper extends BaseMapper<Region> {
    ${entity_name}Mapper INSTANCE = Mappers.getMapper(${entity_name}Mapper.class);
}
