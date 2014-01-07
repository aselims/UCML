package org.z.ucml;

public class USensorInfo {
	private final String name;
	private final boolean supported;
	
	public USensorInfo(String name, boolean supported) {
		this.name = name;
		this.supported = supported;
	}

	public String getName() {
		return name;
	}

	public boolean isSupported() {
		return supported;
	}
	
}
