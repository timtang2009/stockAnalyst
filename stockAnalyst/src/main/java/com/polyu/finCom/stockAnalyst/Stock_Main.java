package com.polyu.finCom.stockAnalyst;

import com.polyu.finCom.Mapper.StockMapper;
import com.polyu.finCom.Model.Stock;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Stock_Main implements ActionListener {
    private static JFrame jFrame;

    public JPanel getWelcome_panel() {
        SqlSessionFactory sqlSessionFactory= GetSessionFactory.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        StockMapper mapper = sqlSession.getMapper(StockMapper.class);
        mapper.createNewTable("stock");
        mapper.createMarketTable("market");
        mapper.createBetaTable("stockBeta");
        mapper.createIndex();
        sqlSession.close();
        return welcome_panel;
    }

    //欢迎页
    private static JPanel welcome_panel;

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
    private JMenuItem Build_PortfolioMenuItem;


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

    //PanelService
    PanelService panelService = new PanelService();

    public Stock_Main(){
        //欢迎页设立
        welcome_panel = createTextPanel("Welcome");

        //创建菜单栏
        menuBar = new JMenuBar();

        //创建一级菜单
        filemenu = new JMenu("File");
        stockmenu = new JMenu("Stock");
        Portfoliomenu = new JMenu("Portofio");

        //添加一级菜单到菜单栏
        menuBar.add(filemenu);
        menuBar.add(stockmenu);
        menuBar.add(Portfoliomenu);

        //创建一级菜单下的子菜单
        openMenuItem = new JMenuItem("Open");
        loadMenuItem = new JMenuItem("Load");
        Build_PortfolioMenuItem = new JMenuItem("Build Portfolio");


        //子菜单添加到一级菜单
        filemenu.add(openMenuItem);
        stockmenu.add(loadMenuItem);
        Portfoliomenu.add(Build_PortfolioMenuItem);


        //添加事件监听器
        openMenuItem.addActionListener(this);
        loadMenuItem.addActionListener(this);
        Build_PortfolioMenuItem.addActionListener(this);


    }

    public static void main(String[] args) {
        jFrame = new JFrame("Stock Analyst");
        jFrame.setSize(1200,800);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Stock_Main stockMain = new Stock_Main();

        jFrame.setJMenuBar(menuBar);
        jFrame.setContentPane(stockMain.getWelcome_panel());
        jFrame.setVisible(true);
    }

    private static JPanel createTextPanel(String text) {
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
            showSpecifiedPanel(welcome_panel,card(jFrame));
            //jFrame.setContentPane(card(jFrame));
            //jFrame.revalidate();


        }else if (source == Build_PortfolioMenuItem){
            showSpecifiedPanel(welcome_panel,Recommend_card(jFrame));
            //jFrame.setContentPane(Recommend_card(jFrame));
            //jFrame.revalidate();

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
            }

            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    toaster.setMarketReturn();
                }
            });
            thread.start();

            //消息对话框提示保存成功
            JOptionPane.showMessageDialog(parent,"Files saved successfully","Notification",JOptionPane.INFORMATION_MESSAGE);

        }

    }

    private JPanel card(Component parent){
        //Load_panel
        Load_panel_now load_panel_now = new Load_panel_now();

        //Load_panel2
        Load_panel_now_2 load_panel_now_2 = new Load_panel_now_2();

        //创建卡片布局
        final CardLayout layout = new CardLayout();
        final JPanel panel = new JPanel(layout);
        final JButton button1 = new JButton("跳转");



        java.util.List<String> tickerList = panelService.getTickerList();
        if (tickerList != null) {
            load_panel_now.setTicker(tickerList.toArray(new String[tickerList.size()]));
        }

        //渲染界面
        load_panel_now.getTicker().setRenderer(new MyComboBoxRenderer("Stock"));
        load_panel_now.getTicker().setSelectedIndex(-1);


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
                int date_state = -1;
                //跳转下个界面
                //需要使用Stockinfo进行存取
                if (source == load_panel_now.getShow_all_details()){
                    //日期判断
                    try {
                         date_state = load_panel_now.compare_start_end_date(load_panel_now.getTicker().getSelectedItem().toString(),load_panel_now.getShowDate1().getText(),load_panel_now.getShowDate2().getText());
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                    // 日期判断正确可跳转下一个界面
                    if (date_state == Load_panel_now.date_state_passed){
                        //Stockinfo存取
                        StockInfo stockInfo = panelService.getStockInfo(load_panel_now.getTicker().getSelectedItem().toString(),
                                load_panel_now.getShowDate1().getText(),
                                load_panel_now.getShowDate2().getText(),
                                Double.parseDouble(load_panel_now.getRisk_free_rate().getText()));
                        List<Stock> stocks = panelService.getStockList(load_panel_now.getTicker().getSelectedItem().toString(),load_panel_now.getShowDate1().getText(),load_panel_now.getShowDate2().getText());
                        if (stockInfo != null) {
                            load_panel_now_2.setStockInfo(stockInfo);
                            load_panel_now_2.init2();
                            new Stocks_load(load_panel_now_2.getJfreeCandlestickChart(),stocks,2).run();
                        }
                        //跳转下一界面
                        layout.next(panel);
                    }else if (date_state == Load_panel_now.start_date_state_failed){
                        //消息对话框提示开始日期输错了
                        JOptionPane.showMessageDialog(parent,"Wrong start date, please type again\n" + load_panel_now.getTicker().getSelectedItem().toString() +"'s start date is "+ load_panel_now.GetTicker_StartDate(load_panel_now.getTicker().getSelectedItem().toString()),"Notification",JOptionPane.INFORMATION_MESSAGE);
                    }else if (date_state == Load_panel_now.end_date_state_failed){
                        //消息对话框提示开始日期输错了
                        JOptionPane.showMessageDialog(parent,"Wrong end date, please type again\n"+ load_panel_now.getTicker().getSelectedItem().toString() +"'s end date is "+ load_panel_now.GetTicker_EndDate(load_panel_now.getTicker().getSelectedItem().toString()),"Notification",JOptionPane.INFORMATION_MESSAGE);
                    }else if (date_state == Load_panel_now.both_date_state_failed){
                        //消息对话框提示开始日期输错了
                        JOptionPane.showMessageDialog(parent,"Wrong start date and end date, please type again\n"+  load_panel_now.getTicker().getSelectedItem().toString() +"'s start date is "+ load_panel_now.GetTicker_StartDate(load_panel_now.getTicker().getSelectedItem().toString())+ "\n" + load_panel_now.getTicker().getSelectedItem().toString() +"'s end date is "+ load_panel_now.GetTicker_EndDate(load_panel_now.getTicker().getSelectedItem().toString()),"Notification",JOptionPane.INFORMATION_MESSAGE);
                    }else if(date_state == Load_panel_now.start_bigger_than_end){
                        JOptionPane.showMessageDialog(parent,"Input start date is after the end date","Notification",JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(parent,"Program debug happens","Notification",JOptionPane.INFORMATION_MESSAGE);
                    }


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

/*下拉列表框选择日期后，
 * label会提示该股票的开始与结束时间
 */
        load_panel_now.getTicker().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED){

                    if (load_panel_now.getTicker().getSelectedItem().toString() !=  null){
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                        DateFormat dateFormat = DateFormat.getDateInstance();
                        String start_date = load_panel_now.GetTicker_StartDate(load_panel_now.getTicker().getSelectedItem().toString());
                        String end_date = load_panel_now.GetTicker_EndDate(load_panel_now.getTicker().getSelectedItem().toString());
                        try {
                            Date start_date_transformed = simpleDateFormat.parse(start_date);
                            Date end_date_transformed = simpleDateFormat.parse(end_date);
                            load_panel_now.getShowDate1().setText("Input start date > " + dateFormat.format(start_date_transformed));
                            load_panel_now.getShowDate2().setText("Input end date < " + dateFormat.format(end_date_transformed));
                            load_panel_now.getShowDate1().setToolTipText("Input start date > " + dateFormat.format(start_date_transformed));
                            load_panel_now.getShowDate2().setToolTipText("Input end date < " + dateFormat.format(end_date_transformed));
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        return panel;
    }

    private JPanel Recommend_card(Component parent){
        //创建卡片布局
        final CardLayout layout = new CardLayout();
        final JPanel panel = new JPanel(layout);
        //Recommend_panel_1
        Recommend_panel_1 recommend_panel_1 = new Recommend_panel_1();
        Recommend_panel_2 recommend_panel_2 = new Recommend_panel_2();

        // 根据加入前后决定顺序
        panel.add("1",recommend_panel_1.getjPanel());
        panel.add("2",recommend_panel_2.getPanel());

        //显示第一个
        layout.show(panel,"1");

        java.util.List<String> tickerList = panelService.getTickerList();
        if (tickerList != null) {
            recommend_panel_1.setStock_list(tickerList.toArray(new String[tickerList.size()]));
        }
        //按Add时会从左边添加选项到右边
        recommend_panel_1.getAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                if (source == recommend_panel_1.getAdd()){
                    if (recommend_panel_1.getStock_list().getSelectedIndices().length != 0){
                        // 获取所有被选中的选项索引
                        int[] indices = recommend_panel_1.getStock_list().getSelectedIndices();
                        // 获取选项数据的ListModel
                        ListModel<String> listModel = recommend_panel_1.getStock_list().getModel();
                        // 将输出选项存入String数组output_list
                        String[] output_list = new String[indices.length];
                        int location = 0;
                        for (int index:indices) {
                            output_list[location] = listModel.getElementAt(index);
                            location ++;
                        }
                        recommend_panel_1.set_Output_Stock_list(output_list);
                    }else {
                        JOptionPane.showMessageDialog(parent,"Nothing to add","Notification",JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            }
        });

        // DELETE功能
        recommend_panel_1.getDel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                if (source == recommend_panel_1.getDel()){
                    if (recommend_panel_1.getOutput_ticker_information() != null){
                        // 获取所有被选中的选项索引
                        int[] del_indices = recommend_panel_1.getOutput_stock_list().getSelectedIndices();
                        // 获取选项数据的ListModel
                        ListModel<String> listModel = recommend_panel_1.getOutput_stock_list().getModel();
                        // 将输出选项存入String数组output_list
                        String[] del_list = new String[del_indices.length];
                        int del_location = 0;
                        for (int index:del_indices) {
                            del_list[del_location] = listModel.getElementAt(index);
                            System.out.println("删除: " + index + "为" + del_list[del_location] );
                            del_location ++;
                        }
                        del_location = 0;
                        recommend_panel_1.Remove_Stock_list(del_list);
                    }else {
                        JOptionPane.showMessageDialog(parent,"Nothing to delete","Notification",JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            }
        });

        //Commit功能
        recommend_panel_1.getCommit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Risk_Free_Rate = recommend_panel_1.getRFR_text().getText();
                //判断RFR
                if (recommend_panel_1.isNumber(Risk_Free_Rate)){
                    Double RFR = Double.parseDouble(Risk_Free_Rate);
                    if (RFR >= 0 && RFR <= 1){
                        // 获取选项数据的ListModel
                        ListModel<String> listModel = recommend_panel_1.getOutput_stock_list().getModel();
                        // 获取整个输出output list
                        String[] output_list = new String[listModel.getSize()];
                        StockInfo[] stockInfos = new StockInfo[listModel.getSize()];

                        // 初始化的weight为0
                        for (int i = 0; i < listModel.getSize(); i++) {
                            output_list[i] = listModel.getElementAt(i);
                            stockInfos[i] = panelService.getStockInfo(output_list[i],recommend_panel_1.GetTicker_StartDate(output_list[i]),recommend_panel_1.GetTicker_EndDate(output_list[i]),RFR);
                            stockInfos[i].setWeight(0.0);
                            stockInfos[i].setRiskFree(RFR);
                        }
                        recommend_panel_2.setStockInfos(stockInfos);
                        recommend_panel_2.init2();
                        layout.next(panel);
                    }else {
                        JOptionPane.showMessageDialog(parent,"Risk free rate's range: 0-1","Notification",JOptionPane.INFORMATION_MESSAGE);
                    }
                }else {
                    JOptionPane.showMessageDialog(parent,"Risk free rate must be a number","Notification",JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        //Recommend panel 2 optimized功能
        recommend_panel_2.getjButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recommend_panel_2.setOptimized_stockInfos();
            }
        });

        return panel;
    }

    // 以WelcomePanel为底，其上覆盖想要展示的panel内容（切换panel）
    private void showSpecifiedPanel(JPanel welcomePanel, JPanel showPanel){
        welcomePanel.removeAll();
        welcomePanel.add(showPanel);
        welcomePanel.validate();
        welcomePanel.repaint();
    }
}

