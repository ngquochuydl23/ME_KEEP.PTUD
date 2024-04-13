package entity;

import java.util.Objects;

public class Slot {
    private int maSlot;
    private Khoang khoang;
    private boolean tinhTrang;

    public Slot() {}

    public Slot(int maSlot, Khoang khoang, boolean tinhTrang) {
        this.maSlot = maSlot;
        this.khoang = khoang;
        this.tinhTrang = tinhTrang;
    }

    public int getMaSlot() {
        return maSlot;
    }

    public void setMaSlot(int maSlot) {
        this.maSlot = maSlot;
    }

    public Khoang getKhoang() {
        return khoang;
    }

    public void setKhoang(Khoang khoang) {
        this.khoang = khoang;
    }

    public boolean isTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(boolean tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot choNgoi = (Slot) o;
        return maSlot == choNgoi.maSlot && tinhTrang == choNgoi.tinhTrang && Objects.equals(khoang, choNgoi.khoang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maSlot, khoang, tinhTrang);
    }

    @Override
    public String toString() {
        return "Slot{" +
                "maSlot=" + maSlot +
                ", khoang=" + khoang +
                ", tinhTrang=" + tinhTrang +
                '}';
    }
}
