package common;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DateInfo {
    public boolean exist = false; // 是否有记录，避免未初始化问题
    public int type; // 纪念日的类型
    // 1. 生日 2. 官方纪念日 3. 事件
    public Color color = MyColor.defaultColor;
    public String name; // 纪念日名称
    public String notes; // 备注

    // 全局日历信息
    public static DateInfo[][] dateInfos = new DateInfo[13][32];

    // 全局当前月份
    public static int currMonth = 1;

    // 全局当前文档
    public static String filename = "default_info.mmcld";
    // 暂时用文件名，以后学了文件流可能会改成文件类
    // 自定义后缀，避免用户打开其他文件
    // 默认为这个初始化的

    // 一个月有多少天
    public static int[] dayNums = {0, 31,29,31,30,31,30,31,31,30,31,30,31};

    // 读取 date infos
    public static void read() {
        // Java 中，创建对象数组时只会初始化数组本身，而不会自动创建数组中的对象。
        for (int i = 0; i < 13; ++i) {
            for (int j = 0; j < 32; j++) {
                dateInfos[i][j] = new DateInfo();
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(DateInfo.filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                // 解析信息，存入数组
                String[] parts = line.split("\\|", -1);
                // 注意要转义；limit 防止丢弃空串（因为可能出现"1|1|1||"这样的）
                int month = Integer.parseInt(parts[0]);
                int day = Integer.parseInt(parts[1]);
                int type = Integer.parseInt(parts[2]);
                String name = parts[3];
                String notes = parts[4];
                dateInfos[month][day].exist = true;
                dateInfos[month][day].type = type;
                dateInfos[month][day].name = name;
                dateInfos[month][day].notes = notes;
                dateInfos[month][day].color = MyColor.typeColors[type];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
