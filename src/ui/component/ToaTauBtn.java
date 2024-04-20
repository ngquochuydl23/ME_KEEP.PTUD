package ui.component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;
import entity.LoaiKhoang;
import entity.Slot;
import entity.ToaTau;

public class ToaTauBtn extends JButton implements MouseListener {
    private List<KhoangBtn> khoangBtns;
    private ToaTau toaTau;
    private JPanel container;
    private boolean isSeleted;

    public ToaTauBtn(ToaTau toaTau) {
        this.toaTau = toaTau;
        khoangBtns = new ArrayList<>();
        initComponent(toaTau.getTenToa());

    }

    private void initComponent(String tenToa) {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/icon/railway-carriage.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel iconLabel = new JLabel(scaledIcon);
        JLabel textLabel = new JLabel(tenToa);

        // Tạo một panel để chứa biểu tượng và văn bản
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); // Đặt layout là FlowLayout với canh giữa và không có// khoảng cách
        container.putClientProperty( FlatClientProperties.STYLE,
                "background: tint(@background,50%);" +
                        "border: 16,16,16,16,shade(@background,10%),,8;");
        container.add(iconLabel);
        container.add(textLabel);

        // Đặt nút trong suốt và các thuộc tính khác
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Đặt panel làm nội dung của JButton
        setLayout(new BorderLayout());
        add(container, BorderLayout.CENTER);
//        addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                setSeleted(!isSeleted);
//            }
//        });
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



    public void setSeleted(boolean isSeleted) {
        this.isSeleted = isSeleted;

        if (isSeleted) {
            container.putClientProperty( FlatClientProperties.STYLE,
                    "background: rgb(199, 18, 190);" +
                            "border: 16,16,16,16,shade(rgb(199, 18, 190),10%),,8;");
        } else {
            container.putClientProperty( FlatClientProperties.STYLE,
                    "background: tint(@background,50%);" +
                            "border: 16,16,16,16,shade(@background,10%),,8;");
        }

        container.repaint();
        container.revalidate();
    }

    public ToaTau getToaTau() {
        return toaTau;
    }

    public void setToaTau(ToaTau toaTau) {
        this.toaTau = toaTau;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
       setSeleted(!isSeleted);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println(toaTau.getMaToa() +  " exit");
        //setSeleted(!isSeleted);
    }
}