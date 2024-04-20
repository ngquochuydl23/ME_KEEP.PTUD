package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class KhachHang {
    private int maKhachHang;
    private String hoTen;
    private String soDienThoai;
    private String soCMND;
    private LocalDateTime thoiGianDangKy;
    private boolean laKhachHangThanThiet;

    public KhachHang() {

    }

    public KhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public KhachHang(String hoTen, String soDienThoai, LocalDateTime thoiGianDangKy, boolean laKhachHangThanThiet) {
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.thoiGianDangKy = thoiGianDangKy;
        this.laKhachHangThanThiet = laKhachHangThanThiet;
    }
    
    public KhachHang(int maKhachHang, String hoTen, String soDienThoai, String soCMND) {
		this.maKhachHang = maKhachHang;
		this.hoTen = hoTen;
		this.soDienThoai = soDienThoai;
		this.soCMND = soCMND;
	}

	public KhachHang(int maKhachHang, String hoTen, String soDienThoai, LocalDateTime thoiGianDangKy,
            boolean laKhachHangThanThiet) {
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.thoiGianDangKy = thoiGianDangKy;
        this.laKhachHangThanThiet = laKhachHangThanThiet;
    }
	
    public KhachHang(int maKhachHang, String hoTen, String soDienThoai, LocalDateTime thoiGianDangKy,
			boolean laKhachHangThanThiet, String soCMND) {
		this.maKhachHang = maKhachHang;
		this.hoTen = hoTen;
		this.soDienThoai = soDienThoai;
		this.soCMND = soCMND;
		this.thoiGianDangKy = thoiGianDangKy;
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

    public LocalDateTime getThoiGianDangKy() {
        return thoiGianDangKy;
    }

    public void setThoiGianDangKy(LocalDateTime ngayDangKy) {
        this.thoiGianDangKy = ngayDangKy;
    }

    public boolean laKhachHangThanThiet() {
        return laKhachHangThanThiet;
    }

    public void setLaKhachHangThanThiet(boolean laKhachHangThanThiet) {
        this.laKhachHangThanThiet = laKhachHangThanThiet;
    }
    
    public String getSoCMND() {
		return soCMND;
	}

	public void setSoCMND(String soCMND) {
		this.soCMND = soCMND;
	}

	public boolean isLaKhachHangThanThiet() {
		return laKhachHangThanThiet;
	}

	@Override
    public String toString() {
        return "KhachHang{" +
                "maKhachHang=" + maKhachHang +
                ", hoTen='" + hoTen + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", soCMND='" + soCMND + '\''+
                ", thoiGianDangKy=" + thoiGianDangKy +
                ", laKhachHangThanThiet=" + laKhachHangThanThiet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        KhachHang khachHang = (KhachHang) o;
        return maKhachHang == khachHang.maKhachHang;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKhachHang);
    }
}
