package models;

import entity.Ve;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class TraVeModel {
    private Ve ve;
    private double donGia;

    private String tenKhoang;
    private LocalDateTime thoiGianKhoiHanh;

    public TraVeModel(Ve ve, double donGia, LocalDateTime thoiGianKhoiHanh, String tenKhoang) {
        this.ve = ve;
        this.donGia = donGia;
        this.thoiGianKhoiHanh = thoiGianKhoiHanh;
        this.tenKhoang = tenKhoang;
    }


    public String getTenKhoang() {
        return tenKhoang;
    }

    public void getTenKhoang(String tenKhoang) {
        this.tenKhoang = tenKhoang;
    }

    public Ve getVe() {
        return ve;
    }

    public void setVe(Ve ve) {
        this.ve = ve;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public LocalDateTime getThoiGianKhoiHanh() {
        return thoiGianKhoiHanh;
    }

    public void setThoiGianKhoiHanh(LocalDateTime thoiGianKhoiHanh) {
        this.thoiGianKhoiHanh = thoiGianKhoiHanh;
    }

    public double tinhGiaTriHoanTien() {

        Duration duration = Duration.between(LocalDateTime.now(), thoiGianKhoiHanh);
        long hours = duration.getSeconds() / 3600;

        if (hours > 24) {
            return 20000;
        } else if (hours > 4 && hours <= 24) {
            return donGia * 0.2;
        }
        return 0;
    }


    @Override
    public String toString() {
        return "TraVeModel{" +
                "ve=" + ve +
                ", donGia=" + donGia +
                ", tenKhoang='" + tenKhoang + '\'' +
                ", thoiGianKhoiHanh=" + thoiGianKhoiHanh +
                '}';
    }
}
