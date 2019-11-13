package com.polyu.finCom.stockAnalyst;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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

    //文件Absolute path的String数组
    private String[] files_absolute_path;

    //Absolute_path的get、set方法
    public String[] getFiles_absolute_path() {
        return files_absolute_path;
    }

    public void setFiles_absolute_path(String[] files_absolute_path) {
        this.files_absolute_path = files_absolute_path;
    }

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

    //菜单栏的逻辑判断
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == openMenuItem){
            showFileOpenDialog(jFrame);

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

    /*
     *创建一个文件选择器
     * 放在open菜单中
     * 功能：导入文件并返回一个String类型数组列明选中文件的absolute path
     *
     */

    private void showFileOpenDialog(Component parent){
        File[] files;
        String[] files_absolute_path;
        String[] files_test;

        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（文件和文件夹均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(true);

        // 打开文件选择框（线程将被阻塞，知道选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION){
            // 如果点击了“确定”，则获取选择的文件路径
            //File file = fileChooser.getSelectedFile();

            // 如果选择多个文件，则通过下面方法获取选择的所有文件
            files = fileChooser.getSelectedFiles();
            files_absolute_path = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                files_absolute_path[i] = files[i].getAbsolutePath();

            }
            //保存文件
            setFiles_absolute_path(files_absolute_path);

            //测试
            files_test = getFiles_absolute_path();
            for (int i = 0; i < files.length; i++) {

                System.out.println(files_test[i]);
            }

            //消息对话框提示保存成功
            JOptionPane.showMessageDialog(parent,"Files saved successfully","Notification",JOptionPane.INFORMATION_MESSAGE);
        }

    }
}

