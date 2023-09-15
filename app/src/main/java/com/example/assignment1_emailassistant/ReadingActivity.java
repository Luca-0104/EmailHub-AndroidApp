package com.example.assignment1_emailassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class ReadingActivity extends AppCompatActivity {

    //we use shared preference to store data, use editor to write data in to this file
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private Button mBtnBack;    //'Back' button

    //the information gotten from the last activity (EditingActivity)
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String body;

    //the text views that should be initialized with the information we get from the last activity
    private TextView mTvFrom;
    private TextView mTvTo;
    private TextView mTvCc;
    private TextView mTvSubject;
    private TextView mTvBody;

    //the recycler view that contains the email information
    private RecyclerView mRvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        //initialize the shared preference file
        this.pref = getSharedPreferences("data", MODE_PRIVATE);
        //initialize the editor
        editor = pref.edit();

        //extract information in the intent that the last activity transmitted
        Intent intent = getIntent();
        this.from = intent.getStringExtra("from");
        this.to = intent.getStringExtra("to");
        this.cc = intent.getStringExtra("cc");
        this.bcc = intent.getStringExtra("bcc");
        this.subject = intent.getStringExtra("subject");
        this.body = intent.getStringExtra("body");

        //initialize the text views (find them by their id)
//        this.mTvFrom = findViewById(R.id.tv_from_data);
//        this.mTvTo = findViewById(R.id.tv_to_data);
//        this.mTvCc = findViewById(R.id.tv_cc_data);
//        this.mTvSubject = findViewById(R.id.tv_subject_data);
        this.mTvBody = findViewById(R.id.tv_body_data);
//
//        //set the correspond data into the text views
//        this.mTvFrom.setText(this.from);
//        this.mTvTo.setText(this.to);
//        this.mTvCc.setText(this.cc);
//        this.mTvSubject.setText(this.subject);
        this.mTvBody.setText(this.body);

        //When this activity is started, which means
        //the 'send' button on the EditingActivity is clicked.
        //therefore we send the email by calling an email app
        sendEmail();


        //initialize the recyclerView
        mRvInfo = findViewById(R.id.rv_email_info);

        //set the layout manager for this recyclerView
        mRvInfo.setLayoutManager(new LinearLayoutManager(ReadingActivity.this));

        //set an adapter for this recyclerView
        mRvInfo.setAdapter(new InfoReadingAdapter(ReadingActivity.this, new String[]{this.from, this.to, this.cc, this.subject}));

        //set the decorator line of each item(divider line)
        mRvInfo.addItemDecoration(new MyDecoration());

        //initialize the Back Button
        mBtnBack = findViewById(R.id.btn_back);
        //if the back Button is clicked
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear the data in the SharedPreferences file when the back button (our own button) is pressed
                editor.clear();
                editor.apply();

                //to call another activity (back to EditingActivity) using explicit intent
                Intent intent = new Intent(ReadingActivity.this, EditingActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * To call another app to send the email by using the implicit intent
     */
    private void sendEmail(){
        //set the action of intent as 'ACTION_SENDTO'
        Intent intent = new Intent(Intent.ACTION_SENDTO);

//        intent.putExtra(Intent.EXTRA_CHOSEN_COMPONENT_INTENT_SENDER, from);

        //set the recipient email
        intent.setData(Uri.parse("mailto:" + this.to));

        //set the cc emails
        intent.putExtra(Intent.EXTRA_CC, new String[]{this.cc});

        //set the bcc emails
        intent.putExtra(Intent.EXTRA_BCC,  new String[]{this.bcc});

        //set the subject of the email
        intent.putExtra(Intent.EXTRA_SUBJECT, this.subject);

        //set the email body
        intent.putExtra(Intent.EXTRA_TEXT, this.body);

        //start this intent
        startActivity(intent);
    }

    /**
     * Using the 'JavaMail for Android' to send a email using our own app
     */
    private void sendEmailWithThisApp(String from, String to, String subject, String body){

        //set the email server, for example 'smtp.163.com', 'smtp.qq.com', 'smtp.gmail.com'
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.163.com");
        Session session = Session.getInstance(props, null);

        try {
            //create a MimeMessage object using the session we got
            MimeMessage msg = new MimeMessage(session);
            //set the sender email
            msg.setFrom(from);
            //set the recipient email
            msg.setRecipients(Message.RecipientType.TO, to);
            //set the subject of the email
            msg.setSubject(subject);
            //set the sending date
            msg.setSentDate(new Date());
            //set the email body
            msg.setText(body);
            //send the email and give the authorization code
            Transport.send(msg, from, "QFQHXLKOHNSMXMEB");

        } catch (MessagingException mex) {
            System.out.println("Send failed, exception: " + mex);
        }
    }

    //a inner class for declaring the decoration of each info item
    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //a divider line under each item
            outRect.set(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.dividerHeight));
        }
    }

}
