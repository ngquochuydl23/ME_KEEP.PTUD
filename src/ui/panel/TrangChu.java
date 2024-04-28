package ui.panel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ui.component.ButtonCustom;
import ui.component.Chart.ThongKeDoTuoiDiTauChart;
import ui.component.Chart.ThongKeDoanhSoBanNhanVienChart;
import ui.component.Chart.ThongKeDoanhSoVeDaBanVaHuyChart;
import ui.component.Chart.ThongKeTuyenHuyChart;
import ui.component.InputDate;
import ui.component.PanelShadow;
import com.formdev.flatlaf.FlatIntelliJLaf;
import ui.dialog.timKhachHangDialog.TimKhachHangDialog;

public class TrangChu extends JPanel {

    private ButtonCustom btnXem, btnReset;
    private InputDate ngayBatDauInputDate;
    private InputDate ngayKetThucInputDate;
    private ThongKeDoanhSoBanNhanVienChart thongKeNhanVienPanel;
    private ThongKeDoTuoiDiTauChart thongKeDoTuoiDiTauChart;
    private ThongKeTuyenHuyChart thongKeTuyenHuyChart;
    private ThongKeDoanhSoVeDaBanVaHuyChart thongKeDoanhSoVeDaBanVaHuyChart;

    private void initComponent() {
        setBackground(Color.white);
        setBounds(0, 200, 300, 1200);
        setLayout(new BorderLayout(0, 0));
        setOpaque(true);

        ngayBatDauInputDate = new InputDate("Ngày bắt đầu");
        ngayKetThucInputDate = new InputDate("Ngày kết thúc");
        ngayBatDauInputDate.setDate(LocalDate.now().minusDays(31));
        ngayKetThucInputDate.setDate(LocalDate.now());

        btnXem = new ButtonCustom("Xem thống kê", "success", 14);
        btnXem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dungBieuDo();
            }
        });

        btnReset = new ButtonCustom("Reset", "danger", 14);
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ngayBatDauInputDate.setDate(LocalDate.now().minusDays(31));
                ngayKetThucInputDate.setDate(LocalDate.now());

                dungBieuDo();
            }
        });
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        JPanel header = new JPanel(layout);
        header.add(ngayBatDauInputDate);
        header.add(ngayKetThucInputDate);
        header.add(btnXem);
        header.add(btnReset);
        header.setBackground(Color.WHITE);

        thongKeDoanhSoVeDaBanVaHuyChart = new ThongKeDoanhSoVeDaBanVaHuyChart();
        thongKeNhanVienPanel = new ThongKeDoanhSoBanNhanVienChart();
        thongKeDoTuoiDiTauChart = new ThongKeDoTuoiDiTauChart();
        thongKeTuyenHuyChart = new ThongKeTuyenHuyChart();

        JPanel chartGridGroup = new JPanel(new GridLayout(0 , 3, 10, 10));

        chartGridGroup.add(thongKeDoanhSoVeDaBanVaHuyChart);
        chartGridGroup.add(thongKeDoTuoiDiTauChart);
        chartGridGroup.add(thongKeTuyenHuyChart);
        chartGridGroup.add(new ThongKeDoanhSoBanNhanVienChart());
        chartGridGroup.add(new ThongKeDoanhSoBanNhanVienChart());
        chartGridGroup.add(thongKeNhanVienPanel);



        add(header, BorderLayout.NORTH);
        add(chartGridGroup, BorderLayout.CENTER);
    }

    public TrangChu() {
        initComponent();
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();

        dungBieuDo();
    }

    private void dungBieuDo() {
        try {
            thongKeNhanVienPanel.loadDataset(
                    ngayBatDauInputDate.getDateAsLocalDate(),
                    ngayKetThucInputDate.getDateAsLocalDate()
            );

            thongKeDoTuoiDiTauChart.loadDataset(
                    ngayBatDauInputDate.getDateAsLocalDate(),
                    ngayKetThucInputDate.getDateAsLocalDate()
            );

            thongKeTuyenHuyChart.loadDataset(
                    ngayBatDauInputDate.getDateAsLocalDate(),
                    ngayKetThucInputDate.getDateAsLocalDate()
            );

            thongKeDoanhSoVeDaBanVaHuyChart.loadDataset(
                    ngayBatDauInputDate.getDateAsLocalDate(),
                    ngayKetThucInputDate.getDateAsLocalDate()
            );

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
