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
            System.out.println("For task with id: " + taskId + " there is " + timeLogged + " logged.");
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
        else System.out.println("For project: " + project + " there is " + timeLogged + " logged.");
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
        else System.out.println("There is " + timeForUser + " logged for user " + user +"." );
    }

    protected void timeLoggedForEpicWithSubProjects(List<Task> tasks, List<WorkLog> workLogs){
    Long taskId = scanLong("Pick epic task id","this is not a number");
    String taskCategory = statisticsFromData.getTaskCategory(tasks,taskId);
    Long timeForTask = statisticsFromData.getTimeLoggedForChosenTask(workLogs,taskId);
    if(taskCategory.equalsIgnoreCase("Epic")){
        Map<String,Long> epicTaskAndSubTasks = statisticsFromData.getTimeLoggedForEpicWithSubProjects(tasks,workLogs,taskId);
        System.out.println("Given task time : " + timeForTask );
        System.out.println("Sub projects time:");
        for (String project: epicTaskAndSubTasks.keySet()) {
            System.out.println("project : " + project + " - time: " + epicTaskAndSubTasks.get(project));
        }
    }else System.out.println("This task is not epic.");

    }
}
