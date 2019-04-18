package com.dimitar.ppmtool.web;

import com.dimitar.ppmtool.domain.Project;
import com.dimitar.ppmtool.services.MapValidationErrorService;
import com.dimitar.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@Autowired
	MapValidationErrorService mapValidationErrorService;

	@PostMapping("")
	public ResponseEntity<?> createNewProjet(@Valid @RequestBody Project project, BindingResult result) {

		if (result.hasErrors()) {
			return mapValidationErrorService.validateBindingResult(result);
		}

		final Project savedProject = projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(savedProject, HttpStatus.CREATED);
	}
}
