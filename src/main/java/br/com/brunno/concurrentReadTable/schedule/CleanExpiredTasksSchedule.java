package br.com.brunno.concurrentReadTable.schedule;

import br.com.brunno.concurrentReadTable.task.ExpiredTaskNotifier;
import br.com.brunno.concurrentReadTable.task.Task;
import br.com.brunno.concurrentReadTable.task.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class CleanExpiredTasksSchedule {

    public static final int DAYS_TO_EXPIRE = 60;
    private final TaskRepository taskRepository;
    private final ExpiredTaskNotifier expiredTaskNotifier;

    public CleanExpiredTasksSchedule(TaskRepository taskRepository, ExpiredTaskNotifier expiredTaskNotifier) {
        this.taskRepository = Objects.requireNonNull(taskRepository);
        this.expiredTaskNotifier = Objects.requireNonNull(expiredTaskNotifier);
    }

    @Transactional
    @Scheduled(fixedRate = 30_000) // Executa a cada 30000 milissegundos (30 segundos)
    void execute() {
        Pageable pageRequest = PageRequest.ofSize(10);
        Page<Task> expireds;
        do {
            final var dateLimit = LocalDateTime.now().minusDays(DAYS_TO_EXPIRE);
            expireds = taskRepository.findExpireds(dateLimit, pageRequest);

            expireds.stream().forEach(expiredTaskNotifier::execute);

            taskRepository.deleteAll(expireds.getContent());

            pageRequest = expireds.nextPageable();
        } while (expireds.hasNext());
    }

}
