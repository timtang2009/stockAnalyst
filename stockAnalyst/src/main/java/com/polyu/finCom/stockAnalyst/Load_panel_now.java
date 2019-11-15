package com.polyu.finCom.stockAnalyst;

import com.polyu.finCom.Toaster.PanelService;
import org.h2.util.StringUtils;

import javax.swing.*;
import java.util.List;

public class Load_panel_now {

        private JPanel panel;

    public void setTicker(String[] ticker_information ) {

        if (ticker_information != null){
            JComboBox<String> ticker = new JComboBox<String>(ticker_information);
            Ticker = ticker;
        }

    }

    private JComboBox<String> Ticker;
        private JLabel start_date_label;
        private JLabel end_date_label;
        private JTextField start_date;
        private JTextField end_date;
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

    public JTextField getStart_date() {
        return start_date;
    }

    public JTextField getEnd_date() {
        return end_date;
    }

    public JButton getShow_all_details() {
        return show_all_details;
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
        //init();
    }

    public void init(){
        panel = new JPanel(null);
        //Ticker初始元素
        String[] Ticker_information;
        Ticker_information = new String[]{"Nothing"};
        //各元素初始化
        //Ticker = new JComboBox<String>();
        start_date_label = new JLabel("Start date");
        end_date_label = new JLabel("End date");
        start_date = new JTextField();
        end_date = new JTextField();
        show_all_details = new JButton("Show all details");
        risk_free_rate_label = new JLabel("Risk free rate");
        risk_free_rate = new JTextField();
        return_rate = new JCheckBox("return rate");
        risk = new JCheckBox("risk");
        coefficient = new JCheckBox("coefficient");
        K = new JCheckBox("K");

        //Ticker 坐标
        Ticker.setLocation(50,50);
        //Ticker 宽高
        Ticker.setSize(200,100);
        //Textfield 坐标和宽高
        start_date_label.setLocation(50,250);
        start_date_label.setSize(90,50);
        end_date_label.setLocation(50,450);
        end_date_label.setSize(90,50);
        start_date.setLocation(150,250);
        start_date.setSize(200,100);
        end_date.setLocation(150,450);
        end_date.setSize(200,100);
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
        panel.add(start_date_label);
        panel.add(end_date_label);
        panel.add(start_date);
        panel.add(end_date);
        panel.add(show_all_details);
        panel.add(risk_free_rate_label);
        panel.add(risk_free_rate);
        //panel.add(return_rate);
        //panel.add(risk);
        //panel.add(coefficient);
        //panel.add(K);
    }

}
