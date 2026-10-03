package window.dialog;

import common.DateInfo;

import javax.swing.*;
import java.awt.*;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

import static common.DateInfo.dateInfos;

/*
 * 窗口分 4 个面板，一行只有一个面板
 * 每个面板被其组件占满
 *
 * 该对话框仅查看，不可编辑。
 * 按下“编辑”按钮后进入编辑窗口。
 *
 * 它需要接收 date infos 数组并传给 edit
 * */
public class ViewDialog extends JDialog {
    final int width = 300;
    final int height = 300;

    JPanel datePanel = new JPanel();
    JPanel namePanel = new JPanel();
    JPanel notesPanel = new JPanel();
    JPanel btnPanel = new JPanel();

    Font font = new Font("微软雅黑", Font.PLAIN, 15);

    JTextField dateField;
    JTextField nameField;
    JTextArea notesArea;
    JButton btn1 = new JButton("确定");
    JButton btn2 = new JButton("编辑");

    private void initPanels() {
        // date
        datePanel.setLayout(new GridLayout(2, 1)); // 面板被组件占满
        JLabel dateLabel = new JLabel("日期\n");
        datePanel.add(dateLabel);
        datePanel.add(dateField);
        add(datePanel); // 添加到窗口

        // name
        namePanel.setLayout(new GridLayout(2, 1)); // 面板被组件占满
        JLabel nameLabel = new JLabel("事件\n");
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        add(namePanel); // 添加到窗口

        // notes
        notesPanel.setLayout(new GridLayout(1, 1)); // 面板被组件占满
        notesPanel.add(notesArea); // 由于布局限制，notesarea 前面就不加 label 了
        add(notesPanel); // 添加到窗口

        // btn
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // 两个按钮 流式布局
        btnPanel.add(btn1);
        btnPanel.add(btn2);
        add(btnPanel); // 添加到窗口
    }

    private void initDate(MonthDay date) {
        dateField = new JTextField(date.format(DateTimeFormatter.ofPattern("MM-dd"))); // 单行
        dateField.setEditable(false); // 日期始终不可编辑
        dateField.setFont(font);
        datePanel.add(dateField);
    }

    private void initName(String name) {
        nameField = new JTextField(name); // 单行
        nameField.setEditable(false); // 编辑时才可更改内容
        nameField.setFont(font);
        namePanel.add(nameField);
    }

    private void initNotes(String notes) {
        notesArea = new JTextArea(notes); // 多行
        notesArea.setEditable(false); // 编辑时才可更改内容
        notesArea.setLineWrap(true); // 自动换行；以后可以加滚动条
        // 不设 font，使用默认的较小的字
        notesPanel.add(notesArea);
    }

    private void initBtn(JComponent c, MonthDay date, String name, String notes) {
        btn1.addActionListener(e -> {
            dispose();
        });

        btn2.addActionListener(e -> {
            dispose();
            EditDialog editDialog = new EditDialog();
            editDialog.init(c, date, name, notes);
        });
    }

    public void init(JComponent c, MonthDay date, String name, String notes) {
        setSize(width, height);
        setLocationRelativeTo(c); // 先 set size，再指定居中于谁
        setLayout(new GridLayout(4, 1, 0, 0)); // 窗口 一行只有一个面板的表格布局
        setTitle("日期详情");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 初始化组件
        initDate(date);
        initName(name);
        initNotes(notes);
        initBtn(c, date, name, notes);
        initPanels(); // 初始化 panels，由于其内部用上了组件，所以组件应比它先初始化

        setVisible(true);
    }
}
