package com.trips.create_service.tasks;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Hydrate {

    public final static Set<String> skipList = new HashSet<>(List.of("gradle", ".gradle", ".idea", "build", "test", "resources"));

    public static void execute() {
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
        for (File entity : entityList) {
            stringBuilder.append(entity.getName().split("[.]")[0]);
            stringBuilder.append(" | ");
        }

        System.out.println(stringBuilder);

        System.out.println("hydrate command used.");
    }

    private static File findDirectory(String directoryName) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get("./"))) {
            return new File(stream
                    .filter(Files::isDirectory)
                    .filter(e -> e.getFileName().toString().equals(directoryName))
                    .map(Path::toString)
                    .findFirst().orElseThrow(IOException::new));
        }
    }


}
