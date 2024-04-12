package entity;

import java.util.Objects;

public class Tau {
    private String maTau;
    private String tenTau;

    public Tau() {
    }

    public Tau(String maTau) {
        this.maTau = maTau;
    }

    public Tau(String maTau, String tenTau) {
        this.maTau = maTau;
        this.tenTau = tenTau;
    }

    public String getMaTau() {
        return maTau;
    }

    public void setMaTau(String maTau) {
        this.maTau = maTau;
    }

    public String getTenTau() {
        return tenTau;
    }

    public void setTenTau(String tenTau) {
        this.tenTau = tenTau;
    }

    @Override
    public String toString() {
        return "Tau{" +
                "maTau='" + maTau + '\'' +
                ", tenTau='" + tenTau + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tau tau = (Tau) o;
        return Objects.equals(maTau, tau.maTau);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maTau);
    }
}
