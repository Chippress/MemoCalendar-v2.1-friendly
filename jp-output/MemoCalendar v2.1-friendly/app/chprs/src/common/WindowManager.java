package common;

import window.MainWindow;
import window.WelcomeWindow;

public class WindowManager {
    // 全局唯一的 welcome window
    public static WelcomeWindow welcomeWindow = new WelcomeWindow();

    // main window，可能会多次摧毁和重新 new
    public static MainWindow mainWindow;

    public static void initWelcomeWindow() {
        welcomeWindow.init();
    }

    public static void showWelcomeWindow() {
        welcomeWindow.setVisible(true);
    }

    public static void hideWelcomeWindow() {
        welcomeWindow.setVisible(false);
    }

    public static void initMainWindow() {
        if (mainWindow != null) mainWindow = null; // 加一层保险
        mainWindow = new MainWindow();
        mainWindow.init();
    }

    public static void showMainWindow() {
        mainWindow.setVisible(true);
    }

    public static void destroyMainWindow() {
        // main window 没有 hide，只有 destroy
        mainWindow.dispose();
        mainWindow = null;
    }

    public static int getCurrMonth() {
        return mainWindow.getCurrMonth();
    }

    // 重新输出右边，用于切换月份后
    public static void rightReload() {
        mainWindow.rightPanel.removeAll(); // 清空面板上的所有组件
        mainWindow.rightPanel.revalidate(); // 让容器重新布局
        mainWindow.rightPanel.repaint(); // 触发重绘，刷新界面

        mainWindow.rightPanel.putLines();
    }

    // 重新加载 main window，用于编辑后
    public static void reloadMainWindow() {
        destroyMainWindow();
        initMainWindow();
        showMainWindow();
    }
}
