package entity;

public class LoaiVe {
    private String maLoaiVe;
    private String tenLoaiVe;
    private String loaiKhoang;

    public LoaiVe() {
    }

    public LoaiVe(String maLoaiVe) {
        this.maLoaiVe = maLoaiVe;
    }

    public LoaiVe(String maLoaiVe, String tenLoaiVe, String loaiKhoang) {
        this.maLoaiVe = maLoaiVe;
        this.tenLoaiVe = tenLoaiVe;
        this.loaiKhoang = loaiKhoang;
    }

    public String getMaLoaiVe() {
        return maLoaiVe;
    }

    public void setMaLoaiVe(String maLoaiVe) {
        this.maLoaiVe = maLoaiVe;
    }

    public String getTenLoaiVe() {
        return tenLoaiVe;
    }

    public void setTenLoaiVe(String tenLoaiVe) {
        this.tenLoaiVe = tenLoaiVe;
    }

    public String getLoaiKhoang() {
        return loaiKhoang;
    }

    public void setLoaiKhoang(String loaiKhoang) {
        this.loaiKhoang = loaiKhoang;
    }
}
