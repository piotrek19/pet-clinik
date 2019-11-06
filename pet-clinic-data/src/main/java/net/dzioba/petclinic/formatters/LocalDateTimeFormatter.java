package net.dzioba.petclinic.formatters;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM));
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM).format(object);
    }
}
