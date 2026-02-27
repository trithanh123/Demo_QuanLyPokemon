package vn.edu.stu.PhamTriThanh_DH52201466;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import vn.edu.stu.PhamTriThanh_DH52201466.Util.DBConfigUtil;
import vn.edu.stu.PhamTriThanh_DH52201466.adapter.PokemonAdapter;
import vn.edu.stu.PhamTriThanh_DH52201466.dao.DbHelper;
import vn.edu.stu.PhamTriThanh_DH52201466.model.pokemon;

public class MainActivity2 extends AppCompatActivity {

    Toolbar toolbar;
    ListView lvPokemon;
    FloatingActionButton fabAdd;

    ArrayList<pokemon> dsPokemon;
    PokemonAdapter adapter;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        DBConfigUtil.copyDatabaseFromAssets(this);
        dbHelper = new DbHelper(this);
        addControls();
        setupToolbar();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hienThiDanhSach();
    }
    private void addControls() {
        toolbar = findViewById(R.id.toolbar);
        lvPokemon = findViewById(R.id.lvPokemon);
        fabAdd = findViewById(R.id.fabAdd);

        dbHelper = new DbHelper(this);
        dsPokemon = new ArrayList<>();
    }
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
        }
    }

    private void hienThiDanhSach() {
        dsPokemon = dbHelper.getAllPokemon();
        adapter = new PokemonAdapter(this, R.layout.item_pokemon, dsPokemon);
        lvPokemon.setAdapter(adapter);
    }
    private void addEvents() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, PokemonDetailActivity.class);
                startActivity(intent);
            }
        });
        lvPokemon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pokemon selectedPoke = dsPokemon.get(position);
                Intent intent = new Intent(MainActivity2.this, PokemonDetailActivity.class);
                intent.putExtra("POKEMON_DATA", selectedPoke);
                startActivity(intent);
            }
        });
        lvPokemon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pokemon p = dsPokemon.get(position);
                xacNhanXoa(p);
                return true;
            }
        });
    }
    private void xacNhanXoa(pokemon p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Xóa Pokemon [" + p.getTen() + "]?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            dbHelper.deletePokemon(p.getId());
            hienThiDanhSach();
            Toast.makeText(MainActivity2.this, "Đã xóa!", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Không", null);
        builder.create().show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        if (id == R.id.menu_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.menu_ql_pokemon) {
            Toast.makeText(this, "Bạn đang ở trang Quản lý Pokemon", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.menu_about) {
            Intent intent = new Intent(this, About_me.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}