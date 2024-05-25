package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class HoaDon {
    private String maHoaDon;
    private LocalDateTime thoiGianTaoHoaDon;
    private String ghiChu;
    private double vat;
    private double tongTien;
    private double tamTinh;
    private double tongTienGiam;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private KhuyenMai khuyenMai;

    public HoaDon() {}

    public HoaDon(String maHoaDon, LocalDateTime thoiGianTaoHoaDon, String ghiChu, double vat, double tongTien, double tamTinh, double tongTienGiam, KhachHang khachHang, NhanVien nhanVien, KhuyenMai khuyenMai) {
        this.maHoaDon = maHoaDon;
        this.thoiGianTaoHoaDon = thoiGianTaoHoaDon;
        this.ghiChu = ghiChu;
        this.vat = vat;
        this.tongTien = tongTien;
        this.tamTinh = tamTinh;
        this.tongTienGiam = tongTienGiam;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.khuyenMai = khuyenMai;
    }

    public HoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
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

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public double getTamTinh() {
        return tamTinh;
    }

    public void setTamTinh(double tamTinh) {
        this.tamTinh = tamTinh;
    }

    public double getTongTienGiam() {
        return tongTienGiam;
    }

    public void setTongTienGiam(double tongTienGiam) {
        this.tongTienGiam = tongTienGiam;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HoaDon hoaDon = (HoaDon) o;
        return Double.compare(hoaDon.vat, vat) == 0 && Double.compare(hoaDon.tongTien, tongTien) == 0 && Double.compare(hoaDon.tamTinh, tamTinh) == 0 && Double.compare(hoaDon.tongTienGiam, tongTienGiam) == 0 && Objects.equals(maHoaDon, hoaDon.maHoaDon) && Objects.equals(thoiGianTaoHoaDon, hoaDon.thoiGianTaoHoaDon) && Objects.equals(ghiChu, hoaDon.ghiChu) && Objects.equals(khachHang, hoaDon.khachHang) && Objects.equals(nhanVien, hoaDon.nhanVien) && Objects.equals(khuyenMai, hoaDon.khuyenMai);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maHoaDon, thoiGianTaoHoaDon, ghiChu, vat, tongTien, tamTinh, tongTienGiam, khachHang, nhanVien, khuyenMai);
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", thoiGianTaoHoaDon=" + thoiGianTaoHoaDon +
                ", ghiChu='" + ghiChu + '\'' +
                ", vat=" + vat +
                ", tongTien=" + tongTien +
                ", tamTinh=" + tamTinh +
                ", tongTienGiam=" + tongTienGiam +
                ", khachHang=" + khachHang +
                ", nhanVien=" + nhanVien +
                ", khuyenMai=" + khuyenMai +
                '}';
    }
}
