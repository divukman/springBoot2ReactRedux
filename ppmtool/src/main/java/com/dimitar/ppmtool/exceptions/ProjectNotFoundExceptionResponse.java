package com.dimitar.ppmtool.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectNotFoundExceptionResponse {

	private String projectNotFound;

	public ProjectNotFoundExceptionResponse(final String msg) {
		this.projectNotFound = msg;
	}
}
