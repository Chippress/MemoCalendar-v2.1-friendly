package window.dialog;

import common.DateInfo;
import common.WindowManager;
import window.WelcomeWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ChooseDocDialog extends JFileChooser {

    public ChooseDocDialog() {
        // 设置文件过滤器
        FileNameExtensionFilter filter = new FileNameExtensionFilter("纪念日日历文档 (*.mmcld)", "mmcld");
        setCurrentDirectory(new File(".")); // 设置初始路径
        setFileFilter(filter);
        setAcceptAllFileFilterUsed(false); // 不显示"所有文件"
    }

    public void shows(WelcomeWindow parent) {
        // 检查用户是否点击了"打开"或"保存"按钮
        int result = showOpenDialog(parent); // 打开
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = getSelectedFile();
            if (selectedFile != null) {
                // 修改全局文件名
                DateInfo.filename = selectedFile.getName();
            }

            cancelSelection(); // 显式清理引用，帮助 GC 回收

            // 关闭 welcome，初始化并打开 main
            WindowManager.hideWelcomeWindow();
            WindowManager.initMainWindow();
            WindowManager.showMainWindow();
        }
    }
}
