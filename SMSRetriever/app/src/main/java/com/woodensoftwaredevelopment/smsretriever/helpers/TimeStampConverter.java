package com.woodensoftwaredevelopment.smsretriever.helpers;

/**
 * Created by 2012a1278 on 4/15/17.
 */
import java.util.*;
public class TimeStampConverter {
    public static String ConvertTimeStampToString(long timeinMilliSeconds)
    {
        Date dt = new Date(timeinMilliSeconds);

        return (dt.getYear()+1 + "-" + dt.getMonth() +1 + "-" + dt.getDate()+1 + " " + dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds());
    }
}
