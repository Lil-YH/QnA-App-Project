package com.example.qna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    protected EditText txtUsername;
    protected EditText txtRmCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * Callback when the user click the "GO" button
     */
    public void btnGoHandler(View view) {
        // show the web page of the URL of the EditText
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtRmCode = (EditText) findViewById(R.id.txtRmCode);
        if( TextUtils.isEmpty(txtUsername.getText())) {
            txtUsername.setError("Username is required!");
        } else if( TextUtils.isEmpty(txtRmCode.getText()) ) {
            txtRmCode.setError("Room Code is required!");
        } else {
            Intent i = new Intent(this, question.class);
            i.putExtra("username",txtUsername.getText().toString());
            startActivity(i);
        }
    }
}