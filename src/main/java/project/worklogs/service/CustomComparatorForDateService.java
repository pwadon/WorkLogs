package project.worklogs.service;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class CustomComparatorForDateService implements Comparator<String> {

    @SneakyThrows
    @Override
    public int compare(String date1, String date2) {
        SimpleDateFormat sdFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date d1 = sdFormat.parse(date1);
        Date d2=sdFormat.parse(date2);
        return Integer.compare(d1.compareTo(d2), 0);
    }
}

