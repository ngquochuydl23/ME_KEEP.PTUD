package entity;

import java.util.List;
import java.util.Objects;

public class VaiTro {
    private String maVaiTro;
    private String tenVaiTro;
    public VaiTro() {

    }

    public VaiTro(String maVaiTro) {
        this.maVaiTro = maVaiTro;
    }

    public VaiTro(String maVaiTro, String tenVaiTro) {
        this.maVaiTro = maVaiTro;
        this.tenVaiTro = tenVaiTro;
    }


    public String getMaVaiTro() {
        return maVaiTro;
    }

    public void setMaVaiTro(String maVaiTro) {
        this.maVaiTro = maVaiTro;
    }

    public String getTenVaiTro() {
        return tenVaiTro;
    }

    public void setTenVaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VaiTro vaiTro = (VaiTro) o;
        return Objects.equals(maVaiTro, vaiTro.maVaiTro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maVaiTro);
    }
}
