package com.example.qna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class question extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
    }
    public void GoToURL(String url) {
        Uri uri = Uri.parse(url);
        Intent intent= new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
    public void btnAnsHandler(View view) {
        // show the web page of the URL of the EditText
        switch (view.getId()) {
            case R.id.buttonA: {
                GoToURL("http://10.27.206.75:9999/clicker/select?choice=a");
                break;
            }
            case R.id.buttonB: {
                GoToURL("http://10.27.206.75:9999/clicker/select?choice=b");
                break;
            }
            case R.id.buttonC: {
                GoToURL("http://10.27.206.75:9999/clicker/select?choice=c");
                break;
            }
            case R.id.buttonD: {
                GoToURL("http://10.27.206.75:9999/clicker/select?choice=d");
                break;
            }
        }
    }
}