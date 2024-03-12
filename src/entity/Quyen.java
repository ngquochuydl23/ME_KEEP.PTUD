package entity;

import java.util.Objects;

public class Quyen {
    private String maQuyen;
    private String tenQuyen;


    public Quyen() {
    }

    public Quyen(String maQuyen) {
        this.maQuyen = maQuyen;
    }

    public Quyen(String maQuyen, String tenQuyen) {
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
    }

    public String getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(String maQuyen) {
        this.maQuyen = maQuyen;
    }

    public String getTenQuyen() {
        return tenQuyen;
    }

    public void setTenQuyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }

    @Override
    public String toString() {
        return "Quyen{" +
                "maQuyen='" + maQuyen + '\'' +
                ", tenQuyen='" + tenQuyen + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quyen quyen = (Quyen) o;
        return Objects.equals(maQuyen, quyen.maQuyen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maQuyen);
    }
}
