package window.MyCompo;

import common.WindowManager;

import javax.swing.*;
import java.awt.*;

public class TypeChooser extends ButtonGroup {
    public final int optsNum = 3; // 除去 0 号选项之外的选项数
    public JCheckBox[] opts = new JCheckBox[optsNum + 1]; // 1-based

    public TypeChooser() {
        opts[0] = new JCheckBox("0 删除");
        opts[1] = new JCheckBox("1 生日绿");
        opts[2] = new JCheckBox("2 纪念红");
        opts[3] = new JCheckBox("3 事件蓝");
        for (JCheckBox opt : opts) {
            add(opt);
        }
    }

    public int getSelected(JDialog owner) { // 避免与 getSelection 重名
        for (int i = 0; i <= optsNum; ++i) { // 注意有 0 号，需要从 0 开始
            if (opts[i].isSelected()) {
                return i;
            } // 遍历求得选中的 type
        }



        return -1; // 让上级知道应该重新 get selected
    }

    public boolean checkSelected() {
        for (int i = 0; i <= optsNum; ++i) { // 注意有 0 号，需要从 0 开始
            if (opts[i].isSelected()) {
                return true;
            }
        }
        return false;
    }

    public void warn() {
        Toolkit.getDefaultToolkit().beep(); // 播放系统提示音
        // 创建 JOptionPane 实例
        JOptionPane dialog = new JOptionPane(
                "请选择纪念日类型！",           // 消息内容
                JOptionPane.PLAIN_MESSAGE,   // 消息类型（无图标）
                JOptionPane.DEFAULT_OPTION     // 选项类型（只有确定按钮）
        );
        // 创建包含该 JOptionPane 的对话框
        JDialog jd = dialog.createDialog(null, "提示");
        jd.setVisible(true); // 显示对话框（模态，必须处理后才能继续）
    }
}
