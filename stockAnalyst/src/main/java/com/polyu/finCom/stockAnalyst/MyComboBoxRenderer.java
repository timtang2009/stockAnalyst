package com.polyu.finCom.stockAnalyst;

import javax.swing.*;
import java.awt.*;

/*
 * 重写JComboBox的渲染器加入标题并美化
 */

public class MyComboBoxRenderer extends JLabel implements ListCellRenderer {
    private String title;

    private DefaultListCellRenderer defaultListCellRenderer = new DefaultListCellRenderer();

    public  MyComboBoxRenderer(String title){
        this.title = title;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        // 每一行，都转换成JLabel来处理
        JLabel renderer = (JLabel) defaultListCellRenderer.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);

        // 加入标题
        if (index == -1 && value == null){
            renderer.setText(this.title);
        }else {
            renderer.setText(value.toString());
        }

        // 每一行的jlabel的颜色
        if (isSelected) {
            renderer.setBackground(new Color(150, 207, 254));
            renderer.setForeground(Color.WHITE);
        } else {
            renderer.setBackground(null);
        }

        // 字体靠左
        renderer.setHorizontalAlignment(JLabel.LEFT);

        // 左侧padding
        renderer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        // render的宽高
        renderer.setPreferredSize(new Dimension(100, 25));

        // list背景色，也就是向下的按钮左边儿那一块儿
        list.setSelectionBackground(null);
        list.setBorder(null);

        return renderer;

    }
}
