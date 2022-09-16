package com.trips.create_service.tasks;

import lombok.Builder;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Builder
public class Generate {

    private final String[] skeletonDefaultPackage = {"com","cf","serve"};

    private String data;
    public void execute() {
        String repoLink = "https://" + "github.com/hardikphalet/region-service.git";
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





        System.out.println("Skeleton repository cloned successfully.");
    }


    public void renamePackage() {

        String[] packageTokenised = data.split("[.]");
        for (String token :
                packageTokenised) {
            System.out.println("[DEBUG] :: " + token);
        }
        boolean successFlag = true;
        for (int i = 0; i < skeletonDefaultPackage.length; i++) {
            try {
                String newFilePath = Hydrate.findDirectory(skeletonDefaultPackage[i]).getParent() + File.separator + packageTokenised[i];
                System.out.println("[DEBUG] :: " + newFilePath);
                File newFile = new File(newFilePath);
                File oldFile = Hydrate.findDirectory(skeletonDefaultPackage[i]);
                oldFile.renameTo(newFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



    }
}
