package com.example.qna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
public class question extends AppCompatActivity {
    TextView txtResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        txtResponse = (TextView) findViewById(R.id.txtResponseId);
    }
    public void GoToURL(String url) {
        new HttpTask().execute(url); // Send HTTP request
        Toast.makeText(this, "Send", Toast.LENGTH_LONG).show(); // Toast a message
    }
    public void btnAnsHandler(View view) {
        // show the web page of the URL of the EditText
        switch (view.getId()) {
            case R.id.buttonA: {
                GoToURL("http://10.27.220.41:9999/clicker/select?choice=a");
                break;
            }
            case R.id.buttonB: {
                GoToURL("http://10.27.220.41:9999/clicker/select?choice=b");
                break;
            }
            case R.id.buttonC: {
                GoToURL("http://10.27.220.41:9999/clicker/select?choice=c");
                break;
            }
            case R.id.buttonD: {
                GoToURL("http://10.27.220.41:9999/clicker/select?choice=d");
                break;
            }
        }
    }
    private class HttpTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strURLs) {
            URL url = null;
            HttpURLConnection conn = null;
            try {
                url = new URL(strURLs[0]);
                conn = (HttpURLConnection) url.openConnection();
                // Get the HTTP response code (e.g., 200 for "OK", 404 for "Not found")
                // and pass a string description in result to onPostExecute(String result)
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {  // 200
                    return "OK (" + responseCode + ")";
                } else {
                    return "Fail (" + responseCode + ")";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // Displays the result of the AsyncTask.
        // The String result is passed from doInBackground().
        @Override
        protected void onPostExecute(String result) {
            txtResponse.setText(result);  // put it on TextView
        }
    }
}