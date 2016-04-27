package com.somoplay.screenshow.util;

/**
 * Created by yaolu on 15-06-11.
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LYDateString {

    public static String dateToString(Date date, int time_date_both )
    {
        DateFormat dateFormat;
        if(time_date_both==1)
        {
            dateFormat = new SimpleDateFormat("HH:mm:ss");
        }
        else if(time_date_both==2)
        {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        else //if(time_date_both==3)
        {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        String datestring="";
        if(date!=null)
        {
            datestring= dateFormat.format(date);
        }

        return datestring;
    }

    public static Date stringToDate(String strDate, int time_date_both) throws Exception {
        Date dtReturn = null;
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");

        DateFormat dateFormat;
        if(time_date_both==1)
        {
            dateFormat = new SimpleDateFormat("HH:mm:ss");
        }
        else if(time_date_both==2)
        {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        else //if(time_date_both==3)
        {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }


        try {
            dtReturn = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dtReturn;
    }
}
