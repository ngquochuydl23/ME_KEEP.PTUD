package ui.component.Chart;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import dao.LichSuTraVeDao;
import dao.VeDao;
import org.apache.poi.ss.formula.functions.T;
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
import java.time.Period;
import java.util.Map;

public class ThongKeDoanhSoVeDaBanVaHuyChart extends JPanel {

    private VeDao veDao;
    private DefaultCategoryDataset dataset;
    private CategoryPlot plot;
    private JFreeChart chart;

    public ThongKeDoanhSoVeDaBanVaHuyChart() {
        setSize(new Dimension(getWidth(), 400));
        veDao = new VeDao();



        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createLineChart(
                "Số lượng vé đã huy và bán ra trong",
                null,
                "Doanh số bán của nhân viên đó",
                dataset, PlotOrientation.VERTICAL, false, false, false);
        chart.setBorderPaint(Color.white);
        chart.setBorderVisible(false);

        chart.setBackgroundPaint(Color.white);

        plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setOutlineStroke(null);
        chart.getCategoryPlot()
                .getRenderer()
                .setSeriesPaint(0, new Color(255, 174, 66));



        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setForeground(Color.white);
        chartPanel.setBackground(Color.white);
        chartPanel.setPreferredSize(new Dimension((int) getPreferredSize().getWidth(), 400));
        setLayout(new BorderLayout());

        add(chartPanel, BorderLayout.CENTER);
    }

    public void loadDataset(LocalDate from, LocalDate to) {
        dataset.clear();
        Period period = Period.between(from, to);
        String postfix;
        if (period.getDays() == 0 && period.getMonths() >= 1) {
            postfix = period.getMonths() + " tháng";
        } else if (period.getDays() > 0 && period.getMonths() == 0) {
            postfix = period.getDays() + " ngày";
        } else {
            postfix = "0 ngày";
        }

        TextTitle textTitle = new TextTitle("Số lượng vé đã hủy và bán ra trong "+ postfix);
        textTitle.setFont(new Font(FlatRobotoFont.FAMILY, 1, 16));;
        chart.setTitle(textTitle);

        Map<LocalDate, Long> mapDatasetDaHuy = veDao.laySoLuongVeTheoNgay(from, to, 0);
        Map<LocalDate, Long> mapDatasetDaBan = veDao.laySoLuongVeTheoNgay(from, to, 1);

        for(Map.Entry<LocalDate, Long> entry : mapDatasetDaBan.entrySet()) {
            dataset.addValue(entry.getValue(), "Số lượng vé đã bán", entry.getKey());
        }

        for(Map.Entry<LocalDate, Long> entry : mapDatasetDaHuy.entrySet()) {
            dataset.addValue(entry.getValue(), "Số lượng vé đã hủy", entry.getKey());
        }
    }
}
