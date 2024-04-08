package entity;

public class LoaiKhoang {
    private String maLoaiKhoang;
    private String tenLoaiKhoang;
    private String loaiKhoang;

    public LoaiKhoang() {
    }

    public LoaiKhoang(String maLoaiKhoang, String tenLoaiKhoang, String loaiKhoang) {
        this.maLoaiKhoang = maLoaiKhoang;
        this.tenLoaiKhoang = tenLoaiKhoang;
        this.loaiKhoang = loaiKhoang;
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

    public String getLoaiKhoang() {
        return loaiKhoang;
    }

    public void setLoaiKhoang(String loaiKhoang) {
        this.loaiKhoang = loaiKhoang;
    }

}
