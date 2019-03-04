package com.example.shosho.dietfood.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dietfoooood.R;

public class RequestedSucess extends AppCompatActivity {

    TextView TextDone;
    SharedPreferences.Editor sharesss;
    TextView txtorder,T_Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_sucess);
        txtorder=findViewById(R.id.ord);
        TextDone=findViewById(R.id.done);
        txtorder.setText(getIntent().getStringExtra("message"));
        TextDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent inty=new Intent(RequestedSucess.this,HomeActivity.class);
//                inty.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                inty.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inty);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent inty=new Intent(RequestedSucess.this,HomeActivity.class);
        startActivity(inty);
        finish();

    }
}
