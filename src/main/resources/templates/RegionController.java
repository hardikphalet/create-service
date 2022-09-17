package ${package_name}.controllers;

import ${package_name}.entities.${entity_name};
import ${package_name}.responses.${entity_name}NameResponse;
import ${package_name}.responses.${entity_name}Response;
import ${package_name}.services.${entity_name}Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trips.service_framework.controllers.BaseController;
import org.trips.service_framework.models.responses.BaseResponse;
import org.trips.service_framework.models.responses.StatusResponse;
import java.util.List;

@RestController
@RequestMapping("/${entity_name}")
public class ${entity_name}Controller extends BaseController<${entity_name}Response, ${entity_name}> {

    public ${entity_name}Controller(${entity_name}Service service) {
        super(service);
    }

    @Override
    public ${entity_name}Service getService() {
        return (${entity_name}Service) this.service;
    }

    @Override
    protected ${entity_name}Response createResponse(List<${entity_name}> ${entity_name}List) {
        return ${entity_name}Response.builder()
                .data(${entity_name}List)
                .status(StatusResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .statusType(StatusResponse.Type.SUCCESS)
                        .totalCount(${entity_name}List.size())
                        .build())
                .build();
    }

    @GetMapping("/{${entity_name}Id}/sub-${entity_name}s")
    public ${entity_name}Response getChild${entity_name}s(@PathVariable Long ${entity_name}Id) {
        return createResponse(getService().getChild${entity_name}s(${entity_name}Id));
    }

    @GetMapping("/{${entity_name}Id}/name")
    public BaseResponse get${entity_name}Name(@PathVariable Long ${entity_name}Id) {
//        return createResponse(Collections.singletonList(getService().find(${entity_name}Id).get${entity_name}Name()))
        return ${entity_name}NameResponse.builder().status(StatusResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .statusType(StatusResponse.Type.SUCCESS)
                .totalCount(1)
                .build())
                .${entity_name}Name(getService()
                        .find(${entity_name}Id)
                        .get${entity_name}Name())
                .build();
    }

}
