package com.trips.create_service.tasks;

public class Component {
    public enum Components {
        CONTROLLERS,
        MAPPER,
        REPOSITORY,
        RESPONSE,
        SERVICE,
    }

    public static String getPackageName(Components componentType) {
        switch (componentType) {
            case MAPPER:
                return "mappers";
            case REPOSITORY:
                return "repositories";
            case CONTROLLERS:
                return "controllers";
            case RESPONSE:
                return "responses";
            default:
                return "services";
        }
    }

    public static String getFileName(Components componentType) {
        switch (componentType) {
            case MAPPER:
                return "Mapper.";
            case REPOSITORY:
                return "Repository.";
            case CONTROLLERS:
                return "Controller.";
            case RESPONSE:
                return "Response.";
            default:
                return "Service.";
        }
    }
}
