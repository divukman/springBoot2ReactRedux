import axios from "axios";
import { GET_BACKLOG, GET_PROJECT_TASK, DELETE_PROJECT_TASK } from "./types";

export const addProjectTask = (
  backlog_id,
  project_task,
  history
) => async dispatch => {
  const res = await axios.post(`/api/backlog/${backlog_id}`, project_task);
  history.push(`/projectBoard/${backlog_id}`);
};
