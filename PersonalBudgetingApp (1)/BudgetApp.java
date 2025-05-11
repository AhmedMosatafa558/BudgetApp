import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class BudgetApp {
    private static final String USERS_FILE = "users.txt";
    private static final String INCOME_FILE = "income.txt";
    private static final String BUDGET_FILE = "budget.txt";
    private static final String REMINDERS_FILE = "reminders.txt";
    private static final String SAVINGS_FILE = "savings.txt";

    private static User currentUser = null;
    private static JFrame currentFrame = null;

    public static void main(String[] args) {
        showWelcomeScreen();
    }

    private static void showWelcomeScreen() {
        JFrame frame = new JFrame("Personal Budgeting App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(255, 255, 200));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 200));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel titleLabel = new JLabel("Personal Budgeting App");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.setPreferredSize(new Dimension(150, 40));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(loginButton, gbc);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Arial", Font.PLAIN, 16));
        signupButton.setPreferredSize(new Dimension(150, 40));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(signupButton, gbc);

        loginButton.addActionListener(e -> {
            frame.dispose();
            showLoginScreen();
        });

        signupButton.addActionListener(e -> {
            frame.dispose();
            showSignUpScreen();
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        currentFrame = frame;
    }

    private static void showLoginScreen() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(255, 255, 200));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 200));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(backButton, gbc);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = authenticateUser(email, password);
            if (user != null) {
                currentUser = user;
                frame.dispose();
                showDashboard();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid email or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            showWelcomeScreen();
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        currentFrame = frame;
    }

    private static void showSignUpScreen() {
        JFrame frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 550);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(255, 255, 200));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 200));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Username Field
        JLabel nameLabel = new JLabel("Username:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(nameField, gbc);

        // Email Field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(emailField, gbc);

        // Phone Field
        JLabel phoneLabel = new JLabel("Phone (11 digits):");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(phoneLabel, gbc);

        JTextField phoneField = new JTextField(20);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(phoneField, gbc);

        // Password Field
        JLabel passwordLabel = new JLabel("Password (5-10 chars):");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(passwordField, gbc);

        // Confirm Password Field
        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(confirmLabel, gbc);

        JPasswordField confirmField = new JPasswordField(20);
        confirmField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(confirmField, gbc);

        // Buttons
        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Arial", Font.PLAIN, 16));
        signupButton.setPreferredSize(new Dimension(150, 40));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(signupButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setPreferredSize(new Dimension(150, 40));
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(backButton, gbc);

        signupButton.addActionListener(e -> {
            String username = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmField.getPassword());

            // Validation
            if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()
                    || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.length() < 5 || password.length() > 10) {
                JOptionPane.showMessageDialog(frame, "Password must be 5-10 characters", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(frame, "Invalid email format", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (phone.length() != 11 || !phone.matches("\\d+")) {
                JOptionPane.showMessageDialog(frame, "Phone must be 11 digits", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (username.length() < 3 || username.length() > 50) {
                JOptionPane.showMessageDialog(frame, "Username must be 3-50 characters", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (isUserExists(email)) {
                JOptionPane.showMessageDialog(frame, "Email already registered", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User newUser = new User(username, email, phone, password);
            saveUser(newUser);
            currentUser = newUser;

            JOptionPane.showMessageDialog(frame, "Registration successful!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            frame.dispose();
            showDashboard();
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            showWelcomeScreen();
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        currentFrame = frame;
    }

    private static void showDashboard() {
        JFrame frame = new JFrame("Dashboard - " + currentUser.getUsername());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(255, 255, 200));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 200));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Welcome, " + currentUser.getUsername() + "!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(titleLabel, gbc);

        JButton incomeButton = new JButton("Track Income");
        incomeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(incomeButton, gbc);

        JButton budgetButton = new JButton("Budget & Analysis");
        budgetButton.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(budgetButton, gbc);

        JButton remindersButton = new JButton("Reminders");
        remindersButton.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(remindersButton, gbc);

        JButton savingsButton = new JButton("Savings & Goals");
        savingsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(savingsButton, gbc);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(logoutButton, gbc);

        incomeButton.addActionListener(e -> {
            frame.dispose();
            showIncomeScreen();
        });

        budgetButton.addActionListener(e -> {
            frame.dispose();
            showBudgetScreen();
        });

        remindersButton.addActionListener(e -> {
            frame.dispose();
            showRemindersScreen();
        });

        savingsButton.addActionListener(e -> {
            frame.dispose();
            showSavingsScreen();
        });

        logoutButton.addActionListener(e -> {
            currentUser = null;
            frame.dispose();
            showWelcomeScreen();
        });

        // Display recent transactions
        JTextArea recentTransactions = new JTextArea(10, 50);
        recentTransactions.setEditable(false);
        recentTransactions.setFont(new Font("Arial", Font.PLAIN, 14));
        recentTransactions.setText(getRecentTransactions());
        JScrollPane scrollPane = new JScrollPane(recentTransactions);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        panel.add(scrollPane, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        currentFrame = frame;
    }

    private static void showIncomeScreen() {
        JFrame frame = new JFrame("Track Income");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(255, 255, 200));

        // Panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        inputPanel.setBackground(new Color(255, 255, 200));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Income Source
        JLabel sourceLabel = new JLabel("Income Source:");
        sourceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JComboBox<String> sourceCombo = new JComboBox<>(
                new String[] { "Salary", "Freelance", "Investment", "Gift", "Other" });
        sourceCombo.setFont(new Font("Arial", Font.PLAIN, 16));
        sourceCombo.setPreferredSize(new Dimension(250, 35));

        // Amount
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField amountField = new JTextField();
        amountField.setFont(new Font("Arial", Font.PLAIN, 16));
        amountField.setPreferredSize(new Dimension(250, 35));

        // Date
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField dateField = new JTextField(LocalDate.now().toString());
        dateField.setFont(new Font("Arial", Font.PLAIN, 16));
        dateField.setPreferredSize(new Dimension(250, 35));

        // Add components to input panel
        inputPanel.add(sourceLabel);
        inputPanel.add(sourceCombo);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(255, 255, 200));

        JButton saveButton = new JButton("Save Income");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setPreferredSize(new Dimension(180, 40));

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(180, 40));

        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        // History Panel
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBackground(new Color(255, 255, 200));
        historyPanel.setBorder(BorderFactory.createTitledBorder("Income History"));

        JTextArea incomeHistory = new JTextArea(10, 50);
        incomeHistory.setFont(new Font("Arial", Font.PLAIN, 14));
        incomeHistory.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(incomeHistory);
        scrollPane.setPreferredSize(new Dimension(650, 200));
        historyPanel.add(scrollPane);

        // Add all panels to frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(historyPanel, BorderLayout.SOUTH);

        // Load existing data
        incomeHistory.setText(getIncomeHistory());

        // Button actions
        saveButton.addActionListener(e -> {
            String source = (String) sourceCombo.getSelectedItem();
            String amountStr = amountField.getText();
            String dateStr = dateField.getText();

            // Validation
            if (amountStr.isEmpty() || !amountStr.matches("^\\d+(\\.\\d{1,2})?$")) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid amount", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Parse date to ensure it's valid
                LocalDate.parse(dateStr);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Please enter date in YYYY-MM-DD format", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save the income record
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(INCOME_FILE, true))) {
                writer.write(currentUser.getEmail() + "," + source + "," + amountStr + "," + dateStr);
                writer.newLine();
                JOptionPane.showMessageDialog(frame, "Income record saved successfully!");

                // Clear fields
                amountField.setText("");
                dateField.setText(LocalDate.now().toString());

                // Refresh history
                incomeHistory.setText(getIncomeHistory());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error saving income record", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            showDashboard();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        currentFrame = frame;
    }

    private static void showBudgetScreen() {
        JFrame frame = new JFrame("Budget & Analysis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(255, 255, 200));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 200));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Budget & Analysis");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(categoryLabel, gbc);

        JTextField categoryField = new JTextField(20);
        categoryField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(categoryField, gbc);

        JLabel amountLabel = new JLabel("Budget Amount:");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(amountLabel, gbc);

        JTextField amountField = new JTextField(20);
        amountField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(amountField, gbc);

        JButton saveButton = new JButton("Save Budget");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(saveButton, gbc);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(backButton, gbc);

        // Display budget analysis
        JTextArea budgetAnalysis = new JTextArea(10, 40);
        budgetAnalysis.setEditable(false);
        budgetAnalysis.setFont(new Font("Arial", Font.PLAIN, 14));
        budgetAnalysis.setText(getBudgetAnalysis());
        JScrollPane scrollPane = new JScrollPane(budgetAnalysis);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        saveButton.addActionListener(e -> {
            String category = categoryField.getText();
            String amountStr = amountField.getText();

            if (category.isEmpty() || amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(frame, "Amount must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (category.length() < 3 || category.length() > 50) {
                    JOptionPane.showMessageDialog(frame, "Category must be 3-50 characters", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Budget budget = new Budget(currentUser.getEmail(), category, amount);
                saveBudget(budget);
                JOptionPane.showMessageDialog(frame, "Budget saved successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Update the display
                budgetAnalysis.setText(getBudgetAnalysis());

                // Clear fields
                categoryField.setText("");
                amountField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid amount format", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            showDashboard();
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        currentFrame = frame;
    }

    private static void showRemindersScreen() {
        JFrame frame = new JFrame("Reminders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(255, 255, 200));

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 15, 20));
        inputPanel.setBackground(new Color(255, 255, 200));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Reminder Title
        JLabel titleLabel = new JLabel("Reminder Title:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField titleField = new JTextField();
        titleField.setFont(new Font("Arial", Font.PLAIN, 16));
        titleField.setPreferredSize(new Dimension(250, 35));

        // Date
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField dateField = new JTextField(LocalDate.now().toString());
        dateField.setFont(new Font("Arial", Font.PLAIN, 16));
        dateField.setPreferredSize(new Dimension(250, 35));

        // Time
        JLabel timeLabel = new JLabel("Time (HH:MM):");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField timeField = new JTextField("12:00");
        timeField.setFont(new Font("Arial", Font.PLAIN, 16));
        timeField.setPreferredSize(new Dimension(250, 35));

        // Add to input panel
        inputPanel.add(titleLabel);
        inputPanel.add(titleField);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        inputPanel.add(timeLabel);
        inputPanel.add(timeField);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(255, 255, 200));

        JButton saveButton = new JButton("Save Reminder");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setPreferredSize(new Dimension(180, 40));

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(180, 40));

        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        // Reminders List Panel
        JPanel remindersPanel = new JPanel(new BorderLayout());
        remindersPanel.setBackground(new Color(255, 255, 200));
        remindersPanel.setBorder(BorderFactory.createTitledBorder("Your Reminders"));

        JTextArea remindersArea = new JTextArea(10, 50);
        remindersArea.setFont(new Font("Arial", Font.PLAIN, 14));
        remindersArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(remindersArea);
        scrollPane.setPreferredSize(new Dimension(650, 200));
        remindersPanel.add(scrollPane);

        // Add all panels to frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(remindersPanel, BorderLayout.SOUTH);

        // Load existing data
        remindersArea.setText(getRemindersList());

        // Button actions
        saveButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String dateStr = dateField.getText().trim();
            String timeStr = timeField.getText().trim();

            // Validation
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a reminder title", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDate.parse(dateStr);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Please enter date in YYYY-MM-DD format", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!timeStr.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                JOptionPane.showMessageDialog(frame, "Please enter time in HH:MM format", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save the reminder
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(REMINDERS_FILE, true))) {
                writer.write(currentUser.getEmail() + "," + title + "," + dateStr + "," + timeStr);
                writer.newLine();
                JOptionPane.showMessageDialog(frame, "Reminder saved successfully!");

                // Clear fields
                titleField.setText("");
                dateField.setText(LocalDate.now().toString());
                timeField.setText("12:00");

                // Refresh list
                remindersArea.setText(getRemindersList());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error saving reminder", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            showDashboard();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        currentFrame = frame;
    }

    private static void showSavingsScreen() {
        JFrame frame = new JFrame("Savings Goals");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 650);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(255, 255, 200));

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 15, 20));
        inputPanel.setBackground(new Color(255, 255, 200));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Goal Name
        JLabel goalLabel = new JLabel("Goal Name:");
        goalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField goalField = new JTextField();
        goalField.setFont(new Font("Arial", Font.PLAIN, 16));
        goalField.setPreferredSize(new Dimension(250, 35));

        // Target Amount
        JLabel targetLabel = new JLabel("Target Amount:");
        targetLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField targetField = new JTextField();
        targetField.setFont(new Font("Arial", Font.PLAIN, 16));
        targetField.setPreferredSize(new Dimension(250, 35));

        // Current Savings
        JLabel currentLabel = new JLabel("Current Savings:");
        currentLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField currentField = new JTextField("0");
        currentField.setFont(new Font("Arial", Font.PLAIN, 16));
        currentField.setPreferredSize(new Dimension(250, 35));

        // Add to input panel
        inputPanel.add(goalLabel);
        inputPanel.add(goalField);
        inputPanel.add(targetLabel);
        inputPanel.add(targetField);
        inputPanel.add(currentLabel);
        inputPanel.add(currentField);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(255, 255, 200));

        JButton saveButton = new JButton("Save Goal");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setPreferredSize(new Dimension(180, 40));

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(180, 40));

        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        // Goals List Panel
        JPanel goalsPanel = new JPanel(new BorderLayout());
        goalsPanel.setBackground(new Color(255, 255, 200));
        goalsPanel.setBorder(BorderFactory.createTitledBorder("Your Savings Goals"));

        JTextArea goalsArea = new JTextArea(12, 50);
        goalsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        goalsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(goalsArea);
        scrollPane.setPreferredSize(new Dimension(650, 250));
        goalsPanel.add(scrollPane);

        // Add all panels to frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(goalsPanel, BorderLayout.SOUTH);

        // Load existing data
        goalsArea.setText(getSavingsGoals());

        // Button actions
        saveButton.addActionListener(e -> {
            String goalName = goalField.getText().trim();
            String targetStr = targetField.getText().trim();
            String currentStr = currentField.getText().trim();

            // Validation
            if (goalName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a goal name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!targetStr.matches("^\\d+(\\.\\d{1,2})?$") || !currentStr.matches("^\\d+(\\.\\d{1,2})?$")) {
                JOptionPane.showMessageDialog(frame, "Please enter valid amounts", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double target = Double.parseDouble(targetStr);
            double current = Double.parseDouble(currentStr);

            if (current > target) {
                JOptionPane.showMessageDialog(frame, "Current savings cannot exceed target amount", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save the savings goal
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVINGS_FILE, true))) {
                writer.write(currentUser.getEmail() + "," + goalName + "," + targetStr + "," + currentStr);
                writer.newLine();
                JOptionPane.showMessageDialog(frame, "Savings goal saved successfully!");

                // Clear fields
                goalField.setText("");
                targetField.setText("");
                currentField.setText("0");

                // Refresh list
                goalsArea.setText(getSavingsGoals());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error saving savings goal", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            showDashboard();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        currentFrame = frame;
    }

    // Data access methods
    private static boolean isUserExists(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet, so no users
        }
        return false;
    }

    private static User authenticateUser(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[1].equals(email) && parts[3].equals(password)) {
                    return new User(parts[0], parts[1], parts[2], parts[3]);
                }
            }
        } catch (IOException e) {
            // File doesn't exist or can't be read
        }
        return null;
    }

    private static void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(user.getUsername() + "," + user.getEmail() + "," + user.getPhone() + "," + user.getPassword());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveIncome(Income income) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INCOME_FILE, true))) {
            writer.write(income.getUserEmail() + "," + income.getSource() + "," + income.getAmount() + ","
                    + income.getDate());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveBudget(Budget budget) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BUDGET_FILE, true))) {
            writer.write(budget.getUserEmail() + "," + budget.getCategory() + "," + budget.getAmount());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveReminder(Reminder reminder) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REMINDERS_FILE, true))) {
            writer.write(reminder.getUserEmail() + "," + reminder.getTitle() + "," + reminder.getDate() + ","
                    + reminder.getTime());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveSavingsGoal(SavingsGoal goal) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVINGS_FILE, true))) {
            writer.write(goal.getUserEmail() + "," + goal.getGoalName() + "," + goal.getTargetAmount() + ","
                    + goal.getCurrentSavings());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getIncomeHistory() {
        StringBuilder sb = new StringBuilder("Income History:\n");
        try (BufferedReader reader = new BufferedReader(new FileReader(INCOME_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].equals(currentUser.getEmail())) {
                    sb.append(String.format("- %s: $%.2f on %s\n", parts[1], Double.parseDouble(parts[2]), parts[3]));
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }
        return sb.toString();
    }

    private static String getBudgetAnalysis() {
        StringBuilder sb = new StringBuilder("Budget Analysis:\n");
        try (BufferedReader reader = new BufferedReader(new FileReader(BUDGET_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(currentUser.getEmail())) {
                    sb.append(String.format("- %s: Budget $%.2f\n", parts[1], Double.parseDouble(parts[2])));
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }
        return sb.toString();
    }

    private static String getRemindersList() {
        StringBuilder sb = new StringBuilder("Upcoming Reminders:\n");
        try (BufferedReader reader = new BufferedReader(new FileReader(REMINDERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].equals(currentUser.getEmail())) {
                    sb.append(String.format("- %s: %s at %s\n", parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }
        return sb.toString();
    }

    private static String getSavingsGoals() {
        StringBuilder sb = new StringBuilder("Savings Goals:\n");
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVINGS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].equals(currentUser.getEmail())) {
                    double target = Double.parseDouble(parts[2]);
                    double current = Double.parseDouble(parts[3]);
                    double progress = (current / target) * 100;
                    sb.append(String.format("- %s: $%.2f of $%.2f (%.1f%%)\n",
                            parts[1], current, target, progress));
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }
        return sb.toString();
    }

    private static String getRecentTransactions() {
        StringBuilder sb = new StringBuilder("Recent Transactions:\n");

        // Get income
        try (BufferedReader reader = new BufferedReader(new FileReader(INCOME_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].equals(currentUser.getEmail())) {
                    sb.append(String.format("+ $%.2f from %s on %s\n",
                            Double.parseDouble(parts[2]), parts[1], parts[3]));
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }

        // TODO: Add expenses when implemented

        return sb.toString();
    }

    // Model classes
    static class User {
        private String username;
        private String email;
        private String phone;
        private String password;

        public User(String username, String email, String phone, String password) {
            this.username = username;
            this.email = email;
            this.phone = phone;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getPassword() {
            return password;
        }
    }

    static class Income {
        private String userEmail;
        private String source;
        private double amount;
        private String date;

        public Income(String userEmail, String source, double amount, LocalDate date) {
            this.userEmail = userEmail;
            this.source = source;
            this.amount = amount;
            this.date = date.toString();
        }

        public String getUserEmail() {
            return userEmail;
        }

        public String getSource() {
            return source;
        }

        public double getAmount() {
            return amount;
        }

        public String getDate() {
            return date;
        }
    }

    static class Budget {
        private String userEmail;
        private String category;
        private double amount;

        public Budget(String userEmail, String category, double amount) {
            this.userEmail = userEmail;
            this.category = category;
            this.amount = amount;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public String getCategory() {
            return category;
        }

        public double getAmount() {
            return amount;
        }
    }

    static class Reminder {
        private String userEmail;
        private String title;
        private String date;
        private String time;

        public Reminder(String userEmail, String title, LocalDate date, String time) {
            this.userEmail = userEmail;
            this.title = title;
            this.date = date.toString();
            this.time = time;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }
    }

    static class SavingsGoal {
        private String userEmail;
        private String goalName;
        private double targetAmount;
        private double currentSavings;

        public SavingsGoal(String userEmail, String goalName, double targetAmount, double currentSavings) {
            this.userEmail = userEmail;
            this.goalName = goalName;
            this.targetAmount = targetAmount;
            this.currentSavings = currentSavings;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public String getGoalName() {
            return goalName;
        }

        public double getTargetAmount() {
            return targetAmount;
        }

        public double getCurrentSavings() {
            return currentSavings;
        }
    }

}