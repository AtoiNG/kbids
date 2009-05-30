/**
 * 
 */
package dt.processor.kbta.ontology.defs;

import dt.processor.kbta.ontology.Ontology;

/**
 * @author
 */
public abstract class ElementDef{
	protected final String _name;

	private int _lastCreated;

	protected boolean _isMonitored;

	protected int _monitoredCounter;

	public ElementDef(String name){
		_name = name;
		_lastCreated = -1;
		_monitoredCounter = 0;
		_isMonitored = false;
	}
	

	public abstract void accept(Ontology ontology, ElementVisitor visitor);
	

	public final void setInitiallyUnmonitored(){
		_monitoredCounter = 0;
		_isMonitored = false;
	}
	
	public final void setInitiallyMonitored(Ontology ontology){
		ElementVisitor callback = new ElementVisitor(){
			@Override
			public void visit(ElementDef ed){				
				ed._isMonitored = true;
				++ed._monitoredCounter;
			}
		};
		accept(ontology, callback);	
	}	

	public final void setMonitored(Ontology ontology, final boolean monitored){
		ElementVisitor callback = new ElementVisitor(){
			@Override
			public void visit(ElementDef ed){
				ed._monitoredCounter += (monitored ? 1 : (ed._monitoredCounter > 0 ? -1 : 0));
				ed._isMonitored = ed._monitoredCounter > 0;
			}
		};
		accept(ontology, callback);	
	}
	

	public final boolean isMonitored(){
		return _isMonitored;
	}

	public final String getName(){
		return _name;
	}

	public final boolean assertNotCreatedIn(int iteration){
		return _lastCreated != iteration;
	}

	public final void setLastCreated(int iteration){
		_lastCreated = iteration;
	}

	public interface ElementVisitor{
		public void visit(ElementDef ed);
	}
}
