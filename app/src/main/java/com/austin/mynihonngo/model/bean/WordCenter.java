package com.austin.mynihonngo.model.bean;

import java.io.Serializable;
import java.util.List;

/**单词bean-- 侧边栏
 * @author Austin
 *
 */
public class WordCenter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4991113284367218083L;
	private List<WordType> wordTypes;
	
	private Integer code;//响应码
	private String msg;//请求响应消息

	public List<WordType> getWordTypes() {
		return wordTypes;
	}

	public void setWordType(List<WordType> wordTypes) {
		this.wordTypes = wordTypes;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
