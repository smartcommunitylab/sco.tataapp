package it.smartcommunitylab.tataapp.beans;

public class EstimatationResult {
	private EstimatationData estimateData;
	private Double estimation;
	private Double bonusRate;
	private Double totalRate;

	public Double getBonusRate() {
		return bonusRate;
	}

	public void setBonusRate(Double bonusRate) {
		this.bonusRate = bonusRate;
	}

	public Double getTotalRate() {
		return totalRate;
	}

	public void setTotalRate(Double totalRate) {
		this.totalRate = totalRate;
	}

	public EstimatationResult(EstimatationData data, Double estimation, Double totalRate, Double bonusRate) {
		this.estimateData = data;
		this.estimation = estimation;
		this.bonusRate = bonusRate;
		this.totalRate = totalRate;
	}

	public EstimatationData getEstimateData() {
		return estimateData;
	}

	public void setEstimateData(EstimatationData estimateData) {
		this.estimateData = estimateData;
	}

	public Double getEstimation() {
		return estimation;
	}

	public void setEstimation(Double estimation) {
		this.estimation = estimation;
	}

}
