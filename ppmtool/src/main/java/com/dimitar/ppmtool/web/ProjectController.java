package com.dimitar.ppmtool.web;

import com.dimitar.ppmtool.domain.Project;
import com.dimitar.ppmtool.services.MapValidationErrorService;
import com.dimitar.ppmtool.services.ProjectService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
@SuppressWarnings("Duplicates")
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@Autowired
	MapValidationErrorService mapValidationErrorService;


	@PostMapping("")
	@PutMapping("")
	public ResponseEntity<?> createNewProjet(@Valid @RequestBody Project project, BindingResult result, Principal principal) {

		if (result.hasErrors()) {
			return mapValidationErrorService.validateBindingResult(result);
		}

		final Project savedProject = projectService.saveOrUpdateProject(project, principal.getName());
		return new ResponseEntity<Project>(savedProject, HttpStatus.CREATED);
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal) {
		final Project project = projectService.findProjectByIdentifier(projectId, principal.getName());
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllProjects(Principal principal) {
		final Iterable<Project> lstProjects = projectService.findAllProjects(principal.getName());
		return new ResponseEntity<Iterable<Project>>(lstProjects, HttpStatus.OK);
	}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProjectByIdentifier(@PathVariable String projectId, Principal principal) {
		projectService.deleteProjectByIdentifier(projectId, principal.getName());
		return new ResponseEntity<String>("Project with ID: " + projectId + " was deleted.", HttpStatus.OK);
	}

}
