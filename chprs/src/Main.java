import common.WindowManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // 设置皮肤
        try {
            // 通过类名直接指定
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("ERROR in setLookAndFeel");
        }

        // 初始化和显示 welcome window
        WindowManager.initWelcomeWindow();
        WindowManager.showWelcomeWindow();
    }
}
