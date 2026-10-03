package window;

import common.DateInfo;
import common.WindowManager;
import window.mainWindowComponents.LeftPanel;
import window.mainWindowComponents.RightPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
MainWindow 指日历界面
左右分割布局
 */
public class MainWindow extends JFrame {
    // 常数
    final String title = "MemoCalendar";
    int x = 0;
    int y = 0;
    final int width = 800;
    final int height = 600;
    final int leftWidth = 500;

    // pane and panels
    JSplitPane pane = new JSplitPane();
    LeftPanel leftPanel = new LeftPanel();
    public RightPanel rightPanel = new RightPanel(); // 这个访问权限有点扎眼

    // init，每次打开前都会 init
    public void init() {
        setSize(width, height);
        setTitle(title);
        setLocationRelativeTo(null); // 居中

        // 设置关闭时摧毁本窗口和回到 welcome
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                WindowManager.destroyMainWindow();
                WindowManager.showWelcomeWindow();
            }
        });

        // 布局和面板及其组件
        setLayout(new GridLayout()); // 布局
        initPaneAndPanels();
    }

    private void initPaneAndPanels() {
        DateInfo.read(); // read
        leftPanel.init();
        rightPanel.init();
        pane.setOrientation(JSplitPane.HORIZONTAL_SPLIT); // 横向分割
        pane.setDividerLocation(leftWidth); // 分割条初始位置
        pane.setLeftComponent(leftPanel); // 添加到 pane
        pane.setRightComponent(rightPanel); // 添加到 pane
        add(pane); // 添加到窗口
    }

    // public void hide() {} // 写在 manager 里

    // public void show() {} // 写在 manager 里

    public int getCurrMonth() {
        return leftPanel.getCurrMonth();
    }
}
