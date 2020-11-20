package project.worklogs.model;

import lombok.*;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class WorkLog {

    private String author;
    private Long timeLogged;
    private Long date;
    private Long taskID;

}
