package ui.component.Chart;

import dao.NhanVienDao;
import models.ThongKeNhanVienModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Map;

public class ThongKeDoanhSoBanNhanVienChart extends JPanel {

    private JFreeChart chart;
    private CategoryPlot plot;
    private NhanVienDao nhanVienDao;
    private DefaultCategoryDataset dataset;

    public ThongKeDoanhSoBanNhanVienChart() {
        nhanVienDao = new NhanVienDao();
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createBarChart(
                "Thống kê nhân viên bán hàng",
                "Tháng",
                "Doanh số bán của nhân viên đó",
                dataset, PlotOrientation.VERTICAL, false, false, false);
        chart.setBorderPaint(Color.white);
        chart.setBorderVisible(false);
        chart.setBackgroundPaint(Color.white);

        plot = (CategoryPlot) chart.getPlot();

        chart.getCategoryPlot()
                .getRenderer()
                .setSeriesPaint(0, new Color(22, 120, 254));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setForeground(Color.white);
        chartPanel.setBackground(Color.white);

        add(chartPanel);
    }

    public void loadDataset(LocalDate from, LocalDate to) {
        Map<String, Long> mapDataset = nhanVienDao.thongKeBanHangTheoNhanVien(from, to);

        for(Map.Entry<String, Long> entry : mapDataset.entrySet()) {
            dataset.addValue(entry.getValue(), "Doanh thu", entry.getKey());
        }

    }
}
