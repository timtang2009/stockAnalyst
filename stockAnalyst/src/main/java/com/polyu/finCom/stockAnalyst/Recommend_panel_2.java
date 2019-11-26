package com.polyu.finCom.stockAnalyst;

import com.polyu.finCom.Model.StockInfo;
import com.polyu.finCom.Toaster.PanelService;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class Recommend_panel_2 {
    public JPanel getPanel() {
        return panel;
    }

    private JPanel panel;
    private Object[] columnNames;
    private Object[][] rowData;
    private JTable table;
    private TableModel tableModel;
    private JScrollPane scrollPane;
    PanelService panelService = new PanelService();

    public void setStockInfos(StockInfo[] stockInfos) {
        this.stockInfos = stockInfos;
        //所有Stock信息和Risk Free Offset和Portfolio
        rowData = new Object[stockInfos.length+2][columnNames.length];
        for (int row = 0; row < stockInfos.length; row++) {
            init_set_value(stockInfos[row],row);
        }
        //Risk Free Offset
        tableModel.setValueAt("Risk Free Rate",stockInfos.length,0);
        tableModel.setValueAt("",stockInfos.length,1);
        tableModel.setValueAt(0.35,stockInfos.length,4);
        tableModel.setValueAt(0,stockInfos.length,5);

        //Portfolio
        tableModel.setValueAt("Portfolio",stockInfos.length+1,0);

    }

    private StockInfo[] stockInfos;

    public Recommend_panel_2(){
        init();
    }

    private void init(){
        // 创建内容面板
        panel = new JPanel();

        // 表头（列名）
        columnNames = new Object[]{"Name", "Weight", "Start Date", "End Date", "Return Rate", "Risk", "β"};

        // 表格所有行数据
        rowData = new Object[][]{

        };

        // 自定义表格模型，创建一个表格
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

        });

        // 获取 表格模型
        tableModel = table.getModel();

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
                System.out.println("总共行数： " + tableModel.getRowCount());
                // 针对 现有数据的更改 更新其他单元格数据
                //当weight等于1时才进行输出
                //其他时候不作任何输出
                if (type == TableModelEvent.UPDATE) {
                    double weight_sum = 0;
                    // 只处理weight这一列
                    // 最后一行是总数
                    if (column == 1){
                        for (int row = 0; row < tableModel.getRowCount()-1; row++){
                            Object value = tableModel.getValueAt(row,column);
                            if (isNumber(value) && value != null){
                                Double weight = Double.parseDouble(String.valueOf(value));
                                weight_sum = weight_sum + weight;
                            }else {
                                System.out.println(row + "行有问题");
                            }
                        }
                    }
                    if (weight_sum == 1){
                        System.out.println("可以输出");
                    }else if (weight_sum > 1){
                        System.out.println("过大");
                    }

                }
            }
        });

        // 设置表格内容颜色
        table.setForeground(Color.BLACK);                   // 字体颜色
        table.setFont(new Font(null, Font.PLAIN, 14));      // 字体样式
        table.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
        table.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
        table.setGridColor(Color.GRAY);                     // 网格颜色

        // 设置表头
        table.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
        table.getTableHeader().setForeground(Color.RED);                // 设置表头名称字体颜色
        table.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        table.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

        // 设置行高
        table.setRowHeight(30);

        // 第一列列宽设置为40
        table.getColumnModel().getColumn(0).setPreferredWidth(40);

        // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
        table.setPreferredScrollableViewportSize(new Dimension(600, 300));

        // 把 表格 放到 滚动面板 中（表头将自动添加到滚动面板顶部）
        JScrollPane scrollPane = new JScrollPane(table);

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
        tableModel.setValueAt(stockInfo.getTicker(),rowIndex,0);
        tableModel.setValueAt(0,rowIndex,1);
        tableModel.setValueAt(stockInfo.getStartDate(),rowIndex,2);
        tableModel.setValueAt(stockInfo.getEndDate(),rowIndex,3);
        tableModel.setValueAt(stockInfo.getReturnRate(),rowIndex,4);
        tableModel.setValueAt(stockInfo.getRisk(),rowIndex,5);
        tableModel.setValueAt(panelService.getStockBeta(stockInfo.getTicker(),stockInfo.getStartDate(),stockInfo.getEndDate()),rowIndex,6);
    }

}
