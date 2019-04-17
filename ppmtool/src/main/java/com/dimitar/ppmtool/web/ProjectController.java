package com.dimitar.ppmtool.web;

import com.dimitar.ppmtool.domain.Project;
import com.dimitar.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@PostMapping("")
	public ResponseEntity<Project> createNewProjet(@RequestBody Project project) {
		final Project savedProject = projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(savedProject, HttpStatus.CREATED);
	}
}
