
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class consists of a stopwatch-style timer to measure execution time of operations
 *
 * The timer starts when TickTock is instantiated.
 * Ticks mark the time when they are made.
 * Summary ends the timer and displays the output.
 *
 * @author Robert Rushton
 * @author https://github.com/robrushton
 */
public class TickTock {

    private long startTime;
    private long endTime;
    private Map<String, Long> ticks;
    private Unit unit;
    private DecimalFormat decimalFormat;
    private String summaryName;

    public enum Unit {
        SECONDS("s", 1000000000.0),
        MILLISECONDS("ms", 1000000.0),
        MICROSECONDS("μs", 1000.0),
        NANOSECONDS("ns", 1.0);

        private final String value;
        private final double divisor;

        private Unit(String value, double divisor) {
            this.value = value;
            this.divisor = divisor;
        }

        public String toString() {
            return value;
        }

        public double getDivisor() {
            return this.divisor;
        }

    }

    /**
     * Contructs TickTock object for use in timing code execution.
     * It takes a string that will be used as the summary name and a unit
     * of time for the output to be converted to. Possible units are
     * SECONDS, MILLISECONDS, MICROSECONDS, and NANOSECONDS.
     *
     * @param summaryName The name of the summary that will appear in output
     * @param unit The unit of time to be displayed
     */
    public TickTock(String summaryName, Unit unit) {
        this.ticks = new LinkedHashMap<>();
        this.unit = unit;
        this.decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        this.decimalFormat.setMaximumFractionDigits(340);
        this.summaryName = " - " + summaryName;
        this.startTime = System.nanoTime();
    }

    /**
     * Saves a tick with the specified name at the time it was called. The ticks
     * are used in the summary function to output the interval times between ticks.
     *
     * @param tickName The name of the tick that wil be displayed in the TickTock Summary
     */
    public void tick(String tickName) {
        ticks.put(tickName, System.nanoTime());
    }

    /**
     * Outputs a summary of interval times and the total runtime between
     * the TickTock instantiation and summary call to console/standard output.
     */
    public void summary() {
        endTime = System.nanoTime();
        String dottedLine = "---------------------------------------------------------------------------------------\n";
        StringBuilder builder = new StringBuilder();
        builder.append(dottedLine).append("TickTock Summary" + summaryName +"\n").append(dottedLine);
        long lastTime = startTime;
        for (String key : ticks.keySet()) {
            long nextTime = ticks.get(key);
            builder.append(key + ": " + decimalFormat.format((nextTime - lastTime) / unit.getDivisor()) + unit.toString() + "\n");
            lastTime = nextTime;
        }
        builder.append(dottedLine)
                .append("Total Runtime: " + decimalFormat.format((endTime - startTime) / unit.getDivisor()) + unit.toString() + "\n")
                .append(dottedLine);
        System.out.println(builder.toString());
    }

}
