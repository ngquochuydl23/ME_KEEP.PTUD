package entity;

import java.util.Objects;

public class ChiTietHoaDon {
    private double donGia; // đâsdsadsadsdada
    private Ve ve;
    private HoaDon hoaDon;

    public ChiTietHoaDon(HoaDon hoaDon, Ve ve) {
        this.hoaDon = hoaDon;
        this.ve = ve;
    }

    public ChiTietHoaDon(HoaDon hoaDon, Ve ve, Double donGia) {
        this.hoaDon = hoaDon;
        this.ve = ve;
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
        if (this == o)
            return true; // đâsdsadsadsdada //đâsdsadsadsdada //đâsdsadsadsdada //đâsdsadsadsdada
                         // //đâsdsadsadsdada //đâsdsadsadsdada
        if (o == null || getClass() != o.getClass())
            return false;
        ChiTietHoaDon that = (ChiTietHoaDon) o;
        return Objects.equals(ve, that.ve) && Objects.equals(hoaDon, that.hoaDon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ve, hoaDon);
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                ", ve=" + ve +
                ", hoaDon=" + hoaDon +
                '}';
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
}
