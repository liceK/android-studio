package com.example.tp2_hajjem_mouttajagane;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class HistoricActivity extends AppCompatActivity {
    TextView lastOperationTextView;
    TextInputEditText urlTextID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_historic);
        Intent intent = this.getIntent();
        lastOperationTextView = ((TextView) findViewById(R.id.LastOperationTextID));
        lastOperationTextView.setText(intent.getStringExtra("lastOperation"));
        Button submitButton = findViewById(R.id.SubmitURLButtonID);
        urlTextID = findViewById(R.id.TextInputURLID);
        submitButton.setOnClickListener(view -> {
            submitURL();
        });
        /*Button calculatorButton = findViewById(R.id.calculator);
        Button submitButton = findViewById(R.id.submit);

        calculatorButton.setOnClickListener(view -> openCalculatorActivity());

        submitButton.setOnClickListener(view -> {
            urlEditText = findViewById(R.id.url_input);
            openWebViewActivity();
        });

         */

    }

    protected void submitURL(){
        String url = urlTextID.getText().toString();
        Intent intent = new Intent(this, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
