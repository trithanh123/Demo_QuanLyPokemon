package vn.edu.stu.PhamTriThanh_DH52201466.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

// Import đúng đường dẫn từ gói model
import vn.edu.stu.PhamTriThanh_DH52201466.model.Hepokemon;
import vn.edu.stu.PhamTriThanh_DH52201466.model.pokemon;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "QuanLyPokemon.db";
    private static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<Hepokemon> getAllHe() {
        ArrayList<Hepokemon> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM quanlyhe_tbl", null);
        if (c.moveToFirst()) {
            do {
                String id = c.getString(0);
                String ten = c.getString(1);
                list.add(new Hepokemon(id, ten));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }
    public ArrayList<pokemon> getAllPokemon() {
        ArrayList<pokemon> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT p.*, h.tenhe FROM quanlypokemon_tbl p " +
                "INNER JOIN quanlyhe_tbl h ON p.mahe = h.mahe";

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                String id = c.getString(0);
                String ten = c.getString(1);
                byte[] hinh = c.getBlob(2);
                String maHe = c.getString(3);
                double cao = c.getDouble(4);
                double nang = c.getDouble(5);
                String tenHe = c.getString(6);
                list.add(new pokemon(id, ten, hinh, maHe, cao, nang, tenHe));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public void deletePokemon(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("quanlypokemon_tbl", "id=?", new String[]{id});
    }
    public boolean checkTenHeExists(String tenHe) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM quanlyhe_tbl WHERE tenhe = ?", new String[]{tenHe});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean addHe(String id, String tenHe) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("mahe", id);
        values.put("tenhe", tenHe);
        long r = this.getWritableDatabase().insert("quanlyhe_tbl", null, values);
        return r != -1;
    }

    public void updateHe(Hepokemon he) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("tenhe", he.getTenHe());
        this.getWritableDatabase().update("quanlyhe_tbl", values, "mahe=?", new String[]{he.getId()});
    }

    public boolean checkHeHasPokemon(String maHe) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM quanlypokemon_tbl WHERE mahe = ?", new String[]{maHe});
        boolean has = c.getCount() > 0;
        c.close();
        return has;
    }

    public void deleteHe(String maHe) {
        this.getWritableDatabase().delete("quanlyhe_tbl", "mahe=?", new String[]{maHe});
    }
    public void addPokemon(pokemon p) {
        SQLiteDatabase db = this.getWritableDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("id", p.getId());
        values.put("tenpokemon", p.getTen());
        values.put("hinhanh", p.getHinhAnh());
        values.put("mahe", p.getMaHe());
        values.put("chieucao", p.getChieuCao());
        values.put("cannang", p.getCanNang());
        db.insertOrThrow("quanlypokemon_tbl", null, values);
    }
    public void updatePokemon(pokemon p) {
        SQLiteDatabase db = this.getWritableDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("tenpokemon", p.getTen());
        values.put("hinhanh", p.getHinhAnh());
        values.put("mahe", p.getMaHe());
        values.put("chieucao", p.getChieuCao());
        values.put("cannang", p.getCanNang());
        db.update("quanlypokemon_tbl", values, "id=?", new String[]{p.getId()});
    }
}