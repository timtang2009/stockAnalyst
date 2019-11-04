package com.polyu.finCom.stockAnalyst;


import com.polyu.finCom.Toaster.Toaster;
import com.polyu.finCom.Utils.UIUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StockAnalystPanel {


    /**
     * Main Start
     */
    public static void main(String[] args) {
        new StockAnalystPanel();
    }

    private StockAnalystPanel() {
        JFrame jf = new JFrame("Stock Analyser");
        jf.setSize(1200, 900);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        // 创建选项卡面板
        final JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("File", createTextPanel("File"));

        tabbedPane.addTab("Stock", createTextPanel("Stock"));

        tabbedPane.addTab("Portfolio", createTextPanel("Portfolio"));

        // 添加选项卡选中状态改变的监听器
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            }
        });

        // 设置默认选中的选项卡
        tabbedPane.setSelectedIndex(1);

        jf.setContentPane(tabbedPane);
        jf.setVisible(true);

    }

    private static JComponent createTextPanel(String text) {
        // 创建面板, 使用一个 1 行 1 列的网格布局（为了让标签的宽高自动撑满面板）
        JPanel panel = new JPanel(new GridLayout(1, 1));

        // 创建标签
        JLabel label = new JLabel(text);
        label.setFont(new Font(null, Font.PLAIN, 50));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        // 添加标签到面板
        panel.add(label);

        return panel;
    }

}
