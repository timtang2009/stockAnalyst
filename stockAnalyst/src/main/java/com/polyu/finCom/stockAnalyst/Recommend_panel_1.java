package com.polyu.finCom.stockAnalyst;

import com.polyu.finCom.Toaster.PanelService;
import com.sun.org.apache.xerces.internal.xs.StringList;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class Recommend_panel_1 {

    PanelService panelService = new PanelService();

    //组件
    private JPanel jPanel;
    private JScrollPane jScrollPane;
    private JScrollPane jScrollPane1;

    public JList<String> getStock_list() {
        return stock_list;
    }

    private JList<String> stock_list;

    public JList<String> getOutput_stock_list() {
        return output_stock_list;
    }

    private JList<String> output_stock_list;
    private JButton add;
    private JButton del;
    private JLabel RFR;
    private JTextField RFR_text;
    private JButton commit;

    private String[] input_ticker_information;

    public String[] getOutput_ticker_information() {
        return output_ticker_information;
    }

    private String[] output_ticker_information;

    public JPanel getjPanel() {
        return jPanel;
    }

    public void setStock_list(String[] input_ticker_information) {
        this.input_ticker_information = input_ticker_information;
        //修改原数据和使用listmodel
        DefaultListModel defaultListModel = new DefaultListModel();
        for (int i = 0; i < this.input_ticker_information.length; i++) {
            defaultListModel.addElement(this.input_ticker_information[i]);
        }
        this.stock_list.setModel(defaultListModel);
    }

    public void set_Output_Stock_list(String[] output_ticker_information) {
        if (this.output_ticker_information != null){
            //数组进行合并去重和按字母排序
            String[] new_ticket_information = concat(this.output_ticker_information,output_ticker_information);
            String[] new_ticket_information_trans = removeDuplicates(new_ticket_information);
            Arrays.sort(new_ticket_information_trans);
            this.output_ticker_information = new_ticket_information_trans;
        }else {
            this.output_ticker_information = output_ticker_information;
        }
        //修改原数据和使用listmodel
        DefaultListModel defaultListModel = new DefaultListModel();
        for (int i = 0; i < this.output_ticker_information.length; i++) {
            defaultListModel.addElement(this.output_ticker_information[i]);
        }

        this.output_stock_list.setModel(defaultListModel);
    }

    public JButton getAdd() {
        return add;
    }

    public JButton getDel() {
        return del;
    }

    public JTextField getRFR_text() {
        return RFR_text;
    }

    public JButton getCommit() {
        return commit;
    }

    private void init(){
        //Ticker初始化
        input_ticker_information = new String[]{""};
        //output_ticker_information = new String[]{""};

        //元素初始化
        stock_list = new JList<String>(input_ticker_information);
        output_stock_list = new JList<String>();
        add = new JButton("ADD");
        del = new JButton("DEL");
        RFR = new JLabel("Risk free risk");
        RFR_text = new JTextField();
        commit = new JButton("SUMMIT");

        // 创建滚动面板，垂直一直显示，水平从不显示
        jScrollPane = new JScrollPane(stock_list,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1 = new JScrollPane(output_stock_list,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // GridBagLayout
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        jPanel = new JPanel(gridBagLayout);

        // 使组件完全填满其显示区域
        gridBagConstraints.fill = GridBagConstraints.BOTH;

        //组件1：Stock list
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.weightx = 0.31;
        gridBagConstraints.weighty = 0.6;
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagLayout.setConstraints(jScrollPane,gridBagConstraints);

        //组件2和3：RFR和RFR的Textfield
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 0.15;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagLayout.setConstraints(RFR,gridBagConstraints);

        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 0.15;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagLayout.setConstraints(RFR_text,gridBagConstraints);

        // 组件4和5:ADD和DEL BUTTON
        // ADD
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 0.15;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagLayout.setConstraints(add,gridBagConstraints);
        // DEL
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 0.15;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagLayout.setConstraints(del,gridBagConstraints);

        // 组件6：output stock list
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.weightx = 0.31;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagLayout.setConstraints(jScrollPane1,gridBagConstraints);

        // 组件7：Commit
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 0.15;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagLayout.setConstraints(commit,gridBagConstraints);

        //Panel添加组件
        jPanel.add(jScrollPane);
        jPanel.add(add);
        jPanel.add(jScrollPane1);
        jPanel.add(del);
        jPanel.add(RFR);
        jPanel.add(RFR_text);
        jPanel.add(commit);
    }

    public Recommend_panel_1(){
        init();
    }

    //数组去重
    private String[] removeDuplicates(String[] nums) {
        List<String> numList = new ArrayList<String>();
        //数组变list
        for (String i : nums)
            numList.add(i);
        //list变set
        Set<String> numSet = new HashSet<String>();
        numSet.addAll(numList);
        //set变数组
        Object[] arr = numSet.toArray();
        String result[] = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = (String) arr[i];
        }
        return result;
    }

    //合并数组
    private String[] concat(String[] a, String[] b) {
        String[] c= new String[a.length+b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    //DELETE功能
    public void Remove_Stock_list(String[] Del_list){
        String[] list_rebuid = compare_delete_secstring(this.output_ticker_information,Del_list);

        //修改原数据和使用listmodel
        DefaultListModel defaultListModel = new DefaultListModel();
        Arrays.sort(list_rebuid);
        for (int i = 0; i < list_rebuid.length; i++) {
            defaultListModel.addElement(list_rebuid[i]);
        }
        this.output_ticker_information = list_rebuid;
        this.output_stock_list.setModel(defaultListModel);
    }

    private String[] compare_delete_secstring(String[] Former, String[] Del_list){
        List Former_list = Arrays.asList(Former);
        List Del = Arrays.asList(Del_list);

        //找出差集
        Collection<String> Former_C = new ArrayList<String>(Former_list);
        Collection<String> Del_C = new ArrayList<String>(Del);
        Former_C.removeAll(Del_C);


        String[] list_rebuid = new String[Former_C.size()];
        int list_location = 0;
        for (String s:Former_C) {
            list_rebuid[list_location] = s;
            list_location++;
        }
        list_location = 0;
        return list_rebuid;
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

    public boolean isNumber (Object obj) {
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

}
