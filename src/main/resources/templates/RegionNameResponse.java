package ${package_name}.responses;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.trips.service_framework.models.responses.BaseResponse;

@Data
@SuperBuilder
public class ${entity_name}NameResponse extends BaseResponse {
    private String ${entity_name}Name;
}
