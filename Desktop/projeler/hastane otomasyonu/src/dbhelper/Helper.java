package dbhelper;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class Helper {
    public static void optionPaneChangeButtonText() {
        UIManager.put("OptionPane.okButtonText", Language.get("yes"));
        UIManager.put("OptionPane.cancelButtonText", Language.get("no"));
    }

    public static void showMsg(String str) {
        optionPaneChangeButtonText();
        String msg;
        String title;
        
        switch(str) {
            case "fill":
                msg = Language.get("fill_fields");
                title = Language.get("warning");
                break;
            case "success":
                msg = Language.get("success");
                title = Language.get("info");
                break;
            case "error":
                msg = Language.get("error");
                title = Language.get("error");
                break;
            default:
                msg = str;
                title = Language.get("info");
        }
        
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str) {
        optionPaneChangeButtonText();
        String msg;
        
        if (str.equals("sure")) {
            msg = Language.get("logout_confirm");
        } else {
            msg = str;
        }
        
        return JOptionPane.showConfirmDialog(null, msg, Language.get("warning"), 
            JOptionPane.YES_NO_OPTION) == 0;
    }

    public static String formatDateTr(Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    public static String getMonthNameTr(int month) {
        String[] monthNames = {
            "Ocak", "Şubat", "Mart", "Nisan",
            "Mayıs", "Haziran", "Temmuz", "Ağustos",
            "Eylül", "Ekim", "Kasım", "Aralık"
        };
        if (month >= 1 && month <= 12) {
            return monthNames[month - 1];
        }
        return "";
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldEmpty(JTextArea area) {
        return area.getText().trim().isEmpty();
    }

    public static int screenCenterPoint(String axis, Dimension size) {
        int point = 0;
        switch (axis) {
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
            default:
                point = 0;
        }
        return point;
    }
}