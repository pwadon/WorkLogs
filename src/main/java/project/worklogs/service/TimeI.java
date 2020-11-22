package project.worklogs.service;

import project.worklogs.model.Task;
import project.worklogs.model.WorkLog;
import java.util.List;
import java.util.Map;


public interface TimeI {
    /**
     *
     * @param workLogs
     * @param subtasksId
     * @return
     */
    default Long timeLoggedForGivenTasksIds(List<WorkLog> workLogs, List<Long> subtasksId) {
        return workLogs.stream().filter(w -> {
            for (Long id : subtasksId) {
                if (w.getTaskID().equals(id) && w.getTimeLogged()>0) return true;
            }
            return false;
        }).map(WorkLog::getTimeLogged).reduce(0L, Long::sum);
    }

    default Long timeLoggedForGivenTaskId(List<WorkLog> workLogs, Long taskId){
        return workLogs.stream().filter(w -> w.getTaskID().equals(taskId) && w.getTimeLogged()>0).map(WorkLog::getTimeLogged).reduce(0L, Long::sum);
    }

    default String taskCategoryforGivenTaskId(List<Task>tasks,Long taskId){
        return tasks.stream().filter(t -> t.getId().equals(taskId)).map(Task::getCategory).reduce("",String::concat);
    }

    Long getTimeLoggedForChosenTaskWithSubtasks(List<Task> tasks, List<WorkLog> workLogs, Long taskId);

    Long getTimeLoggedForChosenProject(List<Task> tasks, List<WorkLog> workLogs, String project);

    Long getTimeLoggedForChosenUser(List<WorkLog> workLogs, String project);

    Map<String, Long> getTimeLoggedForEpicWithSubProjects(List<Task> tasks, List<WorkLog> workLogs, Long taskId);

}
