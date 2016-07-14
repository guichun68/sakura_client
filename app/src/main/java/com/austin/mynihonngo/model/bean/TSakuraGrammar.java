package com.austin.mynihonngo.model.bean;

import java.io.Serializable;

/**
 * 相关语法bean(更多相关语法说明 web urlId)
 * @author Austin
 *
 */
public class TSakuraGrammar implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5578500817270176487L;
	private int skrId;//表主键
	private String title;//语法主题
	private String url;//相关语法说明 web url
//	private Set<TSakura> sakuraSet = new TreeSet<TSakura>(); //习惯于，给容器创建实例，方便操作

	public TSakuraGrammar() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getSkrId() {
		return skrId;
	}
	public void setSkrId(int skrId) {
		this.skrId = skrId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
/*	public Set<TSakura> getSakuraSet() {
		return sakuraSet;
	}
	public void setSakuraSet(Set<TSakura> sakuraSet) {
		this.sakuraSet = sakuraSet;
	}
	public TSakuraGrammar(int skrId, String title, String url,
			Set<TSakura> sakuraSet) {
		super();
		this.skrId = skrId;
		this.title = title;
		this.url = url;
		this.sakuraSet = sakuraSet;
	}*/
	public TSakuraGrammar(int skrId, String title, String url) {
		super();
		this.skrId = skrId;
		this.title = title;
		this.url = url;
	}

}
