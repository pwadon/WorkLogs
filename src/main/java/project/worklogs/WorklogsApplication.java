package project.worklogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.worklogs.model.Task;
import project.worklogs.model.WorkLog;
import project.worklogs.service.ObjectsListFromJSON;

import java.io.*;
import java.util.List;

@SpringBootApplication
public class WorklogsApplication {

	public static void main(String[] args) throws IOException {

//		SpringApplication.run(WorklogsApplication.class, args);
//		ObjectsListFromJSON objectsListFromJSON = new ObjectsListFromJSON();
//		try{
//		List<Task> tasks = objectsListFromJSON.getTasksFromJSON("tasks_java.txt");
//		List<WorkLog> workLogs = objectsListFromJSON.getWorkLogsFromJSON("worklogs_Java.txt");
//			for (Task t: tasks) {
//				System.out.println(t.getCategory());
//			}
//			for (WorkLog t: workLogs) {
//				System.out.println(t.getAuthor());
//			}
//		}catch (Exception e){
//			e.printStackTrace();
//		}

//		try{
//			tasks = new ObjectMapper().readerFor(Task.class).readValue(new File("tasks_java.txt"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

}


