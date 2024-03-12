package entity;

import java.util.Objects;

public class ChiTietHoaDon {

    private int maChiTietHoaDon;
    private double donGia;
    private int soLuongVe;

    private HoaDon hoaDon;

    public ChiTietHoaDon() {

    }

    public ChiTietHoaDon(int maChiTietHoaDon) {
        this.maChiTietHoaDon = maChiTietHoaDon;
    }

    public ChiTietHoaDon(int maChiTietHoaDon, double donGia, int soLuongVe, HoaDon hoaDon) {
        this.maChiTietHoaDon = maChiTietHoaDon;
        this.donGia = donGia;
        this.soLuongVe = soLuongVe;
        this.hoaDon = hoaDon;
    }

    public int getMaChiTietHoaDon() {
        return maChiTietHoaDon;
    }

    public void setMaChiTietHoaDon(int maChiTietHoaDon) {
        this.maChiTietHoaDon = maChiTietHoaDon;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getSoLuongVe() {
        return soLuongVe;
    }

    public void setSoLuongVe(int soLuongVe) {
        this.soLuongVe = soLuongVe;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                "maChiTietHoaDon=" + maChiTietHoaDon +
                ", donGia=" + donGia +
                ", soLuongVe=" + soLuongVe +
                ", hoaDon=" + hoaDon +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietHoaDon that = (ChiTietHoaDon) o;
        return maChiTietHoaDon == that.maChiTietHoaDon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maChiTietHoaDon);
    }
}
