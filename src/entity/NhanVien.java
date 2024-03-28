package entity;

import java.util.Date;
import java.util.Objects;

public class NhanVien {
    private int maNhanVien;
    private String hoTen;
    private String soDienThoai;
    private Date ngayDangKy;
    private String matKhau;

    private VaiTro vaiTro;
    public NhanVien() {}

    public NhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public NhanVien(int maNhanVien, String hoTen, String soDienThoai, Date ngayDangKy, String matKhau, VaiTro vaiTro) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.ngayDangKy = ngayDangKy;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    public void setVaiTro(VaiTro vaiTro) {
        this.vaiTro = vaiTro;
    }

    public VaiTro getVaiTro() {
        return vaiTro;
    }
    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
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

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNhanVien=" + maNhanVien +
                ", hoTen='" + hoTen + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", ngayDangKy=" + ngayDangKy +
                ", matKhau='" + matKhau + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NhanVien taiKhoan = (NhanVien) o;
        return maNhanVien == taiKhoan.maNhanVien;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNhanVien);
    }
}
