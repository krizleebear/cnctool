package de.christianleberfinger.cnc.toollength;

import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.impl.factory.Multimaps;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Parse an EdingCNC log and scan it for tool change keywords.
 * Note: The keywords fit the content of Sorotec's tool changer macro.
 * If you use another macro, you must adapt the parse patterns accordingly.
 * <p>
 * Example logs:
 *
 * <pre>
 * 20210809-104901--CNCLOG.txt:09-08 12:03:06->CNC_EC_INFO CNC_RC_OK Msg:intdefcmd.cpp(35):werkzeug wechseln
 * 20210809-104901--CNCLOG.txt:09-08 12:03:25->CNC_EC_INFO CNC_RC_OK Msg:intdefcmd.cpp(35):werkzeugnr.: 42 mit werkzeugnr.: 62 gewechselt
 * 20210809-104901--CNCLOG.txt:09-08 12:03:25->CNC_EC_INFO CNC_RC_OK Msg:intdefcmd.cpp(35):werkzeug wird vermessen
 * 20210809-104901--CNCLOG.txt:09-08 12:03:37->CNC_EC_INFO CNC_RC_OK Msg:intdefcmd.cpp(35):werkzeuglänge = 27.478
 * 19-06 13:32:26->CNC_EC_INFO CNC_RC_OK MotInitialize:motion.cpp(4069):CPU State = OPERATIONAL ETH
 * </pre>
 */
public class EdingToolLengthParser {

    private static final Charset LOGFILE_CHARSET = Charset.forName("Cp1252");
    Pattern TOOL_CHANGE_BEGIN_PATTERN = Pattern.compile(".*:werkzeug wechseln$");
    Pattern TOOL_NUMBER_PATTERN = Pattern.compile(".*mit werkzeugnr\\.: (\\d+) gewechselt$");
    Pattern TOOL_LENGTH_PATTERN = Pattern.compile(".*werkzeuglänge = (\\d*\\.\\d+|\\d+\\.\\d*)$");
    Pattern CPU_STATE_PATTERN = Pattern.compile(".*CPU State = (.*)$");

    private MutableListMultimap<Integer, Double> toolLengths = Multimaps.mutable.list.empty();
    private Path logFile;

    public EdingToolLengthParser(Path logFile) {
        this.logFile = logFile;
    }

    static ImmutableListMultimap<Integer, Double> parse(Path logFile) {
        try {

            Stream<String> logLines = Files.lines(logFile, LOGFILE_CHARSET);

            EdingToolLengthParser parser = new EdingToolLengthParser(logFile);
            parser.parse(logLines);

            ImmutableListMultimap<Integer, Double> toolLengths = parser.getToolLengths();
            return toolLengths;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ImmutableListMultimap<Integer, Double> getToolLengths() {
        return toolLengths.toImmutable();
    }

    public void parse(Stream<String> toolLogs) {

        // 18-06 22:19:38->CNC_EC_INFO CNC_RC_OK MotInitialize:motion.cpp(3995):CPU State = SIMULATION
        // 19-06 13:32:26->CNC_EC_INFO CNC_RC_OK MotInitialize:motion.cpp(4069):CPU State = OPERATIONAL ETH

        AtomicInteger currentTool = new AtomicInteger(-1);
        AtomicBoolean isSimulationMode = new AtomicBoolean(true);

        toolLogs.forEachOrdered(line -> {

            Matcher cpuStateMatcher = CPU_STATE_PATTERN.matcher(line);
            if (cpuStateMatcher.matches()) {
                String cpuState = cpuStateMatcher.group(1);
                System.out.println("CPU State: " + cpuState);

                boolean isOperational = cpuState.contains("OPERATIONAL");
                isSimulationMode.set(!isOperational);
            }

            if (isSimulationMode.get()) {
                return;
            }

            Matcher toolChangeMatcher = TOOL_CHANGE_BEGIN_PATTERN.matcher(line);
            if (toolChangeMatcher.matches()) {
                currentTool.set(-1);
            }

            Matcher toolNumberMatcher = TOOL_NUMBER_PATTERN.matcher(line);
            if (toolNumberMatcher.matches()) {
                String toolNrString = toolNumberMatcher.group(1);
                System.out.println("Toolnumber: " + toolNrString);
                currentTool.set(Integer.parseInt(toolNrString));
                return;
            }

            Matcher toolLengthMatcher = TOOL_LENGTH_PATTERN.matcher(line);
            if (toolLengthMatcher.matches()) {
                String toolLengthString = toolLengthMatcher.group(1);
                System.out.println("Toollength: " + toolLengthString);

                if (currentTool.get() == -1) {
                    // ignore length for unknown tool numbers
                    return;
                }

                // reset tool number for next match
                int toolNumber = currentTool.getAndSet(-1);
                double toolLength = Double.parseDouble(toolLengthString);
                toolLengths.put(toolNumber, toolLength);
                return;
            }
        });
    }
}