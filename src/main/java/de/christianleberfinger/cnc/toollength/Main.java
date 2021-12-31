package de.christianleberfinger.cnc.toollength;

import org.eclipse.collections.api.multimap.Multimap;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        Path logDir = Paths.get("C:/CNC4.03/logging");

        EdingLogs edingLogs = new EdingLogs(logDir);
        Multimap<Integer, Double> toolLengths = edingLogs.getToolLengths();

        String toolLengthCSV = ToolLengthExport.toCSV(toolLengths);
        System.out.println(toolLengthCSV);

        System.out.println("Logs go back until: " + edingLogs.getOldestLogDate());
        ToolUsage.mostFrequentTools(toolLengths);
    }
}
