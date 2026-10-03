package common;

import java.awt.*;

public class MyColor {
    // 颜色数组 (1-based)
    public static Color springColor = new Color(144, 238, 144);
    public static Color summerColor = new Color(255, 127, 80);
    public static Color autumnColor = new Color(255, 215, 0);
    public static Color winterColor = new Color(100, 180, 255);
    public static Color[] monthColors = {Color.WHITE,
            winterColor,winterColor,springColor,
            springColor,springColor,summerColor,
            summerColor,summerColor,autumnColor,
            autumnColor,autumnColor,winterColor};

    // 默认（记录为空，type 为 0）的半透明颜色
    public static Color defaultColor = new Color(255, 255, 255, 150);

    // 各种 type 的颜色：生日绿、纪念红、事件蓝
    public static Color[] typeColors = {defaultColor, Color.GREEN, Color.RED, Color.BLUE};

    // 信纸米黄色
    public static Color tegamiColor = new Color(250, 240, 220);
}
