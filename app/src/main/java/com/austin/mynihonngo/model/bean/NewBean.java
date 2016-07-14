package com.austin.mynihonngo.model.bean;

import java.util.List;

public class NewBean {
	public NewBeanItem data;
	public int retcode;
	
	public class NewBeanItem{
		public String countcommenturl;
		//加载更多
		public String more;
		public List<News> news;
		public String title;
		public List<Topic> topic;
		public List<Topnews> topnews;
	}
	
	public class News{
		public String comment;
		public String commentlist;
		public String commenturl;
		public String id;
		//新闻列表的图片
		public String listimage;
		//时间
		public String pubdate;
		//新闻列表的标题文字	
		public String title;
		public String type;
		public String url;
		//是否读过当前新闻
		public boolean isRead;
	}
	
	public class Topic{
		public String description;
		public String id;
		public String listimage;
		public String sort;
		public String title;
		public String url;
	}
	
	public class Topnews{
		public String comment;
		public String commentlist;
		public String commenturl;
		public String id;
		//轮播图图片链接地址
		public String topimage;
		//时间
		public String pubdate;
		//新闻列表的标题文字	
		public String title;
		public String type;
		public String url;
	}
}
