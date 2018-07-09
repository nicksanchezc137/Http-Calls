package com.httpcalls.nderi.httpcalls;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.httpcalls.nderi.http.Http;

public class MainActivity extends AppCompatActivity {
Http http;
Button btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        http = new Http("http://vortech.co.ke/noqueue/saverequest.php", 1500, "POST");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                http.setValue("idno", "3236654");
                http.setValue("location", "Kenya");
                http.setValue("service_type","Sex");
                http.setValue("date", "23/3/2019");
                http.setValue("time","2:30");
                http.execute();
                Toast.makeText(MainActivity.this, "Message: "+ http.getResponse() + "\nError: "+ http.error_message, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
