package com.example.py7.appbiodata;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    DBHelper helper;
    EditText TxNik, TxNama, TxNoHp, TxLokasiPeserta, TxWaktuVaksin, TxAlamat;
    Spinner SpJK;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        helper = new DBHelper(this);

        id = getIntent().getLongExtra(DBHelper.row_id, 0);

        TxNik = (EditText)findViewById(R.id.txNik_Add);
        TxNama = (EditText)findViewById(R.id.txNama_Add);
        TxNoHp = (EditText)findViewById(R.id.txNoHp_Add);
        TxLokasiPeserta = (EditText)findViewById(R.id.txLokasiPeserta_Add);
        TxWaktuVaksin = (EditText)findViewById(R.id.txWaktuVaksin_Add);
        TxAlamat = (EditText)findViewById(R.id.txAlamat_Add);
        SpJK = (Spinner)findViewById(R.id.spJK_Add);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        TxWaktuVaksin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                TxWaktuVaksin.setText(dateFormatter.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_add:
                String nik = TxNik.getText().toString().trim();
                String nama = TxNama.getText().toString().trim();
                String nohp = TxNoHp.getText().toString().trim();
                String lokasipeserta = TxLokasiPeserta.getText().toString().trim();
                String waktuvaksin = TxWaktuVaksin.getText().toString().trim();
                String alamat = TxAlamat.getText().toString().trim();
                String jk = SpJK.getSelectedItem().toString().trim();

                ContentValues values = new ContentValues();
                values.put(DBHelper.row_nik, nik);
                values.put(DBHelper.row_nama, nama);
                values.put(DBHelper.row_nohp, nohp);
                values.put(DBHelper.row_lokasipengguna, lokasipeserta);
                values.put(DBHelper.row_waktuvaksin, waktuvaksin);
                values.put(DBHelper.row_alamat, alamat);
                values.put(DBHelper.row_jk, jk);

                if (nik.equals("") || nama.equals("") || nohp.equals("") || lokasipeserta.equals("") || waktuvaksin.equals("") || alamat.equals("")){
                    Toast.makeText(AddActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else{
                    helper.insertData(values);
                    Toast.makeText(AddActivity.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
