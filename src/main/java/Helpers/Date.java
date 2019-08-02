package Helpers;

/**
 * Simple Class That hold year, month and day
 */
public class Date {
    private int year;
    private int month;
    private int day;

    public Date(int year, int month, int day) {
        this.setYear(year);
        this.setMonth(month);
        this.setDay(day);
    }

    public int getYear() {
        return year;
    }

    private void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    private void setMonth(int month) {
        if(month > 0 && month <= 12)
            this.month = month;
        else
            throw  new IllegalArgumentException("Month should be in range of 1-12");
    }

    public int getDay() {
        return day;
    }

    private void setDay(int day) {
        if(this.month == 2){
            if(this.year % 4 == 0){
                if(day > 0 && day <= 29)
                    this.day = day;
                else
                    throw  new IllegalArgumentException("Day should be in range of 1-29");
            }
            else{
                if(day > 0 && day <= 28)
                    this.day = day;
                else
                    throw  new IllegalArgumentException("Day should be in range of 1-28");
            }
        }
        else if(day > 0 && day <= 31){
            this.day = day;
        }
        else{
            throw  new IllegalArgumentException("Day should be in range of 1-31");
        }
    }

    @Override
    public String toString() {
        return year + "/" + month + "/" + day;
    }
}
