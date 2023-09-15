package com.example.assignment1_emailassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditingActivity extends AppCompatActivity implements View.OnClickListener {

    //we use shared preference to store data, use editor to write data in to this file
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private EditText mEtFrom;   //'from' email
    private EditText mEtTo;     //'to' email
    private EditText mEtCc;     //'bc' email
    private EditText mEtBcc;    //'bcc' email
    private EditText mEtSubject;//'subject'
    private EditText mEtBody;   //'email body'

    private Button mBtnSend;    //'send' button
    private Button mBtnClear;   //'clear' button

    //store the information as the String type instance variables
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);

        //initialize the shared preference file
        this.pref = getSharedPreferences("data", MODE_PRIVATE);
        //initialize the editor
        editor = pref.edit();

        //initialize the Buttons
        mBtnSend = findViewById(R.id.btn_send);
        mBtnClear = findViewById(R.id.btn_clear);

        //initialize the EditTexts
        mEtFrom = findViewById(R.id.et_from);
        mEtTo = findViewById(R.id.et_to);
        mEtCc = findViewById(R.id.et_cc);
        mEtBcc = findViewById(R.id.et_bcc);
        mEtSubject = findViewById(R.id.et_subject);
        mEtBody = findViewById(R.id.et_email_body);

        //if there are any existing data, we will put then into the blank as the app created
        //get the data from the SharedPreferences file
        this.from = pref.getString("from", "");
        this.to = pref.getString("to", "");
        this.cc = pref.getString("cc", "");
        this.bcc = pref.getString("bcc", "");
        this.subject = pref.getString("subject", "");
        this.body = pref.getString("body", "");
        //put the data into the EditText blanks
        mEtFrom.setText(from);
        mEtTo.setText(to);
        mEtCc.setText(cc);
        mEtBcc.setText(bcc);
        mEtSubject.setText(subject);
        mEtBody.setText(body);

        //set the on click listener of buttons
        mBtnSend.setOnClickListener(this);
        mBtnClear.setOnClickListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //when activity is stopped
        //first, store the current information into the instance variables
        storeData();
        //then, add the data into the shared preference file
        editor.putString("from", this.from);
        editor.putString("to", this.to);
        editor.putString("cc", this.cc);
        editor.putString("bcc", this.bcc);
        editor.putString("subject", this.subject);
        editor.putString("body", this.body);
        //submit the editing of the file
        editor.apply();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //if the 'send' button is clicked
            case R.id.btn_send:
                //store the information as the String type instance variables
                storeData();

                //checking whether the necessary data are given
                if (this.from.isEmpty()){
                    Toast.makeText(EditingActivity.this, "you should give a sender email address", Toast.LENGTH_SHORT).show();

                }else if (this.to.isEmpty()){
                    Toast.makeText(EditingActivity.this, "you should give a recipient address", Toast.LENGTH_SHORT).show();

                }else if(this.subject.isEmpty()){
                    Toast.makeText(EditingActivity.this, "you should give a subject", Toast.LENGTH_SHORT).show();

                }else if(this.body.isEmpty()){
                    Toast.makeText(EditingActivity.this, "email content should not be empty", Toast.LENGTH_SHORT).show();

                }else{
                    //to call another activity (ReadingActivity) using explicit intent
                    Intent intent = new Intent(EditingActivity.this, ReadingActivity.class);

                    //transmit the data to the next activity (ReadingActivity)
                    intent.putExtra("from", this.from);
                    intent.putExtra("to", this.to);
                    intent.putExtra("cc", this.cc);
                    intent.putExtra("bcc", this.bcc);
                    intent.putExtra("subject", this.subject);
                    intent.putExtra("body", this.body);

                    //clear the data in the SharedPreferences file
                    editor.clear();

                    //go into the next activity
                    startActivity(intent);
                }

                break;

            //if the 'clear' button is clicked
            case R.id.btn_clear:
                Toast.makeText(EditingActivity.this, "data cleared", Toast.LENGTH_SHORT).show();

                //clear the data in the EditText blanks and also set the instance variables to empty
                clearBlank();

                //clear the data in the SharedPreferences file
                editor.clear();

                break;
        }
        //submit the editing of the SharedPreferences file
        editor.apply();
    }

    /**
     * store the information in EditTexts as the String type instance variables
     */
    private void storeData(){
        this.from = mEtFrom.getText().toString();
        this.to = mEtTo.getText().toString();
        this.cc = mEtCc.getText().toString();
        this.bcc = mEtBcc.getText().toString();
        this.subject = mEtSubject.getText().toString();
        this.body = mEtBody.getText().toString();
    }

    /**
     * clear the data in the EditText blanks
     * and also set the instance variables to empty
     */
    private void clearBlank(){
        //clear the data in all the EditText
        mEtFrom.setText("");
        mEtTo.setText("");
        mEtCc.setText("");
        mEtBcc.setText("");
        mEtSubject.setText("");
        mEtBody.setText("");

        //change the correspond strings back to empty strings
        from = "";
        to = "";
        cc = "";
        bcc = "";
        subject = "";
        body = "";
    }

}