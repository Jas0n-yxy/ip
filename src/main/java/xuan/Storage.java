package xuan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Reads and writes tasks to the data file.
 */
public class Storage {
    private static final String DATA_DIR = "./data";
    private static final String FILE_NAME = "loxy.txt";
    private static final String FILE_PATH = DATA_DIR + File.separator + FILE_NAME;

    // Static block to initialize data directory and file
    static {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        File dataFile = new File(dataDir, FILE_NAME);
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                System.out.println("Created new data file: " + FILE_PATH);
            } catch (IOException e) {
                System.out.println("Failed to create data file: " + e.getMessage());
            }
        }
    }

    // Constructor for compatibility with Loxy class
    public Storage(String path) {
    }

    // Save tasks to file
    public void save(List<Task> tasks) throws LoxyException {
        try (FileWriter fw = new FileWriter(FILE_PATH)) {
            for (Task t : tasks) {
                fw.write(t.toFileString() + "\n");
            }
        } catch (IOException e) {
            throw new LoxyException("Save failed: " + e.getMessage());
        }
    }

    // Load tasks from file
    public List<Task> load() throws LoxyException {
        List<Task> tasks = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    tasks.add(parse(line));
                }
            }
        } catch (FileNotFoundException e) {
            throw new LoxyException("Data file not found: " + e.getMessage());
        }
        return tasks;
    }

    // Parse the single line to Task object
    private Task parse(String line) throws LoxyException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new LoxyException("Invalid task format in data file");
        }

        String type = parts[0];
        boolean done = parts[1].equals("1");
        String desc = parts[2];
        Task task;

        switch (type) {
            case "T":
                task = new Todo(desc);
                break;
            case "D":
                if (parts.length < 4) {
                    throw new LoxyException("Missing deadline information");
                }
                task = new Deadline(desc, parts[3]);
                break;
            case "E":
                if (parts.length < 5) {
                    throw new LoxyException("Missing event time information");
                }
                task = new Event(desc, parts[3], parts[4]);
                break;
            default:
                throw new LoxyException("Unknown task type: " + type);
        }

        if (done) {
            task.markAsDone();
        }
        return task;
    }
}