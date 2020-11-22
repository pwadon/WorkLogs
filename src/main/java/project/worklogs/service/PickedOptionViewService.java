package project.worklogs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.worklogs.model.Task;
import project.worklogs.model.WorkLog;
import static project.worklogs.service.ScannerService.scanLong;
import static project.worklogs.service.ScannerService.scanString;

import java.util.List;
import java.util.Map;

@Service
public class PickedOptionViewService {

    ObjectsListFromJSON objectsListFromJSON;
    StatisticsFromData statisticsFromData;

    @Autowired
    public PickedOptionViewService(ObjectsListFromJSON objectsListFromJSON, StatisticsFromData statisticsFromData) {
        this.objectsListFromJSON = objectsListFromJSON;
        this.statisticsFromData = statisticsFromData;
    }

    /**
     * prints task id and time logged for this task.
     * @param tasks
     * @param workLogs
     */
    protected  void timeLoggedForTask(List<Task> tasks, List<WorkLog> workLogs){
        Long taskId = scanLong("Pick task id","this is not a number");
        Long timeLogged = statisticsFromData.getTimeLoggedForChosenTaskWithSubtasks(tasks,workLogs,taskId);
        if (timeLogged == -1L) {
            System.out.println("There is no task with given id.");
        } else {
            System.out.println("For task with id: " + taskId + " there is " + timeLogged + " units of time logged.");
            timeSplitedToDays(tasks,workLogs,taskId);
        }
    }

    /**
     * prints project symbol and time logged for this project.
     * @param tasks
     * @param workLogs
     */
    protected void timeLoggedForProject(List<Task> tasks, List<WorkLog> workLogs){
        String project = scanString("Pick project");
        Long timeLogged = statisticsFromData.getTimeLoggedForChosenProject(tasks,workLogs,project);
        if(timeLogged == -1L) System.out.println("There is no project with such symbol");
        else{
            System.out.println("For project: " + project + " there is " + timeLogged + " units of time logged.");
            timeSplitedToDays(tasks,workLogs,project);
        }
    }

    /**
     * prints number of tasks for given project.
     * @param tasks
     */
    protected void numberOfTasksForChosenProject(List<Task> tasks) {
        String project = scanString("Pick project");
        Long numberOfTasksForProject = statisticsFromData.getNumberOfTasksForChosenProject(tasks,project);
        if(numberOfTasksForProject==0L) System.out.println("There are no tasks for given project.");
        else if (numberOfTasksForProject==1L) System.out.println("For project " + project + " there is 1 task.");
        else System.out.println("For project " + project + " there are " + numberOfTasksForProject +" tasks.");
    }

    /**
     * prints time logged for chosen user.
     * @param workLogs
     */
    protected void timeLoggedForChosenUser(List<WorkLog> workLogs){
        String user = scanString("Pick user");
        Long timeForUser = statisticsFromData.getTimeLoggedForChosenUser(workLogs,user);
        if(timeForUser==0) System.out.println("There is no time logged for such user");
        else{
            System.out.println("There is " + timeForUser + " units of time logged for user " + user +"." );
            timeSplitedToDaysForUser(workLogs,user);
        }
    }

    /**
     * prints time logged for Epic task with Subprojects
     * @param tasks
     * @param workLogs
     */
    protected void timeLoggedForEpicWithSubProjects(List<Task> tasks, List<WorkLog> workLogs){
    Long taskId = scanLong("Pick epic task id","this is not a number");
    String taskCategory = statisticsFromData.getTaskCategory(tasks,taskId);
    Long timeForTask = statisticsFromData.getTimeLoggedForChosenTask(workLogs,taskId);
    if(taskCategory.equalsIgnoreCase("Epic")){
        Map<String,Long> epicTaskAndSubTasks = statisticsFromData.getTimeLoggedForEpicWithSubProjects(tasks,workLogs,taskId);
        System.out.println("Given task time : " + timeForTask );
        System.out.println("Subtasks time combined  on projects:");
        for (String project: epicTaskAndSubTasks.keySet()) {
            System.out.println("project : " + project + " - time: " + epicTaskAndSubTasks.get(project)+ " units of time.");
            timeSplitedToDaysAndProjects(workLogs,tasks,project,taskId);
        }
    }else System.out.println("This task is not epic.");
    }

    /**
     * prints tasks time divided to days
     * @param tasks
     * @param workLogs
     * @param object
     */
    protected void timeSplitedToDays(List<Task> tasks, List<WorkLog> workLogs, Object object){
        Map<String,Long> daysTimeLogged = statisticsFromData.getTimeLoggedForTaskDividedByDays(tasks,workLogs,object);
        for (String str:daysTimeLogged.keySet()) {
            System.out.println(str +": " + daysTimeLogged.get(str) + " units of time logged.");
        }
    }

    /**
     * prints tasks time divided to days
     * @param workLogs
     * @param string
     */
    protected void timeSplitedToDaysForUser( List<WorkLog> workLogs, String string){
        Map<String,Long> daysTimeLogged = statisticsFromData.getTimeLoggedForUserDividedByDays(workLogs,string);
        for (String str:daysTimeLogged.keySet()) {
            System.out.println(str +": " + daysTimeLogged.get(str) + " units of time logged.");
        }
    }

    /**
     * prints time for projects splitted to days
     * @param workLogs
     * @param tasks
     * @param project
     * @param id
     */
    protected void timeSplitedToDaysAndProjects(List<WorkLog> workLogs,List<Task> tasks, String project, Long id){

        Map<String,Long> daysTimeLogged = statisticsFromData.getTimeLoggedForEpicDividedToProjectsAndDays(tasks,workLogs,id,project);
        for (String str:daysTimeLogged.keySet()) {
            System.out.println(str +": " + daysTimeLogged.get(str) + " units of time logged.");
        }
        System.out.println("");
    }

}
