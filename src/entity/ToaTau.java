package entity;

import java.util.Objects;

public class ToaTau {
    private String maToa;
    private String tenToa;

    private Tau tau;

    public ToaTau() {
    }

    public ToaTau(String maToa) {
        this.maToa = maToa;
    }

    public ToaTau(String maToa, String tenToa, Tau tau) {
        this.maToa = maToa;
        this.tenToa = tenToa;
        this.tau = tau;
    }

    public String getMaToa() {
        return maToa;
    }

    public void setMaToa(String maToa) {
        this.maToa = maToa;
    }

    public String getTenToa() {
        return tenToa;
    }

    public void setTenToa(String tenToa) {
        this.tenToa = tenToa;
    }

    public Tau getTau() {
        return tau;
    }

    public void setTau(Tau tau) {
        this.tau = tau;
    }

    @Override
    public String toString() {
        return "ToaTau{" +
                "maToa='" + maToa + '\'' +
                ", tenToa='" + tenToa + '\'' +
                ", tau=" + tau +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToaTau toaTau = (ToaTau) o;
        return Objects.equals(maToa, toaTau.maToa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maToa);
    }
}
