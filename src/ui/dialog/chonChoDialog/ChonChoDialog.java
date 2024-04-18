package ui.dialog.chonChoDialog;

import dao.SlotDao;
import entity.*;
import ui.component.*;

import javax.swing.*;

import dao.LoaiKhoangDao;
import dao.ToaTauDao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChonChoDialog extends JDialog {
    private ToaTauBtn toaTauBtn;
    private ButtonGroup btgToa;
    private JPanel topPanel;
    private Tau tau;
    private JPanel seatPanel;
    private ToaTauDao toaTauDao;
    private LoaiKhoangDao loaiKhoangDao;
    private List<ToaTau> danhSachToaTau;
    private LoaiKhoang loaiKhoang;
    private ChonChoNgoiListener chonChoNgoiListener;
    private List<SlotBtn> dsChoNgoiDaChon;
    private List<SlotBtn> danhSachSlot;
    private ToaTau toaDangChon;
    private SlotDao slotDao;

    public ChonChoDialog() {
        setTitle("Chọn chỗ");
        setModalityType(ModalityType.APPLICATION_MODAL);
        initializeComponents();
        setSize(600, 900);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        danhSachSlot = new ArrayList<>();
        danhSachToaTau = new ArrayList<>();
        dsChoNgoiDaChon = new ArrayList<>();
        toaTauDao = new ToaTauDao();
        loaiKhoangDao = new LoaiKhoangDao();
        slotDao = new SlotDao();
    }

    public void setTau(Tau tau) {
        this.tau = tau;
        dsChoNgoiDaChon.clear();
        danhSachToaTau.clear();
        danhSachToaTau = toaTauDao.layToaTheoMaTau(this.tau.getMaTau());
        loaiKhoang = loaiKhoangDao.layLoaiKhoangTheoMaToa(danhSachToaTau.get(0).getMaToa());
        toaDangChon = danhSachToaTau.get(0);

        layTinhTrangSlotTheoToaTau();
        doToaTauLenUI();
        updateSeatPanel();
        pack();
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        btgToa = new ButtonGroup();
        topPanel = new JPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);


        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(0, 4));

        JScrollPane scrollPane = new JScrollPane(seatPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new ButtonCustom("Xác nhận", "success", 14);
        JButton cancelButton = new ButtonCustom("Hủy bỏ", "danger", 14);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                luuChoNgoiDangChon();
                if (chonChoNgoiListener != null) {
                    chonChoNgoiListener.chonChoNgoiThanhCong(toaDangChon, dsChoNgoiDaChon);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.setPreferredSize(new Dimension(800, 600));
        add(mainPanel);
    }

    private void doToaTauLenUI() {
        for (ToaTau toaTau : danhSachToaTau) {
            final ToaTau currentToa = toaTau;
            ToaTauBtn toaTauBtn = new ToaTauBtn(toaTau);
            toaTauBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toaDangChon = currentToa;
                    loaiKhoang = loaiKhoangDao.layLoaiKhoangTheoMaToa(currentToa.getMaToa());

                    luuChoNgoiDangChon();

                    layTinhTrangSlotTheoToaTau();
                    updateSeatPanel();
                }
            });
            this.btgToa.add(toaTauBtn);
            this.topPanel.add(toaTauBtn);
        }

        if (this.toaDangChon == null) {
            this.toaDangChon = this.danhSachToaTau.get(0);
        }
    }


    private void clearSeatSelection() {
        danhSachSlot.clear();
        danhSachToaTau.clear();
        Component[] components = seatPanel.getComponents();
        for (Component component : components) {
            if (component instanceof SlotBtn) {
                ((SlotBtn) component).clearSelection();
            }
        }
    }

    private void updateSeatPanel() {
        String loaiToa = this.loaiKhoang.getMaLoaiKhoang();
        switch (loaiToa) {
            case "ghe-ngoi":
                toaTauBtn = ToaTauBtn.taoToa1Khoang50Slot(toaDangChon, danhSachSlot);
                break;
            case "giuong-nam-khoang-6":
                toaTauBtn = ToaTauBtn.taoToa4Khoang6Giuong(toaDangChon, danhSachSlot);
                break;
            case "giuong-nam-khoang-4":
                toaTauBtn = ToaTauBtn.taoToa4Khoang4Giuong(toaDangChon, danhSachSlot);
                break;
            default:
                toaTauBtn = ToaTauBtn.taoToa1Khoang50Slot(toaDangChon, danhSachSlot);
                return;
        }
        seatPanel.removeAll();
        List<KhoangBtn> dsKhoang = toaTauBtn.getDsKhoang();
        if (loaiToa.equals("giuong-nam-khoang-6") || loaiToa.equals("giuong-nam-khoang-4")) {
            GridLayout gridLayout = new GridLayout(0, 2);
            gridLayout.setHgap(10);
            gridLayout.setVgap(10);
            seatPanel.setLayout(gridLayout);

        } else if (loaiToa.equals("ghe-ngoi")) {
            GridLayout gridLayout = new GridLayout(0, 4);
            gridLayout.setHgap(10);
            gridLayout.setVgap(10);
            seatPanel.setLayout(gridLayout);
        }

        for (KhoangBtn khoangBtn : dsKhoang) {
            for (SlotBtn slotBtn : khoangBtn.getDsSlotBtn()) {
                seatPanel.add(slotBtn);
            }
            seatPanel.add(new JPanel());
            seatPanel.add(new JPanel());
        }


        revalidate();
        repaint();
    }

    public void luuChoNgoiDangChon() {
        List<SlotBtn> dsChoNgoi = new ArrayList<>();
        for (KhoangBtn khoangBtn : toaTauBtn.getDsKhoang()) {
            dsChoNgoi.addAll(khoangBtn.getDsSlotBtn());
        }

        for (SlotBtn slotBtn : dsChoNgoi) {
            String maSlot = slotBtn.getSlot().getMaSlot();
            dsChoNgoiDaChon.removeIf(item -> item.getSlot().getMaSlot().equals(maSlot));
            if (slotBtn.isSelected()) {
                dsChoNgoiDaChon.add(slotBtn);
            }
        }
    }


    public void setChonChoNgoiListener(ChonChoNgoiListener chonChoNgoiListener) {
        this.chonChoNgoiListener = chonChoNgoiListener;
    }

    @Override
    public void setVisible(boolean b) {

        super.setVisible(b);
        clearSeatSelection();
    }

    private void layTinhTrangSlotTheoToaTau() {
        if (toaDangChon != null) {
            danhSachSlot.clear();
            List<SlotBtn> choDangChonTrongToa = dsChoNgoiDaChon
                    .stream()
                    .filter(slotBtn -> slotBtn.getMaToaTau().equals(toaDangChon.getMaToa()))
                    .toList();

            danhSachSlot.addAll(slotDao
                    .layTinhTrangChoNgoiTheoToaTau(toaDangChon.getMaToa())
                    .stream()
                    .map(item -> {
                        String maSlot = item.getMaSlot();
                        AtomicBoolean selected = new AtomicBoolean(false);
                        choDangChonTrongToa
                                .stream()
                                .filter(dangChon -> dangChon.getSlot().getMaSlot().equals(maSlot))
                                .findAny()
                                .ifPresent(dangChon -> selected.set(true));

                        return new SlotBtn(toaDangChon.getMaToa(), item, selected.get());

                    })
                    .toList());




        }
    }
}
