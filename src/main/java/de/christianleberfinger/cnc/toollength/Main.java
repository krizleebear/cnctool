package de.christianleberfinger.cnc.toollength;

import org.eclipse.collections.api.multimap.Multimap;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        Path edingDir = Paths.get("C:/CNC4.03/");
        Path logDir = edingDir.resolve("logging");

        EdingLogs edingLogs = new EdingLogs(logDir);
        Multimap<Integer, Double> toolLengths = edingLogs.getToolLengths();

        FusionTools fusionTools = FusionTools.empty();
        try {
            fusionTools = FusionToolParser.parseDir(edingDir);
        } catch(Exception ex)
        {
            System.out.println("Could not find Fusion tools file: " + ex.getMessage());
        }

        String toolLengthCSV = ToolLengthExport.toCSV(toolLengths, fusionTools);
        System.out.println(toolLengthCSV);

        //TODO: write to CSV file

        System.out.println("Logs go back until: " + edingLogs.getOldestLogDate());
        ToolUsage.mostFrequentTools(toolLengths);
    }
}
