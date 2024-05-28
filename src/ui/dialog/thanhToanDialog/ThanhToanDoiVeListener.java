package ui.dialog.thanhToanDialog;

import entity.HoaDon;
import entity.Ve;

@FunctionalInterface
public interface ThanhToanDoiVeListener {
    void thanhToanDoiVe(HoaDon hoaDon, Ve ve);
}