package ui;

import ui.component.InputForm;
import ui.dialog.QuenMatKhau;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import dao.NhanVienDao;
import entity.NhanVien;
import singleton.NhanVienSuDungSingleton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EmptyBorder;

public class DangNhapForm extends JFrame implements KeyListener {

    private JPanel pnlMain, pnlLogIn;
    private JLabel lblImage, lbl3, lbl6, lbl7;
    private InputForm matKhauInputForm, soDienThoaiInputForm;
    private NhanVienDao nhanVienDao;
    private Color FontColor = new Color(96, 125, 139);

    public DangNhapForm() {
        initComponent();
        nhanVienDao = new NhanVienDao();



        soDienThoaiInputForm.setText("0868684961");
        matKhauInputForm.setPass("12345678");
    }

    private void initComponent() {
        this.setSize(new Dimension(1000, 500));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 0));
        this.setTitle("Đăng nhập");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame jf = this;

        imgIntro();

        pnlMain = new JPanel();
        pnlMain.setBackground(Color.white);
        pnlMain.setBorder(new EmptyBorder(20, 0, 0, 0));

        pnlMain.setPreferredSize(new Dimension(500, 740));
        pnlMain.setLayout(new FlowLayout(1, 0, 10));

        lbl3 = new JLabel("ĐĂNG NHẬP VÀO HỆ THỐNG");
        lbl3.setFont(new Font(FlatRobotoFont.FAMILY_SEMIBOLD, Font.BOLD, 20));
        pnlMain.add(lbl3);

        JPanel paneldn = new JPanel();
        paneldn.setBackground(Color.BLACK);
        paneldn.setPreferredSize(new Dimension(400, 200));
        paneldn.setLayout(new GridLayout(2, 1));

        soDienThoaiInputForm = new InputForm("Số điện thoại");
        paneldn.add(soDienThoaiInputForm);

        matKhauInputForm = new InputForm("Mật khẩu", "password");
        paneldn.add(matKhauInputForm);

        soDienThoaiInputForm.getTxtForm().addKeyListener(this);
        matKhauInputForm.getTxtPass().addKeyListener(this);

        pnlMain.add(paneldn);

        lbl6 = new JLabel("ĐĂNG NHẬP");
        lbl6.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 16));
        lbl6.setForeground(Color.white);

        pnlLogIn = new JPanel();
        pnlLogIn.putClientProperty(FlatClientProperties.STYLE, "arc: 99");
        pnlLogIn.setBackground(Color.BLACK);
        pnlLogIn.setPreferredSize(new Dimension(380, 45));
        pnlLogIn.setLayout(new FlowLayout(1, 0, 15));

        pnlLogIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                pnlLogInMouseEntered(evt);
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                try {
                    pnlLogInMousePressed(evt);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(DangNhapForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                pnlLogInMouseExited(evt);
            }
        });
        pnlLogIn.add(lbl6);

        lbl7 = new JLabel("Quên mật khẩu", JLabel.RIGHT);
        lbl7.setPreferredSize(new Dimension(380, 50));
        lbl7.setFont(new Font(FlatRobotoFont.FAMILY, Font.ITALIC, 18));
        lbl7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                QuenMatKhau qmk = new QuenMatKhau(jf, true);
                qmk.setVisible(true);
            }
        });
        pnlMain.add(lbl7);
        pnlMain.add(pnlLogIn);

        this.add(pnlMain, BorderLayout.EAST);

    }

    public void checkLogin() throws UnsupportedLookAndFeelException {
        String soDienThoai = soDienThoaiInputForm.getText().trim();
        String matKhau = matKhauInputForm.getPass();

        if (soDienThoai.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin đầy đủ", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        NhanVien nhanVien = nhanVienDao.timNhanVienTheoSdt(soDienThoai);
        if (nhanVien == null) {
            JOptionPane.showMessageDialog(this, "Tài khoản của bạn không tồn tại trên hệ thống", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!nhanVien.getMatKhau().equals(matKhau)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không khớp", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        NhanVienSuDungSingleton.setThongTinNhanVienHienTai(nhanVien);
        new Main().setVisible(true);
        this.dispose();
    }

    private void pnlLogInMousePressed(MouseEvent e) throws UnsupportedLookAndFeelException {
        try {
            checkLogin();
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(DangNhapForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pnlLogInMouseEntered(MouseEvent e) {
        pnlLogIn.setBackground(FontColor);
        pnlLogIn.setForeground(Color.black);
    }

    private void pnlLogInMouseExited(MouseEvent e) {

        pnlLogIn.setBackground(Color.BLACK);
        pnlLogIn.setForeground(Color.white);
    }

    public void imgIntro() {
        JPanel bo = new JPanel();
        bo.setBorder(new EmptyBorder(3, 10, 5, 5));
        bo.setPreferredSize(new Dimension(500, 740));
        bo.setBackground(Color.white);
        this.add(bo, BorderLayout.WEST);

        lblImage = new JLabel();
//        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phone2.jpg")));
        lblImage.setIcon(new FlatSVGIcon("./img/login-image.svg"));
        bo.add(lblImage);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                checkLogin();
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(DangNhapForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
