package nz.co.app.utils;

import nz.co.app.db.Tasks;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TasksUtils {

    public Date subtractDate(Date date, int days) {
        return new Date(date.getTime() - Duration.ofDays(days).toMillis());
    }

    public List<Tasks> filterOverDueTasks(List<Tasks> tasks) {
        return  tasks
                .stream()
                .filter(task -> task.getDue_date() != null)
                .filter(task -> task.getDue_date().before(new Date()))
                .collect(Collectors.toList());
    }
}
