package de.christianleberfinger.cnc.toollength;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.multimap.Multimap;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicReference;

public class ToolLengthExport {

    static String toCSV(Multimap<Integer, Double> allToolLengths, ToolDescription toolDescription) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        printHeaders(pw);

        // sort entries by tool number
        // we assume tool numbers in the range 0-99
        for (int toolNumber = 0; toolNumber < 100; toolNumber++) {
            if (allToolLengths.containsKey(toolNumber)) {
                RichIterable<Double> toolLengths = allToolLengths.get(toolNumber);
                printRow(pw, toolNumber, toolLengths, toolDescription);
            }
        }

        return sw.toString();
    }

    private static void printHeaders(PrintWriter pw) {
        pw.print("T#");
        pw.print("\t");

        pw.print("Len");
        pw.print("\t");
        pw.println();
    }

    private static void printRow(PrintWriter pw, Integer toolNumber, Iterable<Double> toolLengths, ToolDescription toolDescription) {
        pw.print("T");
        pw.print(toolNumber);
        pw.print("\t");

        int suggestedToolLength = summarizeToolLength(toolLengths);
        pw.print(suggestedToolLength);
        pw.print("\t");

        pw.print(toolDescription.getDescription(toolNumber));
        pw.print("\t");

        toolLengths.forEach(val -> {
            pw.print(val);
            pw.print("\t");
        });
        pw.println();
    }

    static int summarizeToolLength(Iterable<Double> lengthHistory) {
        AtomicReference<Double> max = new AtomicReference<>((double) 0);
        lengthHistory.forEach(length -> {
            max.set(Math.max(max.get(), length));
        });

        return (int) Math.ceil(max.get());
    }

}
