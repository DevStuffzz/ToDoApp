package todoapp;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main Application framework and loop.
 * Loads tasks from file, allows adding/completing/removing tasks,
 * and displays them in a simple GUI.
 * 
 * @author Corey
 */
public class TodoApp {

    private List<Task> tasks = new ArrayList<>();
    private final String FILE_NAME = "tasks.txt";

    // Swing components
    private JFrame frame;
    private DefaultListModel<Task> listModel;
    private JList<Task> taskList;
    private JButton addButton, completeButton, removeButton, saveButton;
    
    private boolean headless = false;

    
    public TodoApp(boolean headless) {
        this.headless = headless;

        // Load tasks from file
        loadTasks();

        listModel = new DefaultListModel<>();

        if (!headless) {
            // Start GUI
            initGUI();
        }

        // Always start console loop
        new Thread(this::consoleLoop).start();
    }

    public TodoApp() {
        this(false);
    }
    
    /**
     * Initializes the Swing GUI.
     */
    private void initGUI() {
        frame = new JFrame("Todo App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        taskList = new JList<>(listModel);
        taskList.setCellRenderer(new TaskRenderer());

        JScrollPane scrollPane = new JScrollPane(taskList);

        addButton = new JButton("Add Task");
        completeButton = new JButton("Complete Task");
        removeButton = new JButton("Remove Task");
        saveButton = new JButton("Save");

        addButton.addActionListener(e -> addTaskGUI());
        completeButton.addActionListener(e -> completeTaskGUI());
        removeButton.addActionListener(e -> removeTaskGUI());
        saveButton.addActionListener(e -> saveTasks());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(saveButton);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        refreshTaskList();
        frame.setVisible(true);
    }

    /**
     * Adds a task through GUI input.
     */
    private void addTaskGUI() {
        String name = JOptionPane.showInputDialog(frame, "Enter task name:");
        if (name != null && !name.trim().isEmpty()) {
            Task task = new Task(name.trim());
            tasks.add(task);
            refreshTaskList();
        }
    }

    /**
     * Marks selected task as completed.
     */
    private void completeTaskGUI() {
        int index = taskList.getSelectedIndex();
        if (index >= 0) {
            tasks.get(index).setCompleted(true);
            refreshTaskList();
        }
    }

    /**
     * Removes selected task from GUI list.
     */
    private void removeTaskGUI() {
        int index = taskList.getSelectedIndex();
        if (index >= 0) {
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to remove this task?",
                    "Confirm Remove",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tasks.remove(index);
                refreshTaskList();
            }
        }
    }

    /**
     * Refreshes the JList display with current tasks.
     */
    private void refreshTaskList() {
        listModel.clear();
        for (Task t : tasks) {
            listModel.addElement(t);
        }
    }

    /**
     * Console loop for command-line interaction.
     */
    private void consoleLoop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- TodoApp ---");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + ": " + tasks.get(i));
            }
            System.out.println("[a] Add Task | [c] Complete Task | [r] Remove Task | [q] Quit");

            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("a")) {
                System.out.print("Task name: ");
                String name = scanner.nextLine();
                tasks.add(new Task(name));
                refreshTaskList();
            } else if (input.equalsIgnoreCase("c")) {
                System.out.print("Task index to complete: ");
                int idx = Integer.parseInt(scanner.nextLine());
                if (idx >= 0 && idx < tasks.size()) {
                    tasks.get(idx).setCompleted(true);
                    refreshTaskList();
                }
            } else if (input.equalsIgnoreCase("r")) {
                System.out.print("Task index to remove: ");
                int idx = Integer.parseInt(scanner.nextLine());
                if (idx >= 0 && idx < tasks.size()) {
                    tasks.remove(idx);
                    refreshTaskList();
                }
            } else if (input.equalsIgnoreCase("q")) {
                saveTasks();
                System.out.println("Goodbye!");
                System.exit(0);
            }
        }
    }

    /**
     * Loads tasks from file.
     */
    private void loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 2) {
                    Task t = new Task(parts[0]);
                    t.setCompleted(Boolean.parseBoolean(parts[1]));
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading tasks: " + e.getMessage());
        }
    }

    /**
     * Saves tasks to file.
     */
    private void saveTasks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task t : tasks) {
                writer.println(t.getName() + ";" + t.isCompleted());
            }
            System.out.println("Tasks saved!");
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Custom renderer to show tasks in red/green.
     */
    private static class TaskRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            Component comp = super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

            if (value instanceof Task task) {
                setText(task.getName());
                if (task.isCompleted()) {
                    setForeground(new Color(0, 128, 0)); // green
                } else {
                    setForeground(Color.RED); // red
                }
            }
            return comp;
        }
    }
}
