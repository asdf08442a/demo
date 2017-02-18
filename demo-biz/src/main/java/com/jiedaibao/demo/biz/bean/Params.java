package com.jiedaibao.demo.biz.bean;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Params implements Serializable {
	private static final long serialVersionUID = 6824672497013935827L;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date formatTime;
	private Date originTime;
	private int i;

	public Date getFormatTime() {
		return formatTime;
	}

	public Date getOriginTime() {
		return originTime;
	}

	public int getI() {
		return i;
	}

	public void setFormatTime(Date formatTime) {
		this.formatTime = formatTime;
	}

	public void setOriginTime(Date originTime) {
		this.originTime = originTime;
	}

	public void setI(int i) {
		this.i = i;
	}

}
