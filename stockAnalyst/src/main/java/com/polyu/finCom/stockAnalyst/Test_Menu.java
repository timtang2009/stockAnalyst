package com.polyu.finCom.stockAnalyst;

import com.polyu.finCom.Mapper.StockMapper;
import com.polyu.finCom.Model.StockInfo;
import com.polyu.finCom.Toaster.GetSessionFactory;
import com.polyu.finCom.Toaster.PanelService;
import com.polyu.finCom.Toaster.Toaster;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Test_Menu implements ActionListener {
    private static JFrame jFrame;

    //菜单栏
    public static JMenuBar menuBar;

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

    private Toaster toaster = new Toaster();

    //Absolute_path的get、set方法
    public String[] getFiles_absolute_path() {
        return files_absolute_path;
    }

    public void setFiles_absolute_path(String[] files_absolute_path) {
        this.files_absolute_path = files_absolute_path;
    }

    //Load_panel
    Load_panel_now load_panel_now = new Load_panel_now();

    //Load_panel2
    Load_panel_now_2 load_panel_now_2 = new Load_panel_now_2();

    //PanelService
    PanelService panelService = new PanelService();

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
        SqlSessionFactory sqlSessionFactory= GetSessionFactory.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        StockMapper mapper = sqlSession.getMapper(StockMapper.class);
        mapper.createNewTable("stock");
        sqlSession.close();

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
            jFrame.setContentPane(card());
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
                toaster.readFile(files_test[i]);
                System.out.println(files_test[i]);
            }

            //消息对话框提示保存成功
            JOptionPane.showMessageDialog(parent,"Files saved successfully","Notification",JOptionPane.INFORMATION_MESSAGE);
            //第一次需导入文件后再打开load界面
            //load_panel_now = new Load_panel_now();
        }

    }

    private JComponent card(){
        //创建卡片布局
        final CardLayout layout = new CardLayout();
        final JPanel panel = new JPanel(layout);
        final JButton button1 = new JButton("跳转");
        java.util.List<String> tickerList = panelService.getTickerList();
        if (tickerList != null) {
            load_panel_now.setTicker(tickerList.toArray(new String[tickerList.size()]));
        }

        // 根据加入前后决定顺序
        panel.add("1",load_panel_now.getPanel());
        panel.add("2",load_panel_now_2.getjPanel());



        //显示第一个
        layout.show(panel,"1");
        /*
        *当点击show all details按钮
        * 需要将界面信息全部发到后台进行计算
        **/
        load_panel_now.getShow_all_details().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                //跳转下个界面
                //需要使用Stockinfo进行存取
                if (source == load_panel_now.getShow_all_details()){
                    System.out.println("Ticker information: " + load_panel_now.getTicker().getSelectedItem().toString());
                    System.out.println("Start date: " + load_panel_now.getStart_date().getText());
                    System.out.println("End date: " + load_panel_now.getEnd_date().getText());

                    //Stockinfo存取
                    StockInfo stockInfo = panelService.getStockInfo(load_panel_now.getTicker().getSelectedItem().toString(),
                            load_panel_now.getStart_date().getText(),
                            load_panel_now.getEnd_date().getText(),
                            Double.parseDouble(load_panel_now.getRisk_free_rate().getText()));
                    if (stockInfo != null) {
                        load_panel_now_2.setStockInfo(stockInfo);
                        load_panel_now_2.create_form(stockInfo.getReturnRate(),stockInfo.getRisk(),stockInfo.getSharpRatio());
//                        load_panel_now_2.create_form(2, 2, 2);
                    }
                    //跳转下一界面
                    layout.next(panel);
                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                if (source == button1){
                    layout.next(panel);
                }
            }
        });
        return panel;
    }
}

