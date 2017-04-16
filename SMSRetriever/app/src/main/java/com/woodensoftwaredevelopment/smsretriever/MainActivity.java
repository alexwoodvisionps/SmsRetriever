package com.woodensoftwaredevelopment.smsretriever;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.woodensoftwaredevelopment.smsretriever.helpers.DbHelper;
import com.woodensoftwaredevelopment.smsretriever.models.SmsModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout ll = (LinearLayout) this.findViewById(R.id.llTable);
        final Button btnSearch = (Button) this.findViewById(R.id.btnSearch);
        final EditText etDate = (EditText) this.findViewById(R.id.etDate);
        final EditText etPhone = (EditText) this.findViewById(R.id.etPhone);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(etDate) && isEmpty(etPhone))
                {
                    Toast.makeText(v.getContext(), "You must provide a From Phone And/Or Date", Toast.LENGTH_LONG).show();
                    return;
                }
                ArrayList<SmsModel> models = new DbHelper(v.getContext()).QueryOldTexts(etPhone.getText().toString(), etDate.getText().toString());
                ll.removeAllViews();
                for(SmsModel sms : models)
                {
                    TextView tv = new TextView(v.getContext());
                    tv.setText(String.format("From: %s - Subject: %s - \nBody: %s \n- Time sent: %s", sms.From, sms.Subject, sms.Body, sms.TimeStamp));
                    ll.addView(tv);
                }
            }
        });
    }
}
