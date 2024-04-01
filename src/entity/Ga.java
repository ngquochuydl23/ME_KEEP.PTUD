package entity;

import java.util.Objects;

public class Ga {


    // ga-song-than
    // Ga Sóng Thần
    private String maGa;
    private String tenGa;

    private String vungMien;

    public  Ga() {

    }
    public Ga(String maGa, String tenGa, String vungMien) {
        this.maGa = maGa;
        this.tenGa = tenGa;
        this.vungMien = vungMien;
    }

    public String getMaGa() {
        return maGa;
    }

    public void setMaGa(String maGa) {
        this.maGa = maGa;
    }

    public String getTenGa() {
        return tenGa;
    }

    public void setTenGa(String tenGa) {
        this.tenGa = tenGa;
    }

    public String getVungMien() {
        return vungMien;
    }

    public void setVungMien(String vungMien) {
        this.vungMien = vungMien;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ga ga = (Ga) o;
        return Objects.equals(maGa, ga.maGa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maGa);
    }

    @Override
    public String toString() {
        return "Ga{" +
                "maGa='" + maGa + '\'' +
                ", tenGa='" + tenGa + '\'' +
                ", vungMien='" + vungMien + '\'' +
                '}';
    }
}
