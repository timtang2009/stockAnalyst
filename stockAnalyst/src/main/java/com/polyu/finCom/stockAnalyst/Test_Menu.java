package com.polyu.finCom.stockAnalyst;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.annotation.Retention;

public class Test_Menu implements ActionListener {
    private static JFrame jFrame;

    //菜单栏
    private static JMenuBar menuBar;

    //一级菜单
    private JMenu filemenu;
    private JMenu stockmenu;
    private JMenu Portfoliomenu;
    private JMenu Optimizationmenu;

    //一级菜单的子菜单
    private JMenuItem openMenuItem;
    private JMenuItem loadMenuItem;
    private JMenuItem rankMenuItem;
    private JMenuItem Build_PortfolioMenuItem;
    private JMenuItem Recommend_PortfolioMenuItem;
    private JMenuItem Optimize_PortfolioMenuItem;

    public Test_Menu(){
        //创建菜单栏
        menuBar = new JMenuBar();

        //创建一级菜单
        filemenu = new JMenu("File");
        stockmenu = new JMenu("Stock");
        Portfoliomenu = new JMenu("Portofio");
        Optimizationmenu = new JMenu("Optimization");
        //添加一级菜单到菜单栏
        menuBar.add(filemenu);
        menuBar.add(stockmenu);
        menuBar.add(Portfoliomenu);
        menuBar.add(Optimizationmenu);

        //创建一级菜单下的子菜单
        openMenuItem = new JMenuItem("Open");
        loadMenuItem = new JMenuItem("Load");
        rankMenuItem = new JMenuItem("Rank");
        Build_PortfolioMenuItem = new JMenuItem("Build Portfolio");
        Recommend_PortfolioMenuItem = new JMenuItem("Recommend Portfolio");
        Optimize_PortfolioMenuItem = new JMenuItem("Optimize Portfolio");
        //子菜单添加到一级菜单
        filemenu.add(openMenuItem);
        stockmenu.add(loadMenuItem);
        stockmenu.add(rankMenuItem);
        Portfoliomenu.add(Build_PortfolioMenuItem);
        Optimizationmenu.add(Recommend_PortfolioMenuItem);
        Optimizationmenu.add(Optimize_PortfolioMenuItem);

        //添加事件监听器
        openMenuItem.addActionListener(this);
        loadMenuItem.addActionListener(this);
        rankMenuItem.addActionListener(this);
        Build_PortfolioMenuItem.addActionListener(this);
        Recommend_PortfolioMenuItem.addActionListener(this);
        Optimize_PortfolioMenuItem.addActionListener(this);

    }

    public static void main(String[] args) {
        jFrame = new JFrame("Test");
        jFrame.setSize(1200,900);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Test_Menu test_menu = new Test_Menu();
        jFrame.setJMenuBar(menuBar);
        jFrame.setVisible(true);
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

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == openMenuItem){
            jFrame.setContentPane(createTextPanel("Open"));
            jFrame.revalidate();

        }else if (source == loadMenuItem){
            jFrame.setContentPane(createTextPanel("Load"));
            jFrame.revalidate();

        }else if (source == rankMenuItem){
            jFrame.setContentPane(createTextPanel("Rank"));
            jFrame.revalidate();

        }else if (source == Build_PortfolioMenuItem){
            jFrame.setContentPane(createTextPanel("Build"));
            jFrame.revalidate();

        }else if (source == Recommend_PortfolioMenuItem){
            jFrame.setContentPane(createTextPanel("Recommend"));
            jFrame.revalidate();

        }else if (source == Optimize_PortfolioMenuItem){
            jFrame.setContentPane(createTextPanel("Optimize"));
            jFrame.revalidate();

        }

    }
}

