package project.worklogs.service;

import org.junit.jupiter.api.Test;
import project.worklogs.model.Task;
import project.worklogs.model.WorkLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsFromDataTest {
    List<Task> tasks = createTasks();
    List<WorkLog> workLogs = createWorklogs();
    StatisticsFromData statisticsFromData = new StatisticsFromData();



    @Test
    void getTimeLoggedForChosenTaskWithSubtasksTest() {
        Long timeForChosenTask = statisticsFromData.getTimeLoggedForChosenTaskWithSubtasks(tasks,workLogs,66L);
        assertEquals(19L,timeForChosenTask);
    }

    @Test
    void getTimeLoggedForChosenProjectTest(){
        Long timeforChosenProject = statisticsFromData.getTimeLoggedForChosenProject(tasks,workLogs,"B");
        assertEquals(5L,timeforChosenProject);
    }

    @Test
    void getNumberOfTasksForChosenProjectTest() {

        Long numberOfTasksForChosenProject = statisticsFromData.getNumberOfTasksForChosenProject(tasks,"B");
        assertEquals(3,numberOfTasksForChosenProject);
    }

    @Test
    void getNumberOfTasksForAllProjectsTest() {
        Map<String,Long> numberOfTasksForChosenProject = statisticsFromData.getNumberOfTasksForAllProjects(tasks);
        Map<String,Long> testMap = new HashMap<>();
        testMap.put("B",3L);
        testMap.put("C",10L);
        assertEquals(testMap,numberOfTasksForChosenProject);
    }

    @Test
    void getTimeLoggedForChosenUserTest() {
        Long timeLoggedForUser = statisticsFromData.getTimeLoggedForChosenUser(workLogs,"STANIS\\u0141AW");
        assertEquals(16,timeLoggedForUser);
    }

    @Test
    void getTimeLoggedForChosenTaskTest() {
        Long timeLoggedForTask = statisticsFromData.getTimeLoggedForChosenTask(workLogs,1L);
        assertEquals(1L,timeLoggedForTask);
    }

    List<Task> createTasks(){
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Story",1L,"C",66L));
        tasks.add(new Task("Story",2L,"B",1L));
        tasks.add(new Task("Story",3L,"C",66L));
        tasks.add(new Task("Story",4L,"B",66L));
        tasks.add(new Task("Story",5L,"C",66L));
        tasks.add(new Task("Story",6L,"C",22L));
        tasks.add(new Task("Story",7L,"B",66L));
        tasks.add(new Task("Story",8L,"C",22L));
        tasks.add(new Task("Story",9L,"C",22L));
        tasks.add(new Task("Story",10L,"C",22L));
        tasks.add(new Task("Story",11L,"C",11L));
        tasks.add(new Task("Story",12L,"C",1L));
        tasks.add(new Task("Story",66L,"C",-1L));
        return tasks;
    }

    List<WorkLog> createWorklogs(){
        List<WorkLog> workLogs = new ArrayList<>();
        workLogs.add(new WorkLog("Anna",1L,2L,1L));
        workLogs.add(new WorkLog("STANIS\\u0141AW",1L,2L,2L));
        workLogs.add(new WorkLog("STANIS\\u0141AW",2L,2L,3L));
        workLogs.add(new WorkLog("Anna",3L,2L,4L));
        workLogs.add(new WorkLog("Anna",1L,2L,5L));
        workLogs.add(new WorkLog("Wojtek",1L,2L,6L));
        workLogs.add(new WorkLog("Wojtek",1L,2L,7L));
        workLogs.add(new WorkLog("Wojtek",1L,2L,8L));
        workLogs.add(new WorkLog("STANIS\\u0141AW",1L,2L,9L));
        workLogs.add(new WorkLog("STANIS\\u0141AW",1L,2L,10L));
        workLogs.add(new WorkLog("Anna",1L,2L,11L));
        workLogs.add(new WorkLog("Anna",1L,2L,12L));
        workLogs.add(new WorkLog("STANIS\\u0141AW",11L,2L,66L));

        return workLogs;
    }
}
