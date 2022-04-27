package com.example.mad_n5_t16.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mad_n5_t16.R;
import com.example.mad_n5_t16.model_class.DatabaseHelper;
import com.example.mad_n5_t16.model_class.LichHienMau;
import com.example.mad_n5_t16.model_class.ThoiGian;

import java.util.ArrayList;

public class DangKyHienMauActivity extends AppCompatActivity {
    ArrayList<ItemModelDangKyHienMauActivity> ls;
    DangKyHienMauItems adapter;
    ListView listView;
    DatabaseHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dotv_layout_dangkyhienmau);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        TextView toolbar = findViewById(R.id.txtHoVaTen);
        toolbar.setText("Chọn lịch hiến máu");
        ImageView markerNoti = findViewById(R.id.heart_plus);
        markerNoti.setImageResource(R.drawable.heart_plus_2);
        ls = new ArrayList<>();
        dbh = new DatabaseHelper(getBaseContext());
        ArrayList<LichHienMau> lsDiaDiem = dbh.do_laydsdiadiemhienmau();
        ArrayList<LichHienMau> lsCount = dbh.do_getCountnguoidangky();
        // Lay data
        for(int i = 0 ;i<lsDiaDiem.size();++i){
            for(int j=0;j<lsCount.size();++j)
                if(lsDiaDiem.get(i).getDiaDiem().getId() == lsCount.get(j).getDiaDiem().getId()
                && lsDiaDiem.get(i).getThoiGian().getId() == lsCount.get(j).getThoiGian().getId()){
                    lsDiaDiem.get(i).setGhiChu(lsCount.get(j).getGhiChu());
                    break;
                }
            ls.add(new ItemModelDangKyHienMauActivity(lsDiaDiem.get(i).getDiaDiem().getTenDiaDiem(),
                    "Thời gian :  " + lsDiaDiem.get(i).getThoiGian().getGioBatDau() +" - " + lsDiaDiem.get(i).getThoiGian().getGioKetThuc()
                            +" "+ lsDiaDiem.get(i).getThoiGian().getNgay(),
                    "Số người đã đăng ký : "+ lsDiaDiem.get(i).getGhiChu()));
        }

        adapter = new DangKyHienMauItems(ls);
        listView = findViewById(R.id.dotv_list_dangkyhienmau);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DangKyHienMauActivity.this, DienThongTinDangKyActivity.class);
//                String message = "abc";
//                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });
    }
}

class ItemModelDangKyHienMauActivity{
    String diadiem,thoigian, songuoidangky;

    public ItemModelDangKyHienMauActivity(String diadiem, String thoigian, String songuoidangky) {
        this.diadiem = diadiem;
        this.thoigian = thoigian;
        this.songuoidangky = songuoidangky;
    }

    public String getDiadiem() {
        return diadiem;
    }

    public void setDiadiem(String diadiem) {
        this.diadiem = diadiem;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getSonguoidangky() {
        return songuoidangky;
    }

    public void setSonguoidangky(String songuoidangky) {
        this.songuoidangky = songuoidangky;
    }
}

class DangKyHienMauItems extends BaseAdapter {

    final ArrayList<ItemModelDangKyHienMauActivity> ls;

    public DangKyHienMauItems(ArrayList<ItemModelDangKyHienMauActivity> ls) {
        this.ls = ls;
    }

    @Override
    public int getCount() {
        return ls.size();
    }

    @Override
    public Object getItem(int i) {
        return ls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewItem1;
        if (view == null) {
            viewItem1 = View.inflate(viewGroup.getContext(), R.layout.dotv_layout_items_dangkyhienmau, null);
        } else viewItem1 = view;

        ItemModelDangKyHienMauActivity items = (ItemModelDangKyHienMauActivity) getItem(i);
        ((TextView)viewItem1.findViewById(R.id.dotv_diadiem_dangkyhienmau)).setText(items.getDiadiem());
        ((TextView)viewItem1.findViewById(R.id.dotv_thoigian_dangkyhienmau)).setText(items.getThoigian());
        ((TextView)viewItem1.findViewById(R.id.dotv_luongnguoi_dangkyhienmau)).setText(items.getSonguoidangky());
        return viewItem1;
    }
}