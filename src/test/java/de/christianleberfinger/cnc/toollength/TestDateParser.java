package de.christianleberfinger.cnc.toollength;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDateParser {

    @Test
    void pathToDate() {
        Path path = Paths.get("logging\\20211029-100116--CNCLOG.txt");
        LocalDate date = EdingLogs.dateOfLog(path);
        assertEquals(LocalDate.of(2021,10,29), date);
    }

}
