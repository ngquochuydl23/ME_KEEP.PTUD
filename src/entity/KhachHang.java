package entity;

import java.util.Date;
import java.util.Objects;

public class KhachHang {
    private int maKhachHang;
    private String hoTen;
    private String soDienThoai;
    private Date ngayDangKy;
    private boolean laKhachHangThanThiet;

    public KhachHang() {

    }

    public KhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public KhachHang(int maKhachHang, String hoTen, String soDienThoai, Date ngayDangKy, boolean laKhachHangThanThiet) {
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.ngayDangKy = ngayDangKy;
        this.laKhachHangThanThiet = laKhachHangThanThiet;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public Date getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(Date ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    public boolean isLaKhachHangThanThiet() {
        return laKhachHangThanThiet;
    }

    public void setLaKhachHangThanThiet(boolean laKhachHangThanThiet) {
        this.laKhachHangThanThiet = laKhachHangThanThiet;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "maKhachHang=" + maKhachHang +
                ", hoTen='" + hoTen + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", ngayDangKy=" + ngayDangKy +
                ", laKhachHangThanThiet=" + laKhachHangThanThiet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KhachHang khachHang = (KhachHang) o;
        return maKhachHang == khachHang.maKhachHang;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKhachHang);
    }
}
