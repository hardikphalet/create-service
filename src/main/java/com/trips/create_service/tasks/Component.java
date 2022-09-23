package com.trips.create_service.tasks;

import lombok.Getter;

public enum Component {
    SERVICE("Service", "services"),
    CONTROLLER("Controller", "controllers"),
    MAPPER("Mapper", "mappers"),
    RESPONSE("Response", "responses"),
    REPOSITORY("Repository", "repositories");

    @Getter
    private final String fileName;
    @Getter
    private final String packageName;

    Component(String fileName, String packageName) {
        this.fileName = fileName;
        this.packageName = packageName;
    }
}
