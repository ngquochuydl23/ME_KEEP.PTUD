package ui.dialog.chonChoDialog;

import entity.ToaTau;
import entity.Ve;

import java.util.List;

@FunctionalInterface
public interface ChonChoNgoiListener {
    void chonChoNgoiThanhCong(ToaTau toaTauChon, List<Integer> dsCho);
}
