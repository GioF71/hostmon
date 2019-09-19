package com.giof71.monitoring.editing;

public final class EditingContext {
	
	private final EditingLayout editingLayout;
	
	public EditingContext(EditingLayout editingLayout) {
		this.editingLayout = editingLayout;
	}

	public EditingLayout getEditingLayout() {
		return editingLayout;
	}
}
