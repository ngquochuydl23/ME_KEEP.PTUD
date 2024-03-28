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

        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String txt = search.txtSearchForm.getText();
                String type = (String) search.cbxChoose.getSelectedItem();
                listkh = search(txt, type);
                loadDataTable();
            }
        });
    }

    
}
