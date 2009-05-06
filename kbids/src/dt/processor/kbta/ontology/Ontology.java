package dt.processor.kbta.ontology;

import java.util.ArrayList;
import java.util.HashMap;

import dt.processor.kbta.ontology.defs.EventDef;
import dt.processor.kbta.ontology.defs.PrimitiveDef;
import dt.processor.kbta.ontology.defs.abstractions.StateDef;
import dt.processor.kbta.ontology.defs.context.ContextDef;

public final class Ontology{
	private final HashMap<String, PrimitiveDef> _primitives;

	private final HashMap<String, EventDef> _events;

	private final ContextDef[] _contexts;

	private final StateDef[] _states;

	Ontology(HashMap<String, PrimitiveDef> primitives,
		HashMap<String, EventDef> events, ArrayList<ContextDef> contexts,
		ArrayList<StateDef> states){
		_primitives = primitives;
		_events = events;
		_contexts = contexts.toArray(new ContextDef[contexts.size()]);
		_states = states.toArray(new StateDef[states.size()]);
	}

	public ContextDef[] getContextDefiners(){
		return _contexts;
	}

	public StateDef[] getStateDefiners(){
		return _states;
	}

	public PrimitiveDef getPrimitiveDef(String name){
		return _primitives.get(name);
	}

	public EventDef getEventDef(String name){
		return _events.get(name);
	}
}