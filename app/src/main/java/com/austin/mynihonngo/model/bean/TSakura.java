package com.austin.mynihonngo.model.bean;

import java.io.Serializable;

/**
 * 樱花日语 例句
 * @author Austin
 *
 */
public class TSakura implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2030856838545935776L;
	private int skrId;
	private int skrLevel;//级别
	private int skrClassNo;//课时
	private String skrSentenceCn;//中文例句
	private String skrSentenceJP;//日语例句
	private String skrParse;//解析
	private TSakuraGrammar grammar;//TSakuraGrammar外键，
	private int moreUrlId;

	public TSakura() {
		super();
	}
	public int getSkrId() {
		return skrId;
	}
	public void setSkrId(int skrId) {
		this.skrId = skrId;
	}
	public int getSkrLevel() {
		return skrLevel;
	}
	public void setSkrLevel(int skrLevel) {
		this.skrLevel = skrLevel;
	}
	public int getSkrClassNo() {
		return skrClassNo;
	}
	public void setSkrClassNo(int skrClassNo) {
		this.skrClassNo = skrClassNo;
	}
	public String getSkrSentenceCn() {
		return skrSentenceCn;
	}
	public void setSkrSentenceCn(String skrSentenceCn) {
		this.skrSentenceCn = skrSentenceCn;
	}
	public String getSkrSentenceJP() {
		return skrSentenceJP;
	}
	public void setSkrSentenceJP(String skrSentenceJP) {
		this.skrSentenceJP = skrSentenceJP;
	}
	public String getSkrParse() {
		return skrParse;
	}
	public void setSkrParse(String skrParse) {
		this.skrParse = skrParse;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public TSakuraGrammar getGrammar() {
		return grammar;
	}
	public void setGrammar(TSakuraGrammar grammar) {
		this.grammar = grammar;
	}
	public TSakura(int skrId, int skrLevel, int skrClassNo,
			String skrSentenceCn, String skrSentenceJP, String skrParse,
			TSakuraGrammar grammar) {
		super();
		this.skrId = skrId;
		this.skrLevel = skrLevel;
		this.skrClassNo = skrClassNo;
		this.skrSentenceCn = skrSentenceCn;
		this.skrSentenceJP = skrSentenceJP;
		this.skrParse = skrParse;
		this.grammar = grammar;
	}

	public int getMoreUrlId() {
		return moreUrlId;
	}

	public void setMoreUrlId(int moreUrlId) {
		this.moreUrlId = moreUrlId;
	}
}
