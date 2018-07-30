package cn.yznu.gdmapoperate.ui.widget;

/**
 * Created by alphabet on 2018/6/4.
 */

public interface CommonScrollInterface {

  void pageScrollToTop();

  void pageScrollTo(int disY);

  int maxScrollDisY();

  boolean isPageVisible();
}
