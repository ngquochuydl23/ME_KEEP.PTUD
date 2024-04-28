package ui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import config.DatabaseUtil;
import ui.component.ButtonCustom;
import ui.component.Chart.ThongKeDoanhSoBanNhanVienChart;
import ui.component.HeaderTitle;
import ui.component.InputForm;

public class ThongKeNhanVienDialog extends JDialog{


    private InputForm maHoaDonField, tenKhachHangField, tongTienField, thoiGianTaoHoaDonField;
    private ButtonCustom tatButton;
    private HeaderTitle titlePage;
    private JPanel pnlMain;
    private JPanel pnlButtom;

    private ThongKeDoanhSoBanNhanVienChart thongKeDoanhSoBanNhanVienChart;

    public ThongKeNhanVienDialog() {
        setVisible(false);
        setSize(new Dimension(850, 650));
        setLayout(new BorderLayout(0, 0));

        setBackground(Color.white);
        setForeground(Color.white);
        thongKeDoanhSoBanNhanVienChart = new ThongKeDoanhSoBanNhanVienChart();
        add(thongKeDoanhSoBanNhanVienChart);


        thongKeDoanhSoBanNhanVienChart.loadDataset(
                LocalDate.of(2024, 3, 30),
                LocalDate.of(2024, 4, 30));
    }



    public static void main(String[] args) throws SQLException {
        DatabaseUtil.connect();

        //thongKeBanHangTheoNhanVien
        ThongKeNhanVienDialog dialog = new ThongKeNhanVienDialog();
        dialog.setVisible(true);
    }
}
