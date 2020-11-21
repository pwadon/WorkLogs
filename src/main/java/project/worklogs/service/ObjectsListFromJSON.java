package project.worklogs.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import project.worklogs.model.Task;
import project.worklogs.model.WorkLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ObjectsListFromJSON  implements ObjectsListFromJSONI {

    @Override
    public List<Task> getTasksFromJSON(String pathToFile){
        List<Task> ts = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream input = new FileInputStream(new File(pathToFile));
            TypeReference<List<Task>> typeReference = new TypeReference<>() {
            };
             ts = mapper.readValue(input, typeReference);
        }catch (IOException iOException){
            iOException.printStackTrace();
        }
        return ts;
    }

    @Override
    public List<WorkLog> getWorkLogsFromJSON(String pathToFile){
        List<WorkLog> ts = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream input = new FileInputStream(new File(pathToFile));
            TypeReference<List<WorkLog>> typeReference = new TypeReference<>() {
            };
            ts = mapper.readValue(input, typeReference);
        }catch (IOException iOException){
            iOException.printStackTrace();
        }
        return ts;
    }



}
