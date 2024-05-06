package bookstore.trade;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TradeWindow extends JFrame {
    private JComboBox<String> actionComboBox;
    private JTextField bookNameField;
    private JTextField priceField;
    private JLabel secondaryLabel;
    private JTextField secondaryField;
    private JButton submitButton;

    public TradeWindow() {
        setTitle("Trade");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10)); // 6 rows, 2 columns

        // Action combo box
        add(new JLabel("Action:"));
        actionComboBox = new JComboBox<>(new String[]{"Buy", "Sell", "Trade"});
        add(actionComboBox);

        // Book name field
        add(new JLabel("Book Name:"));
        bookNameField = new JTextField();
        add(bookNameField);

        // Price field
        add(new JLabel("Price:"));
        priceField = new JTextField();
        add(priceField);

        // Secondary label and field based on action
        secondaryLabel = new JLabel("Author:");
        secondaryField = new JTextField();
        add(secondaryLabel);
        add(secondaryField);

        // Submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());
        add(submitButton);

        // Action listener for action combo box
        actionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFields();
            }
        });

        updateFields(); // Initial update based on selected action

        pack();
        setLocationRelativeTo(null); // Center the window on screen
        setVisible(true);
    }

    private void updateFields() {
        String selectedAction = (String) actionComboBox.getSelectedItem();
        switch (selectedAction) {
            case "Buy":
            case "Sell":
                secondaryLabel.setText("Author:");
                break;
            case "Trade":
                secondaryLabel.setText("Trade with:");
                break;
        }
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String action = (String) actionComboBox.getSelectedItem();
            String bookName = bookNameField.getText();
            String secondaryInfo = secondaryField.getText();
            String price = priceField.getText();

            if (bookName.isEmpty() || secondaryInfo.isEmpty() || price.isEmpty()) {
                JOptionPane.showMessageDialog(TradeWindow.this,
                        "Please fill in all fields.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Perform action based on selected action
            switch (action) {
                case "Buy":
                    JOptionPane.showMessageDialog(TradeWindow.this,
                            "You have bought the book: " + bookName + " by " + secondaryInfo + " for $" + price,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Sell":
                    JOptionPane.showMessageDialog(TradeWindow.this,
                            "You have sold the book: " + bookName + " by " + secondaryInfo + " for $" + price,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "Trade":
                    JOptionPane.showMessageDialog(TradeWindow.this,
                            "You have traded the book: " + bookName + " for " + secondaryInfo + " for $" + price,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
            }

            // Clear the input fields
            bookNameField.setText("");
            secondaryField.setText("");
            priceField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TradeWindow::new);
    }
}
