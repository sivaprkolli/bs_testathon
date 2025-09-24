package com.bs.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
        public static String getCurrentDate() {
            LocalDate today = LocalDate.now();
            System.out.println("Current Date: " + today);

            String fullDate[] = today.toString().split("-");
            System.out.println(fullDate[0]);
            System.out.println(fullDate[1]);
            System.out.println(fullDate[2]);

            LocalDate date = LocalDate.of(Integer.parseInt(fullDate[0]), Integer.parseInt(fullDate[1]), Integer.parseInt(fullDate[2]));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
            String formattedDate = date.format(formatter);
            System.out.println(formattedDate);
            return formattedDate;
        }
    }

