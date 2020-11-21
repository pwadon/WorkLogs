package project.worklogs.service;

import org.springframework.stereotype.Service;
import project.worklogs.model.Task;
import project.worklogs.model.WorkLog;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsFromData implements TimeI, TasksI {

    /**
     * @param tasks
     * @param workLogs
     * @param taskId
     * @return Long - time logged for given task id
     */
    @Override
    public Long getTimeLoggedForChosenTaskWithSubtasks(List<Task> tasks, List<WorkLog> workLogs, Long taskId) {
        List<Long> subtasksId = tasks.stream().filter(t -> t.getParent().equals(taskId) || t.getId().equals(taskId)).map(Task::getId).collect(Collectors.toList());
        if (subtasksId.size() == 0) return -1L;
        else return timeLoggedForGivenTasksIds(workLogs, subtasksId);
    }

    /**
     * @param workLogs
     * @param taskId
     * @return time logged for fiven task id
     */

    public Long getTimeLoggedForChosenTask(List<WorkLog> workLogs, Long taskId) {
        return timeLoggedForGivenTaskId(workLogs, taskId);
    }

    public String getTaskCategory(List<Task> tasks, Long taskId) {
        return taskCategoryforGivenTaskId(tasks, taskId);
    }

    /**
     * @param tasks
     * @param workLogs
     * @param project
     * @return Long - time logged for chosen project
     */
    @Override
    public Long getTimeLoggedForChosenProject(List<Task> tasks, List<WorkLog> workLogs, String project) {
        List<Long> subtasksId = tasks.stream().filter(t -> t.getProject().equalsIgnoreCase(project)).map(Task::getId).collect(Collectors.toList());
        if (subtasksId.size() == 0) return -1L;
        else return timeLoggedForGivenTasksIds(workLogs, subtasksId);
    }

    /**
     * @param tasks
     * @param project
     * @return Long - number of tasks for chosen project
     */
    @Override
    public Long getNumberOfTasksForChosenProject(List<Task> tasks, String project) {
        return tasks.stream().filter(t -> t.getProject().equalsIgnoreCase(project)).count();
    }

    /**
     * @param tasks
     * @return
     */
    @Override
    public Map<String, Long> getNumberOfTasksForAllProjects(List<Task> tasks) {
        Set<String> projects = tasks.stream().map(Task::getProject).collect(Collectors.toSet());
        return projects.stream().collect(Collectors.toMap(p -> p, p -> getNumberOfTasksForChosenProject(tasks, p)));
    }

    /**
     * @param workLogs
     * @param user
     * @return Long - time logged for chosen user
     */
    @Override
    public Long getTimeLoggedForChosenUser(List<WorkLog> workLogs, String user) {
        return workLogs.stream().filter(w -> w.getAuthor().equalsIgnoreCase(user)).map(WorkLog::getTimeLogged).reduce(0L, Long::sum);
    }

    /**
     * @param tasks
     * @param workLogs
     * @param taskId
     * @return
     */
    @Override
    public Map<String, Long> getTimeLoggedForEpicWithSubProjects(List<Task> tasks, List<WorkLog> workLogs, Long taskId) {
        Map<String, Long> projectsTime = new HashMap<>();
        List<Task> tasksForProject = tasks.stream().filter(t -> t.getParent().equals(taskId)).collect(Collectors.toList());
        Set<String> projects = tasksForProject.stream().map(Task::getProject).collect(Collectors.toSet());
        for (String project : projects) {
            List<Long> subtasksIds = tasksForProject.stream().filter(t -> t.getProject().equalsIgnoreCase(project)).map(Task::getId).collect(Collectors.toList());
            projectsTime.put(project, timeLoggedForGivenTasksIds(workLogs, subtasksIds));
        }
        return projectsTime;
    }
}
