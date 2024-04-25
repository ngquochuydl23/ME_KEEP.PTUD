package entity;

import org.bridj.cpp.mfc.CString;

import java.time.LocalDateTime;
import java.util.Objects;

public class LichSuTraVe {
    private int maLichSuTraVe;
    private LocalDateTime thoiGianTraVe;
    private String ghiChu;

    private double phiTraVe;
    private String loaiTraVe;

    private KhachHang khachHang;

    private NhanVien nhanVienThucHien;

    private Ve ve;

    public LichSuTraVe() {}

    public LichSuTraVe(int maLichSuTraVe) {
        this.maLichSuTraVe = maLichSuTraVe;
    }


    public LichSuTraVe(int maLichSuTraVe, LocalDateTime thoiGianTraVe, String ghiChu, double phiTraVe, String loaiTraVe, KhachHang khachHang, NhanVien nhanVienThucHien, Ve ve) {
        this.maLichSuTraVe = maLichSuTraVe;
        this.thoiGianTraVe = thoiGianTraVe;
        this.ghiChu = ghiChu;
        this.phiTraVe = phiTraVe;
        this.loaiTraVe = loaiTraVe;
        this.khachHang = khachHang;
        this.nhanVienThucHien = nhanVienThucHien;
        this.ve = ve;
    }

    public double getPhiTraVe() {
        return phiTraVe;
    }

    public void setPhiTraVe(double phiTraVe) {
        this.phiTraVe = phiTraVe;
    }

    public String getLoaiTraVe() {
        return loaiTraVe;
    }

    public void setLoaiTraVe(String loaiTraVe) {
        this.loaiTraVe = loaiTraVe;
    }

    public int getMaLichSuTraVe() {
        return maLichSuTraVe;
    }

    public void setMaLichSuTraVe(int maLichSuTraVe) {
        this.maLichSuTraVe = maLichSuTraVe;
    }

    public LocalDateTime getThoiGianTraVe() {
        return thoiGianTraVe;
    }

    public void setThoiGianTraVe(LocalDateTime thoiGianTraVe) {
        this.thoiGianTraVe = thoiGianTraVe;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public Ve getVe() {
        return ve;
    }

    public void setVe(Ve ve) {
        this.ve = ve;
    }

    @Override
    public String toString() {
        return "LichSuTraVe{" +
                "maLichSuTraVe=" + maLichSuTraVe +
                ", thoiGianTraVe=" + thoiGianTraVe +
                ", ghiChu='" + ghiChu + '\'' +
                ", khachHang=" + khachHang +
                ", ve=" + ve +
                '}';
    }

    public NhanVien getNhanVienThucHien() {
        return nhanVienThucHien;
    }

    public void setNhanVienThucHien(NhanVien nhanVienThucHien) {
        this.nhanVienThucHien = nhanVienThucHien;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LichSuTraVe that = (LichSuTraVe) o;
        return maLichSuTraVe == that.maLichSuTraVe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maLichSuTraVe);
    }
}
