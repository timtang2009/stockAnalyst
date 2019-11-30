package com.polyu.finCom.stockAnalyst;

import com.polyu.finCom.Model.StockInfo;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class Load_panel_now_2 {
    public JPanel getjPanel() {
        return jPanel;
    }

    private JPanel jPanel;
    private StockInfo stockInfo;

    public JFreeCandlestickChart getJfreeCandlestickChart() {
        return jfreeCandlestickChart;
    }

    private JFreeCandlestickChart jfreeCandlestickChart;

    public StockInfo getStockInfo() {
        return stockInfo;
    }

    public void setStockInfo(StockInfo stockInfo) {
        this.stockInfo = stockInfo;
    }



    private void init1(){
        jPanel = new JPanel(null);
    }

    public void init2(){
        //Create and set up the chart.
        jfreeCandlestickChart = new JFreeCandlestickChart(stockInfo.getTicker());
        jfreeCandlestickChart.setLocation(5,5);
        jfreeCandlestickChart.setSize(1100,500);
        jPanel.add(jfreeCandlestickChart);
        create_form();
    }

    public  Load_panel_now_2(){
        init1();
    }

    //文本反馈数据
    public void create_form(){
        // 表头（列名）
        Object[] columnNames = new Object[]{"Name", "Start Date", "End Date", "Return Rate", "Risk", "Sharp ratio"};
        Object[][] table_rowData = new Object[1][columnNames.length];

        table_rowData[0][0] = stockInfo.getTicker();
        table_rowData[0][1] = stockInfo.getStartDate();
        table_rowData[0][2] = stockInfo.getEndDate();
        table_rowData[0][3] = stockInfo.getReturnRate();
        table_rowData[0][4] = stockInfo.getRisk();
        table_rowData[0][5] = stockInfo.getSharpRatio();


        //自定义表格模型，创建一个表格
        JTable table2 = new JTable(new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return table_rowData.length;
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public String getColumnName(int column) {
                return columnNames[column].toString();
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {

                return false;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return table_rowData[rowIndex][columnIndex];
            }

            @Override
            public void setValueAt(Object newValue, int rowIndex, int columnIndex) {
                // 设置新的单元格数据时，必须把新值设置到原数据数值中，
                // 待更新UI重新调用 getValueAt(...) 获取单元格值时才能获取到最新值
                table_rowData[rowIndex][columnIndex] = newValue;
                // 设置完数据后，必须通知表格去更新UI（重绘单元格），否则显示的数据不会改变
                fireTableCellUpdated(rowIndex, columnIndex);
            }

            @Override
            public void fireTableRowsInserted(int firstRow, int lastRow) {
                super.fireTableRowsInserted(firstRow, lastRow);
            }
        });

        // 设置表格内容颜色
        table2.setForeground(Color.BLACK);                   // 字体颜色
        table2.setFont(new Font(null, Font.PLAIN, 14));      // 字体样式
        table2.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
        table2.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
        table2.setGridColor(Color.GRAY);                     // 网格颜色

        // 设置表头
        table2.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
        table2.getTableHeader().setForeground(Color.RED);                // 设置表头名称字体颜色
        table2.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        table2.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

        // 设置行高
        table2.setRowHeight(30);

        // 第一列列宽设置为40
        table2.getColumnModel().getColumn(0).setPreferredWidth(40);

        // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
        table2.setPreferredScrollableViewportSize(new Dimension(800, 200));

        // 把 表格 放到 滚动面板 中（表头将自动添加到滚动面板顶部）
        JScrollPane scrollPane2 = new JScrollPane(table2);

        scrollPane2.setLocation(200,550);
        scrollPane2.setSize(800,200);

        jPanel.add(scrollPane2);
        jPanel.validate();
        jPanel.repaint();
    }

}
