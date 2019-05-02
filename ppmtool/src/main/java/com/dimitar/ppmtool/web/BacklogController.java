package com.dimitar.ppmtool.web;

import com.dimitar.ppmtool.domain.Project;
import com.dimitar.ppmtool.domain.ProjectTask;
import com.dimitar.ppmtool.services.MapValidationErrorService;
import com.dimitar.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	ProjectTaskService projectTaskService;

	@Autowired
	MapValidationErrorService mapValidationErrorService;

	@PostMapping("/{projectIdentifier}")
	public ResponseEntity<?> addProjectTaskToBacklog(
			@Valid @RequestBody ProjectTask projectTask,
			@PathVariable String projectIdentifier,
			BindingResult result) {

		ResponseEntity<?> errMap = mapValidationErrorService.validateBindingResult(result);
		if (errMap != null) {
			return  errMap;
		}

		ProjectTask projectTask1 = projectTaskService.addProjectTask(projectIdentifier, projectTask);

		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);

	}

}
