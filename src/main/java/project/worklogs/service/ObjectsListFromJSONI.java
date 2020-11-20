package project.worklogs.service;


import project.worklogs.model.Task;
import project.worklogs.model.WorkLog;

import java.io.IOException;
import java.util.List;

public interface ObjectsListFromJSONI {

      List<Task> getTasksFromJSON(String pathToFile) throws IOException;
      List<WorkLog> getWorkLogsFromJSON(String pathToFile) throws IOException;


}
