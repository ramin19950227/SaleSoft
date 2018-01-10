package com.salesoft.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Bu Class- Tarixler ile ishlemek ucundur onlari Stringe cevirib ve eksine
 * Stringi Tarixe cevirmek ucun burda lazimi metodlar var
 *
 * @author Ramin Ismayilov
 */
public class MyDateConverter {

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Java Util Date -Objecti ile Ede bileceyimiz emeliyyatlar
     */
    public static class utilDate {

        /**
         * Default Format is = "dd-MM-yyyy, HH:mm:ss", if wont use this string
         * for file name use replaceAll(":", "-")
         *
         * @param date - Java Util Date
         * @return - String of date = "dd-MM-yyyy, HH:mm:ss"
         */
        public static String toString(Date date) {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
            return formatter.format(date);
        }

        /**
         * dd - Day, MM - Month, yyyy - Year, HH - hours, mm - minute, ss -
         * second,
         *
         * @param date - Java Util Date
         * @param format - give the String type formatDate
         * @return
         */
        public static String toStringCustomFormat(Date date, String format) {
            DateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(date);
        }

    }

    public static class sqlDate {

        public static String toString(java.sql.Date date) {

            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
            return formatter.format(date);
        }

        public static String toStringCustomFormat(java.sql.Date date, String format) {
            DateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(date);
        }

    }

    // https://stackoverflow.com/questions/28897303/persist-java-localdate-in-mysql
    /**
     * Convert Java LocalDate - To MY
     *
     * @param entityValue
     * @return
     */
    public static java.sql.Date convertToDatabaseColumn(LocalDate entityValue) {
        return java.sql.Date.valueOf(entityValue);
    }

    public static LocalDate convertToEntityAttribute(java.sql.Date databaseValue) {
        return databaseValue.toLocalDate();
    }

    //http://code.makery.ch/library/javafx-8-tutorial/ru/part3/
    /**
     * Шаблон даты, используемый для преобразования. Можно поменять на свой.
     */
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    private static final String DATE_TIME_PATTERN = "dd-MM-yyyy, HH:mm:ss";

    /**
     * Форматировщик даты.
     */
    private static final DateTimeFormatter DATE_FORMATTER
            = DateTimeFormatter.ofPattern(DATE_PATTERN);

    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    /**
     * Возвращает полученную дату в виде хорошо отформатированной строки.
     * Используется определённый выше {@link DateUtil#DATE_PATTERN}.
     *
     * @param date - дата, которая будет возвращена в виде строки
     * @return отформатированную строку
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    public static String formatDateTime(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_TIME_FORMATTER.format(date);
    }

    /**
     * Преобразует строку, которая отформатирована по правилам шаблона
     * {@link DateUtil#DATE_PATTERN} в объект {@link LocalDate}.
     *
     * Возвращает null, если строка не может быть преобразована.
     *
     * @param dateString - дата в виде String
     * @return объект даты или null, если строка не может быть преобразована
     */
    public static LocalDate parseDate(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Проверяет, является ли строка корректной датой.
     *
     * @param dateString
     * @return true, если строка является корректной датой
     */
    public static boolean validDate(String dateString) {
        // Пытаемся разобрать строку.
        return MyDateConverter.parseDate(dateString) != null;
    }

}
