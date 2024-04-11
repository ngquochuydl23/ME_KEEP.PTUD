package ui.dialog.khachHangDialog;

import entity.KhachHang;

@FunctionalInterface
public interface SuaKhachHangListener {
    void suaKhachHangThanhCong(KhachHang khachHang);
}
