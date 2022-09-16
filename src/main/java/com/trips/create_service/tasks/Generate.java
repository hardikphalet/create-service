package com.trips.create_service.tasks;

import freemarker.template.*;
import lombok.Builder;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
public class Generate {

    private final String[] skeletonDefaultPackage = {"com","cf","serve"};

    private String data;
    public void execute() {
        String repoLink = "https://" + "github.com/hardikphalet/template-sfc.git";
        String cloneDirectoryPath = "./skeleton";

        try {
            System.out.println("Cloning "+repoLink+" into "+cloneDirectoryPath);
            Git.cloneRepository()
                    .setURI(repoLink)
                    .setDirectory(Paths.get(cloneDirectoryPath).toFile())
                    .call();
            System.out.println("Completed Cloning");
        } catch (GitAPIException e) {
            System.out.println("Exception occurred while cloning the skeleton repository.");
        }

        renamePackage();
        renameTestPackage();

        modifyPackageInClass();
        modifyGroupInConfig();

        renameClasses();

        System.out.println("Skeleton repository cloned successfully.");
    }

    private void renameClasses() {
        for (File javaFiles : getAllJavaFiles()) {
            File newJavaFile = new File(javaFiles.getPath().replace("Region", "Demo"));
            javaFiles.renameTo(newJavaFile);
        }
    }

    private void modifyGroupInConfig() {

        Configuration cfg = new Configuration(new Version("2.3.31"));
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setClassForTemplateLoading(Generate.class, "/templates");
        Map<String, Object> input = new HashMap<>();
        input.put("group_name", data.split("[.]")[0] + "." + data.split("[.]")[1]);
        for (File javaFile : getAllGradleFiles()) {
            try {
                Template template = cfg.getTemplate(javaFile.getName());
                FileWriter out = new FileWriter(javaFile);
                template.process(input,out);
                out.close();
            } catch (IOException | TemplateException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void modifyPackageInClass() {

        Configuration cfg = new Configuration(new Version("2.3.31"));
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setClassForTemplateLoading(Generate.class, "/templates");
        Map<String, Object> input = new HashMap<>();
        input.put("package_name", data);
        input.put("entity_name", "Demo");
        for (File javaFile : getAllJavaFiles()) {
            try {
                Template template = cfg.getTemplate(javaFile.getName());
                FileWriter out = new FileWriter(javaFile);
                template.process(input,out);
                out.close();
            } catch (IOException | TemplateException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private List<File> getAllJavaFiles() {
        try (Stream<Path> stream = Files.walk(Paths.get("./skeleton"))) {
            return  stream
                    .filter(Files::isRegularFile)
                    .filter(e -> e.getFileName().toString().endsWith(".java"))
                    .map(e -> new File(e.toString()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private List<File> getAllGradleFiles() {
        try (Stream<Path> stream = Files.walk(Paths.get("./skeleton"))) {
            return  stream
                    .filter(Files::isRegularFile)
                    .filter(e -> e.getFileName().toString().endsWith(".gradle"))
//                    .peek(e -> System.out.println(e.toString()))
                    .map(e -> new File(e.toString()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void renamePackage() {

        String[] packageTokenised = data.split("[.]");
        boolean successFlag = true;
        for (int i = 0; i < skeletonDefaultPackage.length; i++) {
            try {
                String newFilePath = Hydrate.findDirectory(skeletonDefaultPackage[i], "test").getParent() + File.separator + packageTokenised[i];
                File newFile = new File(newFilePath);
                File oldFile = Hydrate.findDirectory(skeletonDefaultPackage[i], "test");
                oldFile.renameTo(newFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void renameTestPackage() {

        String[] packageTokenised = data.split("[.]");
        for (String token :
                packageTokenised) {
        }
        boolean successFlag = true;
        for (int i = 0; i < skeletonDefaultPackage.length; i++) {
            try {
                String newFilePath = Hydrate.findDirectory(skeletonDefaultPackage[i], "main").getParent() + File.separator + packageTokenised[i];
                File newFile = new File(newFilePath);
                File oldFile = Hydrate.findDirectory(skeletonDefaultPackage[i], "main");
                oldFile.renameTo(newFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
