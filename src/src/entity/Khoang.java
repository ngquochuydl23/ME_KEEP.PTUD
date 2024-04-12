package entity;

import java.util.Objects;

public class Khoang {
    private String maKhoang;
    private String tenKhoang;
    private LoaiKhoang loaiKhoang;
    private ToaTau toaTau;

    public Khoang() {
    }

    public Khoang(String maKhoang) {
        this.maKhoang = maKhoang;
    }

    public Khoang(String maKhoang, String tenKhoang, LoaiKhoang loaiKhoang, ToaTau toaTau) {
        this.maKhoang = maKhoang;
        this.tenKhoang = tenKhoang;
        this.loaiKhoang = loaiKhoang;
        this.toaTau = toaTau;
    }

    public String getMaKhoang() {
        return maKhoang;
    }

    public void setMaKhoang(String maKhoang) {
        this.maKhoang = maKhoang;
    }

    public String getTenKhoang() {
        return tenKhoang;
    }

    public void setTenKhoang(String tenKhoang) {
        this.tenKhoang = tenKhoang;
    }

    public ToaTau getToaTau() {
        return toaTau;
    }

    public void setToaTau(ToaTau toaTau) {
        this.toaTau = toaTau;
    }

    public LoaiKhoang getLoaiKhoang() {
        return loaiKhoang;
    }

    public void setLoaiKhoang(LoaiKhoang loaiKhoang) {
        this.loaiKhoang = loaiKhoang;
    }

    @Override
    public String toString() {
        return "Khoang{" +
                "maKhoang='" + maKhoang + '\'' +
                ", tenKhoang='" + tenKhoang + '\'' +
                ", toaTau=" + toaTau +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Khoang khoang = (Khoang) o;
        return Objects.equals(maKhoang, khoang.maKhoang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKhoang);
    }
}
