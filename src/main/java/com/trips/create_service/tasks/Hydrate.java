package com.trips.create_service.tasks;

import freemarker.template.*;
import freemarker.template.utility.StringUtil;
import lombok.Builder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Builder
public class Hydrate {

    public final Set<String> skipList = new HashSet<>(List.of("gradle", ".gradle", ".idea", "build", "test", "resources"));

    public void execute() {
        File folder = null;
        try {
            folder = findDirectory("entities");
        } catch (IOException e) {
            System.out.println("No package called entity found.");
            return;
        }
        File[] entityList = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith("java");
            }
        });

        if (entityList == null || entityList.length == 0) {
            System.out.println("No entity class found in the entities package.");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder("Entites found are: ");
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
        String[] packageTokens = packageName.split("[.]");
        finalPackage.append(packageTokens[0]).append(".");
        for (int i = 1; i < 3; i++) {
            finalPackage.append(".").append(packageTokens[i]);
        }

        for (File entity : entityList) {
            stringBuilder.append(entity.getName().split("[.]")[0]);
            stringBuilder.append(" | ");

            try {
                hydrateResponse(entity, finalPackage.toString());
                hydrateMapper(entity, finalPackage.toString());
                hydrateRepository(entity,finalPackage.toString());
                hydrateService(entity,finalPackage.toString());
                hydrateControllers(entity,finalPackage.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        System.out.println(stringBuilder);

        System.out.println("hydrate command used.");
    }

    private void hydrateControllers(File entityFile, String s) throws IOException {
        File newController = new File(entityFile.getParentFile().getParentFile().getPath() + File.separator +  "controllers" + File.separator + entityFile.getName().replace(".","Controller."));
        if (newController.exists()) {
            return;
        }
        newController.createNewFile();
        Configuration cfg = new Configuration(new Version("2.3.31"));
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setClassForTemplateLoading(Hydrate.class, "/templates");
        Map<String, Object> input = new HashMap<>();
        input.put("entity_name", entityFile.getName().split("[.]")[0]);
        input.put("package_name", s);
        try {
            Template template = cfg.getTemplate("RegionController.java");
            FileWriter out = new FileWriter(newController);
            template.process(input,out);
            out.close();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    private void hydrateMapper(File entityFile, String s) throws IOException {
        File newMapper = new File(entityFile.getParentFile().getParentFile().getPath() + File.separator + "mappers" + File.separator + entityFile.getName().replace(".","Mapper."));
        if (newMapper.exists()) {
            return;
        }
        newMapper.createNewFile();
        Configuration cfg = new Configuration(new Version("2.3.31"));
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setClassForTemplateLoading(Hydrate.class, "/templates");
        Map<String, Object> input = new HashMap<>();
        input.put("entity_name", entityFile.getName().split("[.]")[0]);
        input.put("package_name", s);
        try {
            Template template = cfg.getTemplate("RegionMapper.java");
            FileWriter out = new FileWriter(newMapper);
            template.process(input,out);
            out.close();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    private void hydrateResponse(File entityFile, String s) throws IOException {
        File newResponse = new File(entityFile.getParentFile().getParentFile().getPath() + File.separator + "responses" + File.separator + entityFile.getName().replace(".","Response."));
        if (newResponse.exists()) {
            return;
        }
        newResponse.createNewFile();
        Configuration cfg = new Configuration(new Version("2.3.31"));
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setClassForTemplateLoading(Hydrate.class, "/templates");
        Map<String, Object> input = new HashMap<>();
        input.put("entity_name", entityFile.getName().split("[.]")[0]);
        input.put("package_name", s);
        try {
            Template template = cfg.getTemplate("RegionResponse.java");
            FileWriter out = new FileWriter(newResponse);
            template.process(input,out);
            out.close();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    private void hydrateRepository(File entityFile, String s) throws IOException {
        File newRepository = new File(entityFile.getParentFile().getParentFile().getPath() + File.separator +  "repositories" + File.separator + entityFile.getName().replace(".","Repository."));
        if (newRepository.exists()) {
            return;
        }
        newRepository.createNewFile();
        Configuration cfg = new Configuration(new Version("2.3.31"));
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setClassForTemplateLoading(Hydrate.class, "/templates");
        Map<String, Object> input = new HashMap<>();
        input.put("entity_name", entityFile.getName().split("[.]")[0]);
        input.put("package_name", s);
        try {
            Template template = cfg.getTemplate("RegionRepository.java");
            FileWriter out = new FileWriter(newRepository);
            template.process(input,out);
            out.close();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    private void hydrateService(File entityFile, String s) throws IOException {
        File newService = new File(entityFile.getParentFile().getParentFile().getPath() + File.separator +  "services" + File.separator + entityFile.getName().replace(".","Service."));
        if (newService.exists()) {
            return;
        }
        newService.createNewFile();
        Configuration cfg = new Configuration(new Version("2.3.31"));
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setClassForTemplateLoading(Hydrate.class, "/templates");
        Map<String, Object> input = new HashMap<>();
        input.put("entity_name", entityFile.getName().split("[.]")[0]);
        input.put("package_name", s);
        try {
            Template template = cfg.getTemplate("RegionService.java");
            FileWriter out = new FileWriter(newService);
            template.process(input,out);
            out.close();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }


    // TODO add to util class
    public static File findDirectory(String directoryName) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get("./"))) {
            return new File(stream
                    .filter(Files::isDirectory)
                    .filter(e -> e.getFileName().toString().equals(directoryName))
                    .filter(e -> !e.toString().contains("test"))
                    .map(Path::toString)
                    .findFirst().orElseThrow(IOException::new));
        }
    }

    public static File findDirectory(String directoryName, String filterDirectory) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get("./"))) {
            return new File(stream
                    .filter(Files::isDirectory)
                    .filter(e -> e.getFileName().toString().equals(directoryName))
                    .filter(e -> !e.toString().contains(filterDirectory))
                    .map(Path::toString)
                    .findFirst().orElseThrow(IOException::new));
        }
    }


}
