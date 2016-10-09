package org.kungfugeek.adventure.agents;

/**
 * adventure
 * Modifier
 * @author Nate
 * Oct 8, 2016
 */
public class AbsoluteModifier extends Modifier {
	private int absMod;
	
	public AbsoluteModifier() {}
	
	public AbsoluteModifier(AgentAttribute attribute, int mod) {
		super(attribute);
		this.absMod = mod;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbsoluteModifier [attribute=" + getAttribute() + ", mod=" + absMod + "]";
	}

	/**
	 * @return the mod
	 */
	public int getMod() {
		return absMod;
	}

}

