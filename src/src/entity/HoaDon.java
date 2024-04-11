package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class HoaDon {
    private String maHoaDon;
    private LocalDateTime thoiGianTaoHoaDon;
    private String ghiChu;
    private double thueVat;

    private double tienKhachDua;

    private KhachHang khachHang;
    private NhanVien nhanVien;

    private KhuyenMai khuyenMai;
    public HoaDon() {}

    public HoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public HoaDon(String maHoaDon, LocalDateTime thoiGianTaoHoaDon, String ghiChu, double thueVat, double tienKhachDua, KhachHang khachHang, NhanVien nhanVien, KhuyenMai khuyenMai) {
        this.maHoaDon = maHoaDon;
        this.thoiGianTaoHoaDon = thoiGianTaoHoaDon;
        this.ghiChu = ghiChu;
        this.thueVat = thueVat;
        this.tienKhachDua = tienKhachDua;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.khuyenMai = khuyenMai;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public LocalDateTime getThoiGianTaoHoaDon() {
        return thoiGianTaoHoaDon;
    }

    public void setThoiGianTaoHoaDon(LocalDateTime thoiGianTaoHoaDon) {
        this.thoiGianTaoHoaDon = thoiGianTaoHoaDon;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public double getThueVat() {
        return thueVat;
    }

    public void setThueVat(double thueVat) {
        this.thueVat = thueVat;
    }

    public double getTienKhachDua() {
        return tienKhachDua;
    }

    public void setTienKhachDua(double tienKhachDua) {
        this.tienKhachDua = tienKhachDua;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", thoiGianTaoHoaDon=" + thoiGianTaoHoaDon +
                ", ghiChu='" + ghiChu + '\'' +
                ", thueVat=" + thueVat +
                ", tienKhachDua=" + tienKhachDua +
                ", khachHang=" + khachHang +
                ", nhanVien=" + nhanVien +
                ", khuyenMai=" + khuyenMai +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HoaDon hoaDon = (HoaDon) o;
        return Objects.equals(maHoaDon, hoaDon.maHoaDon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maHoaDon);
    }
}
