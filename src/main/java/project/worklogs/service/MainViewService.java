package project.worklogs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.worklogs.model.Task;
import project.worklogs.model.WorkLog;

import java.util.List;

import static project.worklogs.service.ScannerService.scanInt;

@Service
public class MainViewService {

    PickedOptionViewService pickedOptionViewService;

    @Autowired
    public MainViewService(PickedOptionViewService pickedOptionViewService) {
        this.pickedOptionViewService = pickedOptionViewService;
    }

    protected int choseWhatToDo(){
        int requestNumber;
        String stringBuilder = "which statistic do you want to check?. Pick correct number." +
                "\n" +
                "number 0 - Close application." +
                "\n" +
                "number 1 - Time logged for chosen task combined with time of subtasks." +
                "\n" +
                "number 2 - Time logged for chosen project." +
                "\n" +
                "number 3 - Number of tasks for chosen project." +
                "\n" +
                "number 4 - Time logged for chosen user." +
                "\n" +
                "number 5 - time logged for chosen Epic task with subtasks divided by projects.";
        requestNumber = scanInt(stringBuilder, "give a number");
        return requestNumber;
    }

    public void runPickStatistic(List<Task> tasks, List<WorkLog> workLogs) {
        int task =choseWhatToDo();
        switch (task) {
            case 0:
                System.out.println("Closing application.");
                break;
            case 1:
                System.out.println("Time logged for chosen task combined with time of subtasks.");
                pickedOptionViewService.timeLoggedForTask(tasks, workLogs);
                break;
            case 2:
                System.out.println("Time logged for chosen project.");
                pickedOptionViewService.timeLoggedForProject(tasks,workLogs);
                break;
            case 3:
                System.out.println("Number of tasks for chosen project.");
                pickedOptionViewService.numberOfTasksForChosenProject(tasks);
                break;
            case 4:
                System.out.println("Time logged for chosen user.");
                pickedOptionViewService.timeLoggedForChosenUser(workLogs);
                break;
            case 5:
                System.out.println("time logged for chosen Epic task with subtasks divided by projects.");
                pickedOptionViewService.timeLoggedForEpicWithSubProjects(tasks,workLogs);
                break;
            default:
                System.out.println("there is no statistic with given number, try again");
                runPickStatistic(tasks,workLogs);
        }
        if(task!=0) {

            String stringBuilder ="\n" +
                    "What do you want to do next ?" +
                    "\n" +
                    "number 0 - Close application." +
                    "\n" +
                    "number 1 - Continue the program.";

            int nextStep = scanInt(stringBuilder, "give a number");
            switch (nextStep) {
                case 0:
                    System.out.println("Closing application.");
                    break;
                case 1:
                    runPickStatistic(tasks, workLogs);
            }
        }
    }
}
