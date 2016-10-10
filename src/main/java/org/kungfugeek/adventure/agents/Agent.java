package org.kungfugeek.adventure.agents;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kungfugeek.adventure.AgentAttribute;
import org.kungfugeek.adventure.modifiers.Modifier;
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
	private Map<AgentAttribute, Integer> effectiveAtts;
	private Set<String> flags;
	
	protected Agent() {
		mods = new ArrayList<Modifier>(10);
		attMap = new HashMap<AgentAttribute, Integer>(AgentAttribute.values().length);
		effectiveAtts = new HashMap<AgentAttribute, Integer>(AgentAttribute.values().length);
		flags = new HashSet<String>();
		createdDate = new Date();
	}
	
	protected Agent(Builder builder) {
		this();
		this.name = builder.name;
		attMap.putAll(builder.attMap);
		flags.addAll(builder.flags);
	}
	
	public void addFlags(Collection<String> flags) {
		this.flags.addAll(flags);
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
		setEffectiveAtts();
		return val + bump;
	}

	public int getEffectiveAttribute(final AgentAttribute att) {
		if (effectiveAtts.isEmpty()) {
			setEffectiveAtts();
		}
		return effectiveAtts.get(att);
	}
	
	private void setEffectiveAtts() {
		
		effectiveAtts.clear();
		effectiveAtts.putAll(attMap);
		
		//factors first
		for (Modifier modifier : mods) {
			AgentAttribute att = modifier.getAttribute();
			int currVal = effectiveAtts.get(att);
			currVal = Math.round(currVal * modifier.getFactor());
			effectiveAtts.put(att, currVal);
		}
		
		//then absolutes
		for (Modifier modifier : mods) {
			AgentAttribute att = modifier.getAttribute();
			int currVal = effectiveAtts.get(att);
			currVal += modifier.getMod();
			effectiveAtts.put(att, currVal);
		}
		
	}
	
	/**
	 * @param mod
	 */
	public void addModifier(Modifier mod) {
		mods.add(mod);
		setEffectiveAtts();
	}
	
	/**
	 * @param mod
	 */
	public void removeModifier(Modifier mod) {
		mods.remove(mod);
		setEffectiveAtts();
	}
	
	/**
	 * @param mods
	 */
	public void addModifiers(Collection<Modifier> mods) {
		this.mods.addAll(mods);
		setEffectiveAtts();
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
	public static class Builder {
		private String name;
		private Map<AgentAttribute, Integer> attMap = new HashMap<AgentAttribute, Integer>(AgentAttribute.values().length);
		private Set<String> flags = new HashSet<String>(1);
	
		public Agent build() {
			return new Agent(this);
		}

		/**
		 * @param name the name to set
		 */
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder attribute(AgentAttribute att, int val) {
			attMap.put(att, val);
			return this;
		}

		public Builder flag(String flag) {
			flags.add(flag);
			return this;
		}
	}
	

}

