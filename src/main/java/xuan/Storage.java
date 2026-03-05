package xuan;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final String filePath;
    private final String folderPath;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Storage(String filePath) {
        this.filePath = filePath;
        this.folderPath = filePath.substring(0, filePath.lastIndexOf("/"));
    }

    public List<Task> load() throws LoxyException {
        List<Task> tasks = new ArrayList<>();
        File folder = new File(folderPath);
        File file = new File(filePath);

        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                throw new LoxyException("Failed to create data folder!");
            }
        }

        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new LoxyException("Failed to create data file!");
                }
                return tasks;
            } catch (IOException e) {
                throw new LoxyException("Failed to create data file: " + e.getMessage());
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                tasks.add(parseTaskFromLine(line));
            }
        } catch (IOException e) {
            throw new LoxyException("Failed to load tasks: " + e.getMessage());
        }

        return tasks;
    }

    public void save(List<Task> tasks) throws LoxyException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                bw.write(task.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new LoxyException("Failed to save tasks: " + e.getMessage());
        }
    }

    private Task parseTaskFromLine(String line) throws LoxyException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new LoxyException("Corrupted task line: " + line);
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = switch (type) {
            case "T" -> new Todo(description);
            case "D" -> {
                if (parts.length < 4) {
                    throw new LoxyException("Invalid Deadline format: " + String.join(" | ", parts));
                }
                yield new Deadline(description, parts[3]);
            }
            case "E" -> {
                if (parts.length < 5) {
                    throw new LoxyException("Invalid Event format: " + String.join(" | ", parts));
                }
                yield new Event(description, parts[3], parts[4]);
            }
            default -> throw new LoxyException("Unknown task type: " + type);
        };

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}