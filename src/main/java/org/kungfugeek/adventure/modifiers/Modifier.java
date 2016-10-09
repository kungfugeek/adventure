package org.kungfugeek.adventure.modifiers;

import org.kungfugeek.adventure.AgentAttribute;
import org.kungfugeek.adventure.agents.Agent;

import com.google.gson.annotations.SerializedName;

/**
 * adventure
 * Modifier
 * @author Nate
 * Oct 8, 2016
 */
public class Modifier {
	@SerializedName("attribute")
	private AgentAttribute attribute;
	@SerializedName("factor")
	private Float factor;
	@SerializedName("mod")
	private Integer mod;
	
	public Modifier() {}
	
	public Modifier(AgentAttribute attribute) {
		this.attribute = attribute;
	}
	
	public AgentAttribute getAttribute() {
		return attribute;
	}

	public void addToAgent(Agent agent) {
		agent.removeModifier(this);
		if (mod != null) {
			agent.addModifier(new AbsoluteModifier(attribute, mod));
		} else if (factor != null) {
			agent.addModifier(new RelativeModifier(attribute, factor));
		}
	}
}

