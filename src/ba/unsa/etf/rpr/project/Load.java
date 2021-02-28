package ba.unsa.etf.rpr.project;

import javafx.concurrent.Task;

public class Load extends Task<Integer> {
    @Override
    protected Integer call() throws Exception {
        for(int i = 0; i < 10; i++) {
            updateProgress(i + 1, 10);
            Thread.sleep(500);
        }
        return 10;
    }

    protected void updateProgress(double workDone, double max) {
        updateMessage("Progress: " + workDone);
        super.updateProgress(workDone, max);

    }
}
