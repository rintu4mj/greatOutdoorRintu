package com.capgemini.go.dto;

import java.util.Date;

public class ReturnReportRequest {

	private Date start;
	private Date end;
	private Date year;
	private Date yearStart;
	private Date yearEnd;
	int mode_of_report;

	

	public ReturnReportRequest(Date start, Date end, Date year, Date yearStart, Date yearEnd, int mode_of_report) {
		super();
		this.start = start;
		this.end = end;
		this.year = year;
		this.yearStart = yearStart;
		this.yearEnd = yearEnd;
		this.mode_of_report = mode_of_report;
	}
	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getYear() {
		return year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public Date getYearStart() {
		return yearStart;
	}

	public void setYearStart(Date yearStart) {
		this.yearStart = yearStart;
	}

	public Date getYearEnd() {
		return yearEnd;
	}

	public void setYearEnd(Date yearEnd) {
		this.yearEnd = yearEnd;
	}

	public int getMode_of_report() {
		return mode_of_report;
	}

	public void setMode_of_report(int mode_of_report) {
		this.mode_of_report = mode_of_report;
	}

}
