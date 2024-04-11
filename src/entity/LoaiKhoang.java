package entity;

public class LoaiKhoang {
    private String maLoaiKhoang;
    private String tenLoaiKhoang;

    public LoaiKhoang() {
    }

    public LoaiKhoang(String maLoaiKhoang) {
        this.maLoaiKhoang = maLoaiKhoang;
    }

    public LoaiKhoang(String maLoaiKhoang, String tenLoaiKhoang) {
        this.maLoaiKhoang = maLoaiKhoang;
        this.tenLoaiKhoang = tenLoaiKhoang;
    }

    public String getMaLoaiKhoang() {
        return maLoaiKhoang;
    }

    public void setMaLoaiKhoang(String maLoaiKhoang) {
        this.maLoaiKhoang = maLoaiKhoang;
    }

    public String getTenLoaiKhoang() {
        return tenLoaiKhoang;
    }

    public void setTenLoaiKhoang(String tenLoaiKhoang) {
        this.tenLoaiKhoang = tenLoaiKhoang;
    }

    @Override
    public String toString() {
        return "LoaiKhoang [maLoaiKhoang=" + maLoaiKhoang + ", tenLoaiKhoang=" + tenLoaiKhoang + "]";
    }

}
