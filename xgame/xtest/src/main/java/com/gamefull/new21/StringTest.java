package com.gamefull.new21;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.StringTemplate.STR;

public class StringTest {
    public static void main (String[] args) throws Exception  {
        stringTest21();
//        stringTestBefore21();
    }
    public static void stringTest21() {


        int a = 1;
        int b = 2;
        String interpolated = STR. "\{ a } times \{ b } = \{ a * b }" ;
        System.out.println(interpolated);

        String dateMessage = STR. "Today's date: \{
                LocalDate.now().format(
                        // We could also use DateTimeFormatter.ISO_DATE
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                ) }" ;
        System.out.println(dateMessage);
        int httpStatus = 200;
        String errorMessage = "error pwd";

        String json = STR. """
    {
      "httpStatus": \{ httpStatus },
      "errorMessage": "\{ errorMessage }"
    }""" ;
        System.out.println(json);
    }

    public static void stringTestBefore21() {


        int a = 1;
        int b = 2;

        String concatenated = a + " times " + b + " = " + a * b;
        String format = String.format("%d times %d = %d", a, b, a * b);
        String formatted = "%d times %d = %d".formatted(a, b, a * b);
        System.out.println(concatenated);
        System.out.println(format);
        System.out.println(formatted);
    }

}
