package org.kungfugeek.adventure.scenes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kungfugeek.adventure.modifiers.Modifier;

/**
 * adventure
 * Effect
 * @author Nate
 * Oct 9, 2016
 */
public class Effect {
	private List<Modifier> mods;
	private Set<String> flags;

	public Effect() {
		mods = new ArrayList<Modifier>(1);
		flags = new HashSet<String>(1);
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

	/**
	 * adventure
	 * Builder
	 * @author Nate
	 * Oct 9, 2016
	 */
	public static class Builder {
		private List<Modifier> mods;
		private Set<String> flags;

		public Builder() {
			mods = new ArrayList<Modifier>(1);
			flags = new HashSet<String>(1);
		}
		
		public Builder mod(Modifier mod) {
			this.mods.add(mod);
			return this;
		}

		public Builder flag(String flag) {
			this.flags.add(flag);
			return this;
		}

		public Effect build() {
			return new Effect(this);
		}
	}

	private Effect(Builder builder) {
		this.mods = Collections.unmodifiableList(builder.mods);
		this.flags = Collections.unmodifiableSet(builder.flags);
	}
}
