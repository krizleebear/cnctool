package de.christianleberfinger.cnc.toollength;

import org.eclipse.collections.api.multimap.Multimap;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicReference;

public class ToolLengthExport {

    static String toCSV(Multimap<Integer, Double> allToolLengths) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        //TODO: sort entries by tool number

        allToolLengths.forEachKeyMultiValues((toolNumber, toolLengths) -> {
            pw.print(toolNumber);
            pw.print("\t");

            int suggestedToolLength = summarizeToolLength(toolLengths);
            pw.print(suggestedToolLength);
            pw.print("\t");

            toolLengths.forEach(val -> {
                pw.print(val);
                pw.print("\t");
            });
            pw.println();
        });

        return sw.toString();
    }

    static int summarizeToolLength(Iterable<Double> lengthHistory) {
        AtomicReference<Double> max = new AtomicReference<>((double) 0);
        lengthHistory.forEach(length -> {
            max.set(Math.max(max.get(), length));
        });

        return (int) Math.ceil(max.get());
    }

}
