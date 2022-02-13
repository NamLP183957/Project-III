package model;

public class RiskAssessment {
	private Risk risk;
	private LikelihoodLevel likelihoodLevel;
	private ImpactLevel impactLevel;
	private RiskLevel riskLevel;
	
	public RiskAssessment() {
		// TODO Auto-generated constructor stub
	}

	public RiskAssessment(Risk risk, LikelihoodLevel likelihoodLevel, ImpactLevel impactLevel, RiskLevel riskLevel) {
		super();
		this.risk = risk;
		this.likelihoodLevel = likelihoodLevel;
		this.impactLevel = impactLevel;
		this.riskLevel = riskLevel;
	}

	public Risk getRisk() {
		return risk;
	}

	public void setRisk(Risk risk) {
		this.risk = risk;
	}

	public LikelihoodLevel getLikelihoodLevel() {
		return likelihoodLevel;
	}

	public void setLikelihoodLevel(LikelihoodLevel likelihoodLevel) {
		this.likelihoodLevel = likelihoodLevel;
	}

	public ImpactLevel getImpactLevel() {
		return impactLevel;
	}

	public void setImpactLevel(ImpactLevel impactLevel) {
		this.impactLevel = impactLevel;
	}

	public RiskLevel getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(RiskLevel riskLevel) {
		this.riskLevel = riskLevel;
	}
	
	
}
