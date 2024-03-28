package event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import GUI.Main;
import GUI.Panel.KhachHang;

public class KhachHangEvent extends KhachHang {
    public KhachHangEvent(Main m) {
        super(m);

        search.cbxChoose.addItemListener(this);

        search.btnReset.addActionListener((ActionEvent e) -> {
            search.txtSearchForm.setText("");
            loadDataTable();
        });

        // search.txtSearchForm.addKeyListener(new KeyAdapter() {
        //     @Override
        //     public void keyReleased(KeyEvent e) {
        //         String txt = search.txtSearchForm.getText();
        //         String type = (String) search.cbxChoose.getSelectedItem();
        //         listkh = search(txt, type);
        //         loadDataTable();
        //     }
        // });
    }

    public List<entity.KhachHang> search(String text, String type) {
        List<entity.KhachHang> result = new ArrayList<>();
        text = text.toLowerCase();
        switch (type) {
            case "Tất cả" -> {
                for (entity.KhachHang i : this.listkh) {
                    if (Integer.toString(i.getMaKhachHang()).toLowerCase().contains(text)
                            || i.getHoTen().toLowerCase().contains(text) || i.laKhachHangThanThiet() == true
                            || i.getSoDienThoai().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Mã khách hàng" -> {
                for (entity.KhachHang i : this.listkh) {
                    if (Integer.toString(i.getMaKhachHang()).toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Tên khách hàng" -> {
                for (entity.KhachHang i : this.listkh) {
                    if (i.getHoTen().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
            case "Khách hàng thân thiết" -> {
                for (entity.KhachHang i : this.listkh) {
                    if (i.laKhachHangThanThiet()) {
                        result.add(i);
                    }
                }
            }
            case "Số điện thoại" -> {
                for (entity.KhachHang i : this.listkh) {
                    if (i.getSoDienThoai().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
        }

        return result;
    }
}
