package project.worklogs.service;

import org.springframework.stereotype.Service;
import project.worklogs.model.Task;
import project.worklogs.model.WorkLog;

import java.text.SimpleDateFormat;
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
        List<Long> subtasksId = subtasksIdBygivenParameter(tasks,taskId);
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
        List<Long> subtasksId = subtasksIdBygivenParameter(tasks,project);
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
        return workLogs.stream().filter(w -> w.getAuthor().equalsIgnoreCase(user) && w.getTimeLogged()>0).map(WorkLog::getTimeLogged).reduce(0L, Long::sum);
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

    /**
     *
     * @param worklogs
     * @param string
     * @return map days - time logged for chosen user
     */
    public Map<String,Long> getTimeLoggedForUserDividedByDays(List<WorkLog> worklogs, String string){
        List<WorkLog> workLogsForTask = worklogs.stream().filter(w ->w.getAuthor().equalsIgnoreCase(string) && w.getTimeLogged()>0).collect(Collectors.toList());
        return getTimeDividedByDays(workLogsForTask);
    }

    /**
     *
     * @param tasks
     * @param worklogs
     * @param id
     * @param project
     * @return map days- time logged for epic task subtasks divided by projects
     */
    public Map<String,Long> getTimeLoggedForEpicDividedToProjectsAndDays(List<Task> tasks,List<WorkLog> worklogs,Long id, String project){
        List<Long> taskList = tasks.stream().filter(t -> t.getParent().equals(id) && t.getProject().equalsIgnoreCase(project)).map(Task::getId).collect(Collectors.toList());
        return getStringLongMap(worklogs, taskList);
    }

    /**
     *
     * @param tasks
     * @param worklogs
     * @param object
     * @return map day - time logged for task divided by days
     */
    public Map<String,Long> getTimeLoggedForTaskDividedByDays(List<Task> tasks,List<WorkLog> worklogs, Object object){
        List<Long> subtasksId = subtasksIdBygivenParameter(tasks,object);
        return getStringLongMap(worklogs, subtasksId);
    }

    /**
     *
     * @param worklogs
     * @param subtasksId
     * @return map day - time for provided subtasks ids
     */
    private Map<String, Long> getStringLongMap(List<WorkLog> worklogs, List<Long> subtasksId) {
        List<WorkLog> workLogsForTask = worklogs.stream().filter(w -> {
            for (Long id : subtasksId) {
                if (w.getTaskID().equals(id) && w.getTimeLogged()>0) return true;
            }
            return false;}).collect(Collectors.toList());
        return getTimeDividedByDays(workLogsForTask);
    }

    /**
     *
     * @param workLogsForTask
     * @return map day - time logged for given list of worklogs
     */
    public Map<String,Long> getTimeDividedByDays(List<WorkLog>workLogsForTask){
        Map<String,Long> dateTimeLogged = new TreeMap<>(new CustomComparatorForDateService());
        Set<String> formatedDates = new HashSet<>();
        for (WorkLog worklog: workLogsForTask) {
            formatedDates.add(convertTimeInMsToStringDate(worklog.getDate()));
        }
        for (String date : formatedDates){
            dateTimeLogged.put(date,0L);
        }

        for (WorkLog work: workLogsForTask) {
            dateTimeLogged.put(convertTimeInMsToStringDate(work.getDate()),dateTimeLogged.get(convertTimeInMsToStringDate(work.getDate()))+work.getTimeLogged());
        }
        return dateTimeLogged;
    }

    public String convertTimeInMsToStringDate(Long timeLogged){
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date(timeLogged));
    }

    /**
     *
     * @param tasks
     * @param object
     * @return List of Long ids of given task id or project with subtasks
     */
    public List<Long> subtasksIdBygivenParameter(List<Task> tasks, Object object){
        List<Long> subtasksId = new ArrayList<>();
        if(object instanceof Long) subtasksId = tasks.stream().filter(t -> t.getParent().equals(object) || t.getId().equals(object)).map(Task::getId).collect(Collectors.toList());
        else if(object instanceof String) subtasksId = tasks.stream().filter(t -> t.getProject().equalsIgnoreCase((String) object)).map(Task::getId).collect(Collectors.toList());
        return subtasksId;
    }
}
