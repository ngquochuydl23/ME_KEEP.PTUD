package ui.component;

import entity.NhanVien;
import singleton.NhanVienSuDungSingleton;

import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

public final class ChucNangChinh extends JToolBar {

    public HashMap<String, ButtonToolBar> btn = new HashMap<>();

    public ChucNangChinh(String chucnang, String[] listBtn) {
        initData();
        initComponent(chucnang, listBtn);
    }

    public ChucNangChinh(String[] listBtn) {
        NhanVien nhanVien = NhanVienSuDungSingleton.layThongTinNhanVienHienTai();
        this.setBackground(Color.WHITE);
        this.setRollover(true);
        if (nhanVien == null) {
            return;
        }

        initData();
        for (String accessedBtn : listBtn) {
            if (btn.get(accessedBtn) != null) {
                ButtonToolBar btnElement = btn.get(accessedBtn);
                btnElement.setEnabled(true);
                this.add(btnElement);
            }
        }
    }

    public void initData() {
        btn.put("them", new ButtonToolBar("THÊM", "add.svg", "create"));
        btn.put("xoa", new ButtonToolBar("XÓA", "delete.svg", "delete"));
        btn.put("sua", new ButtonToolBar("SỬA", "edit.svg", "update"));
        btn.put("huy-ve", new ButtonToolBar("HUỶ VÉ", "cancel.svg", "delete"));
        btn.put("chi-tiet", new ButtonToolBar("CHI TIẾT", "detail.svg", "view"));
        btn.put("nhap-excel", new ButtonToolBar("NHẬP EXCEL", "import_excel.svg", "create"));
        btn.put("xuat-excel", new ButtonToolBar("XUẤT EXCEL", "export_excel.svg", "view"));
        btn.put("xem-danh-sach", new ButtonToolBar("XEM DS", "phone.svg", "view"));
        btn.put("find", new ButtonToolBar("TÌM CHUYẾN", "search.svg", "find"));
        btn.put("find-customer", new ButtonToolBar("KHÁCH HÀNG", "customer-bigger.svg", "find"));
    }

    public ButtonToolBar getToolbar(String title) {
        return btn.get(title);
    }

    private void initComponent(String chucnang, String[] listBtn) {
        this.setBackground(Color.WHITE);
        this.setRollover(true);
        initData();
        for (String accessedBtn : listBtn) {
            if (btn.get(accessedBtn) != null) {
                ButtonToolBar btnElement = btn.get(accessedBtn);
                btnElement.setEnabled(true);
                this.add(btnElement);
            }
        }
    }
}
