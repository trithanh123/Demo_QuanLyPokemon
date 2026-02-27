package vn.edu.stu.PhamTriThanh_DH52201466.model;

public class Hepokemon {
    private String id;
    private String tenHe;


    public Hepokemon() {
    }


    public Hepokemon(String id, String tenHe) {
        this.id = id;
        this.tenHe = tenHe;
    }
    public Hepokemon(String tenHe) {
        this.tenHe = tenHe;
    }

    // Getter và Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenHe() {
        return tenHe;
    }

    public void setTenHe(String tenHe) {
        this.tenHe = tenHe;
    }
    @Override
    public String toString() {
        return id + "-" + tenHe;
    }
}