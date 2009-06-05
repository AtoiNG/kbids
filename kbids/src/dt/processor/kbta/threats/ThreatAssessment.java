package dt.processor.kbta.threats;

import android.content.SharedPreferences;
import dt.processor.kbta.Env;
import dt.processor.kbta.ontology.Ontology;
import dt.processor.kbta.ontology.defs.ElementDef;
import dt.processor.kbta.ontology.instances.Element;

public final class ThreatAssessment{
	private final String _title;

	private final String _description;

	private final int _baseCertainty;

	private final GeneratedFrom _generatedFrom;

	private boolean _isMonitored;

	public ThreatAssessment(String title, String description, int baseCertainty,
		boolean isMonitored, GeneratedFrom generatedFrom){
		_title = title;
		_description = description;
		_baseCertainty = baseCertainty;
		_generatedFrom = generatedFrom;
		_isMonitored = isMonitored;
	}

	public String getTitle(){
		return _title;
	}

	public String getDescription(){
		return _description;
	}

	public int getCertainty(Element e){
		long minDuration = _generatedFrom.getMinDuration();
		double actualDuration = e.getTimeInterval().getDuration();
		double percent = actualDuration / minDuration;

		return Math.min(100, (int)(_baseCertainty * percent));
	}

	public GeneratedFrom getGeneratedFrom(){
		return _generatedFrom;
	}

	public boolean setInitiallyMonitoredThreat(Ontology ontology, SharedPreferences.Editor spe){
		ElementDef elementDef = _generatedFrom.getElementDef(ontology);
		if (elementDef == null){
			// Indicating that the element on which the threat assessment relies
			// doesn't exist in the ontology therefore this assessment should
			// be removed
			return false;
		}
		_isMonitored = Env.getSharedPreferences().getBoolean(_title, _isMonitored);
		if (_isMonitored){
			spe.putBoolean(_title, true);
			// There is only a point in traversing the elements if they are
			// monitored, otherwise they will remain unmonitored by default
			elementDef.setInitiallyMonitored(ontology);
		}
		return true;
	}
	
	public void setMonitoredThreat(Ontology ontology, boolean isMonitored){
		_isMonitored = isMonitored;
		ElementDef elementDef = _generatedFrom.getElementDef(ontology);
		elementDef.setMonitored(ontology, isMonitored);
	}
	
	public boolean isMonitored(){
		return _isMonitored;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder("\nAssessment\n");
		sb.append(_title).append(" [BaseCert ").append(_baseCertainty).append("]");
		sb.append("\nDescription: ").append(_description);
		sb.append("\nmonitored= ").append(_isMonitored);
		sb.append("\nGenerated From: ").append(_generatedFrom);
		return sb.toString();
	}

	public String toString(Element e){
		StringBuilder sb = new StringBuilder("\nAssessment\n");
		sb.append(_title).append(" [").append(getCertainty(e)).append("]");
		sb.append("\nDescription: ").append(_description);
		sb.append("\nmonitored= ").append(_isMonitored);
		sb.append("\nGenerated From: ").append(_generatedFrom);
		return sb.toString();
	}

}
