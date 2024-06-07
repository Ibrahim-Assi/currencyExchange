package com.ex.common.tools;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;


public class DateTool {

    public static int oneDay = 86400000;    // day time represent by long

    public static String DD_MM_YYYY = "dd/MM/yyyy";
    public static String MM_DD_YYYY = "MM/dd/yyyy";
    public static String DD_MM_YY = "dd/MM/yy";
    public static String YYYY = "yyyy";
    public static String MM = "MM";
    public static String DD = "dd";
    public static String DDMMYYYY = "dd.MM.yyyy";
    public static String DDMMYY = "dd.MM.yy";
    public static String YYYY_MM_DD = "yyyy/MM/dd";
    public static String YY_MM_DD = "yy/MM/dd";
    public static String YYYYMMDD = "yyyy.MM.dd";
    public static String YYMMDD = "yy.MM.dd";
    public static String YYYY_DD_MM = "yyyy/dd/MM";
    public static String YY_DD_MM = "yy/dd/MM";
    public static String YYYYDDMM = "yyyy.dd.MM";
    public static String YYDDMM = "yy.dd.MM";
    public static String HHMMSS = "HH:mm:SS";
    public static String HHMM = "HH:mm";
    public static String HH = "HH";
    public static String MI = "mm";
    public static String DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";
    public static String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:SS";
    public static String DDMMYYYYHH_MM_SS = "dd.MM.yyyy HH:mm:SS";
    public static String DDMMYYYYHH_MM = "dd.MM.yyyy HH:mm";
    public static String DDMMYYHH_MM = "dd.MM.yy HH:mm";
    public static String DDMMYYHH_MM_SS = "dd.MM.yy HH:mm:SS";
    public static String DD_MM_YY_HH_MM_SS = "dd/MM/yy HH:mm:SS";
    public static String MM_YY =  "MM/yy";
    public static String MM_YYYY =  "MM/yyyy";
    public static String MMYY =  "MM.yy";
    public static String MMYYYY =  "MM.yyyy";
    public static String DASH_DD_MM_YYYY = "dd-MM-yyyy";
    public static String DASH_DD_MM_YY = "dd-MM-yy";
    public static String DASH_DD_MM_YYYY_HH_MM = "dd-MM-yyyy HH:mm";
    public static String DASH_DD_MM_YY_HH_MM = "dd-MM-yy HH:mm";
    public static String DASH_YYYY_MM_DD = "yyyy-MM-dd";
    public static String DASH_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static String DASH_YY_MM_DD = "yy-MM-dd";
    
    
    public static String getNextDate(Date startDate, int period, Hashtable exceptionDate) {

        Date nextDay = new Date(startDate.getTime());
        for (int i = 0; i < period;) {
            nextDay = new Date(nextDay.getTime() + oneDay);
            if (exceptionDate.get(nextDay.getDay() + "") != null) {
                continue;
            }
            i++;
        }
        return new SimpleDateFormat(DD_MM_YYYY).format(new Date(nextDay.getTime()));
    }

    public static String getNextDate(Date startDate, Date endDate, int period, Hashtable exceptionDate) {
        try {
            Date nextDay = new Date(startDate.getTime());
            for (int i = 0; i < period;) {
                nextDay = new Date(nextDay.getTime() + oneDay);
                if (exceptionDate.get(nextDay.getDay() + "") != null) {
                    continue;
                }
                i++;
            }

            if (endDate.getTime() < nextDay.getTime()) {
                return new SimpleDateFormat(DD_MM_YYYY).format(new Date(endDate.getTime()));
            } else {
                return new SimpleDateFormat(DD_MM_YYYY).format(new Date(nextDay.getTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "--/--/----";
        }

    }

    public static String getDateDay(int dayValue) {

        String day = "Sunday";

        if (dayValue == 0) {
            day = "Sunday";
        } else if (dayValue == 1) {
            day = "Monday";
        } else if (dayValue == 2) {
            day = "Tuesday";
        } else if (dayValue == 3) {
            day = "Wednesday";
        } else if (dayValue == 4) {
            day = "Thursday";
        } else if (dayValue == 5) {
            day = "Friday";
        } else if (dayValue == 6) {
            day = "Saturday";
        }
        return day;
    }

    public static String getDateArabicDay(int dayValue) {

        String day = "السبت";

        if (dayValue == 2) {
            day = "الاثنين";
        } else if (dayValue == 3) {
            day = "الثلاثاء";
        } else if (dayValue == 4) {
            day = "الاربعاء";
        } else if (dayValue == 5) {
            day = "الخميس";
        } else if (dayValue == 6) {
            day = "الجمعة";
        } else if (dayValue == 0) {
            day = "السبت";
        } else if (dayValue == 1) {
            day = "الاحد";
        }


        return day;
    }

    public static String getArabicDayName(Date dayDate)
    {
        String dayName = "";
        if(dayDate != null)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dayDate);
            switch(cal.get(Calendar.DAY_OF_WEEK))
            {
                case 1: dayName =  "الاحد";    break;
                case 2: dayName =  "الاثنين";  break;
                case 3: dayName =  "الثلاثاء"; break;
                case 4: dayName =  "الأربعاء"; break;
                case 5: dayName =  "الخميس";   break;
                case 6: dayName =  "الجمعة";   break;
                case 7: dayName =  "السبت";    break;
            }
        }
        return dayName;
    }

    public static String getDateArabicMonth(int monthValue) {

        String month = "كانون الثاني";
        monthValue = monthValue + 1;

        if (monthValue == 1) {
            month = "كانون الثاني";
        } else if (monthValue == 2) {
            month = "شباط";
        } else if (monthValue == 3) {
            month = "آذار";
        } else if (monthValue == 4) {
            month = "نيسان";
        } else if (monthValue == 5) {
            month = "أيار";
        } else if (monthValue == 6) {
            month = "حزيران";
        } else if (monthValue == 7) {
            month = "تموز";
        } else if (monthValue == 8) {
            month = "آب";
        } else if (monthValue == 9) {
            month = "ايلول";
        } else if (monthValue == 10) {
            month = "تشرين اول";
        } else if (monthValue == 11) {
            month = "تشرين ثاني";
        } else if (monthValue == 12) {
            month = "كانون الاول";
        }

        return month;
    }
    public static String getDateArabicMonth(String monthValue) {

        String month = "كانون الثاني";

        if (monthValue == "Jan.") {
            month = "كانون الثاني";
        } else if (monthValue == "Feb.") {
            month = "شباط";
        } else if (monthValue == "Mar.") {
            month = "آذار";
        } else if (monthValue == "Apr.") {
            month = "نيسان";
        } else if (monthValue == "May.") {
            month = "أيار";
        } else if (monthValue == "Jun.") {
            month = "حزيران";
        } else if (monthValue == "Jul.") {
            month = "تموز";
        } else if (monthValue == "Aug.") {
            month = "آب";
        } else if (monthValue =="Sep.") {
            month = "ايلول";
        } else if (monthValue == "Oct.") {
            month = "تشرين اول";
        } else if (monthValue == "Nov.") {
            month = "تشرين ثاني";
        } else if (monthValue == "Dec.") {
            month = "كانون الاول";
        }

        return month;
    }

    public static Date convertStringToDate_dd_mm_yyyy(String dateValue) {

        Date date = null;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DD_MM_YYYY);
            if (dateValue != null && dateValue.trim().length() > 0) {
                date = formatter.parse(dateValue.trim());
            }
        } catch (Exception ex) {
        }
        return date;
    }

    public static Date convertStringDotsToDate_HH_mm_SS(String dateValue) {

        Date date = null;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(HHMMSS);
            if (dateValue != null && dateValue.trim().length() > 0) {
                date = formatter.parse(dateValue.trim());
            }
        } catch (Exception ex) {
        }
        return date;
    }

    public static Date convertStringDotsToDate_HH_mm(String dateValue) {

        Date date = null;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(HHMM);
            if (dateValue != null && dateValue.trim().length() > 0) {
                date = formatter.parse(dateValue.trim());
            }
        } catch (Exception ex) {
        }
        return date;
    }

    public static Date convertStringToDate(String dateValue) {

        Date date = null;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(MM_DD_YYYY);
            if (dateValue != null && dateValue.trim().length() > 0) {
                date = formatter.parse(dateValue.trim());
            }
        } catch (Exception ex) {
        }
        return date;
    }

    public static Date convertStringToDate(String dateValue, String format) {

        Date date = null;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            if (dateValue != null && dateValue.trim().length() > 0) {
                date = formatter.parse(dateValue.trim());
            }
        } catch (Exception ex) {
            System.err.println("Error DateTool.convertStringToDate:"+ex);
        }
        return date;
    }

    public static Date convertStringToDateMM_YYYY(String dateValue) {

        Date date = null;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(MM_YYYY);
            if (dateValue != null && dateValue.trim().length() > 0) {
                date = formatter.parse(dateValue.trim());
            }
        } catch (Exception ex) {
            System.out.println(" error in converting date to month date ");
        }
        return date;
    }

    public static Date convertStringToDateYYY_MM_DD(String dateValue) {

        Date date = null;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DASH_YYYY_MM_DD);
            if (dateValue != null && dateValue.trim().length() > 0) {
                date = formatter.parse(dateValue.trim());
            }
        } catch (Exception ex) {
        }
        return date;
    }

    public static final String decorateYYYY_MM_DD(Object columnValue) {

        FastDateFormat dateFormat = FastDateFormat.getInstance(DASH_YYYY_MM_DD);

        if (columnValue != null) {

            Date date = (Date) columnValue;
            return dateFormat.format(date);
        } else {
            return " ";
        }
    }
    
    
    public static final String decorateDate(Object columnValue,String format) {

        FastDateFormat dateFormat = FastDateFormat.getInstance(format);

        if (columnValue != null) {

            Date date = (Date) columnValue;
            return dateFormat.format(date);
        } else {
            return " ";
        }
    }

    public static final String decorateMM_YYYY(Object columnValue) {

        FastDateFormat dateFormat = FastDateFormat.getInstance(MM_YYYY);

        if (columnValue != null) {

            Date date = (Date) columnValue;
            return dateFormat.format(date);
        } else {
            return " ";
        }
    }

    public static final String decorateMM_YY(Object columnValue) {

        FastDateFormat dateFormat = FastDateFormat.getInstance(MM_YY);

        if (columnValue != null) {

            Date date = (Date) columnValue;
            return dateFormat.format(date);
        } else {
            return " ";
        }
    }

    public static final String decorateHH_mm(Object columnValue) {

        FastDateFormat dateFormat = FastDateFormat.getInstance(HHMM);

        if (columnValue != null) {

            Date date = (Date) columnValue;
            return dateFormat.format(date);
        } else {
            return " ";
        }
    }

     public static final String decorateHH(Object columnValue) {

        FastDateFormat dateFormat = FastDateFormat.getInstance(HH);

        if (columnValue != null) {

            Date date = (Date) columnValue;
            return dateFormat.format(date);
        } else {
            return " ";
        }
    }
    
     public static final String decorateMI(Object columnValue) {

        FastDateFormat dateFormat = FastDateFormat.getInstance(MI);

        if (columnValue != null) {

            Date date = (Date) columnValue;
            return dateFormat.format(date);
        } else {
            return " ";
        }
    }
    

     public static final String decorateDD_MM_YYYY(Object columnValue) {

        FastDateFormat dateFormat = FastDateFormat.getInstance(DD_MM_YYYY);

        if (columnValue != null) {

            Date date = (Date) columnValue;
            return dateFormat.format(date);
        } else {
            return " ";
        }
    }

    public static final String getMonthLastDay() {
        int days = Calendar.getInstance().getActualMaximum(Calendar.DATE);
        return "" + days;
    }

    public static final String getMonthLastDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int days = c.getActualMaximum(Calendar.DATE);
        return "" + days;
    }

    public static final String getMonthDayNo(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int days = c.get(Calendar.DAY_OF_WEEK);
        return "" + days;
    }

    public static final Date getMonthLastDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int days = c.getActualMaximum(Calendar.DATE);
        date.setDate(days);
        return date;
    }

    public static final long calcDiffMinutesBtwnDate(Date startDate,Date endDate)
    {
        long result=-1;
        try
        {
            result=  (endDate.getTime() - startDate.getTime())/ (60 * 1000);
        }
        catch (Exception e)
        {
            result=-1;
            e.printStackTrace();

        }
        return result;
    }

    public static final long calcDiffHrsBtwnDate(Date startDate,Date endDate)
    {
        long result=-1;
        try
        {
            result=  (endDate.getTime() - startDate.getTime())/ (60 * 60 * 1000);
        }
        catch (Exception e)
        {
            result=-1;
            e.printStackTrace();

        }
        return result;
    }
//      public static void main(String [] args)
//    {
//        System.out.println(getMonthDayNo(DateTool.convertStringToDate_dd_mm_yyyy("01/06/2008")));
//     }

    public static Date getDateAfterPeriod(Date startDate,Integer period, Hashtable exceptionDate) {
        
        Date nextDay = new Date(startDate.getTime());
        for (int i = 0; i < period;) {
            nextDay = new Date(nextDay.getTime() + oneDay);
            if (exceptionDate.get(nextDay.getDay() + "") != null) {
                continue;
            }
            i++;
        }
        return new Date(nextDay.getTime());
    }
    public static final Date getDateWithoutTime(Date date)
        {
            Date result=date;
            try
            {
                SimpleDateFormat formatter = new SimpleDateFormat(MM_DD_YYYY);

                String temp = formatter.format(date);
                result=formatter.parse(temp);

            }
            catch (Exception e)
            {
                result=date;
                e.printStackTrace();

            }
            return result;
        }

    public static  Date truncateDate(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        date = cal.getTime();

        return date;
    }

    public static Date getNextDayDate(Date currentDate,int calenderDay)//Calendar.THURSDAY
    {
        Date date= null;

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);

        while (cal.get(Calendar.DAY_OF_WEEK) != calenderDay) {
            cal.add(Calendar.DATE, 1);
        }

        date = cal.getTime();
        return date;
    }
    public static String getDateEnglishDay(Date date)
    {
        if(date ==null)
            return "";

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayValue = cal.get(Calendar.DAY_OF_WEEK);

        String day = "Sunday";

        switch (dayValue)
        {
            case 1: day = "Sunday";      break;
            case 2: day = "Monday";      break;
            case 3: day = "Tuesday";     break;
            case 4: day = "Wednesday";   break;
            case 5: day = "Thursday";    break;
            case 6: day = "Friday";      break;
            case 7: day = "Saturday";    break;
        }

        return day;
    }

    public static String getDateArabicDay(Date date)
    {
        if(date ==null)
            return "";

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayValue = cal.get(Calendar.DAY_OF_WEEK);

        String day = "الاحد";

        switch (dayValue)
        {
            case 1: day = "الاحد";    break;
            case 2: day = "الاثنين";  break;
            case 3: day = "الثلاثاء"; break;
            case 4: day = "الاربعاء"; break;
            case 5: day = "الخميس";   break;
            case 6: day = "الجمعة";   break;
            case 7: day = "السبت";    break;
        }

        return day;
    }


    public static String decorateHH_mm_SS(Object columnValue) {

        FastDateFormat dateFormat = FastDateFormat.getInstance("HH:mm:SS");

        if (columnValue != null) {

            Date date = (Date) columnValue;
            return dateFormat.format(date);
        } else
            return " ";
    }

    public static double  calcDiffMonthsBetweenDates(Date startDate,Date endDate)
    {
        double  result=-1;
        double AVERAGE_MILLIS_PER_MONTH = 365.24 * 24 * 60 * 60 * 1000 / 12;
        try
        {
            result=  (endDate.getTime() - startDate.getTime())/ AVERAGE_MILLIS_PER_MONTH;
        }
        catch (Exception e)
        {
            result=-1;
            e.printStackTrace();

        }
        return result;
    }

    public static long calcDiffDaysBetweenDates(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static Date getNextDate(Date startDate,int period)
    {
        Date result=null;
        try
        {
            Calendar c = Calendar.getInstance();
            c.setTime(startDate);
            c.add(Calendar.DATE, period);
            result = c.getTime();
        }
        catch (Exception e)
        {
            result=null;
            e.printStackTrace();
        }
        return result;
    }



    public static Date getNextDate(Date startDate,int calendarType,int period) // getNextDate(startDate,Calendar.DATE,5)
    {
        Date result=null;
        try
        {
            Calendar c = Calendar.getInstance();
            c.setTime(startDate);
            c.add(calendarType, period);
            result = c.getTime();
        }
        catch (Exception e)
        {
            result=null;
            e.printStackTrace();
        }
        return result;
    }

    public static final int getDiffBetweenTimesInMinutes(String time1, String time2) {//time1,time2 :> HH:mm:ss
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(time1);
            date2 = format.parse(time2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        long difference = date2.getTime() - date1.getTime();
        String diff = ((difference / 1000) / 60) + "";
        return Integer.parseInt(diff);//in minutes
    }
    public static final double getDiffBetweenTimesInSeconds(String time1, String time2) {//time1,time2 :> HH:mm:ss
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(time1);
            date2 = format.parse(time2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        double difference = date2.getTime() - date1.getTime();
        return (difference / 1000);
    }



    public static boolean isToday(Date date){
        boolean result = false;

        try{
            Calendar calendarData = Calendar.getInstance();
            calendarData.setTimeInMillis(date.getTime());
            calendarData.set(Calendar.HOUR_OF_DAY, 0);
            calendarData.set(Calendar.MINUTE, 0);
            calendarData.set(Calendar.SECOND, 0);
            calendarData.set(Calendar.MILLISECOND, 0);

            Calendar calendarToday = Calendar.getInstance();
            calendarToday.setTimeInMillis(System.currentTimeMillis());
            calendarToday.set(Calendar.HOUR_OF_DAY, 0);
            calendarToday.set(Calendar.MINUTE, 0);
            calendarToday.set(Calendar.SECOND, 0);
            calendarToday.set(Calendar.MILLISECOND, 0);

            if(calendarToday.getTimeInMillis() == calendarData.getTimeInMillis()) {
                result = true;
            }
        }
        catch (Exception exception){
            System.out.println("Error in DateTool.isToday:"+exception);
        }

        return result;
    }


    public static Date roundMonth(String dateStr) throws Exception {
         return roundMonth(dateStr,DD_MM_YYYY);
    }

    public static Date roundMonth(String dateStr,String format)throws Exception {

        int year  = Integer.parseInt(dateStr.split("/")[2]);
        int month = Integer.parseInt(dateStr.split("/")[1]);
        int day   = Integer.parseInt(dateStr.split("/")[0]);

        if(month <= 0 || month > 12){
            throw new Exception ("Invalid Month Date");
        }

        int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();
        if(day <= 0 || day > lastDayOfMonth){
            throw new Exception ("Invalid Day Date");
        }

         Date date = convertStringToDate(dateStr,format);
         return roundMonth(date);
    }

    public static Date roundMonth(Date date) throws Exception {

            Date dateRounded = null;
            FastDateFormat dateFormat = FastDateFormat.getInstance(DD_MM_YYYY);
            String dateStr = dateFormat.format(date);

            int year  = Integer.parseInt(dateStr.split("/")[2]);
            int month = Integer.parseInt(dateStr.split("/")[1]);
            int day   = Integer.parseInt(dateStr.split("/")[0]);

            if(month <= 0 || month > 12){
               throw new Exception ("Invalid Month Date");
            }

            int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();
            if(day <= 0 || day > lastDayOfMonth){
                throw new Exception ("Invalid Day Date");
            }

            if(day > 15){
               month = month+1;
            }
            dateRounded = FastDateFormat.getInstance(DD_MM_YYYY).parse("1/"+month+"/"+year);


        return dateRounded;
    }

}
