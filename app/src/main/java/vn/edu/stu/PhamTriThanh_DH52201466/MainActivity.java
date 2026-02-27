package vn.edu.stu.PhamTriThanh_DH52201466;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import vn.edu.stu.PhamTriThanh_DH52201466.Util.DBConfigUtil;
import vn.edu.stu.PhamTriThanh_DH52201466.adapter.TypeAdapter;
import vn.edu.stu.PhamTriThanh_DH52201466.dao.DbHelper;
import vn.edu.stu.PhamTriThanh_DH52201466.model.Hepokemon;

public class MainActivity extends AppCompatActivity {

    EditText edtMaHe, edtTenHe;
    Button btnSave, btnClear;
    ListView lvHePokemon;

    ArrayList<Hepokemon> dsHe;
    TypeAdapter adapter;
    DbHelper dbHelper;

    Hepokemon heDangChon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBConfigUtil.copyDatabaseFromAssets(this);
        dbHelper = new DbHelper(this);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addControls();
        addEvents();
        hienThiDanhSach();
    }

    private void addControls() {
        edtMaHe = findViewById(R.id.edtMaHe);
        edtTenHe = findViewById(R.id.edtTenHe);
        btnSave = findViewById(R.id.btnThem);
        btnClear = findViewById(R.id.btnXoaTrang);
        lvHePokemon = findViewById(R.id.lvHePokemon);
        dbHelper = new DbHelper(this);
    }

    private void hienThiDanhSach() {
        dsHe = dbHelper.getAllHe();
        for (Hepokemon he : dsHe) {
            String keyName = he.getTenHe();
            int resId = getResources().getIdentifier(keyName, "string", getPackageName());
            if (resId != 0) {
                String tenDaDich = getString(resId);
                he.setTenHe(tenDaDich);
            }
        }
        adapter = new TypeAdapter(this, R.layout.item_type, dsHe);
        lvHePokemon.setAdapter(adapter);
    }

    private void addEvents() {
        btnSave.setOnClickListener(v -> {
            String idStr = edtMaHe.getText().toString().trim();
            String tenNhapVao = edtTenHe.getText().toString().trim();
            if (idStr.isEmpty() || tenNhapVao.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ ID và Tên!", Toast.LENGTH_SHORT).show();
                return;
            }
            String tenLuuDatabase = convertNameToKey(tenNhapVao);
            boolean isDuplicateName = false;
            if (heDangChon == null) {
                if (dbHelper.checkTenHeExists(tenLuuDatabase)) isDuplicateName = true;
            } else {
                if (!tenLuuDatabase.equalsIgnoreCase(heDangChon.getTenHe()) && dbHelper.checkTenHeExists(tenLuuDatabase)) {
                    isDuplicateName = true;
                }
            }

            if (isDuplicateName) {
                Toast.makeText(this, "Lỗi: Tên hệ này đã tồn tại!", Toast.LENGTH_LONG).show();
                return;
            }
            if (heDangChon == null) {
                String id = idStr;
                boolean kq = dbHelper.addHe(id, tenLuuDatabase);
                if (kq) {
                    Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    hienThiDanhSach();
                    resetForm();
                } else {
                    Toast.makeText(this, "Lỗi: Trùng Mã Hệ!", Toast.LENGTH_LONG).show();
                }
            }
            else {
                heDangChon.setTenHe(tenLuuDatabase);
                dbHelper.updateHe(heDangChon);
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                hienThiDanhSach();
                resetForm();
            }
        });
        btnClear.setOnClickListener(v -> resetForm());
        lvHePokemon.setOnItemClickListener((parent, view, position, id) -> {
            heDangChon = dsHe.get(position);
            edtMaHe.setText(String.valueOf(heDangChon.getId()));
            edtTenHe.setText(heDangChon.getTenHe());
            edtMaHe.setEnabled(false);
            btnSave.setText(R.string.btn_capnhathe);
        });
        lvHePokemon.setOnItemLongClickListener((parent, view, position, id) -> {
            Hepokemon he = dsHe.get(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xác nhận");
            builder.setMessage("Xóa hệ " + he.getTenHe() + "?");
            builder.setPositiveButton("Có", (dialog, which) -> {
                if (dbHelper.checkHeHasPokemon(he.getId())) {
                    Toast.makeText(this, "Không được xóa! Hệ này đang có Pokemon.", Toast.LENGTH_LONG).show();
                }
                else {
                    dbHelper.deleteHe(he.getId());
                    hienThiDanhSach();
                    resetForm();
                }
            });
            builder.setNegativeButton("Không", null);
            builder.show();
            return true;
        });
    }
    private String convertNameToKey(String inputName) {
        String lowerName = inputName.toLowerCase();
        if (lowerName.equals("ground") || lowerName.equals("đất") || lowerName.equals("hệ đất")) {
            return "type_ground";
        }
        if (lowerName.equals("fire") || lowerName.equals("lửa")) {
            return "type_fire";
        }
        if (lowerName.equals("water") || lowerName.equals("nước")) {
            return "type_water";
        }
        if (lowerName.equals("grass") || lowerName.equals("cỏ")) {
            return "type_grass";
        }
        if (lowerName.equals("electric") || lowerName.equals("điện")) {
            return "type_electric";
        }
        if (lowerName.equals("rock") || lowerName.equals("đá")) {
            return "type_rock";
        }
        if (lowerName.equals("fighting") || lowerName.equals("giác đấu")) {
            return "type_fighting";
        }
        return inputName;
    }
    private void resetForm() {
        edtMaHe.setText("");
        edtTenHe.setText("");
        edtMaHe.setEnabled(true);
        heDangChon = null;
        btnSave.setText("Save");
        edtMaHe.requestFocus();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_ql_pokemon) {

             Intent intent = new Intent(MainActivity.this, MainActivity2.class);
             startActivity(intent);
            Toast.makeText(this, "Chuyển sang QL Pokemon", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.menu_about) {
             Intent intent = new Intent(MainActivity.this, About_me.class);
             startActivity(intent);
            Toast.makeText(this, "Chuyển sang Giới thiệu", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}