package ui.dialog.khuyenMaiDialog;

import entity.KhuyenMai;

@FunctionalInterface
public interface SuaKhuyenMaiListener {
    void suaKhuyenMaiThanhCong(KhuyenMai khuyenMai);
}
