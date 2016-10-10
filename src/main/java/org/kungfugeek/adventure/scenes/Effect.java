package org.kungfugeek.adventure.scenes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kungfugeek.adventure.AgentAttribute;
import org.kungfugeek.adventure.agents.Agent;
import org.kungfugeek.adventure.modifiers.Modifier;

/**
 * adventure
 * Effect
 * @author Nate
 * Oct 9, 2016
 */
public class Effect {
	private Map<AgentAttribute, Integer> bumpMap;
	private List<Modifier> mods;
	private Set<String> flags;

	public Effect() {
		mods = new ArrayList<Modifier>(1);
		flags = new HashSet<String>(1);
		bumpMap = new HashMap<AgentAttribute, Integer>(1);
	}

	private Effect(Builder builder) {
		this.mods = Collections.unmodifiableList(builder.mods);
		this.flags = Collections.unmodifiableSet(builder.flags);
		this.bumpMap = Collections.unmodifiableMap(builder.bumpMap);
	}
	
	/**
	 * @return the mods
	 */
	public List<Modifier> getMods() {
		return mods;
	}

	/**
	 * @return the flags
	 */
	public Set<String> getFlags() {
		return flags;
	}
	
	public boolean hasBumps() {
		return !bumpMap.isEmpty();
	}
	
	public int getBump(AgentAttribute att) {
		Integer bump = bumpMap.get(att);
		return bump != null ? bump : 0;
	}

	public void applyTo(Agent agent) {
		agent.addFlags(flags);
		agent.addModifiers(mods);
		if (this.hasBumps()) {
			for (Map.Entry<AgentAttribute, Integer> entry : bumpMap.entrySet()) {
				if (entry.getValue() != null) {
					agent.bumpAttribute(entry.getKey(), entry.getValue());
				}
			}
		}
	}
	
	/**
	 * adventure
	 * Builder
	 * @author Nate
	 * Oct 9, 2016
	 */
	public static class Builder {
		private List<Modifier> mods;
		private Set<String> flags;
		private Map<AgentAttribute, Integer> bumpMap;
		
		public Builder() {
			mods = new ArrayList<Modifier>(1);
			flags = new HashSet<String>(1);
			bumpMap = new HashMap<AgentAttribute, Integer>(1);
		}
		
		public Builder mod(Modifier mod) {
			this.mods.add(mod);
			return this;
		}

		public Builder flag(String flag) {
			this.flags.add(flag);
			return this;
		}
		
		public Builder bump(AgentAttribute att, int val) {
			bumpMap.put(att, val);
			return this;
		}

		public Effect build() {
			return new Effect(this);
		}
	}

}
