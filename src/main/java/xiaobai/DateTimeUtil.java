package xiaobai;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Utility class for parsing and formatting date and date-time values.
 * - Accepts ISO (yyyy-MM-dd ...) and DMY (d/M/uuuu ...) inputs
 * - Accepts time-only inputs (HHmm, HH:mm) -> combined with today's date
 * - Provides standardized ISO and friendly display formatters
 */
public final class DateTimeUtil {
    private DateTimeUtil() {}

    /* ************************************
     * Friendly display formatters (used by print)
     * ************************************/

    private static final DateTimeFormatter DISPLAY_DATE =
            DateTimeFormatter.ofPattern("MMM d uuuu", Locale.ENGLISH);        // e.g., "Sep 5 2025"
    private static final DateTimeFormatter DISPLAY_DATE_TIME =
            DateTimeFormatter.ofPattern("MMM d uuuu HH:mm", Locale.ENGLISH);  // e.g., "Sep 5 2025 12:00"

    /* ************************************
     * ISO / DMY parsers
     * ************************************/

    // ISO
    private static final DateTimeFormatter ISO_DATE =
            DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter ISO_DATE_TIME_HHMM =
            DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter ISO_DATE_TIME_HH_COLON_MM =
            DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter ISO_T_DATE_TIME_HH_COLON_MM =
            DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm").withResolverStyle(ResolverStyle.STRICT);

    // DMY (lenient day/month digits)
    private static final DateTimeFormatter DMY_DATE =
            DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter DMY_DATE_TIME_HHMM =
            DateTimeFormatter.ofPattern("d/M/uuuu HHmm").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter DMY_DATE_TIME_HH_COLON_MM =
            DateTimeFormatter.ofPattern("d/M/uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

    // Time-only
    private static final DateTimeFormatter TIME_HHMM =
            DateTimeFormatter.ofPattern("HHmm").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter TIME_HH_COLON_MM =
            DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);

    /* ************************************
     * Public printing API (used across the project)
     * ************************************/

    /** Pretty-prints a LocalDate, e.g., "Sep 5 2025". */
    public static String print(LocalDate date) {
        return DISPLAY_DATE.format(date);
    }

    /**
     * Pretty-prints a LocalDateTime.
     * If time == 00:00, prints date only; otherwise prints date + " HH:mm".
     */
    public static String print(LocalDateTime dt) {
        if (dt.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return DISPLAY_DATE.format(dt);
        }
        return DISPLAY_DATE_TIME.format(dt);
    }

    /* ************************************
     * ISO helpers (used by Storage)
     * ************************************/

    /** Formats a LocalDateTime to an ISO-like string with minutes precision: "yyyy-MM-dd HH:mm". */
    public static String toIso(LocalDateTime dt) {
        return ISO_DATE_TIME_HH_COLON_MM.format(dt);
    }

    /**
     * Parses an ISO-like date-time string.
     * Accepts "yyyy-MM-dd HH:mm" and "yyyy-MM-dd'T'HH:mm".
     */
    public static LocalDateTime fromIso(String s) {
        String t = normalizeSpaces(s);
        try {
            return LocalDateTime.parse(t, ISO_DATE_TIME_HH_COLON_MM);
        } catch (DateTimeParseException ignored) { }
        return LocalDateTime.parse(t, ISO_T_DATE_TIME_HH_COLON_MM);
    }

    /* ************************************
     * Lenient parser (main entry used by Deadline/Event constructors)
     * ************************************/

    /**
     * Leniently parses common date/time forms into a LocalDateTime.
     *
     * Accepted examples:
     * - "2025-09-05"                 -> 2025-09-05T00:00
     * - "2025-09-05 1200"            -> 2025-09-05T12:00
     * - "2025-09-05 12:00"           -> 2025-09-05T12:00
     * - "2025-09-05T12:00"           -> 2025-09-05T12:00
     * - "5/9/2025"                   -> 2025-09-05T00:00
     * - "5/9/2025 1200"              -> 2025-09-05T12:00
     * - "5/9/2025 12:00"             -> 2025-09-05T12:00
     * - "1200"                       -> today at 12:00
     * - "12:00"                      -> today at 12:00
     *
     * @throws XiaoBaiException if none of the patterns match
     */
    public static LocalDateTime parseDateTimeLenient(String input) throws XiaoBaiException {
        if (input == null) {
            throw new XiaoBaiException("No date/time provided.");
        }
        String s = normalizeSpaces(input);

        // 1) Time-only first, to avoid routing "1200" into LocalDate.parse(...)
        LocalDateTime tOnly = tryParseTimeOnly(s);
        if (tOnly != null) return tOnly;

        // 2) ISO date-time variants
        LocalDateTime isoDt = tryParseIsoDateTime(s);
        if (isoDt != null) return isoDt;

        // 3) DMY date-time variants
        LocalDateTime dmyDt = tryParseDmyDateTime(s);
        if (dmyDt != null) return dmyDt;

        // 4) Date-only (ISO or DMY) -> default to start-of-day
        LocalDate dateOnly = tryParseDateOnly(s);
        if (dateOnly != null) return dateOnly.atStartOfDay();

        // 5) Nothing matched
        throw new XiaoBaiException(buildHelpMessage(input));
    }

    /* ************************************
     * Internals
     * ************************************/

    private static String normalizeSpaces(String s) {
        return s.trim().replaceAll("\\s+", " ");
    }

    private static boolean isAllDigits(String s) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch < '0' || ch > '9') return false;
        }
        return true;
    }

    /** Try HHmm or HH:mm (time-only); if matched, combine with today's date. */
    private static LocalDateTime tryParseTimeOnly(String s) {
        // "1200" -> HHmm
        if (s.length() == 4 && isAllDigits(s)) {
            try {
                LocalTime t = LocalTime.parse(s, TIME_HHMM);
                return LocalDate.now().atTime(t);
            } catch (DateTimeParseException ignored) {}
        }
        // "12:00" -> HH:mm
        if (s.matches("\\d{1,2}:\\d{2}")) {
            try {
                LocalTime t = LocalTime.parse(s, TIME_HH_COLON_MM);
                return LocalDate.now().atTime(t);
            } catch (DateTimeParseException ignored) {}
        }
        return null;
    }

    /** Try ISO date-time variants. */
    private static LocalDateTime tryParseIsoDateTime(String s) {
        try {
            return LocalDateTime.parse(s, ISO_DATE_TIME_HHMM); // "yyyy-MM-dd HHmm"
        } catch (DateTimeParseException ignored) {}
        try {
            return LocalDateTime.parse(s, ISO_DATE_TIME_HH_COLON_MM); // "yyyy-MM-dd HH:mm"
        } catch (DateTimeParseException ignored) {}
        try {
            return LocalDateTime.parse(s, ISO_T_DATE_TIME_HH_COLON_MM); // "yyyy-MM-dd'T'HH:mm"
        } catch (DateTimeParseException ignored) {}
        return null;
    }

    /** Try DMY date-time variants. */
    private static LocalDateTime tryParseDmyDateTime(String s) {
        try {
            return LocalDateTime.parse(s, DMY_DATE_TIME_HHMM); // "d/M/uuuu HHmm"
        } catch (DateTimeParseException ignored) {}
        try {
            return LocalDateTime.parse(s, DMY_DATE_TIME_HH_COLON_MM); // "d/M/uuuu HH:mm"
        } catch (DateTimeParseException ignored) {}
        return null;
    }

    /** Try date-only (ISO first, then DMY). */
    private static LocalDate tryParseDateOnly(String s) {
        try {
            return LocalDate.parse(s, ISO_DATE); // "uuuu-MM-dd"
        } catch (DateTimeParseException ignored) {}
        try {
            return LocalDate.parse(s, DMY_DATE); // "d/M/uuuu"
        } catch (DateTimeParseException ignored) {}
        return null;
    }

    private static String buildHelpMessage(String raw) {
        return "I couldn't understand the date/time: \"" + raw + "\".\n"
                + "Try formats like:\n"
                + "• 2025-09-05 1200\n"
                + "• 2025-09-05 12:00\n"
                + "• 5/9/2025 1200\n"
                + "• 5/9/2025 12:00\n"
                + "• 1200 (today 12:00)\n"
                + "• 12:00 (today 12:00)";
    }
}
