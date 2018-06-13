package uk.gov.hmcts.reform.sandl.snlrules.rules;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {

    public static OffsetDateTime offsetDateTimeOf(String date) {
        String dateFormat = "dd-MM-yyyy HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);


        return OffsetDateTime.of(localDateTime, ZoneOffset.UTC);
    }
}
