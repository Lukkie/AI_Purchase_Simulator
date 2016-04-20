package Tools;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gilles Callebaut on 20/04/2016.
 */
public class DateUtil {
    public static Date addDays(Date date, int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
