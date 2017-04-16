package com.woodensoftwaredevelopment.smsretriever.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.*;
import android.telephony.*;
import android.os.*;

import com.woodensoftwaredevelopment.smsretriever.helpers.DbHelper;
import com.woodensoftwaredevelopment.smsretriever.helpers.TimeStampConverter;

public class SMSReceiver extends BroadcastReceiver {
    public SMSReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {
                Object [] pdus = (Object[]) bundle.get("pdus");
                if(pdus == null)
                {
                    return;
                }
                SmsMessage[] messages = new SmsMessage[pdus.length];
                DbHelper helper = new DbHelper(context);
                for (int i = 0; i < messages.length; i++)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = bundle.getString("format");
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                    }
                    else {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    if(messages[i].isEmail())
                    {
                        continue;
                    }
                    helper.InsertSms(TimeStampConverter.ConvertTimeStampToString(messages[i].getTimestampMillis()),messages[i].getDisplayMessageBody(),
                            messages[i].getMessageBody(), messages[i].getOriginatingAddress());

                }
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
        finally {
            setResultCode(android.app.Activity.RESULT_OK);
        }
    }
}
