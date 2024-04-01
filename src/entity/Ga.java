package entity;

public class Ga {
    private String maGa;
    private String tenGa;
    private String vungMien;

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

}
