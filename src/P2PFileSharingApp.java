import javax.swing.*;
import java.net.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class P2PFileSharingApp extends JFrame {
    private JTextField sharedFolderTextField;
    private JTextField sharedSecretTextField;
    private JTextArea computersTextArea;
    private JTextArea filesFoundTextArea;
    private JTextArea fileTransfersTextArea;

    public P2PFileSharingApp() {
        createSetupPanel();
    }

    private void createSetupPanel() {
        // Setup screen components
        JLabel sharedFolderLabel = new JLabel("Shared Folder Location:");
        sharedFolderTextField = new JTextField(1);

        JLabel sharedSecretLabel = new JLabel("Shared Secret:");
        sharedSecretTextField = new JTextField(1);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInputs()) {
                    showMainScreen();
                } else {
                    JOptionPane.showMessageDialog(P2PFileSharingApp.this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel setupPanel = new JPanel(new GridLayout(3, 3));
        // 1 column for labels, 2 columns for text areas
        setupPanel.add(sharedFolderLabel);
        setupPanel.add(sharedFolderTextField);
        setupPanel.add(new JLabel()); // Empty label to fill the space
        setupPanel.add(sharedSecretLabel);
        setupPanel.add(sharedSecretTextField);
        setupPanel.add(new JLabel()); // Empty label to fill the space
        setupPanel.add(new JLabel()); // Empty label to fill the space
        setupPanel.add(startButton);
        setupPanel.add(new JLabel()); // Empty label to fill the space

        // Main screen components
        computersTextArea = new JTextArea(10, 20);
        computersTextArea.setEditable(false);

        filesFoundTextArea = new JTextArea(10, 20);
        filesFoundTextArea.setEditable(false);

        fileTransfersTextArea = new JTextArea(10, 20);
        fileTransfersTextArea.setEditable(false);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu filesMenu = new JMenu("Files");
        JMenuItem connectMenuItem = new JMenuItem("Connect");
        JMenuItem disconnectMenuItem = new JMenuItem("Disconnect");
        JMenuItem backToMenuItem = new JMenuItem("Back to Setup");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        filesMenu.add(connectMenuItem);
        filesMenu.add(disconnectMenuItem);
        filesMenu.add(backToMenuItem);
        filesMenu.addSeparator();
        filesMenu.add(exitMenuItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(filesMenu);
        menuBar.add(helpMenu);

        connectMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement connection logic here
            }
        });

        disconnectMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement disconnection logic here
            }
        });

        backToMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reset everything before leaving
                // TODO: Reset the connection and other variables
                // Go back to the setup screen
                showSetupScreen();
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(P2PFileSharingApp.this, "Developer: Ömer Dikyol \nStudent Number: 20200702002");
            }
        });

        setJMenuBar(menuBar);

        // Set up the main frame
        setLayout(new BorderLayout());
        add(setupPanel, BorderLayout.NORTH);
        setTitle("P2P Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createMainScreenPanel() {
    // Create a panel to hold two sections
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(2, 1));

    // Create a panel for info bar
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BorderLayout());

    // First row: computers in network and files found
    JPanel firstRowPanel = new JPanel();
    firstRowPanel.setLayout(new GridLayout(1, 2));

    // Create a section for computers in network
    JPanel networkPanel = new JPanel();
    networkPanel.setBorder(BorderFactory.createTitledBorder("Computers in Network"));
    networkPanel.setLayout(new BorderLayout());

    // Create a list of IP addresses
    DefaultListModel<String> ipListModel = new DefaultListModel<>();
    ipListModel.addElement("10.1.1.2");
    ipListModel.addElement("10.1.1.3");
    ipListModel.addElement("10.1.1.4");
    JList<String> ipList = new JList<>(ipListModel);
    ipList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // Add a scroll pane for the list
    JScrollPane ipScrollPane = new JScrollPane(ipList);
    networkPanel.add(ipScrollPane, BorderLayout.CENTER);

    // Add the network panel to the main panel
    firstRowPanel.add(networkPanel);

    // Create a section for file found
    JPanel fileFoundPanel = new JPanel();
    fileFoundPanel.setBorder(BorderFactory.createTitledBorder("Files Found"));
    fileFoundPanel.setLayout(new BorderLayout());

    // Create a list of file names
    DefaultListModel<String> fileListModel = new DefaultListModel<>();

    // Get the files from the shared folder
    ArrayList<String> files = getFilesFromDirectory(sharedFolderTextField.getText());

    // Add the files to the list
    for (String file : files) {
        fileListModel.addElement(file);
    }

    JList<String> fileList = new JList<>(fileListModel);
    fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // Add a scroll pane for the list
    JScrollPane fileScrollPane_fileFound = new JScrollPane(fileList);
    fileFoundPanel.add(fileScrollPane_fileFound, BorderLayout.CENTER);

    // Add the file found panel to the main panel
    firstRowPanel.add(fileFoundPanel);

    // Second row: file transfers
    JPanel secondRowPanel = new JPanel();
    secondRowPanel.setLayout(new GridLayout(1, 1));

    // Create a section for file transfers
    JPanel filePanel = new JPanel();
    filePanel.setBorder(BorderFactory.createTitledBorder("File Transfers"));
    filePanel.setLayout(new BorderLayout());

    // Create a table of file names and IP addresses
    String[] columnNames = {"File Name", "IP Address"};
    Object[][] data = {
            {"termproj.docx", "10.1.1.2"},
            {"november_rain.mp3", "10.1.1.3"},
            {"gameofthrones.mkv", "10.1.1.4"}
    };
    JTable fileTable = new JTable(data, columnNames);
    fileTable.setFillsViewportHeight(true);

    // Add a scroll pane for the table
    JScrollPane fileScrollPane_Table = new JScrollPane(fileTable);
    filePanel.add(fileScrollPane_Table, BorderLayout.CENTER);

    // Add the file panel to the main panel
    secondRowPanel.add(filePanel);

    // Add the two rows to the main panel
    mainPanel.add(firstRowPanel);
    mainPanel.add(secondRowPanel);


    // Set the info bar
    // Create a label for the computer hostname
    JLabel hostnameLabel = new JLabel(" " + getHostname());
    infoPanel.add(hostnameLabel, BorderLayout.WEST);

    // Create a label for the computer IP
    JLabel ipLabel = new JLabel(getIpAddress() + " ");
    infoPanel.add(ipLabel, BorderLayout.EAST);


    // Combine the main panel and the info panel
    JPanel combinedPanel = new JPanel();
    combinedPanel.setLayout(new BorderLayout());
    combinedPanel.add(infoPanel, BorderLayout.SOUTH);
    combinedPanel.add(mainPanel, BorderLayout.CENTER);

    return combinedPanel;
}

    private void showMainScreen() {
        // Switch to the main screen
        getContentPane().removeAll();
        add(createMainScreenPanel(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void showSetupScreen() {
        getContentPane().removeAll();
        createSetupPanel();
        revalidate();
        repaint();
    }

    private String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Error on getting hostname";
        }
    }

    private String getIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Error on getting IP address";
        }
    }

    private boolean validateInputs() {
        // Check if both text fields are not empty
        return !sharedFolderTextField.getText().trim().isEmpty() && !sharedSecretTextField.getText().trim().isEmpty();
    }

    private ArrayList<String> getFilesFromDirectory(String directoryPath) {
        
        // Get the path from the shared folder
        String pathFromSharedFolder = directoryPath.replace(sharedFolderTextField.getText(), "");

        // Create a list to hold the file names
        ArrayList<String> results = new ArrayList<>();

        File[] files = new File(directoryPath).listFiles();

        if (files == null) {
            results.add("No files found in the directory.");
            return results;
        }

        for (File file : files) {

            if (file.isFile()) {
                results.add(pathFromSharedFolder + "\\" + file.getName());
            } else if (file.isDirectory()) {
                // results.add(pathFromSharedFolder + "\\" + file.getName() + "\\");
                // Recursively call this method to get files in subdirectories
                ArrayList<String> recResults = getFilesFromDirectory(file.getAbsolutePath());                
                // Add the results to the list
                for (String recResult : recResults) {
                    results.add(recResult);
                }
            }
        }

        return results;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new P2PFileSharingApp().setVisible(true);
            }
        });
    }
}
