package com.tiara.sqlite;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editNama, editKelas, editNohp, editTextnim, editEmail;
    Button btnAddData;
    Button btnViewAll;
    Button btnUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editNama = findViewById(R.id.editText_nama);
        editKelas = findViewById(R.id.editText_kelas);
        editNohp = findViewById(R.id.editText_nohp);
        editTextnim = findViewById(R.id.editTextnim);
        editEmail = findViewById(R.id.editText_email);
        btnAddData = findViewById(R.id.button_add);
        btnViewAll = findViewById(R.id.button_view);
        btnUpdate = findViewById(R.id.button_update);
        btnDelete = findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
        deleteData();
    }

    //fungsi hapus
    public void deleteData() {
        btnDelete.setOnClickListener(
                v -> {

                    int deletedRows = myDb.deleteData(editTextnim.getText().toString());

                    if (deletedRows > 0)

                        Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();

                    else
                        Toast.makeText(MainActivity.this,"Data Failed to Deleted!",Toast.LENGTH_LONG).show();
                }
        );
    }

    //fungsi update
    public void UpdateData() {
        btnUpdate.setOnClickListener(
                v -> {

                    boolean isUpdate = myDb.updateData(editTextnim.getText().toString(),
                            editNama.getText().toString(),
                            editKelas.getText().toString(),
                            editNohp.getText().toString(),
                            editEmail.getText().toString());
                    if(isUpdate)

                        Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();

                    else
                        Toast.makeText(MainActivity.this,"Data Failed to Update",Toast.LENGTH_LONG).show();
                }
        );
    }

    //fungsi tambah
    @SuppressLint("SuspiciousIndentation")
    public void AddData() {
        btnAddData.setOnClickListener(
                v -> {

                    boolean isInserted = myDb.insertData(editTextnim.getText().toString(),
                            editNama.getText().toString(),
                            editKelas.getText().toString(),
                            editNohp.getText().toString(),
                            editEmail.getText().toString());

                    if(isInserted)
                        Toast.makeText(MainActivity.this,"Data Iserted",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this,"Data Not Iserted",Toast.LENGTH_LONG).show();
                                editNama.setText("");
                    editKelas.setText("");
                    editTextnim.setText("");
                    editNohp.setText("");
                    editEmail.setText("");
                }
        );
    }

    //fungsi menampilkan data
    public void viewAll() {
        btnViewAll.setOnClickListener(
                v -> {
                    Cursor res = myDb.getAllData();
                    if(res.getCount() == 0) {
// show message

                        showMessage("Error","Noting Found");

                        return;
                    }

                    StringBuilder buffer = new StringBuilder();

                    while (res.moveToNext() ) {
                        buffer.append("NIM :").append(res.getString(0)).append("\n");
                        buffer.append("NAMA :").append(res.getString(1)).append("\n");
                        buffer.append("KELAS :").append(res.getString(2)).append("\n");
                        buffer.append("NOHP :").append(res.getString(3)).append("\n");
                        buffer.append("EMAIL :").append(res.getString(4)).append("\n\n");
                    }

// show all data

                    showMessage("Data Mahasiswa",buffer.toString());
                }
        );
    }

    //membuat alert dialog
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}