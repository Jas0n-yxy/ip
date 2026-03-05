package xuan;

import java.io.*;
import java.util.*;

/**
 * Reads and writes tasks to the data file.
 */
public class Storage {
    private final String path;

    public Storage(String filePath) {
        path = filePath;
    }

    public List<Task> load() throws LoxyException {
        List<Task> tasks = new ArrayList<>();
        try {
            File file = new File(path);
            if (!file.exists()) throw new LoxyException("No file");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                tasks.add(parse(line));
            }
            sc.close();
        } catch (Exception e) {
            throw new LoxyException("Load failed");
        }
        return tasks;
    }

    public void save(List<Task> tasks) throws LoxyException {
        try {
            FileWriter fw = new FileWriter(path);
            for (Task t : tasks) fw.write(t.toFileString() + "\n");
            fw.close();
        } catch (IOException e) {
            throw new LoxyException("Save failed");
        }
    }

    private Task parse(String line) throws LoxyException {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean done = parts[1].equals("1");
        String desc = parts[2];
        Task task;

        switch (type) {
            case "T" -> task = new Todo(desc);
            case "D" -> task = new Deadline(desc, parts[3]);
            case "E" -> task = new Event(desc, parts[3], parts[4]);
            default -> throw new LoxyException("Unknown type");
        }

        if (done) task.markAsDone();
        return task;
    }
}