package com.trips.create_service.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Utils {

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
