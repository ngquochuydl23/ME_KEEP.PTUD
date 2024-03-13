package entity;

import java.util.Objects;

public class QuyenVaiTro {
    private VaiTro vaiTro;

    private Quyen quyen;


    public QuyenVaiTro() {

    }

    public QuyenVaiTro(VaiTro vaiTro, Quyen quyen) {
        this.vaiTro = vaiTro;
        this.quyen = quyen;
    }

    public VaiTro getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(VaiTro vaiTro) {
        this.vaiTro = vaiTro;
    }

    public Quyen getQuyen() {
        return quyen;
    }

    public void setQuyen(Quyen quyen) {
        this.quyen = quyen;
    }

    @Override
    public String toString() {
        return "QuyenVaiTro{" +
                "vaiTro=" + vaiTro +
                ", quyen=" + quyen +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuyenVaiTro that = (QuyenVaiTro) o;
        return Objects.equals(vaiTro, that.vaiTro) && Objects.equals(quyen, that.quyen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vaiTro, quyen);
    }
}
