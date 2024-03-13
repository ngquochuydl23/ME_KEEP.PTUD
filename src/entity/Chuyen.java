package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Chuyen {
    private String maChuyen;
    private LocalDateTime thoiGianKhoiHanh;
    private LocalDateTime thoiGianDen;
    private Tuyen tuyen;

    private Tau tau;

    public Chuyen() {}

    public Chuyen(String maChuyen) {
        this.maChuyen = maChuyen;
    }

    public Chuyen(String maChuyen, LocalDateTime thoiGianKhoiHanh, LocalDateTime thoiGianDen, Tuyen tuyen, Tau tau) {
        this.maChuyen = maChuyen;
        this.thoiGianKhoiHanh = thoiGianKhoiHanh;
        this.thoiGianDen = thoiGianDen;
        this.tuyen = tuyen;
        this.tau = tau;
    }

    public String getMaChuyen() {
        return maChuyen;
    }

    public void setMaChuyen(String maChuyen) {
        this.maChuyen = maChuyen;
    }

    public LocalDateTime getThoiGianKhoiHanh() {
        return thoiGianKhoiHanh;
    }

    public void setThoiGianKhoiHanh(LocalDateTime thoiGianKhoiHanh) {
        this.thoiGianKhoiHanh = thoiGianKhoiHanh;
    }

    public LocalDateTime getThoiGianDen() {
        return thoiGianDen;
    }

    public void setThoiGianDen(LocalDateTime thoiGianDen) {
        this.thoiGianDen = thoiGianDen;
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
    public String toString() {
        return "Chuyen{" +
                "maChuyen='" + maChuyen + '\'' +
                ", thoiGianKhoiHanh=" + thoiGianKhoiHanh +
                ", thoiGianDen=" + thoiGianDen +
                ", tuyen=" + tuyen +
                ", tau=" + tau +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chuyen chuyen = (Chuyen) o;
        return Objects.equals(maChuyen, chuyen.maChuyen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maChuyen);
    }
}
