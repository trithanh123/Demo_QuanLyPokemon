package vn.edu.stu.PhamTriThanh_DH52201466.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.stu.PhamTriThanh_DH52201466.model.pokemon;
import vn.edu.stu.PhamTriThanh_DH52201466.R;


public class PokemonAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<pokemon> listPokemon;

    public PokemonAdapter(Context context, int layout, ArrayList<pokemon> listPokemon) {
        this.context = context;
        this.layout = layout;
        this.listPokemon = listPokemon;
    }
    @Override
    public int getCount() {
        return listPokemon.size();
    }
    @Override
    public Object getItem(int position) {
        return listPokemon.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        ImageView imgHinh;
        TextView txtTen, txtId, txtType, txtChieuCao, txtCanNang;
    }
    private int layIdTenHe(String tenHeGoc) {
        switch (tenHeGoc) {
            case "type_electric": return R.string.type_electric;
            case "type_fire":     return R.string.type_fire;
            case "type_water":    return R.string.type_water;
            case "type_grass":    return R.string.type_grass;
            case "type_ground":     return R.string.type_ground;
            case "type_rock":    return R.string.type_rock;
            case "type_fighting":    return R.string.type_fighting;
            default: return 0;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.imgHinh = convertView.findViewById(R.id.imgPoke);
            holder.txtId = convertView.findViewById(R.id.txtPokeId);
            holder.txtTen = convertView.findViewById(R.id.txtPokeName);
            holder.txtType = convertView.findViewById(R.id.txtPokeType);
            holder.txtChieuCao = convertView.findViewById(R.id.txtHeight);
            holder.txtCanNang = convertView.findViewById(R.id.txtWeight);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        pokemon p = listPokemon.get(position);
        holder.txtTen.setText(context.getString(R.string.poke_name) + " " + p.getTen());
        holder.txtId.setText(context.getString(R.string.poke_id) + " " + p.getId());
        int idTenHe = layIdTenHe(p.getTenHe());
        String tenHeHienThi;
        if (idTenHe != 0) {
            tenHeHienThi = context.getString(idTenHe);
        } else {
            tenHeHienThi = p.getTenHe();
        }
        holder.txtType.setText(
                context.getString(R.string.poke_type) + " " + tenHeHienThi +
                        " (" + context.getString(R.string.poke_type_id) + " " + p.getMaHe() + ")"
        );
        holder.txtChieuCao.setText(context.getString(R.string.poke_height) + " " + p.getChieuCao() + "m");
        holder.txtCanNang.setText(context.getString(R.string.poke_weight) + " " + p.getCanNang() + "kg");
        byte[] hinhAnh = p.getHinhAnh();
        if (hinhAnh != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh, 0, hinhAnh.length);
            holder.imgHinh.setImageBitmap(bitmap);
        } else {
            holder.imgHinh.setImageResource(R.mipmap.ic_launcher);
        }
        return convertView;
    }
}