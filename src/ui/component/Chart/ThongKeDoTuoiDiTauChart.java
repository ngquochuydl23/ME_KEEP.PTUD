package ui.component.Chart;

import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import dao.NhanVienDao;
import dao.VeDao;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Map;

public class ThongKeDoTuoiDiTauChart extends JPanel {
    private JFreeChart chart;
    private RingPlot plot;
    private VeDao veDao;
    private DefaultPieDataset dataset;

    public ThongKeDoTuoiDiTauChart() {
        setSize(new Dimension(getWidth(), 400));
        veDao = new VeDao();
        dataset = new DefaultPieDataset();

        chart = ChartFactory.createRingChart("Thống kê theo độ tuổi đi tàu", dataset, true, true, true);
        chart.setBorderPaint(Color.white);
        chart.setBorderVisible(false);
        chart.setBackgroundPaint(Color.white);
        TextTitle textTitle = new TextTitle("Thống kê theo độ tuổi đi tàu");
        textTitle.setFont(new Font(FlatRobotoFont.FAMILY, 1, 16));;
        chart.setTitle(textTitle);

        plot = (RingPlot) chart.getPlot();
        plot.setSectionDepth(0.3);
        plot.setBackgroundPaint(Color.white);
        plot.setOutlineStroke(null);
        plot.setBackgroundPaint(null);
        plot.setOutlineVisible(false);
        plot.setSectionOutlinesVisible(false);
        plot.setSimpleLabels(true);
        plot.setShadowPaint(null);
        plot.setLabelOutlinePaint(null);
        plot.setSectionPaint("Dưới 18 tuổi", new Color(167,229,254,255));
        plot.setSectionPaint("Từ 18 đến dưới 60 tuổi", new Color(254,204,55,255));
        plot.setSectionPaint("Trên 60 tuổi", new Color(195,157,252,255));


        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setForeground(Color.white);
        chartPanel.setBackground(Color.white);
        chartPanel.setPreferredSize(new Dimension((int) getPreferredSize().getWidth(), 400));
        setLayout(new BorderLayout());

        add(chartPanel, BorderLayout.CENTER);
    }

    public void loadDataset(LocalDate from, LocalDate to) {
        dataset.clear();
        Map<String, Long> mapDataset = veDao.thongKeDoTuoiDiTau(from, to);

        for (Map.Entry<String, Long> entry : mapDataset.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

    }
}
