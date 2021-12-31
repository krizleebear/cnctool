package de.christianleberfinger.cnc.toollength;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Parse the JSON tool library format of Fusion 360.
 */
public class FusionToolParser {

    public static FusionTools parse(Path jsonFile) throws IOException {
        byte[] jsonBytes = Files.readAllBytes(jsonFile);
        String json = new String(jsonBytes, StandardCharsets.UTF_8);
        Gson parser = new Gson();
        return parser.fromJson(json, FusionTools.class);
    }

    public static class FusionTools {

        @SerializedName("data")
        public List<FusionTool> tools = new ArrayList<>();

        public Optional<FusionTool> get(int toolNumber) {
            return tools.stream().filter(t -> t.getToolNumber() == toolNumber).findAny();
        }

        @Override
        public String toString() {
            return "FusionTools{" +
                    "tools=" + tools +
                    '}';
        }
    }
}
