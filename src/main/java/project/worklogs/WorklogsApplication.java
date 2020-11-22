package project.worklogs;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.worklogs.controller.MainView;
import project.worklogs.model.Task;
import project.worklogs.model.WorkLog;
import project.worklogs.service.*;

import java.util.List;

@SpringBootApplication
public class WorklogsApplication {

	public static void main(String[] args) {
		ObjectsListFromJSON objectsListFromJSON = new ObjectsListFromJSON();
		StatisticsFromData statisticsFromData = new StatisticsFromData();
		PickedOptionViewService pickedOptionViewService = new PickedOptionViewService(objectsListFromJSON, statisticsFromData);
		MainViewService mainViewService = new MainViewService(pickedOptionViewService);
		MainView mainView = new MainView(mainViewService);
		List<Task> tasks = objectsListFromJSON.getTasksFromJSON("tasks_java.txt");
		List<WorkLog> workLogs = objectsListFromJSON.getWorkLogsFromJSON("worklogs_Java.txt");
		mainView.initView(tasks, workLogs);
	}
}


