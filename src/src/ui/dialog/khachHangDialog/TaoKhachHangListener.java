package ui.dialog.khachHangDialog;

import entity.KhachHang;

@FunctionalInterface
public interface TaoKhachHangListener {
    void taoKhachHangThanhCong(KhachHang khachHang);
}
