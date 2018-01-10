import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {

    private static List<Task> tasks = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws IOException {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new TaskExecutor(), 30, 30, TimeUnit.SECONDS);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String message  = reader.readLine();

            if (message.equals("quit")) {
                break;
            }

            String[] taskInfo = message.split(" ");
            Task task = new Task(taskInfo[0], Integer.parseInt(taskInfo[1]));
            tasks.add(task);
        }

        scheduler.shutdown();
    }

    private static class TaskExecutor implements Runnable {

        @Override
        public void run() {
            if (tasks.size() == 0) {
                System.out.println("No tasks submitted.");
            } else {
                System.out.println(tasks);
            }

        }
    }
}
