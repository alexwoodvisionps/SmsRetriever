package com.woodensoftwaredevelopment.smsretriever.models;

/**
 * Created by 2012a1278 on 4/15/17.
 */

public class SmsModel {
    public String TimeStamp, Body, Subject, From;
    public SmsModel(String timestamp, String subject, String body, String fromAddress)
    {
        this.TimeStamp = timestamp;
        this.Subject = subject;
        this.Body = body;
        this.From = fromAddress;
    }
}
