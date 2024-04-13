package entity;

import java.util.Objects;

public class Ve {
    private String maVe;
    private int choNgoi;
    private double giaVe;
    private String moTa;
    /*
     * 0: Chua dat
     * 1: Da dat
     */
    private int tinhTrangVe;
    private LoaiKhoang loaiKhoang;

    public Ve() {
    }

    public Ve(String maVe) {
        this.maVe = maVe;
    }

    public Ve(String maVe, int choNgoi, double giaVe, String moTa, int tinhTrangVe, LoaiKhoang loaiKhoang) {
        this.maVe = maVe;
        this.choNgoi = choNgoi;
        this.giaVe = giaVe;
        this.moTa = moTa;
        this.tinhTrangVe = tinhTrangVe;
        this.loaiKhoang = loaiKhoang;
    }

    public String getMaVe() {
        return maVe;
    }

    public void setMaVe(String maVe) {
        this.maVe = maVe;
    }

    public int getChoNgoi() {
        return choNgoi;
    }

    public void setChoNgoi(int choNgoi) {
        this.choNgoi = choNgoi;
    }

    public double getGiaVe() {
        return giaVe;
    }

    public void setGiaVe(double giaVe) {
        this.giaVe = giaVe;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getTinhTrangVe() {
        return tinhTrangVe;
    }

    public void setTinhTrangVe(int tinhTrangVe) {
        this.tinhTrangVe = tinhTrangVe;
    }

    public LoaiKhoang getLoaiKhoang() {
        return loaiKhoang;
    }

    public void setLoaiKhoang(LoaiKhoang loaiKhoang) {
        this.loaiKhoang = loaiKhoang;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Ve ve = (Ve) o;
        return Objects.equals(maVe, ve.maVe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maVe);
    }

    @Override
    public String toString() {
        return "Ve{" +
                "maVe='" + maVe + '\'' +
                ", choNgoi=" + choNgoi +
                ", giaVe=" + giaVe +
                ", moTa='" + moTa + '\'' +
                ", tinhTrangVe=" + tinhTrangVe +
                ", loaiKhoang=" + loaiKhoang +
                '}';
    }
}
