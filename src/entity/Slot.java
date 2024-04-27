package entity;

import java.util.Objects;

public class Slot {

    private String maSlot;

    private int soSlot;

    private Khoang khoang;

    private int tinhTrang;

    public Slot() {
    }

    public Slot(String maSlot, int soSlot) {
        this.maSlot = maSlot;
        this.soSlot = soSlot;
    }

    public Slot(String maSlot, int soSlot, Khoang khoang, int tinhTrang) {
        this.maSlot = maSlot;
        this.soSlot = soSlot;
        this.khoang = khoang;
        this.tinhTrang = tinhTrang;
    }

    public String getMaSlot() {
        return maSlot;
    }

    public void setMaSlot(String maSlot) {
        this.maSlot = maSlot;
    }

    public int getSoSlot() {
        return soSlot;
    }

    public void setSoSlot(int soSlot) {
        this.soSlot = soSlot;
    }

    public Khoang getKhoang() {
        return khoang;
    }

    public void setKhoang(Khoang khoang) {
        this.khoang = khoang;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Slot slot = (Slot) o;
        return maSlot == slot.maSlot && soSlot == slot.soSlot && tinhTrang == slot.tinhTrang
                && Objects.equals(khoang, slot.khoang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maSlot, soSlot, khoang, tinhTrang);
    }

    @Override
    public String toString() {
        return "Slot{" +
                "maSlot=" + maSlot +
                ", soSlot=" + soSlot +
                ", khoang=" + khoang +
                ", tinhTrang=" + tinhTrang +
                '}';
    }
}
