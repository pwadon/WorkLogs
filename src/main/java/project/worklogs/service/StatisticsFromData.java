package project.worklogs.service;

import project.worklogs.model.Task;
import project.worklogs.model.WorkLog;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsFromData implements TimeI, TasksI {

    @Override
   public Long getTimeLoggedForChosenTask(List<Task> tasks, List<WorkLog> workLogs, Long taskId){
       List<Long> subtasksId = tasks.stream().filter(t -> t.getParent().equals(taskId) || t.getId().equals(taskId)).map(Task::getId).collect(Collectors.toList());
       if(subtasksId.size()==0) return -1L;
       else return timeLoggedForGivenTasksIds(workLogs,subtasksId);

    }
    @Override
    public Long getTimeLoggedForChosenProject(List<Task> tasks, List<WorkLog> workLogs, String project){
        List<Long> subtasksId = tasks.stream().filter(t -> t.getProject().equals(project)).map(Task::getId).collect(Collectors.toList());
        if(subtasksId.size()==0) return -1L;
        else return timeLoggedForGivenTasksIds(workLogs,subtasksId);
    }
    @Override
    public Long getNumberOfTasksForChosenProject(List<Task> tasks, String project) {
        return tasks.stream().filter(t -> t.getProject().equals(project)).count();
    }
    @Override
    public Map<String,Long> getNumberOfTasksForAllProjects(List<Task> tasks) {
        Set<String> projects = tasks.stream().map(Task::getProject).collect(Collectors.toSet());
        return projects.stream().collect(Collectors.toMap(p -> p, p -> getNumberOfTasksForChosenProject(tasks,p)));
    }

    @Override
    public Long getTimeLoggedForChosenUser(List<WorkLog> workLogs, String user) {
        return workLogs.stream().filter(w ->w.getAuthor().equals(user)).map(WorkLog::getTimeLogged).reduce(0L,Long::sum);
    }

    @Override
    public Map<Task,Map<String, Long>> getTimeLoggedForEpicWithSubProjects(List<Task> tasks, List<WorkLog> workLogs, Long taskId) {
        List<Task> tasksForProject = tasks.stream().filter(t -> t.getParent().equals(taskId)).collect(Collectors.toList());
        Task task = tasks.stream().filter(t -> t.getId().equals(taskId)).findAny().get();
        Set<String> projects = tasksForProject.stream().map(Task::getProject).collect(Collectors.toSet());
        Map <String,Long> projectsTime = new HashMap<>();

        for (String project: projects) {
            List<Long> subtasksIds = tasksForProject.stream().filter(t -> t.getProject().equals(project)).map(Task::getId).collect(Collectors.toList());
            projectsTime.put(project,timeLoggedForGivenTasksIds(workLogs,subtasksIds));
        }
        Map<Task,Map<String,Long>> epicTaskAndSubProjectsWithTime = new HashMap<>();
        epicTaskAndSubProjectsWithTime.put(task,projectsTime);
        return epicTaskAndSubProjectsWithTime;
    }
}
