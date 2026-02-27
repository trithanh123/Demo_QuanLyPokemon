package vn.edu.stu.PhamTriThanh_DH52201466;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import vn.edu.stu.PhamTriThanh_DH52201466.dao.DbHelper;
import vn.edu.stu.PhamTriThanh_DH52201466.model.Hepokemon;
import vn.edu.stu.PhamTriThanh_DH52201466.model.pokemon;

public class PokemonDetailActivity extends AppCompatActivity {

    ImageView imgDetail;
    Button btnChooseImg, btnSave, btnExit;
    EditText edtId, edtName, edtHeight, edtWeight;
    Spinner spnType;

    DbHelper dbHelper;
    ArrayList<Hepokemon> listType;
    pokemon currentPokemon = null;
    final int REQUEST_CODE_GALLERY = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        addControls();
        loadSpinner();
        getDataFromIntent();
        addEvents();
    }

    private void addControls() {
        imgDetail = findViewById(R.id.imgDetail);
        btnChooseImg = findViewById(R.id.btnChooseImg);
        btnSave = findViewById(R.id.btnSavePoke);
        btnExit = findViewById(R.id.btnExit);
        edtId = findViewById(R.id.edtPokeId);
        edtName = findViewById(R.id.edtPokeName);
        edtHeight = findViewById(R.id.edtHeight);
        edtWeight = findViewById(R.id.edtWeight);
        spnType = findViewById(R.id.spnType);

        dbHelper = new DbHelper(this);
    }
    private void loadSpinner() {
        listType = dbHelper.getAllHe();
        ArrayList<String> listHienThi = new ArrayList<>();
        String ngonNguMay = java.util.Locale.getDefault().getLanguage();

        for (Hepokemon he : listType) {
            String tenDaDich;
            if (ngonNguMay.equals("vi")) {
                tenDaDich = doiTenHeSangTiengViet(he.getTenHe());
            } else {
                tenDaDich = doiTenHeSangTiengAnh(he.getTenHe());
            }
            listHienThi.add(he.getId() + "-" + tenDaDich);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listHienThi);
        spnType.setAdapter(adapter);
    }
    private String doiTenHeSangTiengViet(String tenHeGoc) {
        if (tenHeGoc == null) return "";
        String soSanh = tenHeGoc.toLowerCase().trim();
        switch (soSanh) {
            case "type_electric": return "Điện";
            case "type_fire":     return "Lửa";
            case "type_water":    return "Nước";
            case "type_grass":    return "Cỏ";
            case "type_ground":   return "Đất";
            case "type_rock":     return "Đá";
            case "type_fighting": return "Đấu sĩ";
            case "rock":          return "Đá";
            case "ground":        return "Đất";
            default: return tenHeGoc;
        }
    }
    private String doiTenHeSangTiengAnh(String tenHeGoc) {
        if (tenHeGoc == null) return "";
        String soSanh = tenHeGoc.toLowerCase().trim();
        switch (soSanh) {
            case "type_electric": return "Electric";
            case "type_fire":     return "Fire";
            case "type_water":    return "Water";
            case "type_grass":    return "Grass";
            case "type_ground":   return "Ground";
            case "type_rock":     return "Rock";
            case "type_fighting": return "Fighting";
            case "rock":          return "Rock";
            case "ground":        return "Ground";
            default: return tenHeGoc;
        }
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("POKEMON_DATA")) {
            currentPokemon = (pokemon) intent.getSerializableExtra("POKEMON_DATA");
            edtId.setText(currentPokemon.getId());
            edtId.setEnabled(false);
            edtName.setText(currentPokemon.getTen());
            edtHeight.setText(String.valueOf(currentPokemon.getChieuCao()));
            edtWeight.setText(String.valueOf(currentPokemon.getCanNang()));
            if (currentPokemon.getHinhAnh() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(currentPokemon.getHinhAnh(), 0, currentPokemon.getHinhAnh().length);
                imgDetail.setImageBitmap(bitmap);
            }
            for (int i = 0; i < listType.size(); i++) {
                           if (listType.get(i).getId().equals(currentPokemon.getMaHe())) {
                    spnType.setSelection(i);
                    break;
                }
            }
        }
    }

    private void addEvents() {
        btnChooseImg.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_GALLERY);
        });
        btnSave.setOnClickListener(v -> {
            String id = edtId.getText().toString().trim();
            String name = edtName.getText().toString().trim();

            if (id.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ ID và Tên!", Toast.LENGTH_SHORT).show();
                return;
            }

            double height = 0, weight = 0;
            try {
                height = Double.parseDouble(edtHeight.getText().toString());
                weight = Double.parseDouble(edtWeight.getText().toString());
            } catch (Exception e) {}
            int vitri = spnType.getSelectedItemPosition();
            Hepokemon selectedType = listType.get(vitri);
            String typeId = String.valueOf(selectedType.getId());
            byte[] imgBytes = convertImageToByte(imgDetail);

            if (currentPokemon == null) {
                // Thêm mới
                pokemon p = new pokemon(id, name, imgBytes, typeId, height, weight, null);
                try {
                    dbHelper.addPokemon(p);
                    Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, "Lỗi: ID có thể bị trùng!", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Cập nhật
                currentPokemon.setTen(name);
                currentPokemon.setHinhAnh(imgBytes);
                currentPokemon.setMaHe(typeId);
                currentPokemon.setChieuCao(height);
                currentPokemon.setCanNang(weight);

                dbHelper.updatePokemon(currentPokemon);
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnExit.setOnClickListener(v -> finish());
    }
    private byte[] convertImageToByte(ImageView img) {
        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgDetail.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}