package org.kungfugeek.adventure.agents;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kungfugeek.adventure.AgentAttribute;
import org.kungfugeek.adventure.modifiers.AbsoluteModifier;
import org.kungfugeek.adventure.modifiers.Modifier;
import org.kungfugeek.adventure.modifiers.RelativeModifier;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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
	
	@Indexed
	private String name;	
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date createdDate;
	private List<Modifier> mods;
	private Map<AgentAttribute, Integer> attMap;
	private Set<String> flags;
	
	protected Agent() {
		mods = new ArrayList<Modifier>(10);
		attMap = new HashMap<AgentAttribute, Integer>(AgentAttribute.values().length);
		flags = new HashSet<String>();
		createdDate = new Date();
	}
	
	protected Agent(String name, Map<AgentAttribute, Integer> atts) {
		this();
		this.name = name;
		attMap.putAll(atts);
	}
	
	protected Agent(Agent other) {
		this();
		this.name = other.name;
		this.mods = new ArrayList<Modifier>(other.mods);
		this.attMap = new HashMap<AgentAttribute, Integer>(other.attMap);
	}
	
	public void addFlag(String flag) {
		flags.add(flag);
	}
	
	public void removeFlag(String flag) {
		flags.remove(flag);
	}
	
	public boolean hasFlag(String flag) {
		return flags.contains(flag);
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
				} else if (modifier instanceof RelativeModifier) {
					factor += ((RelativeModifier)modifier).getFactor();
				}
			}
		}
		
		//factors first, then absolutes
		if (factor < 0.0f) factor = 0.0f;
		int effectiveVal = (Math.round(baseVal * factor)) + absMods;
		return effectiveVal >= 0 ? effectiveVal : 0;
	}
	
	/**
	 * @param mod
	 */
	public void addModifier(Modifier mod) {
		mods.add(mod);
	}
	
	/**
	 * @param mod
	 */
	public boolean removeModifier(Modifier mod) {
		return mods.remove(mod);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(100);
		sb.append("Agent ").append(name).append("\n");
		for (AgentAttribute att : attMap.keySet()) {
			sb.append("  ").append(att.name).append(": ").append(attMap.get(att)).append(" (").append(getEffectiveAttribute(att)).append(")\n");
		}
		sb.append("Mods [\n");
		for (Modifier mod : mods) {
			sb.append("  ").append(mod.toString()).append("\n");
		}
		sb.append("]\nFlags: ");
		for (String flag : flags) {
			sb.append(flag).append(", ");
		}
		sb.append("\n");
		return sb.toString();
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
	
	
	/**
	 * adventure
	 * AgentBuilder
	 * @author Nate
	 * Oct 8, 2016
	 */
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
	

}

