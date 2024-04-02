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

    public Ga(String maGa) {
        this.maGa = maGa;
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
    public String toString() {
        return "Ga [maGa=" + maGa + ", tenGa=" + tenGa + ", vungMien=" + vungMien + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maGa == null) ? 0 : maGa.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ga other = (Ga) obj;
        if (maGa == null) {
            if (other.maGa != null)
                return false;
        } else if (!maGa.equals(other.maGa))
            return false;
        return true;
    }

}
