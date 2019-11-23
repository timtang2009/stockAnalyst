package com.polyu.finCom.stockAnalyst;

import com.polyu.finCom.Toaster.PanelService;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Load_panel_now {

    //判断日期的参数
    public static final int date_state_passed = 0;
    public static final int start_date_state_failed = 1;
    public static final int end_date_state_failed = 2;
    public static final int both_date_state_failed = 3;
    public static final int start_bigger_than_end = 4;
    public static final int unknown_error = 5;


    private JPanel panel;

    public String[] getTicker_information() {
        return Ticker_information;
    }

    private String[] Ticker_information;

    //Ticker更新数据
    public void setTicker(String[] ticker_information ) {
        this.Ticker_information = ticker_information;
        //修改原数据和使用datamodel
        Ticker.setModel(new DefaultComboBoxModel<>(this.Ticker_information));

    }

    private JComboBox<String> Ticker;
//        private JLabel start_date_label;
//        private JLabel end_date_label;
        private JLabel showDate1;
        private JLabel showDate2;
        private JButton show_all_details;
        private JLabel risk_free_rate_label;
        private JTextField risk_free_rate;



    private JCheckBox return_rate;
        private JCheckBox risk;
        private JCheckBox coefficient;
        private JCheckBox K;
        PanelService panelService = new PanelService();

    public JPanel getPanel() {
        return panel;
    }

    public JComboBox<String> getTicker() {
        return Ticker;
    }

    public JButton getShow_all_details() {
        return show_all_details;
    }

    public JLabel getShowDate1() {
        return showDate1;
    }

    public Load_panel_now setShowDate1(JLabel showDate1) {
        this.showDate1 = showDate1;
        return this;
    }

    public JLabel getShowDate2() {
        return showDate2;
    }

    public Load_panel_now setShowDate2(JLabel showDate2) {
        this.showDate2 = showDate2;
        return this;
    }

    public JCheckBox getReturn_rate() {
        return return_rate;
    }

    public JCheckBox getRisk() {
        return risk;
    }

    public JCheckBox getCoefficient() {
        return coefficient;
    }

    public JCheckBox getK() {
        return K;
    }

    public JTextField getRisk_free_rate() {
        return risk_free_rate;
    }

    public Load_panel_now(){
        init();
    }

    public void init(){
        panel = new JPanel(null);
        //Ticker初始元素
        //渲染界面
        Ticker = new JComboBox<String>();
        Ticker.setRenderer(new MyComboBoxRenderer("Stock"));
        Ticker.setSelectedIndex(-1);


        show_all_details = new JButton("Show all details");
        risk_free_rate_label = new JLabel("Risk free rate");
        risk_free_rate = new JTextField();
        return_rate = new JCheckBox("return rate");
        risk = new JCheckBox("risk");
        coefficient = new JCheckBox("coefficient");
        K = new JCheckBox("K");

        DateChooser dateChooser1 = DateChooser.getInstance("yyyyMMdd");
        DateChooser dateChooser2 = DateChooser.getInstance("yyyyMMdd");
        showDate1 = new JLabel("choose start date");
        showDate2 = new JLabel("choose end date");
        showDate1.setToolTipText("Start date");
        showDate2.setToolTipText("End date");

        dateChooser1.register(showDate1);
        dateChooser2.register(showDate2);

        //Ticker 坐标
        Ticker.setLocation(50,50);
        //Ticker 宽高
        Ticker.setSize(200,100);
        //Textfield 坐标和宽高
//        start_date_label.setLocation(50,250);
//        start_date_label.setSize(90,50);
//        end_date_label.setLocation(50,450);
//        end_date_label.setSize(90,50);
        showDate1.setLocation(50,250);
        showDate1.setSize(180,50);
        showDate2.setLocation(50,450);
        showDate2.setSize(180,50);
        //Button坐标和宽高
        show_all_details.setLocation(100,650);
        show_all_details.setSize(200,100);
        //Risk free rate坐标和宽高
        risk_free_rate_label.setLocation(650,50);
        risk_free_rate_label.setSize(90,50);
        risk_free_rate.setLocation(750,50);
        risk_free_rate.setSize(200,100);
        //四个CheckBox坐标和宽高
        /*return_rate.setLocation(650,50);
        return_rate.setSize(200,100);
        risk.setLocation(650,250);
        risk.setSize(200,100);
        coefficient.setLocation(650,450);
        coefficient.setSize(200,100);
        K.setLocation(650,650);
        K.setSize(200,100);*/

        //Ticker默认选中项目
        //Ticker.setSelectedIndex(0);
        panel.add(Ticker);
//        panel.add(start_date_label);
//        panel.add(end_date_label);
        panel.add(showDate1, BorderLayout.NORTH);
        panel.add(showDate2, BorderLayout.SOUTH);
        panel.add(show_all_details);
        panel.add(risk_free_rate_label);
        panel.add(risk_free_rate);
        //panel.add(return_rate);
        //panel.add(risk);
        //panel.add(coefficient);
        //panel.add(K);
    }

    // 比较设定开始时间和结束时间是否有超出股票时间期限
    public int compare_start_end_date(String ticker, String retrived_start_date, String retriced_end_date) throws ParseException {
        int state;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date start_date = simpleDateFormat.parse(GetTicker_StartDate(ticker));
        Date end_date = simpleDateFormat.parse(GetTicker_EndDate(ticker));
        Date start_date_retrived = simpleDateFormat.parse(retrived_start_date);
        Date end_date_retrived = simpleDateFormat.parse(retriced_end_date);

        //日期比较
        // 1.输入没问题
        // 2.输入开始日期小于股票开始日期
        // 3.输入结束日期大于股票结束日期
        // 4.输入开始日期小于股票开始日期和输入结束日期大于股票结束日期
        // 5.输入开始日期大于输入结束日期
        if (start_date_retrived.after(start_date) && end_date_retrived.before(end_date)){
            state = date_state_passed;
        }else if (start_date_retrived.before(start_date) && end_date_retrived.before(end_date)){
            state = start_date_state_failed;
        }else if (start_date_retrived.after(start_date) && end_date_retrived.after(end_date)){
            state = end_date_state_failed;
        }else if (start_date_retrived.before(start_date) && end_date_retrived.after(end_date)){
            state = both_date_state_failed;
        }else if (start_date.after(end_date)){
            state = start_bigger_than_end;
        }else {
            state = unknown_error;
        }

        return state;
    }

    public String GetTicker_StartDate(String ticker){
        Map<String,String> ticker_StartEndDate = panelService.getStartEndDate(ticker);
        String start_key = "START";
        String StartDate = ticker_StartEndDate.get(start_key);
        return StartDate;
    }

    public String GetTicker_EndDate(String ticker){
        Map<String,String> ticker_StartEndDate = panelService.getStartEndDate(ticker);
        String end_key = "END";
        String EndDate = ticker_StartEndDate.get(end_key);
        return EndDate;
    }

}
