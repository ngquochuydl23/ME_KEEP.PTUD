package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Ve implements Comparable<Ve>{

    private String maVe;

    private Slot slot;

    private KhachHang khachHang;

    private Tuyen tuyen;

    private Tau tau;

    private String hoTenNguoiDi;

    private String cccdNguoiDi;

    private int namSinhNguoiDi;

    private int tinhTrangVe;

    public Ve() {

    }

    public Ve(String maVe) {
        this.maVe = maVe;
    }

    public Ve(String maVe, Slot slot, KhachHang khachHang, Tuyen tuyen, Tau tau, String hoTenNguoiDi, String cccdNguoiDi, int namSinhNguoiDi, int tinhTrangVe) {
        this.maVe = maVe;
        this.slot = slot;
        this.khachHang = khachHang;
        this.tuyen = tuyen;
        this.tau = tau;
        this.hoTenNguoiDi = hoTenNguoiDi;
        this.cccdNguoiDi = cccdNguoiDi;
        this.namSinhNguoiDi = namSinhNguoiDi;
        this.tinhTrangVe = tinhTrangVe;
    }

    public String getMaVe() {
        return maVe;
    }

    public void setMaVe(String maVe) {
        this.maVe = maVe;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public int getTinhTrangVe() {
        return tinhTrangVe;
    }

    public void setTinhTrangVe(int tinhTrangVe) {
        this.tinhTrangVe = tinhTrangVe;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public Tuyen getTuyen() {
        return tuyen;
    }

    public void setTuyen(Tuyen tuyen) {
        this.tuyen = tuyen;
    }

    public Tau getTau() {
        return tau;
    }

    public void setTau(Tau tau) {
        this.tau = tau;
    }


    public String getHoTenNguoiDi() {
        return hoTenNguoiDi;
    }

    public void setHoTenNguoiDi(String hoTenNguoiDi) {
        this.hoTenNguoiDi = hoTenNguoiDi;
    }

    public String getCccdNguoiDi() {
        return cccdNguoiDi;
    }

    public void setCccdNguoiDi(String cccdNguoiDi) {
        this.cccdNguoiDi = cccdNguoiDi;
    }

    public int getNamSinhNguoiDi() {
        return namSinhNguoiDi;
    }

    public void setNamSinhNguoiDi(int namSinhNguoiDi) {
        this.namSinhNguoiDi = namSinhNguoiDi;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ve ve = (Ve) o;
        return Objects.equals(maVe, ve.maVe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maVe);
    }

    @Override
    public String toString() {
        return "Ve{" +
                "maVe='" + maVe + '\'' +
                ", slot=" + slot +
                ", khachHang=" + khachHang +
                ", tuyen=" + tuyen +
                ", tau=" + tau +
                ", hoTenNguoiDi='" + hoTenNguoiDi + '\'' +
                ", cccdNguoiDi='" + cccdNguoiDi + '\'' +
                ", namSinhNguoiDi=" + namSinhNguoiDi +
                '}';
    }

    public double tinhGiaBanVe(String loaiKhoang) {
        double giaVe;
        double giaNiemYet = tuyen.getGiaNiemYet();


        if (loaiKhoang.equals("giuong-nam-khoang-4")) {
            giaVe = giaNiemYet + (giaNiemYet * 0.3);
        } else if (loaiKhoang.equals("giuong-nam-khoang-6")) {
            giaVe = giaNiemYet + (giaNiemYet * 0.15);
        } else {
            giaVe = giaNiemYet;
        }

        return giaVe;
    }

    public boolean laNguoiLon() {

        return (LocalDate.now().getYear() - namSinhNguoiDi) >= 18;
    }

    @Override
    public int compareTo(Ve o) {
        return getMaVe().compareTo(o.getMaVe());
    }
}
