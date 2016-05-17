package com.study.java8;


import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by tianyuzhi on 16/5/17.
 */
public class DateTimeTest {

    public static void main(String[] args) {
        DateTimeTest test = new DateTimeTest();
        test.testLocalDateTime();
        test.testZonedDateTime();
        test.testChronoUnit();
        test.testBackwardCompatability();
    }

    public void testLocalDateTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        System.out.println("Current DateTime:" + currentTime);

        LocalDate date1 = currentTime.toLocalDate();
        System.out.println("date1:" + date1);

        Month month = currentTime.getMonth();
        int day = currentTime.getDayOfMonth();
        int seconds = currentTime.getSecond();

        System.out.println("Month:" + month + ", day:" + day + ",seconds" + seconds);




        LocalDateTime dateTime = currentTime.withDayOfMonth(10).withYear(2012);
        System.out.println("datetime " + dateTime);
        //
        LocalDate date3 = LocalDate.of(2014, Month.DECEMBER, 12);
        System.out.println("date3:" + date3);

        LocalTime date4 = LocalTime.of(22,15);
        System.out.println("date4:" + date4);

        LocalTime date5 = LocalTime.parse("20:15:30");
        System.out.println("date5:" + date5);
    }

    public void testZonedDateTime() {
        ZonedDateTime date1 = ZonedDateTime.parse("2007-12-03T10:15:30+05:30[Asia/Karachi]");
        System.out.println("date1:" + date1);

        ZoneId id = ZoneId.of("Europe/Paris");
        System.out.println("ZoneId:" + id);

        ZoneId currentZone = ZoneId.systemDefault();
        System.out.println("CurrentZone:" + currentZone);
    }

    public void testChronoUnit() {
        LocalDate today = LocalDate.now();
        System.out.println("Current date:" + today);

        // add 1 week to the current date
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
        System.out.println("Next week:" + nextWeek);

        LocalDate nextMonth = today.plus(1, ChronoUnit.MONTHS);
        System.out.println("Next month:" + nextMonth);

        LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
        System.out.println("Next year:" + nextYear);

        LocalDate nextDecade = today.plus(1, ChronoUnit.DECADES);
        System.out.println("Date after ten years:" + nextDecade);
    }

    public void testBackwardCompatability() {
        Date currentDate = new Date();
        System.out.println("CurrentDate:" + currentDate);
        Instant now = currentDate.toInstant();
        ZoneId currentZone = ZoneId.systemDefault();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, currentZone);
        System.out.println("Local date:" + localDateTime);

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, currentZone);
        System.out.println("Zoned date:" + zonedDateTime);

    }
}
