import React, { Component } from "react";
import ProjectTask from "./ProjectTasks/ProjectTask";

class Backlog extends Component {
  render() {
    const { project_tasks_prop } = this.props;
    const { project_id } = this.props;
    const tasks = project_tasks_prop.map(project_task => (
      <ProjectTask
        key={project_task.id}
        project_task={project_task}
        project_id={project_id}
      />
    ));

    const lstTasksToDo = tasks.filter(
      task => task.props.project_task.status == "TO_DO"
    );

    const lstTasksInProgress = tasks.filter(
      task => task.props.project_task.status == "IN_PROGRESS"
    );

    const lstTasksDone = tasks.filter(
      task => task.props.project_task.status == "DONE"
    );

    return (
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-secondary text-white">
                <h3>TO DO</h3>
              </div>
            </div>

            {lstTasksToDo}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-primary text-white">
                <h3>In Progress</h3>
              </div>
            </div>

            {lstTasksInProgress}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-success text-white">
                <h3>Done</h3>
              </div>
            </div>

            {lstTasksDone}
          </div>
        </div>
      </div>
    );
  }
}

export default Backlog;
