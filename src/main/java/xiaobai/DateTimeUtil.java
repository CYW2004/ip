package xiaobai;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.DayOfWeek;
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
        assert date != null : "Date must not be null";
        return DISPLAY_DATE.format(date);
    }

    /**
     * Pretty-prints a LocalDateTime.
     * If time == 00:00, prints date only; otherwise prints date + " HH:mm".
     */
    public static String print(LocalDateTime dt) {
        assert dt != null : "DateTime must not be null";
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
        assert dt != null : "DateTime must not be null";
        return ISO_DATE_TIME_HH_COLON_MM.format(dt);
    }

    /**
     * Parses an ISO-like date-time string.
     * Accepts "yyyy-MM-dd HH:mm" and "yyyy-MM-dd'T'HH:mm".
     */
    public static LocalDateTime fromIso(String s) {
        assert s != null : "ISO string must not be null";
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
        assert input != null : "Input string must not be null";
        String s = normalizeSpaces(input);

        LocalDateTime natural = tryParseNatural(s);
        if (natural != null) return natural;

        LocalDateTime tOnly = tryParseTimeOnly(s);
        if (tOnly != null) return tOnly;

        LocalDateTime isoDt = tryParseIsoDateTime(s);
        if (isoDt != null) return isoDt;

        LocalDateTime dmyDt = tryParseDmyDateTime(s);
        if (dmyDt != null) return dmyDt;

        LocalDate dateOnly = tryParseDateOnly(s);
        if (dateOnly != null) return dateOnly.atStartOfDay();

        throw new XiaoBaiException(buildHelpMessage(input));
    }

    private static String normalizeSpaces(String s) {
        assert s != null : "Input string must not be null";
        return s.trim().replaceAll("\\s+", " ");
    }

    private static boolean isAllDigits(String s) {
        assert s != null : "Input string must not be null";
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch < '0' || ch > '9') return false;
        }
        return true;
    }

    private static LocalDateTime tryParseTimeOnly(String s) {
        assert s != null : "Input string must not be null";
        if (s.length() == 4 && isAllDigits(s)) {
            try {
                LocalTime t = LocalTime.parse(s, TIME_HHMM);
                return LocalDate.now().atTime(t);
            } catch (DateTimeParseException ignored) {}
        }
        if (s.matches("\\d{1,2}:\\d{2}")) {
            try {
                LocalTime t = LocalTime.parse(s, TIME_HH_COLON_MM);
                return LocalDate.now().atTime(t);
            } catch (DateTimeParseException ignored) {}
        }
        return null;
    }

    private static LocalDateTime tryParseIsoDateTime(String s) {
        assert s != null : "Input string must not be null";
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

    private static LocalDateTime tryParseDmyDateTime(String s) {
        assert s != null : "Input string must not be null";
        try {
            return LocalDateTime.parse(s, DMY_DATE_TIME_HHMM); // "d/M/uuuu HHmm"
        } catch (DateTimeParseException ignored) {}
        try {
            return LocalDateTime.parse(s, DMY_DATE_TIME_HH_COLON_MM); // "d/M/uuuu HH:mm"
        } catch (DateTimeParseException ignored) {}
        return null;
    }

    private static LocalDate tryParseDateOnly(String s) {
        assert s != null : "Input string must not be null";
        try {
            return LocalDate.parse(s, ISO_DATE); // "uuuu-MM-dd"
        } catch (DateTimeParseException ignored) {}
        try {
            return LocalDate.parse(s, DMY_DATE); // "d/M/uuuu"
        } catch (DateTimeParseException ignored) {}
        return null;
    }

    private static String buildHelpMessage(String raw) {
        assert raw != null : "Raw input must not be null";
        return "I couldn't understand the date/time: \"" + raw + "\".\n"
                + "Try formats like:\n"
                + "• 2025-09-05 1200\n"
                + "• 2025-09-05 12:00\n"
                + "• 5/9/2025 1200\n"
                + "• 5/9/2025 12:00\n"
                + "• 1200 (today 12:00)\n"
                + "• 12:00 (today 12:00)";
    }

    /* ************************************
     * Natural language helpers
     * ************************************/

    private static LocalDateTime tryParseNatural(String s) {
        assert s != null : "Input string must not be null";
        String lower = s.toLowerCase(Locale.ENGLISH);

        // today / tomorrow / tmr [<time>]
        if (lower.startsWith("today") || lower.startsWith("tomorrow") || lower.startsWith("tmr")) {
            LocalDate base = LocalDate.now();
            if (lower.startsWith("tomorrow") || lower.startsWith("tmr")) base = base.plusDays(1);
            String timePart = extractOptionalTime(s, 5); // "today" length used as baseline
            LocalTime time = parseOptionalTime(timePart);
            if (time == null) time = LocalTime.MIDNIGHT;
            return base.atTime(time);
        }

        // next <weekday> OR <weekday> [<time>]
        // examples: "mon", "next tue", "fri 1430", "thursday 14:30"
        String[] tokens = lower.split("\\s+");
        if (tokens.length >= 1) {
            boolean hasNext = tokens[0].equals("next");
            String wToken = hasNext && tokens.length >= 2 ? tokens[1] : tokens[0];
            DayOfWeek dow = parseWeekdayToken(wToken);
            if (dow != null) {
                LocalDate base = LocalDate.now();
                LocalDate target = next(dow, base); // always the NEXT occurrence (even if today)
                String timePart = null;
                if (hasNext && tokens.length >= 3) {
                    timePart = tokens[2];
                } else if (!hasNext && tokens.length >= 2) {
                    timePart = tokens[1];
                }
                LocalTime time = parseOptionalTime(timePart);
                if (time == null) time = LocalTime.MIDNIGHT;
                return target.atTime(time);
            }
        }

        return null;
    }

    private static String extractOptionalTime(String s, int baselineWordLen) {
        // For phrases like "today 14:00" or "tomorrow 1400"
        String trimmed = s.trim();
        if (trimmed.length() <= baselineWordLen) return null;
        String rest = trimmed.substring(baselineWordLen).trim();
        return rest.isEmpty() ? null : rest;
    }

    private static LocalTime parseOptionalTime(String maybeTime) {
        if (maybeTime == null) return null;
        try {
            if (maybeTime.matches("\\d{4}")) return LocalTime.parse(maybeTime, TIME_HHMM);
        } catch (DateTimeParseException ignored) {}
        try {
            if (maybeTime.matches("\\d{1,2}:\\d{2}")) return LocalTime.parse(maybeTime, TIME_HH_COLON_MM);
        } catch (DateTimeParseException ignored) {}
        return null;
    }

    private static DayOfWeek parseWeekdayToken(String tok) {
        if (tok == null) return null;
        switch (tok) {
        case "mon":
        case "monday": return DayOfWeek.MONDAY;
        case "tue":
        case "tues":
        case "tuesday": return DayOfWeek.TUESDAY;
        case "wed":
        case "weds":
        case "wednesday": return DayOfWeek.WEDNESDAY;
        case "thu":
        case "thur":
        case "thurs":
        case "thursday": return DayOfWeek.THURSDAY;
        case "fri":
        case "friday": return DayOfWeek.FRIDAY;
        case "sat":
        case "saturday": return DayOfWeek.SATURDAY;
        case "sun":
        case "sunday": return DayOfWeek.SUNDAY;
        default: return null;
        }
    }

    private static LocalDate next(DayOfWeek target, LocalDate base) {
        int diff = (target.getValue() - base.getDayOfWeek().getValue() + 7) % 7;
        if (diff == 0) diff = 7; // "next Monday" even if today is Monday
        return base.plusDays(diff);
    }
}
