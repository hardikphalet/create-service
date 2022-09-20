package com.trips.create_service.tasks;

import freemarker.template.*;
import freemarker.template.utility.StringUtil;
import lombok.Builder;

import java.io.*;
import java.util.*;

@Builder
public class Hydrate {
    public void execute() {


        // Find entity classes
        File folder;
        try {
            folder = Utils.findDirectory("entities");
        } catch (IOException e) {
            System.out.println("No package called entity found.");
            return;
        }
        File[] entityList = folder.listFiles(pathname -> pathname.isFile() && pathname.getName().endsWith("java"));
        if (entityList == null || entityList.length == 0) {
            System.out.println("No entity class found in the entities package.");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder("Entities found are: ");

        // Find the new Package Name for the project
        // This is done to decouple generate and hydrate functionalities
        String packageName = null;
        try {
            Scanner packageScanner = new Scanner(entityList[0]);
            while (packageScanner.hasNext()) {
                String line = packageScanner.nextLine();
                if (line != null && line.contains("package")) {
                    System.out.println(line);
                    packageName = StringUtil.split(line, ' ')[1].trim();
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        StringBuilder finalPackage = new StringBuilder();
        String[] packageTokens = Objects.requireNonNull(packageName).split("[.]");
        finalPackage.append(packageTokens[0]).append(".");
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
        File newFile = new File(entityFile.getParentFile().getParentFile().getPath() + File.separator + componentType.getPackageName() + File.separator + entityFile.getName().replace(".",componentType.getFileName()));
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
                Template template = cfg.getTemplate("Region" + componentType.getFileName() + "java");
                FileWriter out = new FileWriter(newFile);
                template.process(input,out);
                out.close();
            } catch (IOException | TemplateException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
