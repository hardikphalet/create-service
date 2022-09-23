package com.trips.create_service.scanners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class SourceScanner {

    private final String sourcePath;

    public SourceScanner(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public List<File> getEntityList() throws IOException {
        return Files.walk(Paths.get(sourcePath))
                .filter(Files::isRegularFile)
                .filter(x -> x.getFileName().endsWith(".java"))
                .map(Path::toFile)
                .filter(this::isEntity)
                .collect(Collectors.toList());
    }

    private boolean isEntity(File fileHandle) {
        try {
            return Files.lines(fileHandle.toPath()).anyMatch(x -> x.contains("extends BaseEntity"));
        } catch (IOException e) {
            return false;
        }
    }
}
