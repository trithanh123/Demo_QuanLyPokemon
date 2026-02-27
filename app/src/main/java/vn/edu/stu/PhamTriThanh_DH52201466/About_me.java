package vn.edu.stu.PhamTriThanh_DH52201466;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Nhớ import Toolbar

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class About_me extends AppCompatActivity {
    private MapView map;
    private Button btnDial, btnCall;
    private String phoneNumber = "0941061704";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_about_me);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        addControls();
        addEvents();
        setupMap();
    }
    private void addControls() {
        btnDial = findViewById(R.id.btnDial);
        btnCall = findViewById(R.id.btnCall);
        map = findViewById(R.id.mapView);
    }

    private void addEvents() {
        btnDial.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        });

        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        });
    }

    private void setupMap() {
        map.setMultiTouchControls(true);
        GeoPoint startPoint = new GeoPoint(10.738142, 106.677843); // Tọa độ STU
        map.getController().setZoom(18.0);
        map.getController().setCenter(startPoint);

        Marker startMarker = new Marker(map);
        startMarker.setPosition(startPoint);
        startMarker.setTitle("ĐH Công nghệ Sài Gòn");
        startMarker.setSnippet("180 Cao Lỗ, P4, Q8");
        map.getOverlays().add(startMarker);
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
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.menu_about) {
            Toast.makeText(this, "Bạn đang xem thông tin Giới thiệu", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override public void onResume() { super.onResume(); if(map!=null) map.onResume(); }
    @Override public void onPause() { super.onPause(); if(map!=null) map.onPause(); }
}