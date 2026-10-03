package window.dialog;

import common.WindowManager;
import window.MyCompo.TypeChooser;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

// 注意这里导入了 DateInfo 的所有 static 常量
import static common.DateInfo.*;

public class EditDialog extends JDialog {
    final int width = 300;
    final int height = 400;
    final int dateHeight = 40;
    final int nameHeight = 60;
    final int notesHeight = 150;
    final int btnHeight = 50;

    JPanel datePanel = new JPanel();
    JPanel namePanel = new JPanel();
    JPanel typePanel = new JPanel();
    JPanel notesPanel = new JPanel();
    JPanel btnPanel = new JPanel();

    Font font = new Font("微软雅黑", Font.PLAIN, 15);

    JTextField dateField;
    JTextField nameField;
    TypeChooser typeChooser; // 这个只是为勾选框分组，不是能加到面板上的实体
    JTextArea notesArea;
    JButton btn3 = new JButton("取消");
    JButton btn4 = new JButton("保存");

    GridLayout r2c1 = new GridLayout(2, 1);

    private void initPanels() {
        // date
        datePanel.setLayout(r2c1); // 面板被组件占满
        JLabel dateLabel = new JLabel("日期");
        datePanel.add(dateLabel);
        datePanel.add(dateField);
        add(datePanel); // 添加到窗口

        // name
        namePanel.setLayout(r2c1); // 面板被组件占满
        JLabel nameLabel = new JLabel("事件");
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        add(namePanel); // 添加到窗口

        // type
        // type 在 initType() 中完成所有初始化操作

        // notes
        notesPanel.setLayout(new GridLayout(1, 1)); // 面板被组件占满
        notesPanel.add(notesArea); // 由于布局限制，notesarea 前面就不加 label 了
        add(notesPanel); // 添加到窗口

        // btn
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // 两个按钮 流式布局
        btnPanel.add(btn3);
        btnPanel.add(btn4);
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
        nameField.setEditable(true); // 编辑
        nameField.setFont(font);
        namePanel.add(nameField);
    }

    private void initType() {
        // label
        typePanel.setLayout(new GridLayout(2, 2)); // 布局暂且这样
        JLabel typeLabel = new JLabel("类型（必选）");
        typePanel.add(typeLabel);

        // checkboxs
        typeChooser = new TypeChooser();
        for (int i = 0; i <= typeChooser.optsNum; ++i) { // 注意有 0 号
            typePanel.add(typeChooser.opts[i]);
        }

        add(typePanel); // 添加到窗口
    }

    private void initNotes(String notes) {
        notesArea = new JTextArea(notes); // 多行
        notesArea.setEditable(true); // 编辑
        notesArea.setLineWrap(true); // 自动换行；以后可以加滚动条
        // 不设 font，使用默认的较小的字
        notesPanel.add(notesArea);
    }

    private void initBtn(JComponent c, MonthDay date) {
        btn3.addActionListener(e -> {
            dispose(); // 取消并退出
        });

        btn4.addActionListener(e -> {
            // 先检查是否选了
            boolean selected = typeChooser.checkSelected();
            if (selected) {
                saveAndReload(date); // 保存
                dispose(); // 退出
            }
            else {
                typeChooser.warn();
            }
        });
    }

    private void saveToCache(MonthDay date, int type) {
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        if (type == 0) { // 0 号选项为删除
            dateInfos[month][day].exist = false;
            return;
        }

        dateInfos[month][day].exist = true;

        String nameStr = nameField.getText().replaceAll("[|\n\r]", "");
        String notesStr = notesArea.getText().replaceAll("[|\n\r]", ""); // 记得去除换行符和‘|’
        dateInfos[month][day].name = nameStr;
        dateInfos[month][day].type = type;
        dateInfos[month][day].notes = notesStr;
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            // 遍历 date infos
            for (int i = 1; i <= 12; ++i) {
                for (int j = 1; j <= dayNums[i]; ++j) {
                    // 拼接字符串，注意 | 的位置
                    if (dateInfos[i][j].exist) { // 只有存在的才写
                        String dateStr = i + "|" + j + "|";
                        String typeStr = dateInfos[i][j].type + "|";
                        String nameStr = dateInfos[i][j].name + "|";
                        String notesStr = dateInfos[i][j].notes;
                        String line = dateStr + typeStr + nameStr + notesStr; // 末尾无换行
                        bw.write(line); // 写入
                        bw.newLine(); // 换行
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAndReload(MonthDay date) {
        int type = typeChooser.getSelected(this);
        saveToCache(date, type);
        saveToFile();
        WindowManager.reloadMainWindow(); // 重新创建 main window，会重新读取数据
    }

    public void init(JComponent c, MonthDay date, String name, String notes) {
        setSize(width, height);
        setLocationRelativeTo(c); // 先 set size，再指定居中于谁
        setLayout(new GridLayout(5, 1, 0, 10)); // 窗口 一行只有一个面板的表格布局
        setTitle("日期详情（编辑中）");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 初始化组件
        initDate(date);
        initName(name);
        initNotes(notes);
        initBtn(c, date);
        initType(); // 这个特殊，暂时只能在最先或最后上屏，之后更新时可以修改一下
        initPanels(); // 初始化 panels，由于其内部用上了组件，所以组件应比它先初始化

        setVisible(true);
    }
}
