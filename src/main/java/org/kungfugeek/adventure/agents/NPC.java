package org.kungfugeek.adventure.agents;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Nate
 *
 */
@Document(collection = "agent")
public class NPC extends Agent {
	
	private String description;
	
	private NPC() {}
	
	private NPC(NPCBuilder builder) {
		super(builder);
		this.description = builder.description;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NPC " + super.toString() + " [description=" + description + "]";
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	public static class NPCBuilder extends Agent.Builder {
		private String description;

		public NPCBuilder() {
			super();
		}
		
		public NPC build() {
			return new NPC(this);
		}

		/**
		 * @param description the description to set
		 */
		public Builder description(String description) {
			this.description = description;
			return this;
		}
		
	}

}
