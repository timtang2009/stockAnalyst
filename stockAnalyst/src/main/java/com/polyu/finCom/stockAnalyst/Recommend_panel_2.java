package com.polyu.finCom.stockAnalyst;

import com.polyu.finCom.Model.Stock;
import com.polyu.finCom.Model.StockInfo;
import com.polyu.finCom.Toaster.PanelService;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Recommend_panel_2{
    public JPanel getPanel() {
        return panel;
    }

    private JPanel panel;

    private Object[] columnNames;
    private Object[][] rowData;
    private JTable table;

    private Object[][] optimized_rowData;
    private JTable table2;


    private JScrollPane scrollPane;
    private JScrollPane scrollPane2;

    private JLabel customize;
    private JLabel optimize;

    private StockInfo risk_free_rate;
    private StockInfo portfolio;

    //百分数
    NumberFormat percentInstance = NumberFormat.getPercentInstance();


    public JButton getjButton() {
        return jButton;
    }

    private JButton jButton;

    List<StockInfo> stockInfos = new ArrayList<>();
    List<StockInfo> calculated_stockInfos;
    List<StockInfo> optimized_stockInfos;
    PanelService panelService = new PanelService();

    public void setStockInfos(StockInfo[] stockInfos) {

        //所有Stock信息和Risk Free Offset和Portfolio
        rowData = new Object[stockInfos.length+1][columnNames.length];
        for (int row = 0; row < stockInfos.length; row++) {
            init_set_value(stockInfos[row],row);
            this.stockInfos.add(stockInfos[row]);
        }

        //Portfolio
        rowData[stockInfos.length][0] = "Portfolio";

    }

    public void setOptimized_stockInfos(){

        // Optimized Portfolio
        optimized_stockInfos = panelService.getOptimization(this.stockInfos);

        optimized_rowData = new Object[optimized_stockInfos.size()][columnNames.length];


        for (int i = 0; i <optimized_stockInfos.size()-1; i++) {
            optimized_rowData[i][0] = optimized_stockInfos.get(i).getTicker();
            optimized_rowData[i][1] = optimized_stockInfos.get(i).getWeight();
            optimized_rowData[i][2] = optimized_stockInfos.get(i).getStartDate();
            optimized_rowData[i][3] = optimized_stockInfos.get(i).getEndDate();
            optimized_rowData[i][4] = percentInstance.format(optimized_stockInfos.get(i).getAnnualRate());
            optimized_rowData[i][5] = optimized_stockInfos.get(i).getRisk();
            optimized_rowData[i][6] = optimized_stockInfos.get(i).getBeta();
        }

        optimized_rowData[optimized_stockInfos.size()-1][0] = optimized_stockInfos.get(optimized_stockInfos.size()-1).getTicker();
        optimized_rowData[optimized_stockInfos.size()-1][1] = optimized_stockInfos.get(optimized_stockInfos.size()-1).getWeight();
        optimized_rowData[optimized_stockInfos.size()-1][4] = percentInstance.format(optimized_stockInfos.get(optimized_stockInfos.size()-1).getAnnualRate());
        optimized_rowData[optimized_stockInfos.size()-1][5] = optimized_stockInfos.get(optimized_stockInfos.size()-1).getRisk();
        optimized_rowData[optimized_stockInfos.size()-1][6] = optimized_stockInfos.get(optimized_stockInfos.size()-1).getBeta();

        //自定义表格模型，创建一个表格
        table2 = new JTable(new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return optimized_rowData.length;
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
                return optimized_rowData[rowIndex][columnIndex];
            }

            @Override
            public void setValueAt(Object newValue, int rowIndex, int columnIndex) {
                // 设置新的单元格数据时，必须把新值设置到原数据数值中，
                // 待更新UI重新调用 getValueAt(...) 获取单元格值时才能获取到最新值
                optimized_rowData[rowIndex][columnIndex] = newValue;
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
        table2.setFont(new Font(null, Font.PLAIN, 12));      // 字体样式
        table2.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
        table2.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
        table2.setGridColor(Color.GRAY);                     // 网格颜色

        // 设置表头
        table2.getTableHeader().setFont(new Font(null, Font.BOLD, 12));  // 设置表头名称字体样式
        table2.getTableHeader().setForeground(Color.RED);                // 设置表头名称字体颜色
        table2.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        table2.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

        // 设置行高
        table2.setRowHeight(30);

        // 第一列列宽设置为40
        table2.getColumnModel().getColumn(0).setPreferredWidth(50);

        // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
        table2.setPreferredScrollableViewportSize(new Dimension(930, 300));

        // 把 表格 放到 滚动面板 中（表头将自动添加到滚动面板顶部）
        scrollPane2 = new JScrollPane(table2);

        scrollPane2.setLocation(30,440);
        scrollPane2.setSize(930,300);

        optimize = new JLabel("Optimized portfolio");
        optimize.setFont(new Font("TimesRoman",Font.BOLD,16));
        optimize.setLocation(455,380);
        optimize.setSize(170,50);

        panel.add(optimize);
        panel.add(scrollPane2);
        panel.validate();
        panel.repaint();

    }

    public Recommend_panel_2(){
        init1();
    }

    public void init1(){
        panel = new JPanel(null);
        // 表头（列名）
        columnNames = new Object[]{"Name", "Weight", "Start Date", "End Date", "Return Rate(annual)", "Risk", "β"};

        jButton = new JButton("Optimize portfolio");
        jButton.setFont(new Font("TimesRoman",Font.BOLD,16));
        jButton.setLocation(970,340);
        jButton.setSize(170,100);

        customize = new JLabel("Customized portfolio");
        customize.setFont(new Font("TimesRoman",Font.BOLD,16));
        customize.setLocation(455,5);
        customize.setSize(170,50);

        percentInstance.setMaximumFractionDigits(2); // 保留小数两位


        panel.add(jButton);
        panel.add(customize);


    }

    public void init2(){

         //自定义表格模型，创建一个表格
        table = new JTable(new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return rowData.length;
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
                //只有weight一列可以改动
                return columnIndex == 1;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return rowData[rowIndex][columnIndex];
            }

            @Override
            public void setValueAt(Object newValue, int rowIndex, int columnIndex) {
                // 设置新的单元格数据时，必须把新值设置到原数据数值中，
                // 待更新UI重新调用 getValueAt(...) 获取单元格值时才能获取到最新值
                rowData[rowIndex][columnIndex] = newValue;
                // 设置完数据后，必须通知表格去更新UI（重绘单元格），否则显示的数据不会改变
                fireTableCellUpdated(rowIndex, columnIndex);
            }

            @Override
            public void fireTableRowsInserted(int firstRow, int lastRow) {
                super.fireTableRowsInserted(firstRow, lastRow);
            }
        });

        TableModel tableModel = table.getModel();
        // 在 表格模型上 添加 数据改变监听器
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

                // 获取被改变的列
                int column = e.getColumn();

                // 事件的类型，可能的值有:
                //     TableModelEvent.INSERT   新行或新列的添加
                //     TableModelEvent.UPDATE   现有数据的更改
                //     TableModelEvent.DELETE   有行或列被移除
                int type = e.getType();

                // 针对 现有数据的更改 更新其他单元格数据
                //当weight等于1时才进行输出
                //其他时候不作任何输出
                if (type == TableModelEvent.UPDATE) {
                    double weight_sum = 0;

                    // 只处理weight这一列
                    // 最后一行是总数
                    if (column == 1 && e.getLastRow() < tableModel.getRowCount()-1){
                        for (int row = 0; row < tableModel.getRowCount()-1; row++){
                            String value = tableModel.getValueAt(row,column).toString();
                            if (isNumber(value) && value != null){
                                Double weight = Double.parseDouble(value);
                                weight_sum = weight_sum + weight;
                            }
                        }
                        BigDecimal b = new BigDecimal(weight_sum);
                        double weight_sum_aprox = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                        tableModel.setValueAt(weight_sum_aprox,tableModel.getRowCount()-1,column);
                        if (weight_sum_aprox == 1.0){
                            for (int i = 0; i < stockInfos.size(); i++) {
                                stockInfos.get(i).setWeight(Double.parseDouble(tableModel.getValueAt(i,column).toString()));

                            }
                            calculated_stockInfos = panelService.getBatchInterest(stockInfos);
                            StockInfo portfolio = calculated_stockInfos.get(calculated_stockInfos.size()-1);
                            tableModel.setValueAt(percentInstance.format(portfolio.getAnnualRate()),tableModel.getRowCount()-1,4);
                            tableModel.setValueAt(portfolio.getRisk(),tableModel.getRowCount()-1,5);
                            tableModel.setValueAt(portfolio.getBeta(),tableModel.getRowCount()-1,6);

                        }
                    }
                }
            }
        });

        // 设置表格内容颜色
        table.setForeground(Color.BLACK);                   // 字体颜色
        table.setFont(new Font(null, Font.PLAIN, 12));      // 字体样式
        table.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
        table.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
        table.setGridColor(Color.GRAY);                     // 网格颜色

        // 设置表头
        table.getTableHeader().setFont(new Font(null, Font.BOLD, 12));  // 设置表头名称字体样式
        table.getTableHeader().setForeground(Color.RED);                // 设置表头名称字体颜色
        table.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        table.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

        // 设置行高
        table.setRowHeight(30);

        // 第一列列宽设置为40
        table.getColumnModel().getColumn(0).setPreferredWidth(50);

        // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
        table.setPreferredScrollableViewportSize(new Dimension(930, 300));

        // 把 表格 放到 滚动面板 中（表头将自动添加到滚动面板顶部）
        scrollPane = new JScrollPane(table);

        scrollPane.setLocation(30,65);
        scrollPane.setSize(930,300);

        // 添加 滚动面板 到 内容面板
        panel.add(scrollPane);

    }

    private boolean isNumber (Object obj) {
        if (obj instanceof Number) {
            return true;
        } else if (obj instanceof String){
            try{
                Double.parseDouble((String)obj);
                return true;
            }catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    private void init_set_value(StockInfo stockInfo,int rowIndex){
        //设置一行的数据，weight都设为0，weight都需要手动修改
        // Stock的Beta修改
        rowData[rowIndex][0] =stockInfo.getTicker();
        rowData[rowIndex][1] =stockInfo.getWeight();
        rowData[rowIndex][2] =stockInfo.getStartDate();
        rowData[rowIndex][3] =stockInfo.getEndDate();
        rowData[rowIndex][4] =percentInstance.format(stockInfo.getAnnualRate());
        rowData[rowIndex][5] =stockInfo.getRisk();
        rowData[rowIndex][6] =panelService.getStockBeta(stockInfo.getTicker(),stockInfo.getStartDate(),stockInfo.getEndDate());
    }
}
