package de.christianleberfinger.cnc.toollength;

public interface ToolDescription {
    default String getDescription(int toolNumber) {
        return "";
    }
}
