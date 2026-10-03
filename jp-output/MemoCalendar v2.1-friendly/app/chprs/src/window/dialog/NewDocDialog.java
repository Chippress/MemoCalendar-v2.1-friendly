package window.dialog;

import common.DateInfo;
import common.WindowManager;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
包括 label、textfield、okBtn 3 个组件，直接添加到 dialog
每次需要 new 出来
 */
public class NewDocDialog extends JDialog {
    // 窗口的常数
    final String title = "新建日历";
    final int width = 400;
    final int height = 200;

    // 标签
    JLabel label = new JLabel("请输入新日历的名称");;
    // 文件名输入框
    JTextField textField = new JTextField("文档-1");;
    // 确认按钮
    JButton okBtn = new JButton("创建并进入");;

    // 构造，每次 new 出来时调用
    public NewDocDialog() {
        // dialog
        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(WindowManager.welcomeWindow);
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10)); // 布局
        setResizable(false);
        setModal(true); // 用户必须处理

        // 添加 3 个组件
        initLabel();
        initTextField();
        initOkBtn();
    }

    public void shows() {
        setVisible(true);
    }

    private void initLabel() {
        label.setPreferredSize(new Dimension(width, height / 5));
        add(label);
    }

    private void initTextField() {
        textField.setPreferredSize(new Dimension(width, height / 4));
        add(textField);
    }

    private void initOkBtn() {
        okBtn.addActionListener(event -> {
            String input = textField.getText();
            String docName = input.replaceAll("[\\\\/:*?\"<>|]", "") + ".mmcld"; // 注意格式化和加后缀
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(docName))) {
                String exampleStr = "1|1|1|示例信息|示例备注。";
                bw.write(exampleStr); // 为新建的文档写入示例信息
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 修改全局文件名
            DateInfo.filename = docName;

            // 关闭自身和 welcome，初始化并打开 main
            dispose();
            WindowManager.hideWelcomeWindow();
            WindowManager.initMainWindow();
            WindowManager.showMainWindow();
        });

        add(okBtn);
    }
}
