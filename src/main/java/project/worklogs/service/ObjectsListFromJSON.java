package project.worklogs.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import project.worklogs.model.Task;
import project.worklogs.model.WorkLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ObjectsListFromJSON  implements ObjectsListFromJSONI {

    @Override
    public List<Task> getTasksFromJSON(String pathToFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream input = new FileInputStream(new File(pathToFile));
        TypeReference<List<Task>> typeReference = new TypeReference<>(){};
        List<Task> ts = mapper.readValue(input, typeReference);
        return ts;
    }

    @Override
    public List<WorkLog> getWorkLogsFromJSON(String pathToFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream input = new FileInputStream(new File(pathToFile));
        TypeReference<List<WorkLog>> typeReference = new TypeReference<>(){};
        List<WorkLog> ts = mapper.readValue(input, typeReference);
        return ts;
    }

}
