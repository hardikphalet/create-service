package com.trips.create_service.tasks;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.nio.file.Paths;

public class Generate {
    public static void execute() {
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


        System.out.println("Skeleton repository cloned successfully.");
    }
}
