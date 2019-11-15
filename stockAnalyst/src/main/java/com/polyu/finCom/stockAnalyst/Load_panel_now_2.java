package com.polyu.finCom.stockAnalyst;

import com.polyu.finCom.Model.StockInfo;

import javax.swing.*;
import java.awt.*;

public class Load_panel_now_2 {
    public JPanel getjPanel() {
        return jPanel;
    }

    private JPanel jPanel;
    private JScrollPane jScrollPane;
    private JScrollPane jScrollPane1;
    private JLabel jLabel;
    private JTextArea jTextArea;
    private JLabel jLabel1;
    private StockInfo stockInfo;

    public StockInfo getStockInfo() {
        return stockInfo;
    }

    public void setStockInfo(StockInfo stockInfo) {
        this.stockInfo = stockInfo;
    }

    private void init(){
        jLabel = new JLabel("K lines here");
        jLabel.setFont(new Font(null, Font.PLAIN, 50));
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jTextArea = new JTextArea("",3,3);
        //自动换行
        jTextArea.setLineWrap(true);
        //jLabel1 = new JLabel("Forms here");
        //jLabel1.setFont(new Font(null, Font.PLAIN, 50));
        //jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        // 创建滚动面板，垂直一直显示，水平从不显示
        jScrollPane = new JScrollPane(jLabel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1 = new JScrollPane(jTextArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //实例化布局对象
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        jPanel = new JPanel(gridBagLayout);
        // 使组件完全填满其显示区域
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        // 组件1（gridx,gridy）组件的左上角坐标，gridwidth，gridheight：组件占用的网格行数和列数
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 0.6;
        gridBagConstraints.weighty = 1;
        //gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagLayout.setConstraints(jScrollPane,gridBagConstraints);
        //组件2
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 1;
        //gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagLayout.setConstraints(jScrollPane1,gridBagConstraints);

        jPanel.add(jScrollPane);
        jPanel.add(jScrollPane1);
    }

    public  Load_panel_now_2(){
        init();
    }

    //文本反馈数据
    public void create_form(double Return_rate, double Risk, double SharpRatio){

        jTextArea.append("Return rate: " + Return_rate + "\n");
        jTextArea.append("Risk：" + Risk + "\n");
        jTextArea.append("SharpRatio: " + SharpRatio + "\n");
    }

}
