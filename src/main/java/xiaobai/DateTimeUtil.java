package xiaobai;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Locale;

/**
 * Utility class for parsing and formatting date and date-time values.
 * Supports ISO and DMY formats and provides standardized printing methods.
 */
public final class DateTimeUtil {
    private DateTimeUtil() {}

    private static final DateTimeFormatter DATE_ONLY_ISO =
            DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH);

    private static final DateTimeFormatter DATE_TIME_ISO =
            new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd")
                    .optionalStart().appendLiteral(' ').appendPattern("HHmm").optionalEnd()
                    .optionalStart().appendLiteral(' ').appendPattern("HH:mm").optionalEnd()
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter(Locale.ENGLISH);

    private static final DateTimeFormatter DATE_TIME_DMY =
            new DateTimeFormatterBuilder()
                    .appendPattern("d/M/uuuu")
                    .optionalStart().appendLiteral(' ').appendPattern("HHmm").optionalEnd()
                    .optionalStart().appendLiteral(' ').appendPattern("HH:mm").optionalEnd()
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter(Locale.ENGLISH);

    private static final DateTimeFormatter PRINT_DATE =
            DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter PRINT_DATE_TIME =
            DateTimeFormatter.ofPattern("MMM d yyyy h:mma", Locale.ENGLISH);

    /**
     * Parses a date-time string in ISO or DMY formats, with optional time component.
     * Falls back to parsing as a date-only string if no time is present.
     *
     * @param raw Input date-time string.
     * @return Parsed LocalDateTime object.
     */
    public static LocalDateTime parseDateTimeLenient(String raw) {
        String s = raw.trim();

        try {
            LocalDateTime dt = tryParseISODateTime(s);
            if (dt != null) return dt;
        } catch (DateTimeParseException ignored) {}

        try {
            LocalDateTime dt = tryParseDMYDateTime(s);
            if (dt != null) return dt;
        } catch (DateTimeParseException ignored) {}

        LocalDate d = LocalDate.parse(s, DATE_ONLY_ISO);
        return d.atStartOfDay();
    }


    private static LocalDateTime tryParseISODateTime(String s) {
        try {
            if (s.matches("\\d{4}-\\d{2}-\\d{2}([\\s]+\\d{4}|[\\s]+\\d{2}:\\d{2})?")) {
                return LocalDateTime.parse(s.matches("\\d{4}-\\d{2}-\\d{2}$") ? s + " 0000" : s, DATE_TIME_ISO);
            }
        } catch (DateTimeParseException ignored) {}
        return null;
    }

    private static LocalDateTime tryParseDMYDateTime(String s) {
        return LocalDateTime.parse(s.matches("\\d{1,2}/\\d{1,2}/\\d{4}$") ? s + " 0000" : s, DATE_TIME_DMY);
    }

    /**
     * Prints a LocalDateTime in a standardised format.
     *
     * @param dt Date-time to format.
     * @return Formatted string representation.
     */
    public static String print(LocalDateTime dt) {
        if (dt.getHour() == 0 && dt.getMinute() == 0 && dt.getSecond() == 0) {
            return dt.toLocalDate().format(PRINT_DATE);
        }
        return dt.format(PRINT_DATE_TIME);
    }

    /**
     * Prints a LocalDate
     *
     * @param d Date to format.
     * @return Formatted string representation.
     */
    public static String print(LocalDate d) {
        return d.format(PRINT_DATE);
    }

    /**
     * Converts a LocalDateTime to its string representation.
     *
     * @param dt Date-time to convert.
     * @return ISO string representation.
     */
    public static String toIso(LocalDateTime dt) {
        return dt.toString();
    }

    /**
     * Parses a string into a LocalDateTime.
     *
     * @param s ISO string to parse.
     * @return Parsed LocalDateTime object.
     */
    public static LocalDateTime fromIso(String s) {
        return LocalDateTime.parse(s);
    }
}

