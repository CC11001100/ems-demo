package org.cc.ems.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内存存储
 * 
 * 将数据存储在内存中，维护着一个List存储数据，然后在<abbr>适当的时机</abbr>同步数据到磁盘上。
 * 
 * 适当的时机：
 * 1. 显式的调用save()方法的时候
 * 2. 有一个定时器，每隔SAVE_INTERVAL同步一次，是为了避免半途崩溃造成不必要的数据丢失。
 * 
 * 	当然当然啦，肯定会有一个标志来记录上次同步后数据是否被改变，如果没有改变的话当然没有同步的必要了。
 * 
 * 
 * @author cc
 *
 */
public class MemoryDB<K,V> {

	//就用这玩意儿存储
	private Map<K,V> db=new HashMap<K,V>();

	/**保存间隔**/
	public int SAVE_INTERVAL=1000*60*10;
	
	public Map<K, V> getDb() {
		return db;
	}

	/**
	 * 将DB中的对象以XML的方式保存到磁盘的指定位置
	 * @return
	 */
	public boolean save(String fqdn){
		//当第一次保存的时候将sqdn保存下来同时启动一个任务每隔一段时间就保存一下。
		return true;
	}
	
}
