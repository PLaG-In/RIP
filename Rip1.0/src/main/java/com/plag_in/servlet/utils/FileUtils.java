package com.plag_in.servlet.utils;

import com.sun.istack.internal.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Created by Владимир on 28.10.2016.
 */
public class FileUtils {

    @NotNull
    public static String readFileToString(final String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
    }

    public static void appendLinesToFile(final String logFileName, final List<String> lines) throws IOException {
        Path path = Paths.get(logFileName);
        if (!Files.exists(path)) {
            Path parentPath = path.getParent();
            if (parentPath != null) {
                Files.createDirectories(parentPath);
                Files.createFile(path);
            }
        }
        Files.write(Paths.get(logFileName), lines, StandardOpenOption.APPEND);
    }
}
