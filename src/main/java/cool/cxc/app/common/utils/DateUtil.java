package cool.cxc.app.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    /**
     * 判断给定字符串时间是否为今日(效率不是很高，不过也是一种方法)
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 将字符串转为日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将日期转为字符串类型
     *
     * @param date
     * @return
     */
    public static String ToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2021-10-21 10:10:30" "2021-10-21" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                System.out.println(cal.get(Calendar.HOUR_OF_DAY));
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前时间下的小时数
     *
     * @param day 传入的 时间  "2021-10-21 10:10:30" "2021-10-21"
     * @return 小时数
     * @throws ParseException
     */
    public static int getNowHour(String day) throws ParseException {

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前时间下的分钟数
     *
     * @param day 传入的 时间  "2021-10-21 10:10:30" "2021-10-21"
     * @return 分钟数
     * @throws ParseException
     */
    public static int getNowMin(String day) throws ParseException {

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        return cal.get(Calendar.MINUTE);
    }


    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 2021-11-01 00:00:000   9:00~10:00
        calendar.add(Calendar.HOUR, hour); // 2021-11-01 10:00:000
        return calendar.getTime();
    }

    public static Date addMin(Date date, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 2021-11-01 00:00:000   9:00~10:00
        calendar.add(Calendar.MINUTE, min); // 2021-11-01 10:00:000
        return calendar.getTime();
    }


    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2021-10-21 10:10:30" "2021-10-21" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToDay(Date day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
//        Date date = getDateFormat().parse(day);
        cal.setTime(day);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay == 0) {     // 是今天
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否小于今天
     *
     * @param day 传入的 时间  "2021-10-21 10:10:30" "2021-10-21" 都可以
     * @return true是 false不是
     * @throws ParseException
     */
    public static boolean lessThanToday(Date day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
//        Date date = getDateFormat().parse(day);
        cal.setTime(day);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay < 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取系统当前时间（24小时制）
     * 区别：12小时制使用小写的hh，24小时制使用大写的HH
     *
     * @return 系统当前时间(年月日 时分秒)
     */
    public static String getCurrent24DateTotal() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        return date;
    }

    /**
     * 获取系统当前时间（年月日）
     *
     * @return 系统当前时间(年月日)
     */
    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        return date;
    }


    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
        }
        return DateLocal.get();
    }


}

