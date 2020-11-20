package project.worklogs.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Task {

    private String category;
    private Long id;
    private String project;
    private Long parent;
}
