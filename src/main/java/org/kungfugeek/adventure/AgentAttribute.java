package org.kungfugeek.adventure;

public enum AgentAttribute {
	STRENGTH("Strength"),
	AGILITY("Agility"),
	LIFE("Life");
	
	public final String name;
	
	private AgentAttribute(String name) {
		this.name = name;
	}
}
