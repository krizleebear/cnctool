package de.christianleberfinger.cnc.toollength;

import org.eclipse.collections.api.factory.SortedSets;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.impl.factory.Multimaps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.stream.Stream;

public class EdingLogs {
    private final MutableListMultimap<Integer, Double> allLengths = Multimaps.mutable.list.empty();
    private final MutableSortedSet<LocalDate> logDates = SortedSets.mutable.empty();

    public EdingLogs(Path logDir) throws IOException {
        Stream<Path> logFiles = EdingLogs.allLogFiles(logDir);

        // parse log files, collect information
        logFiles.forEach(logFile -> {
            ImmutableListMultimap<Integer, Double> toolLengths = EdingToolLengthParser.parse(logFile);
            allLengths.putAll(toolLengths);
            logDates.add(EdingLogs.dateOfLog(logFile));
        });
    }

    protected static Stream<Path> allLogFiles(Path logDir) throws IOException {
        Stream<Path> files = Files.list(logDir);
        return files.filter(f -> f.getFileName().toString().endsWith("CNCLOG.txt"));
    }

    protected static LocalDate dateOfLog(Path logfile) {
        //e.g. "20211029-100116--CNCLOG.txt"
        String dateString = logfile.getFileName().toString().substring(0, 8);

        DateTimeFormatter dateFormat = new DateTimeFormatterBuilder().appendPattern("yyyyMMdd").toFormatter();
        return LocalDate.parse(dateString, dateFormat);
    }

    public Multimap<Integer, Double> getToolLengths() {
        return allLengths.toImmutable();
    }

    public LocalDate getOldestLogDate() {
        return logDates.first();
    }

}
