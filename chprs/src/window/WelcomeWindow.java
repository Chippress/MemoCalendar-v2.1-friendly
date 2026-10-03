package window;

import common.MyColor;
import common.WindowManager;
import window.dialog.ChooseDocDialog;
import window.dialog.NewDocDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
WelcomeWindow 指开始界面
设计成像“登录”那样的很小的窗口

welcome 构造之后就不会变
进入 main 时，welcome 隐藏；关闭 main 时，welcome 重新可见
叉掉 welcome 时退出程序

在此选择日历后再进日历，实现多份数据的处理
需要 2 个按钮：新建日历、选择日历
需要 2 个 panel：标题和按钮

窗口是 border layout，上面的 title panel 是 grid layout，下面的 btn panel 是 flow layout
上下两个 panel 分别设为 preferred 高度是窗口高度的 3/5 和 1/5, 才能好看，很奇怪
 */
public class WelcomeWindow extends JFrame {
    // 常数
    final String title = "MemoCalendar";
    int x = 0;
    int y = 0;
    final int width = 400;
    final int height = 300;

    // panels
    JPanel titlePanel = new JPanel();
    JPanel btnPanel = new JPanel();

    // buttons
    JButton newDocBtn = new JButton("新建日历");
    JButton chooseDocBtn = new JButton("选择日历");

    // init，一经 init 便不会变化
    public void init() {
        setSize(width, height);
        setResizable(false); // 禁止调整尺寸
        setTitle(title);
        setLocationRelativeTo(null); // 居中
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { // 关闭时行为
                System.gc(); // 强制垃圾回收（仅建议调试时使用，生产环境不要调用）
                System.exit(0);
            }
        });

        setLayout(new BorderLayout()); // 布局

        initTitlePanel(); // 添加上半部分 title panel
        initBtnPanel(); // 添加下半部分 btn panel
    }

    // public void hide() {} // 写在 manager 里

    // public void show() {} // 写在 manager 里

    private void initTitlePanel() {
        titlePanel.setPreferredSize(new Dimension(width, height / 5 * 3));
        titlePanel.setBackground(MyColor.tegamiColor);

        titlePanel.setLayout(new GridLayout(2, 1)); // 布局

        // 在 title panel 上添加标签
        Font font = new Font("华文琥珀", Font.PLAIN, 30);
        JLabel label1 = new JLabel("纪念日日历", SwingConstants.CENTER);
        label1.setFont(font);
        JLabel label2 = new JLabel("MemoCalendar", SwingConstants.CENTER);
        label2.setFont(font);

        titlePanel.add(label1);
        titlePanel.add(label2);

        add(titlePanel, BorderLayout.NORTH); // 添加到窗口
    }

    private void initBtnPanel() {
        btnPanel.setPreferredSize(new Dimension(width, height / 5));

        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 布局

        initNewDocBtn();
        initChooseDocBtn();

        add(btnPanel, BorderLayout.SOUTH); // 添加到窗口
    }

    private void initNewDocBtn() {
        newDocBtn.addActionListener(e -> {
            NewDocDialog dialog = new NewDocDialog();
            dialog.shows(); // 打开
        });
        btnPanel.add(newDocBtn); // 添加到面板
    }

    private void initChooseDocBtn() {
        chooseDocBtn.addActionListener(event -> {
            ChooseDocDialog dialog = new ChooseDocDialog();
            dialog.shows(WindowManager.welcomeWindow); // 打开
        });
        String tips = "<html><b>在该目录下</b>选择一个 .mmcld 文件作为日历数据" +
                "<br>已附带 \"default_info.mmcld\" 文件供选</html>";
        chooseDocBtn.setToolTipText(tips);
        ToolTipManager ttm = ToolTipManager.sharedInstance(); // 获取全局的 ToolTipManager 实例
        ttm.setInitialDelay(10); // 设置从鼠标悬停到显示提示的时间
        ttm.setDismissDelay(30000); // 持续时间
        btnPanel.add(chooseDocBtn);
    }
}
