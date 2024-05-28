package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class TiepNhanYeuCauDoiVe {
    private int maYeuCau;
    private LocalDateTime thoiGianTiepNhan;
    private LocalDate thoiGianYeuCauDoi;

    private String ghiChu;
    private NhanVien nhanVien;
    private KhachHang khachHang;

    private Ve ve;

    public TiepNhanYeuCauDoiVe() {

    }

    public TiepNhanYeuCauDoiVe(int maYeuCau) {
        this.maYeuCau = maYeuCau;
    }

    public TiepNhanYeuCauDoiVe(int maYeuCau, LocalDateTime thoiGianTiepNhan, LocalDate thoiGianYeuCauDoi, String ghiChu, NhanVien nhanVien, KhachHang khachHang, Ve ve) {
        this.maYeuCau = maYeuCau;
        this.thoiGianTiepNhan = thoiGianTiepNhan;
        this.thoiGianYeuCauDoi = thoiGianYeuCauDoi;
        this.ghiChu = ghiChu;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.ve = ve;
    }

    public TiepNhanYeuCauDoiVe(LocalDate thoiGianYeuCauDoi, String ghiChu, NhanVien nhanVien, KhachHang khachHang, Ve ve) {
        this.thoiGianYeuCauDoi = thoiGianYeuCauDoi;
        this.ghiChu = ghiChu;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.ve = ve;
    }

    public Ve getVe() {
        return ve;
    }

    public void setVe(Ve ve) {
        this.ve = ve;
    }

    public int getMaYeuCau() {
        return maYeuCau;
    }

    public void setMaYeuCau(int maYeuCau) {
        this.maYeuCau = maYeuCau;
    }

    public LocalDateTime getThoiGianTiepNhan() {
        return thoiGianTiepNhan;
    }

    public void setThoiGianTiepNhan(LocalDateTime thoiGianTiepNhan) {
        this.thoiGianTiepNhan = thoiGianTiepNhan;
    }

    public LocalDate getThoiGianYeuCauDoi() {
        return thoiGianYeuCauDoi;
    }

    public void setThoiGianYeuCauDoi(LocalDate thoiGianYeuCauDoi) {
        this.thoiGianYeuCauDoi = thoiGianYeuCauDoi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    @Override
    public String toString() {
        return "TiepNhanYeuCauDoiVe{" +
                "maYeuCau=" + maYeuCau +
                ", thoiGianTiepNhan=" + thoiGianTiepNhan +
                ", thoiGianYeuCauDoi=" + thoiGianYeuCauDoi +
                ", ghiChu='" + ghiChu + '\'' +
                ", nhanVien=" + nhanVien +
                ", khachHang=" + khachHang +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TiepNhanYeuCauDoiVe that = (TiepNhanYeuCauDoiVe) o;
        return maYeuCau == that.maYeuCau;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maYeuCau);
    }
}
