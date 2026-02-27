package vn.edu.stu.PhamTriThanh_DH52201466.model;
import java.io.Serializable;

public class pokemon implements Serializable {

    private String id;
    private String ten;
    private byte[] hinhAnh;
    private String maHe;
    private double chieuCao;
    private double canNang;
    private String tenHe;
    public pokemon() {
    }
    public pokemon(String id, String ten, byte[] hinhAnh, String maHe, double chieuCao, double canNang, String tenHe) {
        this.id = id;
        this.ten = ten;
        this.hinhAnh = hinhAnh;
        this.maHe = maHe;
        this.chieuCao = chieuCao;
        this.canNang = canNang;
        this.tenHe = tenHe;
    }
    public pokemon(String ten, byte[] hinhAnh, String maHe, double chieuCao, double canNang) {
        this.ten = ten;
        this.hinhAnh = hinhAnh;
        this.maHe = maHe;
        this.chieuCao = chieuCao;
        this.canNang = canNang;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public byte[] getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(byte[] hinhAnh) { this.hinhAnh = hinhAnh; }
    public String getMaHe() { return maHe; }
    public void setMaHe(String maHe) { this.maHe = maHe; }

    public double getChieuCao() { return chieuCao; }
    public void setChieuCao(double chieuCao) { this.chieuCao = chieuCao; }

    public double getCanNang() { return canNang; }
    public void setCanNang(double canNang) { this.canNang = canNang; }

    public String getTenHe() { return tenHe; }
    public void setTenHe(String tenHe) { this.tenHe = tenHe; }
}