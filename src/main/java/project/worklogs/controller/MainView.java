package project.worklogs.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.worklogs.model.Task;
import project.worklogs.model.WorkLog;
import project.worklogs.service.MainViewService;

import java.util.List;

@Service
public class MainView {

    MainViewService mainViewService;

    @Autowired
    public MainView(MainViewService mainViewService) {
        this.mainViewService = mainViewService;
    }

    public void initView(List<Task> tasks, List<WorkLog> workLogs){

        System.out.println("Hello user !");
        init(tasks,workLogs);
    }
    protected void init(List<Task> tasks, List<WorkLog> workLogs){
        mainViewService.runPickStatistic(tasks,workLogs);
    }

}
