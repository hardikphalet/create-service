package com.trips.create_service.tasks;

import freemarker.template.*;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Slf4j
public class Generate {

    private final String[] skeletonDefaultPackage = {"com", "cf", "serve"};
    private String data;

    public void execute() {
        buildSkeleton();
        populateSource();
    }

    private void populateSource() {
        List<String> packageTokens = Arrays.asList(data.split("[.]"));
        String cloneDirectoryName = packageTokens.get(packageTokens.size() - 1);

        String baseSourcePath = cloneDirectoryName + File.separator + "src" + File.separator + "main" + File.separator + "java";
        System.out.println(baseSourcePath);
        try {
            File packageDirectory = new File(baseSourcePath);
            for (String packageToken : packageTokens) {
                packageDirectory = new File(packageDirectory.getPath() + File.separator + packageToken);
            }
            Configuration cfg = new Configuration(new Version("2.3.31"));
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setClassForTemplateLoading(Generate.class, "/templates");
            for (Component component : Component.values()) {
                File newComponentFile = new File(packageDirectory.getPath() + File.separator + component.getPackageName() + File.separator + "Demo" + component.getFileName() + ".java");
                System.out.println(component.getFileName());
                System.out.println(newComponentFile.getPath());
                newComponentFile.getParentFile().mkdirs();
                newComponentFile.createNewFile();
                Map<String, Object> input = new HashMap<>();
                input.put("entity_name", "Demo");
                input.put("package_name", data);

                Template template = cfg.getTemplate(component.getFileName() + "Blueprint.java");
                FileWriter out = new FileWriter(newComponentFile);
                template.process(input, out);
                out.close();
                System.out.println(newComponentFile.getPath() + " created successfully");

            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

    }

    private void buildSkeleton() {
        String repoLink = "https://github.com/hardikphalet/template-sfc.git";
        List<String> packageToken = Arrays.asList(data.split("[.]"));
        String cloneDirectoryPath = "./" + packageToken.get(packageToken.size() - 1);

        try {
            System.out.println("Cloning " + repoLink + " into " + cloneDirectoryPath);
            Git.cloneRepository()
                    .setURI(repoLink)
                    .setDirectory(Paths.get(cloneDirectoryPath).toFile())
                    .call();
            System.out.println("Completed Cloning");
        } catch (GitAPIException e) {
            log.debug("Exception occurred while cloning skeleton repo " + e.getMessage());
            System.out.println("Exception occurred while cloning the skeleton repository.");
        }
    }
}
