package com.austin.mynihonngo.engine;
/**
* 对外的ViewPager的回调接口(单词中心使用的 自定义控件viewPagerIndicator中回调使用)
*
* @author zhy
*
*/
public interface IPageChangeListener
{
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
 
      public void onPageSelected(int position);
 
      public void onPageScrollStateChanged(int state);
}
 