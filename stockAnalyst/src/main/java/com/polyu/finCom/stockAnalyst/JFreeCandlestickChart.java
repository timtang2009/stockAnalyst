package com.polyu.finCom.stockAnalyst;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JFreeCandlestickChart extends JPanel {
    private OHLCSeries ohlcSeries;
    private TimeSeries volumeSeries;

    public JFreeCandlestickChart(String title){
        // Create new chart
        final JFreeChart candlestickChart = createChart(title);
        // Create new chart panel
        final ChartPanel chartPanel = new ChartPanel(candlestickChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1100, 500));
        // Enable zooming
        chartPanel.setMouseZoomable(true);
        chartPanel.setMouseWheelEnabled(true);
        add(chartPanel, BorderLayout.CENTER);
    }

    private JFreeChart createChart(String chartTitle){
        /**
         * Creating candlestick subplot
         */
        // Create OHLCSeriesCollection as a price dataset for candlestick chart
        OHLCSeriesCollection candlestickDataset = new OHLCSeriesCollection();
        ohlcSeries = new OHLCSeries("Price");
        candlestickDataset.addSeries(ohlcSeries);
        // Create candlestick chart priceAxis
        NumberAxis priceAxis = new NumberAxis("Price");
        priceAxis.setAutoRangeIncludesZero(false);
        CandlestickRenderer candlestickRenderer = new CandlestickRenderer(CandlestickRenderer.WIDTHMETHOD_AVERAGE,false,new CustomHighLowItemLabelGenerator(new SimpleDateFormat("YYYYMMDD"),new DecimalFormat("0.0000")));
        XYPlot candlestickSubplot = new XYPlot(candlestickDataset, null, priceAxis, candlestickRenderer);
        candlestickSubplot.setBackgroundPaint(Color.white);

        /**
         * Creating volume subplot
         */
        // creates TimeSeriesCollection as a volume dataset for volume chart
        TimeSeriesCollection volumeDataset = new TimeSeriesCollection();
        volumeSeries = new TimeSeries("Volume");
        volumeDataset.addSeries(volumeSeries);
        // Create volume chart volumeAxis
        NumberAxis volumeAxis = new NumberAxis("Volume");
        volumeAxis.setAutoRangeIncludesZero(false);
        // Set to no decimal
        volumeAxis.setNumberFormatOverride(new DecimalFormat("0"));
        // Create volume chart renderer
        XYBarRenderer timeRenderer = new XYBarRenderer();
        timeRenderer.setShadowVisible(false);
        timeRenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator("Volume--> Time={1} Size={2}",
                new SimpleDateFormat("YYYYMMDD"), new DecimalFormat("0")));
        // Create volumeSubplot
        XYPlot volumeSubplot = new XYPlot(volumeDataset, null, volumeAxis, timeRenderer);
        volumeSubplot.setBackgroundPaint(Color.white);

        /**
         * Create chart main plot with two subplots (candlestickSubplot,
         * volumeSubplot) and one common dateAxis
         */
        // Creating charts common dateAxis
        DateAxis dateAxis = new DateAxis("Time");
        dateAxis.setDateFormatOverride(new SimpleDateFormat("YYYYMMDD"));
        // reduce the default left/right margin from 0.05 to 0.02
        dateAxis.setLowerMargin(0.02);
        dateAxis.setUpperMargin(0.02);
        // Create mainPlot
        CombinedDomainXYPlot mainPlot = new CombinedDomainXYPlot(dateAxis);
        mainPlot.setGap(10.0);
        mainPlot.add(candlestickSubplot, 3);
        mainPlot.add(volumeSubplot, 1);
        mainPlot.setOrientation(PlotOrientation.VERTICAL);

        JFreeChart chart = new JFreeChart(chartTitle, JFreeChart.DEFAULT_TITLE_FONT, mainPlot, true);
        chart.removeLegend();
        return chart;
    }

    public void addCandel(Day day, double o, double h, double l, double c, long v) {
        try {
            ohlcSeries.add(day, o, h, l, c);
            volumeSeries.add(day, v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
