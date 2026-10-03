package window.MyCompo;

import common.DateInfo;
import common.MyColor;
import window.dialog.ViewDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.MonthDay;

// 注意这里导入了 static 常量 date infos
import static common.DateInfo.dateInfos;
import static common.DateInfo.dayNums;

public class SplitPaneItem extends JComponent {
    // panel
    public JPanel panel = new JPanel(new GridLayout(5, 7));

    public void putDays(int month) {
        // 样式
        panel.setBorder(new EmptyBorder(20, 30, 30, 30)); // 边界留白
        for (int i = 1; i <= dayNums[month]; ++i) { // 按钮
            int I = i; // lambda 表达式需要
            JButton btn = new JButton("" + i) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // 半透明背景
                    if (dateInfos[month][I].exist) g2.setColor(dateInfos[month][I].color); // 纪念日变色
                    else g2.setColor(MyColor.defaultColor);
                    g2.fillRect(0, 0, getWidth(), getHeight());

                    // 绘制文字
                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("宋体", Font.PLAIN, 25));
                    g2.drawString(getText(), 20, 40);

                    g2.dispose();
                }
            };
            btn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            panel.add(btn);

            // 事件监听
            btn.addActionListener(e -> {
                MonthDay date = MonthDay.of(month, I);
                String name = "";
                String notes = "";
                if (dateInfos[month][I].exist) { // 如果有记录就获取，否则保持空串
                    name = dateInfos[month][I].name;
                    notes = dateInfos[month][I].notes;
                }
                ViewDialog dialog = new ViewDialog();
                dialog.init(panel, date, name, notes);
            });
        }
    }

    public void setMonthColor(int month) {
        panel.setBackground(MyColor.monthColors[month]);
    }
}
