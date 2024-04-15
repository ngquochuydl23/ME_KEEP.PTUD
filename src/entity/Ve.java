package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ve {

    private String maVe;

    private Slot slot;

    private KhachHang khachHang;

    private Tuyen tuyen;

    private Tau tau;


    public Ve() {

    }

    public Ve(String maVe) {
        this.maVe = maVe;
    }

    public Ve(String maVe, Slot slot, KhachHang khachHang, Tuyen tuyen, Tau tau) {
        this.maVe = maVe;
        this.slot = slot;
        this.khachHang = khachHang;
        this.tuyen = tuyen;
        this.tau = tau;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ve ve = (Ve) o;
        return Objects.equals(maVe, ve.maVe) && Objects.equals(slot, ve.slot) && Objects.equals(khachHang, ve.khachHang) && Objects.equals(tuyen, ve.tuyen) && Objects.equals(tau, ve.tau);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maVe, slot, khachHang, tuyen, tau);
    }

    @Override
    public String toString() {
        return "Ve{" +
                "maVe='" + maVe + '\'' +
                ", slot=" + slot +
                ", khachHang=" + khachHang +
                ", tuyen=" + tuyen +
                ", tau=" + tau +
                '}';
    }

    public double tinhGiaBanVe() {
        double giaVe;
        double giaNiemYet = tuyen.getGiaNiemYet();

        String loaiKhoang = slot
                .getKhoang()
                .getLoaiKhoang()
                .getMaLoaiKhoang();

        if (loaiKhoang.equals("giuong-nam-khoang-4")) {
            giaVe = giaNiemYet + (giaNiemYet * 0.3);
        } else if (loaiKhoang.equals("giuong-nam-khoang-6")) {
            giaVe = giaNiemYet + (giaNiemYet * 0.15);
        } else {
            giaVe = giaNiemYet;
        }

        return giaVe;
    }
}
