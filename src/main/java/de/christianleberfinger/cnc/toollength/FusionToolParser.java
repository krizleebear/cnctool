package de.christianleberfinger.cnc.toollength;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Parse the JSON tool library format of Fusion 360.
 */
public class FusionToolParser {

    public static FusionTools parse(Path toolFile) throws IOException {
        try (BufferedReader reader = read(toolFile)) {
            Gson parser = new Gson();
            return parser.fromJson(reader, FusionTools.class);
        }
    }

    private static BufferedReader read(Path toolFile) throws IOException {

        // extract JSON from zip if necessary
        if (isCompressedToolsFile(toolFile)) {
            ZipFile zip = new ZipFile(toolFile.toFile());
            ZipEntry zipEntry = zip.entries().nextElement();
            return new BufferedReader(new InputStreamReader(zip.getInputStream(zipEntry)));
        }

        return Files.newBufferedReader(toolFile);
    }

    public static Optional<Path> findToolsFile(Path dir) throws IOException {

        //TODO: detect most recent file

        return Files.list(dir).filter(FusionToolParser::isCompressedToolsFile).findAny();
    }

    private static boolean isCompressedToolsFile(Path f) {
        return f.getFileName().toString().endsWith(".tools");
    }

    public static FusionTools parseDir(Path dir) throws IOException {
        Path toolsFile = findToolsFile(dir).orElseThrow(FileNotFoundException::new);
        return parse(toolsFile);
    }

}
