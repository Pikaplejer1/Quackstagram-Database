package UIFiles;import MainFiles.FilePathInstance;import javax.swing.*;import java.awt.*;import java.awt.event.ActionEvent;import java.io.BufferedReader;import java.io.BufferedWriter;import java.io.File;import java.io.IOException;import java.nio.file.*;import java.time.LocalDateTime;import java.time.format.DateTimeFormatter;/** * Provides the user interface for uploading images to Quackstagram. * It includes functionalities for selecting, previewing, and uploading images along with a bio. */public class ImageUploadUI extends BaseUI {    private JLabel imagePreviewLabel;    private JTextArea bioTextArea;    private JButton uploadButton;    private JButton saveButton;    private boolean imageUploaded = false;    private JPanel headerPanel;    private JLabel photoPanel;    private JPanel navigationPanel;    private JPanel contentPanel;    private ImageIcon emptyImageIcon;    private JScrollPane bioScrollPane;    private JPanel uploadButtonPanel;    private final FilePathInstance pathFile = FilePathInstance.getInstance();    /**     * Constructor for ImageUploadUI.     * Initializes the components and layout for the image upload interface.     */    public ImageUploadUI() {        super("Upload Image");        initializeComponents();        layoutComponents();    }    /**     * Initializes the components for the image upload interface.     */    private void initializeComponents() {        headerPanel = createHeaderPanel("upload image");        navigationPanel = createNavigationPanel();        photoPanel = createPicture("img/logos/DACS.png");        contentPanel = createContentPanel();        imagePreviewLabel = createImagePreviewLabel();        bioTextArea = createBioTextArea();        bioScrollPane = createBioScrollPane();        uploadButton = createButton("Upload Image", Color.YELLOW);        uploadButtonPanel = CreateUploadButtonPanel();        saveButton = createSaveButton();    }    /**     * Lays out the components on the UI.     */    private void layoutComponents() {        this.add(headerPanel);        contentPanel.add(photoPanel);        contentPanel.add(imagePreviewLabel);        contentPanel.add(uploadButtonPanel);        add(headerPanel, BorderLayout.NORTH);        add(contentPanel, BoxLayout.Y_AXIS);        add(navigationPanel, BorderLayout.SOUTH);    }    private JPanel CreateUploadButtonPanel() {        uploadButtonPanel = new JPanel();        uploadButtonPanel.add(uploadButton);        uploadButton.addActionListener(this::uploadAction);        return uploadButtonPanel;    }    private JScrollPane createBioScrollPane() {        bioScrollPane = new JScrollPane(bioTextArea);        bioScrollPane.setPreferredSize(new Dimension(WIDTH - 50, HEIGHT / 6));        contentPanel.add(bioScrollPane);        return bioScrollPane;    }    private JTextArea createBioTextArea() {        bioTextArea = new JTextArea();        bioTextArea.setAlignmentX(CENTER_ALIGNMENT);        bioTextArea.setLineWrap(true);        bioTextArea.setWrapStyleWord(true);        addHoverText(bioTextArea,"Enter a caption");        return bioTextArea;    }    private JLabel createImagePreviewLabel() {        imagePreviewLabel = new JLabel();        imagePreviewLabel.setAlignmentX(CENTER_ALIGNMENT);        imagePreviewLabel.setPreferredSize(new Dimension(WIDTH, HEIGHT / 2));        imagePreviewLabel.setIcon(emptyImageIcon);        return imagePreviewLabel;    }    private JButton createSaveButton() {        // Save button (for bio)        saveButton = createButton("Save Caption", Color.WHITE);        saveButton.setAlignmentX(CENTER_ALIGNMENT);        saveButton.addActionListener(this::saveBioAction);        return saveButton;    }    //TODO nawetr nie wiem    /**     * Handles the action of uploading an image.     *     * @param event The action event associated with the upload action.     */    private void uploadAction(ActionEvent event) {        JFileChooser fileChooser = configureFileChooser();        int returnValue = fileChooser.showOpenDialog(null);        if (returnValue == JFileChooser.APPROVE_OPTION) {            File selectedFile = fileChooser.getSelectedFile();            try {                handleImageUpload(selectedFile);            } catch (IOException ex) {                JOptionPane.showMessageDialog(this, "Error saving image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);            }        }    }    //TODO zrobic insert i tyle    /**     * Handles the process of selecting and uploading an image file.     *     * @param selectedFile The selected image file.     * @throws IOException If an error occurs during file handling.     */    private void handleImageUpload(File selectedFile) throws IOException {        String username = readUsername(); // Read username from users.txt        int imageId = getNextImageId(username);        String fileExtension = getFileExtension(selectedFile);        String newFileName = username + "_" + imageId + "." + fileExtension;        Path destPath = Paths.get("img", "uploaded", newFileName);        Files.copy(selectedFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);        // Save the bio and image ID to a text file        saveImageInfo(username + "_" + imageId, username, bioTextArea.getText());        // Load the image from the saved path        calculateImageDimensions(destPath, imagePreviewLabel);        // Update the flag to indicate that an image has been uploaded        imageUploaded = true;        // Change the text of the upload button        uploadButton.setText("Upload Another Image");        JOptionPane.showMessageDialog(this, "Image uploaded and preview updated!");    }    //TODO tak jak pisalam zrobiz zeby dodawalo poprostu 1 do najwiekszego id    /**     * Generates the next available image ID for a given username.     *     * @param username The username associated with the uploaded image.     * @return An integer representing the next available image ID.     * @throws IOException If an error occurs during file access.     */    private static int getNextImageId(String username) throws IOException {        Path storageDir = Paths.get("img", "uploaded"); // Ensure this is the directory where images are saved        if (!Files.exists(storageDir)) {            Files.createDirectories(storageDir);        }        int maxId = 0;        try (DirectoryStream<Path> stream = Files.newDirectoryStream(storageDir, username + "_*")) {            for (Path path : stream) {                String fileName = path.getFileName().toString();                int idEndIndex = fileName.lastIndexOf('.');                if (idEndIndex != -1) {                    String idStr = fileName.substring(username.length() + 1, idEndIndex);                    try {                        int id = Integer.parseInt(idStr);                        if (id > maxId) {                            maxId = id;                        }                    } catch (NumberFormatException ex) {                        // Ignore filenames that do not have a valid numeric ID                    }                }            }        }        return maxId + 1; // Return the next available ID    }    //TODO nie wiem czy potrzebne skoro juz i tak zapisujemy w bazie danych    /**     * Saves image details including the image ID, username, bio, and timestamp.     *     * @param imageId   The ID of the image.     * @param username  The username associated with the image.     * @param bio       The bio text associated with the image.     * @throws IOException If an error occurs during file writing.     */    private void saveImageInfo(String imageId, String username, String bio) throws IOException {        Path infoFilePath = pathFile.imageDetailsPath();        if (!Files.exists(infoFilePath)) {            Files.createFile(infoFilePath);        }        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));        try (BufferedWriter writer = Files.newBufferedWriter(infoFilePath, StandardOpenOption.APPEND)) {            writer.write(String.format("ImageID: %s, Username: %s, Bio: %s, Timestamp: %s, Likes: 0", imageId, username, bio, timestamp));            writer.newLine();        }    }    //TODO potrzebujemy tego?    public static String getFileExtension(File file) {        String name = file.getName();        int lastIndexOf = name.lastIndexOf(".");        if (lastIndexOf == -1) {            return ""; // empty extension        }        return name.substring(lastIndexOf + 1);    }//TODO chyba bedzie trzeba zrobic alter i tyle    /**     * Handles the action of saving the bio text.     *     * @param event The action event associated with saving the bio.     */    private void saveBioAction(ActionEvent event) {        // Here you would handle saving the bio text        String bioText = bioTextArea.getText();        // For example, save the bio text to a file or database        JOptionPane.showMessageDialog(this, "Caption saved: " + bioText);    }//TODO ktory username?    /**     * Reads the username from the users file.     *     * @return The username as a string.     * @throws IOException If an error occurs during file reading.     */    public String readUsername() throws IOException {        Path usersFilePath = pathFile.usersPath();        try (BufferedReader reader = Files.newBufferedReader(usersFilePath)) {            String line = reader.readLine();            if (line != null) {                return line.split(":")[0]; // Extract the username from the first line            }        }        return null; // Return null if no username is found    }}