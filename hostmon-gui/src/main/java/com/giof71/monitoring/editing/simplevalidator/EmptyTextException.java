package com.giof71.monitoring.editing.simplevalidator;

import com.giof71.monitoring.editing.ValidationException;

public class EmptyTextException extends ValidationException {

	private static final long serialVersionUID = 3631383869062799831L;

	public EmptyTextException() {
	}

	public EmptyTextException(String message) {
		super(message);
	}

	public EmptyTextException(Throwable cause) {
		super(cause);
	}

	public EmptyTextException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyTextException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
