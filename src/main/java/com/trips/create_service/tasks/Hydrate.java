package com.trips.create_service.tasks;

import com.trips.create_service.scanners.SourceScanner;
import freemarker.template.*;
import lombok.Builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Builder
public class Hydrate {
    public void execute() {


        SourceScanner scanner = new SourceScanner("./");
        List<File> entityList;
        try {
            entityList = new ArrayList<>(scanner.getEntityList());
        } catch (IOException e) {
            System.out.println("Entity List has no elements. Access error.");
            return;
        }

        if (entityList.size() == 0) {
            System.out.println("No entity class found in the entities package.");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder("Entities found are: ");

        // Find the new Package Name for the project
        // This is done to decouple generate and hydrate functionalities
        String packageName = null;
        try {
            packageName = Files.lines(entityList.get(0).toPath())
                    .filter(x -> x.contains("package"))
                    .map(x -> x.split("[ ;]")[1])
                    .findFirst()
                    .orElseThrow(() -> new FileNotFoundException("Cannot find package of the Entity."));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder finalPackage = new StringBuilder();
        String[] packageTokens = Objects.requireNonNull(packageName).split("[.]");
        finalPackage.append(packageTokens[0]);
        for (int i = 1; i < 3; i++) {
            finalPackage.append(".").append(packageTokens[i]);
        }

        // Hydrating components for each Entity class
        // Done because implementation of certain entities might be there already
        for (File entity : entityList) {
            stringBuilder.append(entity.getName().split("[.]")[0]);
            stringBuilder.append(" | ");

            try {
                hydrateComponent(entity, Component.MAPPER, finalPackage.toString());
                hydrateComponent(entity, Component.REPOSITORY, finalPackage.toString());
                hydrateComponent(entity, Component.RESPONSE, finalPackage.toString());
                hydrateComponent(entity, Component.SERVICE, finalPackage.toString());
                hydrateComponent(entity, Component.CONTROLLER, finalPackage.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        System.out.println(stringBuilder);
    }

    private void hydrateComponent(File entityFile, Component componentType, String packageName) throws IOException {
        File newFile = new File(entityFile.getParentFile().getParentFile().getPath() + File.separator + componentType.getPackageName() + File.separator + entityFile.getName().replace(".", componentType.getFileName() + "."));
        if (newFile.exists()) {
            return;
        }
        if (newFile.createNewFile()) {
            Configuration cfg = new Configuration(new Version("2.3.31"));
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setClassForTemplateLoading(Hydrate.class, "/templates");
            Map<String, Object> input = new HashMap<>();
            input.put("entity_name", entityFile.getName().split("[.]")[0]);
            input.put("package_name", packageName);
            try {
                Template template = cfg.getTemplate(componentType.getFileName() + "Blueprint.java");
                FileWriter out = new FileWriter(newFile);
                template.process(input, out);
                out.close();
            } catch (IOException | TemplateException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
