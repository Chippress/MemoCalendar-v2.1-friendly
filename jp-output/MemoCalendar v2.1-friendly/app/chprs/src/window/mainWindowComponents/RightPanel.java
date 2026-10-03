package window.mainWindowComponents;

import common.DateInfo;
import common.MyColor;
import common.WindowManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// 注意这里导入了 static 常量 date infos
import static common.DateInfo.dateInfos;
import static common.DateInfo.dayNums;

public class RightPanel extends JPanel {
    // 窗口尺寸常数
    final int minWidth = 200;
    final int height = 600;

    public void init() {
        setBackground(MyColor.tegamiColor);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // 每个标签独占一行，从左对齐
        setMinimumSize(new Dimension(minWidth, height));

        putLines();
    }

    public void putLines() {
        int month = WindowManager.getCurrMonth();
        for (int i = 1; i <= dayNums[month]; ++i) {
            if (dateInfos[month][i].exist) { // 若存在
                String dateStr = month + "." + i + "  ";
                String nameStr = dateInfos[month][i].name;
                JLabel line = new JLabel(dateStr + nameStr);

                // 添加颜色图标
                switch (dateInfos[month][i].type) {
                    case 1:
                        line.setIcon(createColorIcon(Color.GREEN));
                        break;
                    case 2:
                        line.setIcon(createColorIcon(Color.RED));
                        break;
                    case 3:
                        line.setIcon(createColorIcon(Color.BLUE));
                        break;
                    default:
                        line.setIcon(createColorIcon(Color.GRAY));
                        break;
                } // 为了使用旧版的 exe4j，使用 Java 11

                // 设置下边距，配合 box layout
                line.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

                line.setFont(new Font("微软雅黑", Font.PLAIN, 20));
                add(line);
            }
        }
    }

    // 彩色方块
    private ImageIcon createColorIcon(Color color) {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, 16, 16);
        g2d.dispose();
        return new ImageIcon(image);
    }
}
