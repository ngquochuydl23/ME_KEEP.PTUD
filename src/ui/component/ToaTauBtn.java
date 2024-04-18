package ui.component;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entity.LoaiKhoang;
import entity.Slot;
import entity.ToaTau;

public class ToaTauBtn extends JButton {
    private List<KhoangBtn> khoangBtns;
    private ToaTau toaTau;

    public ToaTauBtn(ToaTau toaTau) {
        this.toaTau = toaTau;
        khoangBtns = new ArrayList<>();
        initComponent(toaTau.getTenToa());

    }

    private void initComponent(String tenToa) {
        // Tạo biểu tượng và văn bản
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/icon/railway-carriage.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel iconLabel = new JLabel(scaledIcon);
        JLabel textLabel = new JLabel(tenToa);

        // Tạo một panel để chứa biểu tượng và văn bản
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Đặt layout là FlowLayout với canh giữa và không có
                                                                 // khoảng cách

        // Thêm biểu tượng và văn bản vào panel
        panel.add(iconLabel);
        panel.add(textLabel);

        // Đặt nút trong suốt và các thuộc tính khác
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Đặt panel làm nội dung của JButton
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

    }

    public static ToaTauBtn taoToa1Khoang50Slot(ToaTau toaTau, List<SlotBtn> dsBtnSlot) {
        ToaTauBtn carriage = new ToaTauBtn(toaTau);
        String maKhoang = dsBtnSlot
                .getFirst()
                .getSlot()
                .getKhoang()
                .getMaKhoang();
        carriage.themKhoangTau(new KhoangBtn(maKhoang, toaTau, dsBtnSlot));
        return carriage;
    }

    public static ToaTauBtn taoToa4Khoang6Giuong(ToaTau toaTau, List<SlotBtn> dsBtnSlot) {
        ToaTauBtn toaTauBtn = new ToaTauBtn(toaTau);

        List<SlotBtn> seatsOfEachCabin = new ArrayList<>();

        for(SlotBtn slotBtn : dsBtnSlot) {
            Slot slot = slotBtn.getSlot();
            seatsOfEachCabin.add(slotBtn);

            if (slot.getSoSlot()  % 6 == 0) {
                toaTauBtn.themKhoangTau(new KhoangBtn(slot.getKhoang().getMaKhoang(),toaTau, new ArrayList<>(seatsOfEachCabin)));
                seatsOfEachCabin.clear();
            }
        }
        return toaTauBtn;
    }

    public static ToaTauBtn taoToa4Khoang4Giuong(ToaTau toaTau, List<SlotBtn> dsBtnSlot) {
        ToaTauBtn toaTauBtn = new ToaTauBtn(toaTau);

        List<SlotBtn> seatsOfEachCabin = new ArrayList<>();

        for(SlotBtn slotBtn : dsBtnSlot) {
            Slot slot = slotBtn.getSlot();
            seatsOfEachCabin.add(slotBtn);

            if (slot.getSoSlot()  % 4 == 0) {
                toaTauBtn.themKhoangTau(new KhoangBtn(slot.getKhoang().getMaKhoang(),toaTau, new ArrayList<>(seatsOfEachCabin)));
                seatsOfEachCabin.clear();
            }
        }
        return toaTauBtn;
    }

    public void themKhoangTau(KhoangBtn cabin) {
        khoangBtns.add(cabin);
    }

    public List<KhoangBtn> getDsKhoang() {
        return khoangBtns;
    }



    public ToaTau getToaTau() {
        return toaTau;
    }

    public void setToaTau(ToaTau toaTau) {
        this.toaTau = toaTau;
    }
}