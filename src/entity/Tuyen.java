package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Tuyen {
    private String maTuyen;
    private String diemDi;
    private String diemDen;
    private LocalDateTime thoiGianTaoTuyen;

    public Tuyen() {

    }

    public Tuyen(String maTuyen) {
        this.maTuyen = maTuyen;
    }

    public Tuyen(String maTuyen, String diemDi, String diemDen, LocalDateTime thoiGianTaoTuyen) {
        this.maTuyen = maTuyen;
        this.diemDi = diemDi;
        this.diemDen = diemDen;
        this.thoiGianTaoTuyen = thoiGianTaoTuyen;
    }

    public String getMaTuyen() {
        return maTuyen;
    }

    public void setMaTuyen(String maTuyen) {
        this.maTuyen = maTuyen;
    }

    public String getDiemDi() {
        return diemDi;
    }

    public void setDiemDi(String diemDi) {
        this.diemDi = diemDi;
    }

    public String getDiemDen() {
        return diemDen;
    }

    public void setDiemDen(String diemDen) {
        this.diemDen = diemDen;
    }


    public LocalDateTime getThoiGianTaoTuyen() {
        return thoiGianTaoTuyen;
    }

    public void setThoiGianTaoTuyen(LocalDateTime thoiGianTaoTuyen) {
        this.thoiGianTaoTuyen = thoiGianTaoTuyen;
    }

    @Override
    public String toString() {
        return "Tuyen{" +
                "maTuyen='" + maTuyen + '\'' +
                ", diemDi='" + diemDi + '\'' +
                ", diemDen='" + diemDen + '\'' +
                ", thoiGianTaoTuyen=" + thoiGianTaoTuyen +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuyen tuyen = (Tuyen) o;
        return Objects.equals(maTuyen, tuyen.maTuyen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maTuyen);
    }
}
