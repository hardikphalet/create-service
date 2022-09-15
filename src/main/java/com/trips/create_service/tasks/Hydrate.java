package com.trips.create_service.tasks;

import java.io.File;

public class Hydrate {
    public static void execute() {
        File folder = new File("./skeleton/src/main/java/com/cf/serve/entities");
        File[] entityList = folder.listFiles();
        if (!folder.isDirectory()) {
            System.out.println("Entity package doesn't exist. Please re-run the -generate command.");
            return;
        }

        if(entityList == null || entityList.length == 0) {
            System.out.println("No entities.");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder("Entites found are: ");
        for (File entity : entityList) {
            stringBuilder.append(entity.getName().split("[.]")[0]);
            stringBuilder.append(" | ");
        }

        System.out.println(stringBuilder);

        System.out.println("hydrate command used.");
    }
}
