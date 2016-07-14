package com.austin.mynihonngo.model.bean;

/**
 * 
 * @author  EntreGb entity. @author Austin
 * 业务抢单表bean
 * 
 */
public class EntreGb implements java.io.Serializable {

	// Fields

	private String recId;
	private String gbNo; //订单号的产生规则
	private String gbEntrename;
	private String gbEntreusername;
	private String gbGuestname;
	private String gbGuestphone;
	private String gbGuestaddress;
	private String gbQuest;
	private String gbMemo;
	private String gbStartdate;
	private String gbStartampm;
	private String gbEnddate;
	private String gbEndampm;
	private String gbGrabed;//抢单状态 初始是:0(未被抢单）  1：已被抢单
	private Float gbFee;
	private String gbStatus;//抢单状态 1:过期 2:取消
	private String gbHbxusername;
	private String gbItemname;//服务项目
	private String gbCityname;
	private String gbCitycode;
	private String gbTelephone;
	private String gbPubtime;//抢单发布时间
	private String oddGdsclass;
	private String oddGdsspeci;
	private String oddGdsamt;
	private String oddOther;
	private String oddCate;
	private String oddType;
	private String oddAA01;
	private String oddAA02;
	private String oddAA03;
	private String oddAA04;
	private String oddAA05;
	private String oddAA06;
	private String oddAA07;
	private String oddBA01;
	private String oddBA02;
	private String oddBA03;
	private String oddCA01;
	private String oddCA02;
	private String oddJpg1;//动态图片1
	private String oddJpg2;//动态图片2
	private String oddJpg3;//动态图片3
	// Constructors

	/** default constructor */
	public EntreGb() {
	}

	/** full constructor */
	public EntreGb(String recId, String gbNo, String gbEntrename,
			String gbEntreusername, String gbGuestname, String gbGuestphone,
			String gbGuestaddress, String gbQuest, String gbMemo,
			String gbStartdate, String gbStartampm, String gbEnddate,
			String gbEndampm, String gbGrabed,float gbFee,String gbStatus,String gbHbxusername,
			String gbItemname, String gbCityname, String gbCitycode, String gbTelephone, String gbPubtime,
			String oddGdsclass, String oddGdsspeci, String oddGdsamt,
			String oddOther, String oddCate, String oddType, String oddAA01,
			String oddAA02, String oddAA03, String oddAA04, String oddAA05,
			String oddAA06, String oddAA07, String oddBA01, String oddBA02,
			String oddBA03, String oddCA01, String oddCA02,
			String oddJpg1, String oddJpg2, String oddJpg3) {
		this.recId = recId;
		this.gbNo = gbNo;
		this.gbEntrename = gbEntrename;
		this.gbEntreusername = gbEntreusername;
		this.gbGuestname = gbGuestname;
		this.gbGuestphone = gbGuestphone;
		this.gbGuestaddress = gbGuestaddress;
		this.gbQuest = gbQuest;
		this.gbMemo = gbMemo;
		this.gbStartdate = gbStartdate;
		this.gbStartampm = gbStartampm;
		this.gbEnddate = gbEnddate;
		this.gbEndampm = gbEndampm;
		this.gbGrabed = gbGrabed;
		this.gbFee = gbFee;
		this.gbStatus = gbStatus;
		this.gbHbxusername = gbHbxusername;
		this.gbItemname = gbItemname;
		this.gbCityname = gbCityname;
		this.gbCitycode = gbCitycode;
		this.gbTelephone = gbTelephone;
		this.gbPubtime = gbPubtime;
		this.oddGdsclass = oddGdsclass;
		this.oddGdsspeci = oddGdsspeci;
		this.oddGdsamt = oddGdsamt;
		this.oddOther = oddOther;
		this.oddCate = oddCate;
		this.oddType = oddType;
		this.oddAA01 = oddAA01;
		this.oddAA02 = oddAA02;
		this.oddAA03 = oddAA03;
		this.oddAA04 = oddAA04;
		this.oddAA05 = oddAA05;
		this.oddAA06 = oddAA06;
		this.oddAA07 = oddAA07;
		this.oddBA01 = oddBA01;
		this.oddBA02 = oddBA02;
		this.oddBA03 = oddBA03;
		this.oddCA01 = oddCA01;
		this.oddCA02 = oddCA02;
		this.oddJpg1 = oddJpg1;
		this.oddJpg2 = oddJpg2;
		this.oddJpg3 = oddJpg3;
	}

	// Property accessors

	public String getRecId() {
		return this.recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public String getGbNo() {
		return this.gbNo;
	}

	public void setGbNo(String gbNo) {
		this.gbNo = gbNo;
	}

	public String getGbEntrename() {
		return this.gbEntrename;
	}

	public void setGbEntrename(String gbEntrename) {
		this.gbEntrename = gbEntrename;
	}

	public String getGbEntreusername() {
		return this.gbEntreusername;
	}

	public void setGbEntreusername(String gbEntreusername) {
		this.gbEntreusername = gbEntreusername;
	}

	public String getGbGuestname() {
		return this.gbGuestname;
	}

	public void setGbGuestname(String gbGuestname) {
		this.gbGuestname = gbGuestname;
	}

	public String getGbGuestphone() {
		return this.gbGuestphone;
	}

	public void setGbGuestphone(String gbGuestphone) {
		this.gbGuestphone = gbGuestphone;
	}

	public String getGbGuestaddress() {
		return this.gbGuestaddress;
	}

	public void setGbGuestaddress(String gbGuestaddress) {
		this.gbGuestaddress = gbGuestaddress;
	}

	public String getGbQuest() {
		return this.gbQuest;
	}

	public void setGbQuest(String gbQuest) {
		this.gbQuest = gbQuest;
	}

	public String getGbMemo() {
		return this.gbMemo;
	}

	public void setGbMemo(String gbMemo) {
		this.gbMemo = gbMemo;
	}

	public String getGbStartdate() {
		return this.gbStartdate;
	}

	public void setGbStartdate(String gbStartdate) {
		this.gbStartdate = gbStartdate;
	}

	public String getGbStartampm() {
		return this.gbStartampm;
	}

	public void setGbStartampm(String gbStartampm) {
		this.gbStartampm = gbStartampm;
	}

	public String getGbEnddate() {
		return this.gbEnddate;
	}

	public void setGbEnddate(String gbEnddate) {
		this.gbEnddate = gbEnddate;
	}

	public String getGbEndampm() {
		return this.gbEndampm;
	}

	public void setGbEndampm(String gbEndampm) {
		this.gbEndampm = gbEndampm;
	}

	public String getGbGrabed() {
		return this.gbGrabed;
	}

	public void setGbGrabed(String gbGrabed) {
		this.gbGrabed = gbGrabed;
	}

	public Float getGbFee() {
		return gbFee;
	}

	public void setGbFee(Float gbFee) {
		this.gbFee = gbFee;
	}

	public String getGbStatus() {
		return gbStatus;
	}

	public void setGbStatus(String gbStatus) {
		this.gbStatus = gbStatus;
	}

	public String getGbHbxusername() {
		return gbHbxusername;
	}

	public void setGbHbxusername(String gbHbxusername) {
		this.gbHbxusername = gbHbxusername;
	}

	public String getGbItemname() {
		return gbItemname;
	}

	public void setGbItemname(String gbItemname) {
		this.gbItemname = gbItemname;
	}

	public String getGbCityname() {
		return gbCityname;
	}

	public void setGbCityname(String gbCityname) {
		this.gbCityname = gbCityname;
	}

	public String getGbCitycode() {
		return gbCitycode;
	}

	public void setGbCitycode(String gbCitycode) {
		this.gbCitycode = gbCitycode;
	}

	public String getGbTelephone() {
		return gbTelephone;
	}

	public void setGbTelephone(String gbTelephone) {
		this.gbTelephone = gbTelephone;
	}

	public String getGbPubtime() {
		return gbPubtime;
	}

	public void setGbPubtime(String gbPubtime) {
		this.gbPubtime = gbPubtime;
	}

	public String getOddGdsclass() {
		return oddGdsclass;
	}

	public void setOddGdsclass(String oddGdsclass) {
		this.oddGdsclass = oddGdsclass;
	}

	public String getOddGdsspeci() {
		return oddGdsspeci;
	}

	public void setOddGdsspeci(String oddGdsspeci) {
		this.oddGdsspeci = oddGdsspeci;
	}

	public String getOddGdsamt() {
		return oddGdsamt;
	}

	public void setOddGdsamt(String oddGdsamt) {
		this.oddGdsamt = oddGdsamt;
	}

	public String getOddOther() {
		return oddOther;
	}

	public void setOddOther(String oddOther) {
		this.oddOther = oddOther;
	}

	public String getOddCate() {
		return oddCate;
	}

	public void setOddCate(String oddCate) {
		this.oddCate = oddCate;
	}

	public String getOddType() {
		return oddType;
	}

	public void setOddType(String oddType) {
		this.oddType = oddType;
	}

	public String getOddAA01() {
		return oddAA01;
	}

	public void setOddAA01(String oddAA01) {
		this.oddAA01 = oddAA01;
	}

	public String getOddAA02() {
		return oddAA02;
	}

	public void setOddAA02(String oddAA02) {
		this.oddAA02 = oddAA02;
	}

	public String getOddAA03() {
		return oddAA03;
	}

	public void setOddAA03(String oddAA03) {
		this.oddAA03 = oddAA03;
	}

	public String getOddAA04() {
		return oddAA04;
	}

	public void setOddAA04(String oddAA04) {
		this.oddAA04 = oddAA04;
	}

	public String getOddAA05() {
		return oddAA05;
	}

	public void setOddAA05(String oddAA05) {
		this.oddAA05 = oddAA05;
	}

	public String getOddAA06() {
		return oddAA06;
	}

	public void setOddAA06(String oddAA06) {
		this.oddAA06 = oddAA06;
	}

	public String getOddAA07() {
		return oddAA07;
	}

	public void setOddAA07(String oddAA07) {
		this.oddAA07 = oddAA07;
	}

	public String getOddBA01() {
		return oddBA01;
	}

	public void setOddBA01(String oddBA01) {
		this.oddBA01 = oddBA01;
	}

	public String getOddBA02() {
		return oddBA02;
	}

	public void setOddBA02(String oddBA02) {
		this.oddBA02 = oddBA02;
	}

	public String getOddBA03() {
		return oddBA03;
	}

	public void setOddBA03(String oddBA03) {
		this.oddBA03 = oddBA03;
	}

	public String getOddCA01() {
		return oddCA01;
	}

	public void setOddCA01(String oddCA01) {
		this.oddCA01 = oddCA01;
	}

	public String getOddCA02() {
		return oddCA02;
	}

	public void setOddCA02(String oddCA02) {
		this.oddCA02 = oddCA02;
	}

	public String getOddJpg1() {
		return oddJpg1;
	}

	public void setOddJpg1(String oddJpg1) {
		this.oddJpg1 = oddJpg1;
	}

	public String getOddJpg2() {
		return oddJpg2;
	}

	public void setOddJpg2(String oddJpg2) {
		this.oddJpg2 = oddJpg2;
	}

	public String getOddJpg3() {
		return oddJpg3;
	}

	public void setOddJpg3(String oddJpg3) {
		this.oddJpg3 = oddJpg3;
	}

}