package de.christianleberfinger.cnc.toollength;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FusionTools implements ToolDescription {

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

    public static FusionTools empty() {
        return new FusionTools();
    }

    @Override
    public String getDescription(int toolNumber) {
        Optional<FusionTool> toolOptional = get(toolNumber);
        if(toolOptional.isPresent()) {
            return toolOptional.get().description;
        }
        return "";
    }
}
