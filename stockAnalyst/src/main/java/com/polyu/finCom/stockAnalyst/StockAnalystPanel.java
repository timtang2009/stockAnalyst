package com.polyu.finCom.stockAnalyst;


import com.polyu.finCom.Mapper.StockMapper;
import com.polyu.finCom.Model.Stock;
import com.polyu.finCom.Toaster.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;

public class StockAnalystPanel {

    private static JFrame jf;
    // 获取txt文件变量
    private static File[] files;

    // Logger
    //private static Logger logger = Logger.getLogger(StockAnalystPanel.class.getName());
    /**
     * Main Start
     */
    public static void main(String[] args) {
        new StockAnalystPanel();
    }

    private StockAnalystPanel() {
        //JFrame jf = new JFrame("Stock Analyser");
        jf = new JFrame("Stock Analyser");
        jf.setSize(1200, 900);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        SqlSessionFactory sqlSessionFactory= GetSessionFactory.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        StockMapper mapper = sqlSession.getMapper(StockMapper.class);
        mapper.createNewTable("stock");
        sqlSession.close();

        // 创建选项卡面板
        final JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("File", createloadPanel());

        tabbedPane.addTab("Stock", createTextPanel("Stock"));

        tabbedPane.addTab("Portfolio", createTextPanel("Portfolio"));

        // 添加选项卡选中状态改变的监听器
        tabbedPane.addChangeListener(new ChangeListener() {
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

    // File标签中的面板
    private static JComponent createloadPanel(){
        // 创建中间容器
        // 创建面板
        JPanel panel = new JPanel();

        // 创建文本区域，用于显示相关信息
        final JTextArea msgTextArea = new JTextArea(10,30);
        msgTextArea.setLineWrap(true);

        // 创建一个button
        JButton open_button = new JButton("打开按钮");
        // Button的监听
        open_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 打开文件，选择文件保存后文件存在变量files中
                showFileOpenDialog(jf,msgTextArea);
            }
        });
        panel.add(open_button);
        panel.add(msgTextArea);

        return panel;
    }

    private static void showFileOpenDialog(Component parent, JTextArea msgTextArea){

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
            for (int i = 0; i < files.length; i++) {
                msgTextArea.append("打开文件： " + files[i].getAbsolutePath() + "\n\n");
            }

        }

    }

}
