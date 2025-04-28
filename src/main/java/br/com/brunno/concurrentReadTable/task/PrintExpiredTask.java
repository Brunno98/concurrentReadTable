package br.com.brunno.concurrentReadTable.task;

import org.springframework.stereotype.Service;

@Service
public class PrintExpiredTask implements ExpiredTaskNotifier {
    @Override
    public void execute(Task task) {
        System.out.println("=== Processando task expirada ===");
        System.out.println("Task id:" + task.getId());
        System.out.println("Descrição: " + task.getDescricao());
        System.out.println("Data de criação: " + task.getCreatedAt());
        System.out.println("=================================");

        try {
            Thread.sleep(300); // simular um tempo de processamento
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
