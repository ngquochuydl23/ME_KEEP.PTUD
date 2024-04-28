package ui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JDialog;
import config.DatabaseUtil;
import ui.component.Chart.ThongKeDoanhSoBanNhanVienChart;


public class ThongKeNhanVienDialog extends JDialog{

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
}
