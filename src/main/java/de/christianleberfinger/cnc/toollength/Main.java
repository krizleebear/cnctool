package de.christianleberfinger.cnc.toollength;

import org.eclipse.collections.api.factory.SortedSets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.primitive.MutableIntIntMap;
import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.multimap.sortedset.MutableSortedSetMultimap;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.impl.factory.Multimaps;
import org.eclipse.collections.impl.factory.primitive.IntIntMaps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        Path logDir = Paths.get("C:/CNC4.03/logging");
        Stream<Path> logFiles = allLogFiles(logDir);

        MutableListMultimap<Integer, Double> allLengths = Multimaps.mutable.list.empty();
        MutableSortedSet<LocalDate> logDates = SortedSets.mutable.empty();

        // parse log files, collect information
        logFiles.forEach(logFile -> {
            ImmutableListMultimap<Integer, Double> toolLengths = EdingToolLengthParser.parse(logFile);
            allLengths.putAll(toolLengths);
            logDates.add(dateOfLog(logFile));
        });

        String toolLengthCSV = ToolLengthExport.toCSV(allLengths);
        System.out.println(toolLengthCSV);

        System.out.println("Logs go back until: " + logDates.first());
        ToolUsage.mostFrequentTools(allLengths);
    }

    private static Stream<Path> allLogFiles(Path logDir) throws IOException {
        Stream<Path> files = Files.list(logDir);
        return files.filter(f -> f.getFileName().toString().endsWith("CNCLOG.txt"));
    }

    protected static LocalDate dateOfLog(Path logfile) {
        //e.g. "20211029-100116--CNCLOG.txt"
        String dateString = logfile.getFileName().toString().substring(0, 8);

        DateTimeFormatter dateFormat = new DateTimeFormatterBuilder().appendPattern("yyyyMMdd").toFormatter();
        return LocalDate.parse(dateString, dateFormat);
    }


}
