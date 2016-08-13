package org.cc.ems.util;

import java.awt.Point;
import java.awt.Toolkit;

/**
 * 计算屏幕大小、位置等工具类
 * @author cc
 *
 */
public final class ScreenUtil {

	private ScreenUtil() {
	}
	
	private static ScreenUtil instance;
	
	public synchronized static ScreenUtil getInstance(){
		synchronized (ScreenUtil.class) {
			if(instance==null) instance=new ScreenUtil();
		}
		return instance;
	}
	
	/**
	 * 根据传入的窗体的大小计算居中应处的位置
	 * @param width
	 * @param height
	 * @return
	 */
	public Point getCenterLocation(int width,int height){
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		return new Point((w-width)/2,(h-height)/2);
	}
}
