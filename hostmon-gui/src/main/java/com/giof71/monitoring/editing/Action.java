package com.giof71.monitoring.editing;

public enum Action {
	ADD("Add"),
	EDIT("Edit"),
	DELETE("Delete");
	
	private final String displayText;
	
	private Action(String displayText) {
		this.displayText = displayText;
	}
	
	public String getDisplayText() {
		return displayText;
	}
}
