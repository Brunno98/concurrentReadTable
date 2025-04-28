package br.com.brunno.concurrentReadTable.task;

public interface ExpiredTaskNotifier {

    void execute(Task task);

}
