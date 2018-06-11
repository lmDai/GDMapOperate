package cn.yznu.gdmapoperate;

/**
 * 作者：uiho_mac
 * 时间：2018/6/7
 * 描述：
 * 版本：1.0
 * 修订历史：
 */

public class DemoModel {
    private String title;
    private Class clazz;
    private int icon;

    public DemoModel(String title, Class clazz, int icon) {
        this.title = title;
        this.clazz = clazz;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public Class getClazz() {
        return clazz;
    }
}
