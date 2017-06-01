package kr.co.codedraw.dto;

public class CrimeCountDto {
	private int cId;
	private String date;
	private double crimeCount;
	private String regionCodeNum;
	private String crimeCodeNum;
	private String presentDate;
	private String preYearDate;
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getCrimeCount() {
		return crimeCount;
	}
	public void setCrimeCount(double crimeCount) {
		this.crimeCount = crimeCount;
	}
	public String getRegionCodeNum() {
		return regionCodeNum;
	}
	public void setRegionCodeNum(String regionCodeNum) {
		this.regionCodeNum = regionCodeNum;
	}
	public String getCrimeCodeNum() {
		return crimeCodeNum;
	}
	public void setCrimeCodeNum(String crimeCodeNum) {
		this.crimeCodeNum = crimeCodeNum;
	}
	public String getPresentDate() {
		return presentDate;
	}
	public void setPresentDate(String presentDate) {
		this.presentDate = presentDate;
	}
	public String getPreYearDate() {
		return preYearDate;
	}
	public void setPreYearDate(String preYearDate) {
		this.preYearDate = preYearDate;
	}
	
}
