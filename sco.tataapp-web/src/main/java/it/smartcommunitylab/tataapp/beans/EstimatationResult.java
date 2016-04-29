package it.smartcommunitylab.tataapp.beans;

public class EstimatationResult {
	EstimatationData estimateData;
	Double estimation;

	public EstimatationResult(EstimatationData data, Double estimation) {
		this.estimateData = data;
		this.estimation = estimation;
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
