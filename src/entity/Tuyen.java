package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Tuyen {
    private String maTuyen;
    private Ga gaDi;
    private Ga gaDen;
    private LocalDateTime thoiGianTaoTuyen;

    private double giaNiemYet;

    public Tuyen() {

    }

    public Tuyen(String maTuyen) {
        this.maTuyen = maTuyen;
    }

    public Tuyen(String maTuyen, Ga gaDi, Ga gaDen, LocalDateTime thoiGianTaoTuyen) {
        this.maTuyen = maTuyen;
        this.gaDi = gaDi;
        this.gaDen = gaDen;
        this.thoiGianTaoTuyen = thoiGianTaoTuyen;
    }

    public Tuyen(String maTuyen, Ga gaDi, Ga gaDen, double giaNiemYet) {
        this.maTuyen = maTuyen;
        this.gaDi = gaDi;
        this.gaDen = gaDen;
        this.giaNiemYet = giaNiemYet;
    }

    public String getMaTuyen() {
        return maTuyen;
    }

    public void setMaTuyen(String maTuyen) {
        this.maTuyen = maTuyen;
    }

    public LocalDateTime getThoiGianTaoTuyen() {
        return thoiGianTaoTuyen;
    }

    public void setThoiGianTaoTuyen(LocalDateTime thoiGianTaoTuyen) {
        this.thoiGianTaoTuyen = thoiGianTaoTuyen;
    }


    public Ga getGaDi() {
        return gaDi;
    }

    public void setGaDi(Ga gaDi) {
        this.gaDi = gaDi;
    }

    public Ga getGaDen() {
        return gaDen;
    }

    public void setGaDen(Ga gaDen) {
        this.gaDen = gaDen;
    }

    public double getGiaNiemYet() {
        return giaNiemYet;
    }

    public void setGiaNiemYet(double giaNiemYet) {
        this.giaNiemYet = giaNiemYet;
    }

    @Override
    public String toString() {
        return "Tuyen{" +
                "maTuyen='" + maTuyen + '\'' +
                ", gaDi=" + gaDi +
                ", gaDen=" + gaDen +
                ", thoiGianTaoTuyen=" + thoiGianTaoTuyen +
                ", giaNiemYet=" + giaNiemYet +
                '}';
    }


}
