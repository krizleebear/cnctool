package de.christianleberfinger.cnc.toollength;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.primitive.MutableIntIntMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.sortedset.MutableSortedSetMultimap;
import org.eclipse.collections.impl.factory.Multimaps;
import org.eclipse.collections.impl.factory.primitive.IntIntMaps;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class ToolUsage {

    private static final Comparator<Integer> DESCENDING = ((Comparator<Integer>) Integer::compare).reversed();

    protected static void mostFrequentTools(Multimap<Integer, Double> allToolLengths) {
        MutableIntIntMap useCountPerTool = IntIntMaps.mutable.empty();
        allToolLengths.forEachKeyMultiValues((toolNumber, toolLengths) -> {
            AtomicInteger useCount = new AtomicInteger(0);
            toolLengths.forEach(length -> useCount.incrementAndGet());
            useCountPerTool.put(toolNumber, useCount.get());
        });

        // group tools by count of use
        MutableSortedSetMultimap<Integer, Integer> groupedByCount = Multimaps.mutable.sortedSet.with(Integer::compare);
        useCountPerTool.forEachKeyValue((tool, count) -> groupedByCount.put(count, tool));

        System.out.println("Most frequently used tools: ");
        MutableList<Integer> descendingCount = groupedByCount.keysView().toSortedList(DESCENDING);
        descendingCount.forEach(count -> {
            System.out.println(count + "x tool(s) " + groupedByCount.get(count));
        });
    }

}
