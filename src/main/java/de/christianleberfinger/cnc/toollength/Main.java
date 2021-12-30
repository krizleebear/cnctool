package de.christianleberfinger.cnc.toollength;

import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.impl.factory.Multimaps;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        Path logDir = Paths.get("C:/CNC4.03/logging");
        Stream<Path> logFiles = allLogFiles(logDir);

        MutableListMultimap<Integer, Double> allLengths = Multimaps.mutable.list.empty();

        logFiles.forEach(logFile -> {
            ImmutableListMultimap<Integer, Double> toolLengths = EdingToolLengthParser.parse(logFile);
            allLengths.putAll(toolLengths);
        });

        System.out.println(toCSV(allLengths));
    }

    private static int suggestedToolLength(Iterable<Double> lengthHistory) {
        AtomicReference<Double> max = new AtomicReference<>((double) 0);
        lengthHistory.forEach(length -> {
            max.set(Math.max(max.get(), length));
        });

        return (int) Math.ceil(max.get());
    }

    private static String toCSV(Multimap<Integer, Double> values) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        values.forEachKeyMultiValues((key, vals) -> {
            pw.print(key);
            pw.print("\t");

            int suggestedToolLength = suggestedToolLength(vals);
            pw.print(suggestedToolLength);
            pw.print("\t");

            vals.forEach(val -> {
                pw.print(val);
                pw.print("\t");
            });
            pw.println();
        });

        return sw.toString();
    }

    private static Stream<Path> allLogFiles(Path logDir) throws IOException {
        Stream<Path> files = Files.list(logDir);
        return files.filter(f -> f.getFileName().toString().endsWith("CNCLOG.txt"));
    }
}
