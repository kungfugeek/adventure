package org.kungfugeek.adventure.agents;

/**
 * adventure
 * Modifier
 * @author Nate
 * Oct 8, 2016
 */
public interface Modifier {
	int modify(int baseValue);
	AgentAttribute getAttribute();
}

