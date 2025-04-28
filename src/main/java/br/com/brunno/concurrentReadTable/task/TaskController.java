package br.com.brunno.concurrentReadTable.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping("task")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = Objects.requireNonNull(taskRepository);
    }

    @Transactional
    @GetMapping("expireds")
    public Page<Task> findExpireds(
            @RequestParam(required = false, name = "days",defaultValue = "60") int days,
            @RequestParam(required = false, name = "page",defaultValue = "0") int page,
            @RequestParam(required = false, name = "size",defaultValue = "10") int size
    ) throws InterruptedException {
        final var pageRequest = PageRequest.of(page, size);

        final var expireds = taskRepository.findExpireds(LocalDateTime.now().minusDays(days), pageRequest);
        return expireds;
    }

    @Transactional
    @GetMapping("expireds/delay")
    public Page<Task> findExpiredsDelayed(
            @RequestParam(required = false, name = "days",defaultValue = "60") int days,
            @RequestParam(required = false, name = "page",defaultValue = "0") int page,
            @RequestParam(required = false, name = "size",defaultValue = "10") int size
    ) throws InterruptedException {
        final var pageRequest = PageRequest.of(page, size);

        final var expireds = taskRepository.findExpireds(LocalDateTime.now().minusDays(days), pageRequest);
        for (int i = 0; i <10_000_000; i++) {
            System.out.println("esperando...");
        }
        taskRepository.deleteAll(expireds.getContent());
        return expireds;
    }


}
