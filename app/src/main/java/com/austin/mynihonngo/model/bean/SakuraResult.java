package com.austin.mynihonngo.model.bean;

import java.io.Serializable;

/**樱花JP 每课时的单个句子条目
 * @author Austin
 *
 */
public class SakuraResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3067200373157305740L;
	private int skrId;
	private int skrLevel;
	private int skrClassNo;
	private String skrSentenceJP;
	private String skrSentenceCn;
	private String skrParse;
	private TSakuraGrammar grammar;
	
	public SakuraResult() {
		super();
	}

	public SakuraResult(int skrId, int skrLevel, int skrClassNo,
			String skrSentenceJP, String skrSentenceCn, String skrParse) {
		super();
		this.skrId = skrId;
		this.skrLevel = skrLevel;
		this.skrClassNo = skrClassNo;
		this.skrSentenceJP = skrSentenceJP;
		this.skrSentenceCn = skrSentenceCn;
		this.skrParse = skrParse;
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

	public String getSkrSentenceJP() {
		return skrSentenceJP;
	}

	public void setSkrSentenceJP(String skrSentenceJP) {
		this.skrSentenceJP = skrSentenceJP;
	}

	public String getSkrSentenceCn() {
		return skrSentenceCn;
	}

	public void setSkrSentenceCn(String skrSentenceCn) {
		this.skrSentenceCn = skrSentenceCn;
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
}
