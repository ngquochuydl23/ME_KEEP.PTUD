package entity;

import java.util.Objects;

public class ChiTietHoaDon {
    private double donGia;

    private Ve ve;
    private HoaDon hoaDon;

    public ChiTietHoaDon(HoaDon hoaDon, Ve ve) {
        this.hoaDon = hoaDon;
        this.ve = ve;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public Ve getVe() {
        return ve;
    }

    public void setVe(Ve ve) {
        this.ve = ve;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietHoaDon that = (ChiTietHoaDon) o;
        return Double.compare(donGia, that.donGia) == 0 && Objects.equals(ve, that.ve) && Objects.equals(hoaDon, that.hoaDon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(donGia, ve, hoaDon);
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                "donGia=" + donGia +
                ", ve=" + ve +
                ", hoaDon=" + hoaDon +
                '}';
    }
}
