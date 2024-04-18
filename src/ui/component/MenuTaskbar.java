package ui.component;


import ui.DangNhapForm;
import ui.dialog.TaiKhoanCuaToiPanel;
import ui.Main;
import ui.panel.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import entity.NhanVien;
import singleton.NhanVienSuDungSingleton;

public class MenuTaskbar extends JPanel {

    String[][] getSt = {
            {"Trang chủ", "home.svg", "trangchu"},
            {"Hóa đơn", "product.svg", "sanpham"},
            {"Lịch sử trả vé", "import.svg", "nhaphang"},
            {"Khách hàng", "customer.svg", "khachhang"},
            {"Yêu cầu đổi vé", "supplier.svg", "nhacungcap"},
            {"Nhân viên", "staff.svg", "nhanvien"},
            {"Khuyến mãi", "discount.svg", "khuyenmai"},
//            {"Thống kê", "statistical.svg", "thongke"},
            {"Đăng xuất", "log_out.svg", "dangxuat"},
            {"Bán vé", "import.svg", "banve"}
    };

    Main main;

    public itemTaskbar[] listitem;
    JScrollPane scrollPane;

    JPanel pnlCenter, pnlTop, pnlBottom, bar1, bar2, bar3, bar4;

    Color FontColor = new Color(255, 255, 255);
    Color DefaultColor = new Color(255, 255, 255);
    Color HowerFontColor = new Color(255, 255, 255);
    Color HowerBackgroundColor = new Color(199, 18, 190);

    public MenuTaskbar(Main main) {
        this.main = main;
        initComponent();
    }

    private void initComponent() {
        listitem = new itemTaskbar[getSt.length];
        this.setOpaque(true);
        this.setBackground(DefaultColor);
        this.setLayout(new BorderLayout(0, 0));

        pnlTop = new JPanel();
        pnlTop.setPreferredSize(new Dimension(250, 80));
        pnlTop.setBackground(DefaultColor);
        pnlTop.setLayout(new BorderLayout(0, 0));
        this.add(pnlTop, BorderLayout.NORTH);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BorderLayout(0, 0));
        pnlTop.add(info, BorderLayout.CENTER);
        in4(info);

        bar1 = new JPanel();
        bar1.setBackground(new Color(204, 214, 219));
        bar1.setPreferredSize(new Dimension(1, 0));
        pnlTop.add(bar1, BorderLayout.EAST);

        bar2 = new JPanel();
        bar2.setBackground(new Color(204, 214, 219));
        bar2.setPreferredSize(new Dimension(0, 1));
        pnlTop.add(bar2, BorderLayout.SOUTH);

        pnlCenter = new JPanel();
        pnlCenter.setPreferredSize(new Dimension(230, 600));
        pnlCenter.setBackground(DefaultColor);
        pnlCenter.setLayout(new FlowLayout(0, 0, 5));

        bar3 = new JPanel();
        bar3.setBackground(new Color(204, 214, 219));
        bar3.setPreferredSize(new Dimension(1, 1));
        add(bar3, BorderLayout.EAST);

        scrollPane = new JScrollPane(pnlCenter, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(5, 10, 0, 10));
        add(scrollPane, BorderLayout.CENTER);

        pnlBottom = new JPanel();
        pnlBottom.setPreferredSize(new Dimension(250, 50));
        pnlBottom.setBackground(DefaultColor);
        pnlBottom.setLayout(new BorderLayout(0, 0));

        bar4 = new JPanel();
        bar4.setBackground(new Color(204, 214, 219));
        bar4.setPreferredSize(new Dimension(1, 1));
        pnlBottom.add(bar4, BorderLayout.EAST);

        add(pnlBottom, BorderLayout.SOUTH);

        for (int i = 0; i < getSt.length; i++) {
            if (i + 1 == getSt.length) {
                listitem[i] = new itemTaskbar(getSt[i][1], getSt[i][0]);
                pnlBottom.add(listitem[i]);
            } else {
                listitem[i] = new itemTaskbar(getSt[i][1], getSt[i][0]);
                pnlCenter.add(listitem[i]);
                // if (i != 0) {
                // if (!checkRole(getSt[i][2])) {
                // listitem[i].setVisible(false);
                // }
                // }
            }
        }

        listitem[0].setBackground(HowerBackgroundColor);
        listitem[0].setForeground(HowerFontColor);
        listitem[0].isSelected = true;

        for (int i = 0; i < getSt.length; i++) {
            listitem[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent evt) {
                    pnlMenuTaskbarMousePress(evt);
                }
            });
        }

        listitem[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                main.setPanel(new TrangChu());
            }
        });

        listitem[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                main.setPanel(new HoaDonPanel());
            }
        });
        listitem[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                main.setPanel(new LichSuTraVePanel());
            }
        });
        listitem[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                main.setPanel(new KhachHangPanel());
            }
        });
        listitem[4].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                main.setPanel(new YeuCauDoiVePanel());
            }
        });
        listitem[5].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                main.setPanel(new NhanVienPanel());
            }
        });
        listitem[6].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                main.setPanel(new KhuyenMaiPanel());
            }
        });
//        listitem[7].addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent evt) {
//                //main.setPanel(new ThongKe());
//            }
//        });
        listitem[7].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (JOptionPane.showConfirmDialog(
                        null,
                        "Bạn có chắc chắn muốn đăng xuất?", "Đăng xuất",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE) == 0) {
                    NhanVienSuDungSingleton.setThongTinNhanVienHienTai(null);
                    main.dispose();
                    new DangNhapForm().setVisible(true);
                }
            }
        });
        listitem[8].addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent evt) {
                main.setPanel(new BanVe());
            }
        });
    }

    public void pnlMenuTaskbarMousePress(MouseEvent evt) {

        for (int i = 0; i < getSt.length; i++) {
            if (evt.getSource() == listitem[i]) {
                listitem[i].isSelected = true;
                listitem[i].setBackground(HowerBackgroundColor);
                listitem[i].setForeground(HowerFontColor);
            } else {
                listitem[i].isSelected = false;
                listitem[i].setBackground(DefaultColor);
                listitem[i].setForeground(FontColor);
            }
        }
    }

    public void resetChange() {
        //this.nhanVienDTO = new NhanVienDAO().selectById(String.valueOf(nhanVienDTO.getManv()));
    }

    public void in4(JPanel infoPanel) {
        NhanVien nhanVien = NhanVienSuDungSingleton.layThongTinNhanVienHienTai();

        JLabel lblIcon = new JLabel();
        lblIcon.setPreferredSize(new Dimension(50, 70));
        lblIcon.setIcon(new FlatSVGIcon(nhanVien.getGioitinh() == 1 ? "./icon/man_50px.svg" : "./icon/women_50px.svg"));
        lblIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                // MyAccount ma = new MyAccount(owner, MenuTaskbar.this, "Chỉnh sửa thông tin tài khoản", true);
                new TaiKhoanCuaToiPanel().setVisible(true);
            }
        });

        JPanel pnlIconPanel = new JPanel(new FlowLayout());
        pnlIconPanel.setPreferredSize(new Dimension(60, 0));
        pnlIconPanel.setOpaque(false);
        pnlIconPanel.add(lblIcon);


        Box pnlInfoPanel = Box.createVerticalBox();
        pnlInfoPanel.setBorder(new EmptyBorder(15, 0, 0, 0));


        JLabel lblHoTen = new JLabel(nhanVien.getHoTen());
        lblHoTen.putClientProperty("FlatLaf.style", "font: 150% $semibold.font");

        JLabel lblVaiTro = new JLabel(nhanVien.getVaiTro());
        lblVaiTro.putClientProperty("FlatLaf.style", "font: 120% $light.font");
        lblVaiTro.setForeground(Color.GRAY);
        pnlInfoPanel.add(lblVaiTro);
        pnlInfoPanel.add(lblHoTen);
        pnlInfoPanel.setBorder(new EmptyBorder(20, 20, 0, 0));
        infoPanel.add(pnlIconPanel, BorderLayout.WEST);
        infoPanel.add(pnlInfoPanel, BorderLayout.CENTER);
    }
}
