package window.mainWindowComponents;

import common.DateInfo;
import common.MyColor;
import common.WindowManager;
import window.MyCompo.SplitPaneItem;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {
    // 窗口尺寸常数
    final int minWidth = 400;
    final int height = 600;

    // 多标签页
    JTabbedPane splitPane = new JTabbedPane();

    public void init() {
        setMinimumSize(new Dimension(minWidth, height));
        setLayout(new GridLayout()); // left panel 的布局，让 split pane 占满

        // 在每个标签页放置按钮
        for (int i = 1; i <= 12; ++i) { // i 表示月份
            SplitPaneItem item = new SplitPaneItem();
            item.putDays(i);
            item.setMonthColor(i);
            splitPane.addTab(i+"月", item.panel);
        }

        // 左边切换标签页时，修改全局当前月份，右边重新输出
        splitPane.addChangeListener(e -> {
            DateInfo.currMonth = WindowManager.getCurrMonth();
            WindowManager.rightReload();
        });

        add(splitPane); // 添加到 left panel
    }

    public int getCurrMonth() {
        return splitPane.getSelectedIndex() + 1;
    }
}
