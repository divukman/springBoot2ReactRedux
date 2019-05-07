package com.dimitar.ppmtool.web;

import com.dimitar.ppmtool.domain.Project;
import com.dimitar.ppmtool.domain.ProjectTask;
import com.dimitar.ppmtool.exceptions.ProjectIdException;
import com.dimitar.ppmtool.exceptions.ProjectNotFoundException;
import com.dimitar.ppmtool.repositories.ProjectRepository;
import com.dimitar.ppmtool.services.MapValidationErrorService;
import com.dimitar.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	ProjectTaskService projectTaskService;

	@Autowired
	MapValidationErrorService mapValidationErrorService;

	@Autowired
	ProjectRepository projectRepository;

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

	@GetMapping("/{projectIdentifier}")
	public ResponseEntity<List<ProjectTask>> getProjectBacklog(@PathVariable String projectIdentifier) {
		final Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
		if (project == null) {
			throw new ProjectNotFoundException("Project with id: '" + projectIdentifier + "' does not exist.");
		}
		final List<ProjectTask> lstTasks = projectTaskService.getProjectTasks(projectIdentifier);
		return new ResponseEntity<List<ProjectTask>>(lstTasks, HttpStatus.OK);
	}

	@GetMapping("/{projectIdentifier}/{pt_id}")
	public ResponseEntity<?> getProjectTask(final @PathVariable String projectIdentifier, final @PathVariable String pt_id) {
		ProjectTask projectTask = projectTaskService.findByProjectSequence(projectIdentifier, pt_id);

		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}

}
