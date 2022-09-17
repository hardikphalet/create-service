package ${package_name}.responses;

import ${package_name}.entities.Region;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.trips.service_framework.models.responses.BaseResponse;

import java.util.List;

@Data
@SuperBuilder
public class ${entity_name}Response extends BaseResponse {
    private List<${entity_name}> data;
}
