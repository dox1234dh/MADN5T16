package com.example.mad_n5_t16;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mad_n5_t16.Model.History;
import com.example.mad_n5_t16.Model.HistoryAdapter;
import com.example.mad_n5_t16.model_class.DatabaseHelper;
import com.example.mad_n5_t16.user.MainActivityUser;

import java.util.ArrayList;
import java.util.Date;

public class BloodDonationHistoryActivity extends AppCompatActivity {
    private ListView lvDonationhistory;
    HistoryAdapter historyAdapter;
    private History[] histories;
    TextView txtHoVaTen;
    ImageView marker, home, heart, history, infor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donation_history);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        lvDonationhistory = (ListView) findViewById(R.id.lvDonationhistory);
        txtHoVaTen = findViewById(R.id.txtHoVaTen);
        txtHoVaTen.setText("Lịch sử hiến máu");

        home = findViewById(R.id.home);
        home.setImageResource(R.drawable.home_1);
        marker = findViewById(R.id.marker);
        marker.setImageResource(R.drawable.marker_1);
        heart = findViewById(R.id.heart_plus);
        heart.setImageResource(R.drawable.heart_plus_1);
        history = findViewById(R.id.order_history);
        history.setImageResource(R.drawable.order_history_2);
        infor = findViewById(R.id.guest_male);
        infor.setImageResource(R.drawable.guest_male_1);


        ArrayList<History> listHistory = new ArrayList<>();
        DatabaseHelper dbh = new DatabaseHelper(getBaseContext());

        //Sau nay lay gia tri tu nguoi dang nhap
        Intent intent=getIntent();

        int idNguoiHienMau = intent.getIntExtra("id",1);

        listHistory = dbh.nam_getLichSuHienMauByIdNguoiHienMau(idNguoiHienMau);
        int len = listHistory.size();
        histories = new History[len];
        for(int i = 0; i < histories.length; i++) {
            int time = listHistory.get(i).getNumber();
            int amount = listHistory.get(i).getAmount();
            String location = listHistory.get(i).getLocation();
            String date = listHistory.get(i).getDonationDate();
            histories[i] = new History(time, amount , location ,date );
        }
        if(histories.length > 0) {
            historyAdapter = new HistoryAdapter(this, histories);
            lvDonationhistory.setAdapter(historyAdapter);
        }else{
            Toast.makeText(this, "Bạn chưa đi hiến máu lần nào", Toast.LENGTH_SHORT).show();
        }

        marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goMarker = new Intent(BloodDonationHistoryActivity.this, ThongBaoActivity.class);
                startActivity(goMarker);
            }
        });
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHeart = new Intent(BloodDonationHistoryActivity.this, MainActivity.class);
                startActivity(goHeart);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHome = new Intent(BloodDonationHistoryActivity.this, MainActivityUser.class);
                goHome.putExtra("id", idNguoiHienMau);
                startActivity(goHome);
            }
        });
        infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goInfor = new Intent(BloodDonationHistoryActivity.this, MainActivity.class);
                startActivity(goInfor);
            }
        });
    }
}