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

import javax.naming.Binding;
import javax.validation.Valid;
import java.security.Principal;
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
			BindingResult result,
			@PathVariable String projectIdentifier,
			Principal principal) {

		ResponseEntity<?> errMap = mapValidationErrorService.validateBindingResult(result);
		if (errMap != null) {
			return  errMap;
		}

		ProjectTask projectTask1 = projectTaskService.addProjectTask(projectIdentifier, projectTask, principal.getName());

		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);

	}

	@GetMapping("/{projectIdentifier}")
	public ResponseEntity<List<ProjectTask>> getProjectBacklog(@PathVariable String projectIdentifier, Principal principal) {
		final Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
		if (project == null) {
			throw new ProjectNotFoundException("Project with id: '" + projectIdentifier + "' does not exist.");
		}
		final List<ProjectTask> lstTasks = projectTaskService.getProjectTasks(projectIdentifier, principal.getName());
		return new ResponseEntity<List<ProjectTask>>(lstTasks, HttpStatus.OK);
	}

	@GetMapping("/{projectIdentifier}/{pt_id}")
	public ResponseEntity<?> getProjectTask(final @PathVariable String projectIdentifier, final @PathVariable String pt_id, Principal principal) {
		ProjectTask projectTask = projectTaskService.findByProjectSequence(projectIdentifier, pt_id, principal.getName());

		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}


	@PatchMapping("/{projectIdentifier}/{pt_id}")
	public ResponseEntity<?> updateProjectTask(final @PathVariable String projectIdentifier, final @PathVariable String pt_id, @Valid @RequestBody final ProjectTask updatedTask, BindingResult result, Principal principal) {
		ResponseEntity<?> errMap = mapValidationErrorService.validateBindingResult(result);
		if (errMap != null) {
			return  errMap;
		}
		ProjectTask projectTask = projectTaskService.updateByProjectSequence(updatedTask, projectIdentifier, pt_id, principal.getName());

		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}


	@DeleteMapping("/{projectIdentifier}/{pt_id}")
	public ResponseEntity<?> deleteProjectTask(final @PathVariable String projectIdentifier, final @PathVariable String pt_id, Principal principal) {
		projectTaskService.deletePTByProjectSequence(projectIdentifier, pt_id, principal.getName());
		return new ResponseEntity<String>("Project task: + '" + projectIdentifier + "' was successfully deleted.", HttpStatus.OK);
	}
}
