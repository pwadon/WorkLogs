package project.worklogs.service;

import project.worklogs.model.Task;
import project.worklogs.model.WorkLog;

import java.util.List;
import java.util.Map;


public interface TimeI {

    default Long timeLoggedForGivenTasksIds(List<WorkLog> workLogs, List<Long> subtasksId) {
        return workLogs.stream().filter(w -> {
            for (Long id : subtasksId) {
                if (w.getTaskID().equals(id)) return true;
            }
            return false;
        }).map(WorkLog::getTimeLogged).reduce(0L, Long::sum);
    }

    Long getTimeLoggedForChosenTask(List<Task> tasks, List<WorkLog> workLogs, Long taskId);

    Long getTimeLoggedForChosenProject(List<Task> tasks, List<WorkLog> workLogs, String project);

    Long getTimeLoggedForChosenUser(List<WorkLog> workLogs, String project);

    Map<Task,Map<String, Long>> getTimeLoggedForEpicWithSubProjects(List<Task> tasks, List<WorkLog> workLogs, Long taskId);

}
