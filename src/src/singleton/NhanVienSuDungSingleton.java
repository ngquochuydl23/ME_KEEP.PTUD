package singleton;

import entity.NhanVien;

public class NhanVienSuDungSingleton {

    private static NhanVien nhanVien;

    public static void setThongTinNhanVienHienTai(NhanVien nv) {
        nhanVien = nv;
    }

    public static NhanVien layThongTinNhanVienHienTai() {
        return nhanVien;
    }
}
