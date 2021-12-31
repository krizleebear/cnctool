package de.christianleberfinger.cnc.toollength;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FusionTool {

    public String type;
    public String description;
    public Geometry geometry;
    public String unit;

    @SerializedName("post-process")
    public PostProcess postProcess;

    public static class Geometry {
        @SerializedName("SFDM")
        public double shaftDiameter;

        @SerializedName("DC")
        public double diameter;

        @SerializedName("NOF")
        public int fluteCount;
    }

    public static class PostProcess {
        @SerializedName("number")
        public int toolNumber;

        @Override
        public String toString() {
            return "PostProcess{" +
                    "toolNumber=" + toolNumber +
                    '}';
        }
    }

    public int getToolNumber() {
        return postProcess.toolNumber;
    }

    public double getDiameter() {
        return geometry.diameter;
    }

    public double getShaftDiameter() {
        return geometry.shaftDiameter;
    }

    public int getFluteCount() {
        return geometry.fluteCount;
    }

    @Override
    public String toString() {
        return "FusionTool{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", unit='" + unit + '\'' +
                ", toolNumber=" + getToolNumber() +
                ", diameter=" + getDiameter() +
                ", shaftDiameter=" + getShaftDiameter() +
                ", fluteCount=" + getFluteCount() +
                '}';
    }
}
