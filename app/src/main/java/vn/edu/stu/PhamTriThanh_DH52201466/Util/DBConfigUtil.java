package vn.edu.stu.PhamTriThanh_DH52201466.Util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DBConfigUtil {
    final static String DATABASE_NAME = "QuanLyPokemon.db";
    final static String DB_PATH_SUFFIX = "/databases/";

    public static void copyDatabaseFromAssets(Context context) {
        try {
            File dbFile = context.getDatabasePath(DATABASE_NAME);
            if (!dbFile.exists()) {
                File parentDir = dbFile.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                InputStream is = context.getAssets().open(DATABASE_NAME);
                OutputStream os = new FileOutputStream(dbFile);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                os.flush();
                os.close();
                is.close();

                Toast.makeText(context, "Đã khởi tạo CSDL thành công!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Lỗi sao chép CSDL: " + e.toString(), Toast.LENGTH_LONG).show();
            Log.e("DBConfig", "Error copying DB", e);
        }
    }
}