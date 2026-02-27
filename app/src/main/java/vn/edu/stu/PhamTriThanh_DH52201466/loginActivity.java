package vn.edu.stu.PhamTriThanh_DH52201466;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class loginActivity extends AppCompatActivity {
    private EditText user ,pass;
    private Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        user =findViewById(R.id.login);
        pass =findViewById(R.id.password);
        btnlogin= findViewById(R.id.btnButton);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XulyDangNhap();
            }
        });
    }
    private void XulyDangNhap() {
        String username = user.getText().toString().trim();
        String password = pass.getText().toString().trim();
        if (username.equals("admin") && password.equals("123")) {
            Toast.makeText(loginActivity.this, "Đăng Nhập Thành Công!", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(loginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        } else if (!username.equals("admin") && password.equals("123")) {
            user.setError("Tài Khoản Đã Được Cố Định Vui Lòng Nhập Lại !!!");

        } else if (username.equals("admin")&& !password.equals("123")) {
            pass.setError("Mật Khẩu Đã Được Cố Định Vui lòng Nhập Lại !!!!");
        } else {
            Toast.makeText(this, "Sai cả tên tài khoản và mật khẩu!", Toast.LENGTH_SHORT).show();
        }
    }
}
