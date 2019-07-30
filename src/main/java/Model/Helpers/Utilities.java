package Model.Helpers;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Scanner;

public  class Utilities {
    public static Scanner sc;

    // https://memorynotfound.com/get-first-day-of-the-month-date-java/
    // https://stackoverflow.com/questions/34076518/calendar-get-last-day-of-previous-month
    public static java.util.Date firstOfThisMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static java.util.Date firstOfLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.setTime(firstOfThisMonth());
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * prompt user again again till the user enters a Integer
     * @param message - prompt message
     * @param error - Error given when user enters a invalid number
     * @return - number user input
     */
    public static int getIntegerInput(String message, String error) {
        System.out.print(message);
        while (!sc.hasNextInt()) {
            System.out.printf("\n\t%s\n\n", error);
            System.out.print(message);
            sc.nextLine();
        }
        int input = sc.nextInt();
        sc.nextLine();
        return input;
    }

    public static int getIntegerInput(String message, String error, Boolean nonNegative) {
        System.out.print(message);
        while (!sc.hasNextInt()) {
            System.out.printf("\n\t%s\n\n", error);
            System.out.print(message);
            sc.nextLine();
        }
        int input = sc.nextInt();
        sc.nextLine();
        if(nonNegative && input < 0){
            System.out.printf("\n\t%s\n\n", error);
            input = getIntegerInput(message, error);
        }
        return input;
    }

    // Same as above but for BigDecimals
    public static BigDecimal getBigDecimalInput(String message, String error) {
        System.out.print(message);
        while (!sc.hasNextBigDecimal()) {
            System.out.printf("\n\t%s\n\n", error);
            System.out.print(message);
            sc.nextLine();
        }
        BigDecimal input = sc.nextBigDecimal();
        sc.nextLine();
        return input;
    }

    // Same as above but for Doubles
    public static double getDoubleInput(String message, String error) {
        System.out.print(message);
        while (!sc.hasNextDouble()) {
            System.out.printf("\n\t%s\n\n", error);
            System.out.print(message);
            sc.nextLine();
        }
        double input = sc.nextDouble();
        sc.nextLine();
        return input;
    }

    public static double getDoubleInput(String message, String error, Boolean nonNegative) {
        System.out.print(message);
        while (!sc.hasNextDouble()) {
            System.out.printf("\n\t%s\n\n", error);
            System.out.print(message);
            sc.nextLine();
        }
        double input = sc.nextDouble();
        if(nonNegative && input < 0){
            System.out.printf("\n\t%s\n\n", error);
            input = getDoubleInput(message, error);
        }
        sc.nextLine();
        return input;
    }

    public static Date getReleaseDate(String type){
        try{
            int releasedYear = getIntegerInput(String.format("Released year of this %s: ", type), "Invalid year!");
            int releasedMonth = getIntegerInput(String.format("Released month of this %s: ", type), "Invalid month!");
            int releasedDay = getIntegerInput(String.format("Released day of this %s: ", type), "Invalid day!");
            return new Date(releasedYear, releasedMonth, releasedDay);
        }catch (IllegalArgumentException e){
            System.out.println("\n"+e.getMessage());
            return getReleaseDate(type);
        }
    }
}
