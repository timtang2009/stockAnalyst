package com.polyu.finCom.stockAnalyst;

public class Load_panel_2 {
    public static void main(String[] args)
    {
        String str[][] = new String[10][3];
        for(int i=0;i<str.length;i++)
        {
            str[i][0]=i+6+"";
        }
        javax.swing.JComboBox jComboBox = new RwJComboBox(str);
        jComboBox.setEditable(true);
        jComboBox.setPreferredSize(new java.awt.Dimension(400,60));

        javax.swing.JTextField jTextField = new  javax.swing.JTextField(40);
        //测试焦点事件

        javax.swing.JFrame frame = new  javax.swing.JFrame();
        frame.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 20));
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.add(jComboBox);
        frame.add(jTextField);
        frame.setVisible(true);
    }
    public static class RwJComboBox extends  javax.swing.JComboBox implements java.awt.event.FocusListener
    {
        protected java.util.Set set = new java.util.HashSet();
        public RwJComboBox(String str[][])
        {
            setModel(new RwDefaultComboBoxModel(str));
            setUI(new RwMetalComboBoxUI(this));
            setRenderer(new RwJCheckBox());
            setSelectedIndex(-1);
//            ((javax.swing.JTextField)getEditor().getEditorComponent()).setEditable(false);
//            禁止编辑的代码
        }
        public void focusGained( java.awt.event.FocusEvent e)
        {
        }
        public void focusLost( java.awt.event.FocusEvent e)
        {
//            这是一种特殊情况下的使用方法
//            java.awt.Container container=(java.awt.Container)getEditor().getEditorComponent();
//            while(container!=null&&!(container instanceof  javax.swing.JLabel))
//            {
//                container=container.getParent();
//            }
//            ((cLabel)container).setValue(((javax.swing.JTextField)getEditor().getEditorComponent()).getText());
        }
    }
    public static class  RwMetalComboBoxUI extends  javax.swing.plaf.metal.MetalComboBoxUI
    {
        private RwMetalComboBoxUI(RwJComboBox rwJComboBox)
        {
            this.comboBox=rwJComboBox;
        }
        protected javax.swing.plaf.basic.ComboPopup createPopup()
        {
            return new RwBasicComboPopup(comboBox);
        }
    }
    public static class RwBasicComboPopup extends javax.swing.plaf.basic.BasicComboPopup
    {
        public RwBasicComboPopup(javax.swing.JComboBox combo)
        {
            super(combo);
        }
        protected void configureList()
        {
            super.configureList();
            list.setSelectionModel(new RwDefaultListSelectionModel(comboBox));
        }
        protected java.awt.event.MouseListener createListMouseListener()
        {
            return new RwMouseAdapter(list,comboBox);
        }
    }
    private static class RwDefaultListSelectionModel extends javax.swing.DefaultListSelectionModel
    {
        protected RwJComboBox rwJComboBox;
        public RwDefaultListSelectionModel(javax.swing.JComboBox comboBox)
        {
            this.rwJComboBox=(RwJComboBox) comboBox;
        }
        public boolean isSelectedIndex(int index)
        {
            return rwJComboBox.set.contains(index);
        }
    }
    private static  class  RwMouseAdapter extends java.awt.event.MouseAdapter
    {
        protected javax.swing.JList list;
        protected RwJComboBox rwJComboBox;
        private RwMouseAdapter(javax.swing.JList list, javax.swing.JComboBox comboBox)
        {
            this.list=list;
            this.rwJComboBox=(RwJComboBox) comboBox;
        }
        public void mousePressed(java.awt.event.MouseEvent anEvent)
        {
            StringBuilder sb,sb1,sb2 ,sb3;
            int k,index;
            index = list.getSelectedIndex();
            javax.swing.JTextField jTextField=(javax.swing.JTextField)rwJComboBox.getEditor().getEditorComponent();
            sb = new StringBuilder(jTextField.getText());

            if(sb.length()>0&&','!=sb.charAt(0))
                sb.insert(0, ",");
            if(sb.length()>0&&','!=sb.charAt(sb.length()-1))
                sb.append(",");
            if(sb.length()>0&&",".equals(sb.toString()))
                sb = new StringBuilder();
            if (list.getSelectionMode() == javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
            {
                if (rwJComboBox.set.contains(index))
                {
                    rwJComboBox.set.remove(index);
                    sb1 = new StringBuilder();
                    sb1.append(",").append(rwJComboBox.getModel().getElementAt( index)).append(",");
                    k=sb.indexOf(sb1.toString());
                    while(k!=-1)
                    {
                        sb.replace(k, k+sb1.length(), ",");
                        k=sb.indexOf(sb1.toString());
                    }
                }
                else
                {
                    rwJComboBox.set.add(index);
                    if(sb.length()==0)
                        sb.append(",").append(rwJComboBox.getModel().getElementAt( index)).append(",");
                    else
                        sb.append(rwJComboBox.getModel().getElementAt( index)).append(",");
                }
            }
            else
            {
                if (!rwJComboBox.set.contains(index))
                {
                    rwJComboBox.set.clear();
                    rwJComboBox.set.add(index);
                }
            }
            Object obj;
            sb2 =new StringBuilder(sb);
            //替换完正常的可选值
            for(int i=0;i<list.getModel().getSize();i++)
            {
                obj=list.getModel().getElementAt(i);
                sb1 = new StringBuilder();
                sb1.append(",").append(obj).append(",");
                k=sb2.indexOf(sb1.toString());
                while(k!=-1)
                {
                    sb2.replace(k, k+sb1.length(), ",");
                    k=sb2.indexOf(sb1.toString());
                }
            }
            java.util.List list1 = new java
                    .util.ArrayList(rwJComboBox.set);
            java.util.Collections.sort(list1);
            for (int i=0;i<list1.size();i++)
            {
                obj = rwJComboBox.getModel().getElementAt( Integer.parseInt(list1.get(i).toString()));
                sb1 = new StringBuilder();
                sb1.append(",").append(obj).append(",");
                k=sb.indexOf(sb1.toString());
                if(k!=-1&&sb2.indexOf(sb1.toString())==-1)
                {
                    sb2.append(obj).append(",");
                }
            }
            sb = new StringBuilder(sb2);
            if(sb.length()>0&&','==sb.charAt(0))
                sb.deleteCharAt(0);
            if(sb.length()>0&&','==sb.charAt(sb.length()-1))
                sb.deleteCharAt(sb.length()-1);
            if(sb.length()>0&&",".equals(sb.toString()))
                sb = new StringBuilder();
            jTextField.setText(sb.toString());
            //        java.awt.Container container  =jTextField;
            //        while(container!=null&&!(container instanceof  javax.swing.JLabel))
            //        {
            //                container=container.getParent();
            //        }
            //        ((cLabel)container).setValue(sb.toString());
            rwJComboBox.repaint();
            list.repaint();
        }
    }
    public static class  RwDefaultComboBoxModel extends javax.swing.DefaultComboBoxModel
    {
        private RwDefaultComboBoxModel(String[][] str)
        {
            for(int i=0;i<str.length;i++)
                addElement(str[i][0]);
        }
    }
    public static class RwJCheckBox extends javax.swing.JCheckBox implements javax.swing.ListCellRenderer
    {
        public java.awt.Component getListCellRendererComponent(javax.swing.JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
        {
            setComponentOrientation(list.getComponentOrientation());
            if (isSelected)
            {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }
            else
            {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
            setSelected(isSelected);
            setText(value == null ? "" : value.toString());
            setFont(list.getFont());
            return this;
        }
    }
}
