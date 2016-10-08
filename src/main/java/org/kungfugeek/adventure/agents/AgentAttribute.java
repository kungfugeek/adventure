package org.kungfugeek.adventure.agents;

public enum AgentAttribute {
	STRENGTH("strength"),
	AGILITY("agility"),
	LIFE("life");
	
	public final String name;
	
	private AgentAttribute(String name) {
		this.name = name;
	}
}
