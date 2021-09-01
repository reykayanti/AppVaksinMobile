package com.example.py7.appbiodata;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
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

public class EditActivity extends AppCompatActivity {

    DBHelper helper;
    EditText TxNik, TxNama, TxNoHp, TxLokasiPeserta, TxWaktuVaksin, TxAlamat;
    Spinner SpJK;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        helper = new DBHelper(this);

        id = getIntent().getLongExtra(DBHelper.row_id, 0);

        TxNik = (EditText)findViewById(R.id.txNik_Edit);
        TxNama = (EditText)findViewById(R.id.txNama_Edit);
        TxNoHp = (EditText)findViewById(R.id.txNoHp_Edit);
        TxLokasiPeserta = (EditText)findViewById(R.id.txLokasiPeserta_Edit);
        TxWaktuVaksin = (EditText)findViewById(R.id.txWaktuVaksin_Edit);
        TxAlamat = (EditText)findViewById(R.id.txAlamat_Edit);
        SpJK = (Spinner)findViewById(R.id.spJK_Edit);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        TxWaktuVaksin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        getData();
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

    private void getData(){
        Cursor cursor = helper.oneData(id);
        if(cursor.moveToFirst()){
            String nik = cursor.getString(cursor.getColumnIndex(DBHelper.row_nik));
            String nama = cursor.getString(cursor.getColumnIndex(DBHelper.row_nama));
            String nohp = cursor.getString(cursor.getColumnIndex(DBHelper.row_nohp));
            String lokasipeserta = cursor.getString(cursor.getColumnIndex(DBHelper.row_lokasipengguna));
            String jk = cursor.getString(cursor.getColumnIndex(DBHelper.row_jk));
            String waktuvaksin = cursor.getString(cursor.getColumnIndex(DBHelper.row_waktuvaksin));
            String alamat = cursor.getString(cursor.getColumnIndex(DBHelper.row_alamat));

            TxNik.setText(nik);
            TxNama.setText(nama);
            TxNoHp.setText(nohp);

            if (jk.equals("Laki-laki")){
                SpJK.setSelection(0);
            }else if(jk.equals("Perempuan")){
                SpJK.setSelection(1);
            }

            TxLokasiPeserta.setText(lokasipeserta);
            TxWaktuVaksin.setText(waktuvaksin);
            TxAlamat.setText(alamat);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_edit:
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
                    Toast.makeText(EditActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT);
                }else{
                    helper.updateData(values, id);
                    Toast.makeText(EditActivity.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        switch (item.getItemId()){
            case R.id.delete_edit:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                builder.setMessage("Data ini akan dihapus.");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        helper.deleteData(id);
                        Toast.makeText(EditActivity.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
