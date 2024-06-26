package ui.component.Chart;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import dao.NhanVienDao;
import models.ThongKeNhanVienModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
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
        setSize(new Dimension(getWidth(), 400));
        nhanVienDao = new NhanVienDao();
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createBarChart(
                "Thống kê nhân viên bán hàng",
                "Tên nhân viên",
                "Doanh số bán của nhân viên đó",
                dataset, PlotOrientation.VERTICAL, false, false, false);

        TextTitle textTitle = new TextTitle("Thống kê nhân viên bán hàng");
        textTitle.setFont(new Font(FlatRobotoFont.FAMILY, 1, 16));;
        chart.setTitle(textTitle);
        chart.setBorderPaint(Color.white);
        chart.setBorderVisible(false);
        chart.setBackgroundPaint(Color.white);

        plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);

        plot.setOutlineStroke(null);
        chart.getCategoryPlot()
                .getRenderer()
                .setSeriesPaint(0, new Color(255, 174, 66));
        BarRenderer.setDefaultBarPainter(new StandardBarPainter());
        ((BarRenderer) plot.getRenderer()).setBarPainter(new StandardBarPainter());



        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setForeground(Color.white);
        chartPanel.setBackground(Color.white);
        chartPanel.setPreferredSize(new Dimension((int) getPreferredSize().getWidth(), 400));
        setLayout(new BorderLayout());

        add(chartPanel, BorderLayout.CENTER);
    }

    public void loadDataset(LocalDate from, LocalDate to) {
        dataset.clear();
        Map<String, Long> mapDataset = nhanVienDao.thongKeBanHangTheoNhanVien(from, to);

        for(Map.Entry<String, Long> entry : mapDataset.entrySet()) {
            dataset.addValue(entry.getValue(), "Tên nhân viên", entry.getKey());
        }

    }
}
