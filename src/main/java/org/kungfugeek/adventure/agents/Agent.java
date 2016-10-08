package org.kungfugeek.adventure.agents;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.data.annotation.Id;

/**
 * adventure
 * Agent
 * @author Nate
 * Oct 8, 2016
 */
public class Agent {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Agent.class);

	@Id
	private String id;
	
	private String name;	
	private List<Modifier> mods;
	
	private Map<AgentAttribute, Integer> attMap;
	
	public static class AgentBuilder {
		private String name;
		private Map<AgentAttribute, Integer> attMap = new HashMap<AgentAttribute, Integer>(AgentAttribute.values().length);
		
		public Agent build() {
			return new Agent(name, attMap);
		}
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public AgentBuilder setName(String name) {
			this.name = name;
			return this;
		}
		/**
		 * @return the attMap
		 */
		public Map<AgentAttribute, Integer> getAttMap() {
			return attMap;
		}
		/**
		 * @param attMap the attMap to set
		 */
		public AgentBuilder setAttMap(Map<AgentAttribute, Integer> attMap) {
			this.attMap = attMap;
			return this;
		}
		
		public AgentBuilder setAttribute(AgentAttribute att, int val) {
			attMap.put(att, val);
			return this;
		}

	}
	
	protected Agent() {
		mods = new ArrayList<Modifier>(10);
		attMap = new HashMap<AgentAttribute, Integer>(AgentAttribute.values().length);
	}
	
	protected Agent(String name, Map<AgentAttribute, Integer> atts) {
		this();
		this.name = name;
		attMap.putAll(atts);
	}
	
	protected Agent(Agent other) {
		this.name = other.name;
		this.mods = new ArrayList<Modifier>(other.mods);
		this.attMap = new HashMap<AgentAttribute, Integer>(other.attMap);
	}
	
	public int bumpAttribute(final AgentAttribute att, final int bump) {
		Integer val = attMap.get(att);
		if (val == null) val = 0;
		attMap.put(att, val + bump);
		return val + bump;
	}

	public int getEffectiveAttribute(final AgentAttribute att) {
		return getEffectiveVal(attMap.get(att), att);
	}
	
	private int getEffectiveVal(final int baseVal, final AgentAttribute att) {
		int absMods = 0;
		float factor = 1.0f;
		for (Modifier modifier : mods) {
			if (att.equals(modifier.getAttribute())) {
				if (modifier instanceof AbsoluteModifier) {
					absMods += ((AbsoluteModifier)modifier).getMod();
				} else {
					factor += ((RelativeModifier)modifier).getFactor();
				}
			}
		}
		
		//factors first, then absolutes
		if (factor < 0.0f) factor = 0.0f;
		int effectiveVal = (Math.round(baseVal * factor)) + absMods;
		return effectiveVal >= 0 ? effectiveVal : 0;
	}
	
	public void addModifier(Modifier mod) {
		mods.add(mod);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Agent [name=" + name + ", mods=" + mods + ", attMap=" + attMap + "]";
	}

	/**
	 * @return the mods
	 */
	public List<Modifier> getMods() {
		return mods;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}

