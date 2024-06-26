package bookstore.RibbonLayout;

import javax.swing.*;

import javax.swing.text.DefaultEditorKit;

import bookstore.cart.Cart;
import bookstore.homepage.GridLayoutManager;
import bookstore.loginWindow.LoginWindow;
import bookstore.checkout.CheckoutWindow;
import bookstore.bookDetails.BookSearchHandler;


import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class RibbonLayout implements ActionListener {

    JFrame frame;
    JMenuBar menuBar;
    JMenu home, edit, search, cart, checkout, trade, help, admin, logout;
    JMenuItem cutItem, copyItem, pasteItem, selectAllItem, viewCart, clearCart, searchItem;
    Cart cart_obj;
    BookSearchHandler searchHandler;
    JPanel cartPanel;


    public RibbonLayout() {
        frame = new JFrame();
        cart_obj = new Cart();
        cart_obj = Cart.getInstance();
        searchHandler = new BookSearchHandler();

        // Setup Grid Layout Manager
        GridLayoutManager.setupGridLayout(frame, cart_obj);

        // Initialize components
        initializeMenuBar();

        // Set frame properties
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

     // Add action listener for help menu
        help.addActionListener(this);
    }

    // Method to initialize menu bar and its components
    private void initializeMenuBar() {
        menuBar = new JMenuBar();

        // Initialize menus
        home = new JMenu("Home");
        //edit = new JMenu("Edit");
        search = new JMenu("Search");
        cart = new JMenu("Cart");
        //checkout = new JMenu("Checkout");
        trade = new JMenu("Trade");
        help = new JMenu("Help");
        //admin = new JMenu("Admin");
        logout = new JMenu("Logout");

        // Initialize menu items
        initializeMenuItems();

        // Add menus to the menu bar with separators
        addMenuItemWithSeparator(home);
        //addMenuItemWithSeparator(edit);
        addMenuItemWithSeparator(search);
        addMenuItemWithSeparator(cart);
        //addMenuItemWithSeparator(checkout);
        addMenuItemWithSeparator(trade);
        addMenuItemWithSeparator(help);
        //addMenuItemWithSeparator(admin);
        addMenuItemWithSeparator(logout);

        // Set menu bar to frame
        frame.setJMenuBar(menuBar);
    }

    // Method to initialize menu items
    private void initializeMenuItems() {
        // Add add yo cart menu item with its handler
        JMenuItem viewCartMenuItem = new JMenuItem("View Cart");
        viewCartMenuItem.addActionListener(e -> viewCartContents());
        // Add logout menu item with its handler
        JMenuItem logoutMenuItem = new JMenuItem("Are you sure you want to Logout?");
        logoutMenuItem.addActionListener(new LogoutHandler());
        logout.add(logoutMenuItem);

        // Add items inside edit menu with their handlers
//        cutItem = new JMenuItem(new DefaultEditorKit.CutAction());
//        copyItem = new JMenuItem(new DefaultEditorKit.CopyAction());
//        pasteItem = new JMenuItem(new DefaultEditorKit.PasteAction());
//        selectAllItem = new JMenuItem("Select All");
//
//        edit.add(cutItem);
//        edit.add(copyItem);
//        edit.add(pasteItem);
//        edit.add(selectAllItem);


        cart = new JMenu("Cart");
        viewCart = new JMenuItem("View Cart");
        clearCart = new JMenuItem("Clear Cart");
        cart.add(viewCart);
        cart.add(clearCart);
        viewCart.addActionListener(e -> viewCartContents());
        clearCart.addActionListener(e -> clearCartContents());
        viewCartMenuItem.addActionListener(e -> viewCartContents());


        // Search menu item
        searchItem = new JMenuItem("Search Book");
        searchItem.addActionListener(e -> {
            try {
                BookSearchHandler.handleSearch();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        search.add(searchItem);

        // Initialize cartPanel
        cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS)); // Set layout for the cart panel
        frame.add(cartPanel, BorderLayout.EAST);

        // Add action listener for help menu
        help.addActionListener(this);


    }

    // Method to add menu item with separator
    private void addMenuItemWithSeparator(JMenu menu) {
        menuBar.add(menu);
        menuBar.add(new JSeparator(SwingConstants.VERTICAL));
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RibbonLayout::new);
    }

    // Inner class for handling logout action
    private class LogoutHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            SwingUtilities.invokeLater(LoginWindow::new);
        }
    }


    private void viewCartContents() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        JTextArea textArea = new JTextArea(10, 30);
        textArea.setText(cart_obj.getCartDetails());
        textArea.setEditable(false);
        JTextArea cartContentsArea = new JTextArea(Cart.getInstance().getCartDetails());
        cartContentsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);

        JButton checkoutButton = new JButton("Proceed to Checkout");
        checkoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkoutButton.addActionListener(e -> proceedToCheckout());
        panel.add(checkoutButton);

        JOptionPane.showMessageDialog(frame, panel, "Cart Contents", JOptionPane.INFORMATION_MESSAGE);
    }
    private void updateCartDisplay() {
        JTextArea cartContentsArea = new JTextArea(Cart.getInstance().getCartDetails());
        cartContentsArea.setEditable(false);
        // Assuming there is a dedicated panel or area to display this information
        cartPanel.removeAll();
        cartPanel.add(new JScrollPane(cartContentsArea));
        cartPanel.revalidate();
        cartPanel.repaint();
    }


    private void proceedToCheckout() {

//        new CheckoutWindow();
        JDialog checkoutDialog = new JDialog(frame, "Checkout", true); // true for modal
        CheckoutWindow checkoutWindow = new CheckoutWindow(); // Create an instance of CheckoutWindow
        checkoutDialog.add(checkoutWindow); // Add the CheckoutWindow to the dialog
        checkoutDialog.pack(); // Pack the dialog to size
        checkoutDialog.setLocationRelativeTo(frame); // Set location relative to the main frame
        checkoutDialog.setVisible(true); // Display the checkout dialog
//        JOptionPane.showMessageDialog(frame, "Checkout process started.", "Checkout", JOptionPane.INFORMATION_MESSAGE);

    }


    private void clearCartContents() {
        cart_obj.clearCart();
        JOptionPane.showMessageDialog(frame, "Cart has been cleared.", "Cart", JOptionPane.INFORMATION_MESSAGE);
    }



 // Action performed method for help menu
    @Override
    public void actionPerformed(ActionEvent e) {
        // Display help message
        JOptionPane.showMessageDialog(frame,
                "Please call following number: +1 816-100-3163\n" +
                "or Email us: bookstore.manager@bookstore.com.\n" +
                "We will respond as soon as possible within 24 hours.",
                "Help",
                JOptionPane.INFORMATION_MESSAGE);
    }
}