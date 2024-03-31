package entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class NhanVien {
    private int maNhanVien;
    private String hoTen;
    private String soDienThoai;
    private LocalDate ngayDangKy;
    private LocalDate ngaySinh;
    private int trangthai;
    private String matKhau;
    private String email;
    private int gioitinh;
    private String vaiTro;

    public NhanVien() {
    }

    public NhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public NhanVien(int maNhanVien, String hoTen, int gioitinh, String soDienThoai, LocalDate ngayDangky, LocalDate ngaySinh, int trangthai, String email) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.gioitinh = gioitinh;
        this.soDienThoai = soDienThoai;
        this.ngayDangKy = ngayDangky;
        this.ngaySinh = ngaySinh;
        this.trangthai = trangthai;
        this.email = email;
    }

    public NhanVien(int maNhanVien, String hoTen, int gioitinh, String soDienThoai, LocalDate ngayDangky, LocalDate ngaySinh, int trangthai,
            String matKhau, String email, String vaiTro) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.gioitinh = gioitinh;
        this.soDienThoai = soDienThoai;
        this.ngayDangKy = ngayDangky;
        this.ngaySinh = ngaySinh;
        this.trangthai = trangthai;
        this.matKhau = matKhau;
        this.email = email;
        this.vaiTro = vaiTro;
    }

    public NhanVien(String hoTen, int gioitinh, String soDienThoai, LocalDate ngaySinh, int trangthai,
                    String matKhau, String email, String vaiTro) {
        this.hoTen = hoTen;
        this.gioitinh = gioitinh;
        this.soDienThoai = soDienThoai;
        this.ngaySinh = ngaySinh;
        this.trangthai = trangthai;
        this.matKhau = matKhau;
        this.email = email;
        this.vaiTro = vaiTro;
    }

    public NhanVien(int maNhanVien, String hoTen, int gioitinh, String soDienThoai, LocalDate ngaySinh, int trangthai,
                    String matKhau, String email, String vaiTro) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.gioitinh = gioitinh;
        this.soDienThoai = soDienThoai;
        this.ngaySinh = ngaySinh;
        this.trangthai = trangthai;
        this.matKhau = matKhau;
        this.email = email;
        this.vaiTro = vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getVaiTro() {
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

    public LocalDate getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(LocalDate ngayDangKy) {
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

    public int getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(int gioitinh) {
        this.gioitinh = gioitinh;
    }

    public LocalDate getNgaysinh() {
        return ngaySinh;
    }

    public void setNgaysinh(LocalDate ngaysinh) {
        this.ngaySinh = ngaysinh;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
