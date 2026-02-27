package vn.edu.stu.PhamTriThanh_DH52201466.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.stu.PhamTriThanh_DH52201466.R;
import vn.edu.stu.PhamTriThanh_DH52201466.model.Hepokemon;

public class TypeAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Hepokemon> listHe;
    public TypeAdapter(Context context, int layout, ArrayList<Hepokemon> listHe) {
        this.context = context;
        this.layout = layout;
        this.listHe = listHe;
    }
    @Override
    public int getCount() {
        return listHe.size();
    }
    @Override
    public Object getItem(int position) {
        return listHe.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        TextView txtTenHe;
        TextView txtMaHe;
    }
    private int layIdTenHe(String tenHeGoc) {
        if (tenHeGoc == null) return 0;
        String soSanh = tenHeGoc.toLowerCase().trim();

        switch (soSanh) {
            case "type_electric": return R.string.type_electric;
            case "type_fire":     return R.string.type_fire;
            case "type_water":    return R.string.type_water;
            case "type_grass":    return R.string.type_grass;
            case "type_ground":    return R.string.type_ground;
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
            holder.txtTenHe = convertView.findViewById(R.id.txtTypeName);
            holder.txtMaHe= convertView.findViewById((R.id.txtTypeId));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Hepokemon he = listHe.get(position);
        holder.txtMaHe.setText(context.getString(R.string.item_type_id) + " " + he.getId());
        holder.txtTenHe.setText(context.getString(R.string.item_type_name) + " " + he.getTenHe());
        return convertView;
    }
}