package com.example.tp2_hajjem_mouttajagane;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    TextView mathExpressionText;
    TextView mathResultText;
    private ProgressBar progress;
    private Handler handler;
    double resultat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_calculatrice);
        mathExpressionText = (TextView) findViewById(R.id.expressionTextID);
        mathResultText = (TextView) findViewById(R.id.resultTextID);
        resultat = 0;
        progress = findViewById(R.id.progressBar1);
        handler = new Handler();


        LinearLayout buttonsLinearLayout = findViewById(R.id.buttonsLinearLayoutID);
        LinearLayout equalLinearLayout = new LinearLayout(this);
        equalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        equalLinearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));

        Button equalButton = new Button(this);
        LinearLayout.LayoutParams equalButtonLayoutParams = new LinearLayout.LayoutParams(150, LinearLayout.LayoutParams.MATCH_PARENT);
        equalButtonLayoutParams.weight = 1;
        equalButtonLayoutParams.width = 0;
        equalButton.setLayoutParams(equalButtonLayoutParams);
        equalButton.setText("=");
        //equalButton.setBackgroundColor(0xFFFF0000);
        equalButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        equalButtonHandler();
                    }
                });

        equalButton.setId(R.id.equalButtonID);
        equalLinearLayout.addView(equalButton);
        buttonsLinearLayout.addView(equalLinearLayout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()){
            case R.id.historic:
                intent = new Intent(this, HistoricActivity.class);
                intent.putExtra("lastOperation",mathExpressionText.getText() );
                break;
            case R.id.reset:
                intent = new Intent(this, hereismyappActivity.class);
                break;
            default:
                intent = new Intent(this, HistoricActivity.class);
                intent.putExtra("lastOperation",mathExpressionText.getText() );
                break;
        }

        startActivity(intent);
        return true;
    }

    public void calculatriceButtonHandler(View view){
        switch (view.getId()){
            case R.id.zeroButtonID:
                mathExpressionText.setText(mathExpressionText.getText() + "0");
                break;
            case R.id.oneButtonID:
                mathExpressionText.setText(mathExpressionText.getText() + "1");
                break;
            case R.id.twoButtonID:
                mathExpressionText.setText(mathExpressionText.getText() + "2");
                break;
            case R.id.threeButtonID:
                mathExpressionText.setText(mathExpressionText.getText() + "3");
                break;
            case R.id.fourButtonID:
                mathExpressionText.setText(mathExpressionText.getText() + "4");
                break;
            case R.id.fiveButtonID:
                mathExpressionText.setText(mathExpressionText.getText() + "5");
                break;
            case R.id.sixButtonID:
                mathExpressionText.setText(mathExpressionText.getText() + "6");
                break;
            case R.id.sevenButtonID:
                mathExpressionText.setText(mathExpressionText.getText() + "7");
                break;
            case R.id.eightButtonID:
                mathExpressionText.setText(mathExpressionText.getText() + "8");
                break;
            case R.id.nineButtonID:
                mathExpressionText.setText(mathExpressionText.getText() + "9");
                break;
            case R.id.additionButtonID:
                mathExpressionText.setText(mathExpressionText.getText() + "+");
                break;
            case R.id.substractionButtonID:
                mathExpressionText.setText(mathExpressionText.getText() + "-");
                break;
            case R.id.multiplicationButtonID:
                mathExpressionText.setText(mathExpressionText.getText() + "*");
                break;
            case R.id.divisionButtonID:
                mathExpressionText.setText(mathExpressionText.getText() + "/");
                break;
            case R.id.equalButtonID:
                double result = 0;
                if(mathExpressionText.getText().toString().contains("+")){
                    result = Integer.valueOf(mathExpressionText.getText().toString().split("\\+")[0]) + Integer.valueOf(mathExpressionText.getText().toString().split("\\+")[1]);
                }
                else if(mathExpressionText.getText().toString().contains("-")){
                    result = Integer.valueOf(mathExpressionText.getText().toString().split("-")[0]) - Integer.valueOf(mathExpressionText.getText().toString().split("-")[1]);

                }
                else if(mathExpressionText.getText().toString().contains("*")){
                    result = Integer.valueOf(mathExpressionText.getText().toString().split("\\*")[0]) * Integer.valueOf(mathExpressionText.getText().toString().split("\\*")[1]);

                }
                else if(mathExpressionText.getText().toString().contains("/")){
                    result = Integer.valueOf(mathExpressionText.getText().toString().split("/")[0]) / Integer.valueOf(mathExpressionText.getText().toString().split("/")[1]);

                }

                mathResultText.setText(String.valueOf(result));
                break;

        }
    }

    public void equalButtonHandler(){


        displayResult dr = new displayResult();
        dr.execute();
        /*
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(mathExpressionText.getText().toString().contains("+")){
                    resultat = Integer.valueOf(mathExpressionText.getText().toString().split("\\+")[0]) + Integer.valueOf(mathExpressionText.getText().toString().split("\\+")[1]);
                }
                else if(mathExpressionText.getText().toString().contains("-")){
                    resultat = Integer.valueOf(mathExpressionText.getText().toString().split("-")[0]) - Integer.valueOf(mathExpressionText.getText().toString().split("-")[1]);

                }
                else if(mathExpressionText.getText().toString().contains("*")){
                    resultat = Integer.valueOf(mathExpressionText.getText().toString().split("\\*")[0]) * Integer.valueOf(mathExpressionText.getText().toString().split("\\*")[1]);

                }
                else if(mathExpressionText.getText().toString().contains("/")){
                    resultat = Integer.valueOf(mathExpressionText.getText().toString().split("/")[0]) / Integer.valueOf(mathExpressionText.getText().toString().split("/")[1]);

                }
                for (int i = 0; i <= 10; i++) {
                    final int value = i;
                    // simulate a slow network !
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e){
                        e.printStackTrace(); }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progress.setProgress(value);

                        }

                    });
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mathResultText.setText(String.valueOf(resultat));
                    }

                });

            }
        };
        new Thread(runnable).start();
        */


    }

    private class displayResult extends AsyncTask<String, Integer, Long> {
        protected void onPreExecute(){

        }
        protected Long doInBackground(String... urls) {

            Socket clientSocket = null;
            try {
                clientSocket = new Socket("10.0.2.2", 9876);
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());

                Double firstDigit = Double.valueOf(mathExpressionText.getText().toString().split("[-+*/]")[0]);
                Double secondDigit = Double.valueOf(mathExpressionText.getText().toString().split("[-+*/]")[1]);
                char op = '+';

                if(mathExpressionText.getText().toString().contains("+")){
                    op = '+';
                }
                else if(mathExpressionText.getText().toString().contains("-")){
                    op = '-';
                }
                else if(mathExpressionText.getText().toString().contains("*")){
                    op = '*';
                }
                else if(mathExpressionText.getText().toString().contains("/")){
                    op = '/';
                }

                out.writeDouble(firstDigit);
                out.writeChar(op);
                out.writeDouble(secondDigit);
                out.flush();

                resultat = in.readDouble();

            } catch (IOException e) {
                e.printStackTrace();
            }

            int count = 10;
            long totalSize = 0;

            for (int i = 1; i <= count; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress((int) ((i / (float) count) * 10));

            }


            return totalSize;


        }
        protected void onProgressUpdate(Integer... p) {
            progress.setProgress(p[0]);
        }
        protected void onPostExecute(Long result) {
            mathResultText.setText(String.valueOf(resultat));
        }

     
    }


}