package ui.dialog.timKhachHangDialog;

import entity.KhachHang;

public interface TimKhachHangListener {
    void timThayhachhang(KhachHang khachHang);

    void khongTimThayKhachHang(String soDienThoai);
}
