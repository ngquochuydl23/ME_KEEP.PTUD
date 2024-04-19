package ui.dialog.khuyenMaiDialog;

import entity.KhuyenMai;

@FunctionalInterface
public interface TaoKhuyenMaiListener {
    void taoKhuyenMaiThanhCong(KhuyenMai khuyenMai);
}
