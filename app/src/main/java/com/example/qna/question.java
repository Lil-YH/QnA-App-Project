package com.example.qna;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

public class question extends AppCompatActivity {
    TextView txtResponse, txtQn, txtInfo, txtTime;
    ImageButton nextBtn, backBtn;
    Button btnA, btnB, btnC, btnD;
    int currentQuestionIndex = 1;
    String username, roomCode;
    char lastPicked;
    Toast currentToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        txtResponse = (TextView) findViewById(R.id.txtResponseId);
        txtQn = (TextView) findViewById(R.id.answer_text_view);
        txtInfo = (TextView) findViewById(R.id.infoTextView) ;
        txtTime = (TextView) findViewById(R.id.timerTextView) ;
        nextBtn = (ImageButton) findViewById(R.id.next_button);
        backBtn = (ImageButton) findViewById(R.id.prev_button);
        btnA = (Button) findViewById(R.id.buttonA);
        btnB = (Button) findViewById(R.id.buttonB);
        btnC = (Button) findViewById(R.id.buttonC);
        btnD = (Button) findViewById(R.id.buttonD);
        username = getIntent().getExtras().getString("username");
        roomCode = getIntent().getExtras().getString("roomCode");
        backBtn.setVisibility(View.INVISIBLE);
        txtQn.setText("Question " + currentQuestionIndex);
        txtInfo.setText("Welcome " + username + "!\nYou are currently in room: " + roomCode);
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtTime.setText("Seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                txtTime.setText("DONE! Please exit to menu");
                txtTime.setTextColor(Color.parseColor("#DFD2D1"));
                btnA.setVisibility(View.INVISIBLE);
                btnB.setVisibility(View.INVISIBLE);
                btnC.setVisibility(View.INVISIBLE);
                btnD.setVisibility(View.INVISIBLE);
                nextBtn.setVisibility(View.INVISIBLE);
                backBtn.setVisibility(View.INVISIBLE);
                txtQn.setText("TIME'S UP!");
            }
        }.start();
    }
    public void GoToURL(String url) {
        new HttpTask().execute(url); // Send HTTP request
        if (currentToast != null){
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, "Answer for question " + currentQuestionIndex + " is now " + lastPicked, Toast.LENGTH_SHORT); // Toast a message
        currentToast.show();
    }
    public void btnAnsHandler(View view) {
        // show the web page of the URL of the EditText
        switch (view.getId()) {
            case R.id.buttonA: {
                lastPicked = 'A';
                GoToURL("http://10.27.133.246:9999/clicker/select?choice=a&questionNo=" + currentQuestionIndex + "&username=" + username);
                break;
            }
            case R.id.buttonB: {
                lastPicked = 'B';
                GoToURL("http://10.27.133.246:9999/clicker/select?choice=b&questionNo=" + currentQuestionIndex + "&username=" + username);
                break;
            }
            case R.id.buttonC: {
                lastPicked = 'C';
                GoToURL("http://10.27.133.246:9999/clicker/select?choice=c&questionNo=" + currentQuestionIndex + "&username=" + username);
                break;
            }
            case R.id.buttonD: {
                lastPicked = 'D';
                GoToURL("http://10.27.133.246:9999/clicker/select?choice=d&questionNo=" + currentQuestionIndex + "&username=" + username);
                break;
            }
            case R.id.next_button: {
                if (currentQuestionIndex < 6) {
                    currentQuestionIndex
                            = currentQuestionIndex + 1;
                    if (currentQuestionIndex == 5) {
                        nextBtn.setVisibility(
                                View.INVISIBLE);
                    }
                    if (currentQuestionIndex < 5 && currentQuestionIndex > 1) {
                        nextBtn.setVisibility(View.VISIBLE);
                        backBtn.setVisibility(View.VISIBLE);
                    }
                    updateQuestion();
                }
                break;
            }
            case R.id.prev_button: {
                if (currentQuestionIndex > 1) {
                    currentQuestionIndex
                            = currentQuestionIndex - 1;
                    if (currentQuestionIndex == 1) {
                        backBtn.setVisibility(
                                View.INVISIBLE);
                    }
                    if (currentQuestionIndex < 5 && currentQuestionIndex > 1) {
                        backBtn.setVisibility(View.VISIBLE);
                        nextBtn.setVisibility(View.VISIBLE);
                    }
                    updateQuestion();
                }
                break;
            }
            case R.id.buttonExit: {
                exitToMenu();
            }

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateQuestion()
    {
        Log.d("Current",
                "onClick: " + currentQuestionIndex);

        txtQn.setText("Question " + currentQuestionIndex);
        // setting the textview with new question


    }
    private void exitToMenu() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
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