package com.dimitar.ppmtool.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectIdExceptionResponse {

	private String projectIdentifier;

	public ProjectIdExceptionResponse(final String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
}
