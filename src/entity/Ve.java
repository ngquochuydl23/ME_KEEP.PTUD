package entity;

import java.util.Objects;

public class Ve {
    private String maVe;
    private int choNgoi;
    private double giaVe;
    private String moTa;
    private int tinhTrangVe;

    private LoaiVe loaiVe;
    private Chuyen chuyen;

    public Ve() {
    }

    public Ve(String maVe) {
        this.maVe = maVe;
    }

    public Ve(String maVe, int choNgoi, double giaVe, String moTa, int tinhTrangVe, LoaiVe loaiVe,Chuyen chuyen) {
        this.maVe = maVe;
        this.choNgoi = choNgoi;
        this.giaVe = giaVe;
        this.moTa = moTa;
        this.tinhTrangVe = tinhTrangVe;
        this.loaiVe = loaiVe;
        this.chuyen = chuyen;
    }

    public Chuyen getChuyen() {
        return chuyen;
    }

    public void setChuyen(Chuyen chuyen) {
        this.chuyen = chuyen;
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

    public LoaiVe getLoaiVe() {
        return loaiVe;
    }

    public void setLoaiVe(LoaiVe loaiVe) {
        this.loaiVe = loaiVe;
    }

    @Override
    public String toString() {
        return "Ve{" +
                "maVe='" + maVe + '\'' +
                ", choNgoi=" + choNgoi +
                ", giaVe=" + giaVe +
                ", moTa='" + moTa + '\'' +
                ", tinhTrangVe='" + tinhTrangVe + '\'' +
                ", loaiVe=" + loaiVe +
                ", chuyen=" + chuyen +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ve ve = (Ve) o;
        return Objects.equals(maVe, ve.maVe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maVe);
    }
}
