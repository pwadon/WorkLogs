package project.worklogs.service;

import project.worklogs.model.Task;

import java.util.List;
import java.util.Map;

public interface TasksI {

    Long getNumberOfTasksForChosenProject(List<Task> tasks, String project);

    Map<String,Long> getNumberOfTasksForAllProjects(List<Task> tasks);
}
