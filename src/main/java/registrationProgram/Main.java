package registrationProgram;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
	    String stylesheet = getClass()
	            .getResource("/registrationProgram/app.css")
	            .toExternalForm();
	    stage.sceneProperty().addListener((observable, oldScene, newScene) -> {
	        if (newScene != null && !newScene.getStylesheets().contains(stylesheet)) {
	            newScene.getStylesheets().add(stylesheet);
	        }
	    });

	    CourseDatabase data = DataManager.loadCourseDatabase();

	    Label title = new Label("Course Registration System");
	    title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

	    Label subtitle = new Label(
	            "Welcome! Please select your account type."
	    );
	    subtitle.setStyle("-fx-font-size: 16px;");

	    Button studentButton = new Button("Student");
	    Button adminButton = new Button("Administrator");
	    studentButton.setOnAction(e -> showStudentLogin(stage, data));
	    adminButton.setOnAction(e -> showAdminLogin(stage, data));

	    studentButton.setPrefWidth(180);
	    adminButton.setPrefWidth(180);

	    VBox buttons = new VBox(15, studentButton, adminButton);
	    buttons.setAlignment(Pos.CENTER);

	    VBox layout = new VBox(20, title, subtitle, buttons);
	    layout.setAlignment(Pos.CENTER);

	    Scene scene = new Scene(layout, 700, 500);

	    stage.setTitle("Course Registration");
	    stage.setMinWidth(680);
	    stage.setMinHeight(500);
	    stage.setScene(scene);
	    stage.show();
	    stage.setOnCloseRequest(e -> DataManager.saveCourseDatabase(data));
	}
	
	private void showStudentLogin(Stage stage, CourseDatabase data) {

	    Label title = new Label("Student Login");
	    title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

	    Label usernameLabel = new Label("Username");
	    TextField usernameField = new TextField();
	    usernameField.setPromptText("Enter your username");

	    Label passwordLabel = new Label("Password");
	    PasswordField passwordField = new PasswordField();
	    passwordField.setPromptText("Enter your password");

	    Button loginButton = new Button("Login");
	    Button backButton = new Button("Back");

	    Label message = new Label();

	    loginButton.setOnAction(e -> {

	        String username = usernameField.getText();
	        String password = passwordField.getText();

	        Student student = null;

	        for (Student s : data.getStudentDirectory()) {
	            if (s.getUsername().equals(username)) {
	                student = s;
	                break;
	            }
	        }

	        if (student == null) {
	            message.setText("Username not found.");
	        } 
	        else if (!student.getPassword().equals(password)) {
	            message.setText("Incorrect password.");
	        } 
	        else {
	            showStudentDashboard(stage, data, student);
	        }
	    });

	    backButton.setOnAction(e -> start(stage));

	    VBox layout = new VBox(
	            12,
	            title,
	            usernameLabel,
	            usernameField,
	            passwordLabel,
	            passwordField,
	            loginButton,
	            message,
	            backButton
	    );

	    layout.setAlignment(Pos.CENTER);
	    layout.setMaxWidth(350);

	    StackPane root = new StackPane(layout);

	    Scene scene = new Scene(root, 700, 500);

	    stage.setScene(scene);
	}
	
	
	private void showAdminLogin(Stage stage, CourseDatabase data) {

	    Label title = new Label("Administrator Login");
	    title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

	    Label usernameLabel = new Label("Username");
	    TextField usernameField = new TextField();
	    usernameField.setPromptText("Enter your username");

	    Label passwordLabel = new Label("Password");
	    PasswordField passwordField = new PasswordField();
	    passwordField.setPromptText("Enter your password");

	    Button loginButton = new Button("Login");
	    Button backButton = new Button("Back");

	    Label message = new Label();

	    loginButton.setOnAction(e -> {

	        String username = usernameField.getText();
	        String password = passwordField.getText();

	        if (!username.equals("Admin")) {
	            message.setText("Username not found.");
	        }
	        else if (!password.equals("Admin001")) {
	            message.setText("Incorrect password.");
	        }
	        else {
	            Admin admin = new Admin("adminFirst","adminLast","Admin","Admin001");
	            showAdminDashboard(stage, data, admin);
	        }
	    });

	    backButton.setOnAction(e -> start(stage));
	    VBox layout = new VBox(
	            12,
	            title,
	            usernameLabel,
	            usernameField,
	            passwordLabel,
	            passwordField,
	            loginButton,
	            message,
	            backButton
	    );

	    layout.setAlignment(Pos.CENTER);
	    layout.setMaxWidth(350);
	    StackPane root = new StackPane(layout);
	    Scene scene = new Scene(root, 700, 500);
	    stage.setScene(scene);
	}
	
	private VBox createCourseCard(Course course) {

	    VBox courseCard = new VBox(8);
	    courseCard.getStyleClass().add("course-card");

	    Label name = new Label(course.getCourseName());
	    name.getStyleClass().add("course-card-title");

	    Label courseID = new Label(
	            course.getCourseID() +
	            "  •  Section " +
	            course.getSection()
	    );
	    courseID.getStyleClass().add("course-card-meta");

	    Label instructor = new Label(
	            "Instructor: " + course.getInstructor()
	    );

	    Label location = new Label(
	            "Location: " + course.getLocation()
	    );

	    Label enrollment = new Label(
	            "Enrollment: " +
	            course.getCurrentEnrollment() +
	            " / " +
	            course.getCapacity()
	    );

	    Label status = new Label();

	    if (course.isFull()) {
	        status.setText("FULL");
	        status.getStyleClass().addAll("course-status", "course-status-full");
	    } else {
	        int remainingSeats =
	                course.getCapacity() -
	                course.getCurrentEnrollment();

	        status.setText(
	                "OPEN • " + remainingSeats + " seat" +
	                (remainingSeats == 1 ? "" : "s") + " remaining"
	        );

	        status.getStyleClass().addAll("course-status", "course-status-open");
	    }

	    VBox courseInfo = new VBox(
	            4,
	            courseID,
	            instructor,
	            location,
	            enrollment,
	            status
	    );

	    courseCard.getChildren().addAll(
	            name,
	            courseInfo
	    );

	    courseCard.setPadding(new Insets(15));
	    courseCard.setMaxWidth(Double.MAX_VALUE);

	    return courseCard;
	}
	
	private ScrollPane createCourseListPane(VBox courses) {

	    ScrollPane scrollPane = new ScrollPane();

	    scrollPane.setContent(courses);
	    scrollPane.setFitToWidth(true);
	    scrollPane.setFitToHeight(false);
	    scrollPane.setPrefHeight(450);

	    courses.setFillWidth(true);

	    return scrollPane;
	}
	
	private void showAdminDashboard(Stage stage,CourseDatabase data,Admin admin) {

	    Label welcome = new Label(
	            "Welcome, Administrator!"
	    );

	    welcome.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    Label subtitle = new Label(
	            "Administrator Dashboard"
	    );

	    subtitle.setStyle("-fx-font-size: 16px;");

	    Button viewAllButton =
	            new Button("View All Courses");

	    Button viewFullButton =
	            new Button("View Full Courses");

	    Button createCourseButton =
	            new Button("Create a Course");

	    Button deleteCourseButton =
	            new Button("Delete a Course");
	    
	    Button deleteSectionButton =
	            new Button("Delete Section");

	    Button studentsButton =
	            new Button("View Students in a Course");

	    Button studentCoursesButton =
	            new Button("View a Student's Courses");
	    
	    Button editCourseButton =
	            new Button("Edit a Course");
	    
	    Button registerStudentButton = new Button("Register a New Student");

	    Button logoutButton =
	            new Button("Logout");
	    

	    Button[] buttons = {
	            viewAllButton,
	            viewFullButton,
	            createCourseButton,
	            deleteCourseButton,
	            deleteSectionButton,
	            editCourseButton,
	            studentsButton,
	            studentCoursesButton,
	            registerStudentButton,
	            logoutButton
	    };

	    for (Button button : buttons) {
	        button.setPrefWidth(280);
	        button.setPrefHeight(40);
	    }

	    viewAllButton.setOnAction(e -> showAdminAllCourses(stage, data, admin));

	    viewFullButton.setOnAction(e -> showAdminFullCourses(stage, data, admin) );

	    createCourseButton.setOnAction(e -> showCreateCourse(stage, data, admin));

	    deleteCourseButton.setOnAction(e -> showDeleteCourse(stage, data, admin));
	    
	    deleteSectionButton.setOnAction(e -> showDeleteSection(stage, data, admin));
	    
	    editCourseButton.setOnAction(e -> showEditCourse(stage, data, admin));

	    studentsButton.setOnAction(e -> showStudentsInCourse(stage, data, admin));

	    studentCoursesButton.setOnAction(e -> showStudentCourses(stage, data, admin));
	    
	    registerStudentButton.setOnAction(e -> showRegisterStudent(stage, data, admin));

	    logoutButton.setOnAction(e ->
	            start(stage)
	    );

	    VBox menu = new VBox(
	            12,
	            viewAllButton,
	            viewFullButton,
	            createCourseButton,
	            deleteCourseButton,
	            deleteSectionButton,
	            editCourseButton,
	            studentsButton,
	            studentCoursesButton,
	            registerStudentButton,
	            logoutButton
	    );

	    menu.setAlignment(Pos.CENTER);

	    VBox layout = new VBox(
	            15,
	            welcome,
	            subtitle,
	            menu
	    );

	    layout.setPadding(new Insets(25));
	    layout.setAlignment(Pos.TOP_CENTER);

	    Scene scene = new Scene(layout, 750, 650);

	    stage.setScene(scene);
	}
	
	private void showAdminAllCourses(Stage stage,CourseDatabase data,Admin admin) {

	    Label title = new Label("All Courses");

	    title.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    VBox courses = new VBox(12);
	    courses.setPadding(new Insets(10));

	    for (Course course : data.getCourseDirectory()) {

	        VBox courseCard = createCourseCard(course);

	        courses.getChildren().add(courseCard);
	    }

	    ScrollPane scrollPane = new ScrollPane(courses);

	    scrollPane.setFitToWidth(true);
	    scrollPane.setPrefHeight(450);

	    Button backButton =
	            new Button("Back to Dashboard");

	    backButton.setOnAction(e ->
	            showAdminDashboard(stage, data, admin)
	    );

	    VBox layout = new VBox(
	            15,
	            title,
	            scrollPane,
	            backButton
	    );

	    layout.setPadding(new Insets(25));
	    layout.setAlignment(Pos.TOP_CENTER);

	    VBox.setVgrow(scrollPane, Priority.ALWAYS);

	    Scene scene = new Scene(layout, 750, 650);

	    stage.setScene(scene);
	}
	
	private void showRegisterStudent(Stage stage,CourseDatabase data,Admin admin) {

	    Label title = new Label("Register Student");

	    title.setStyle(
	            "-fx-font-size: 24px; -fx-font-weight: bold;"
	    );

	    Label firstNameLabel = new Label("First Name");
	    TextField firstNameField = new TextField();
	    firstNameField.setPromptText("Enter first name");

	    Label lastNameLabel = new Label("Last Name");
	    TextField lastNameField = new TextField();
	    lastNameField.setPromptText("Enter last name");

	    Label usernameLabel = new Label("Username");
	    TextField usernameField = new TextField();
	    usernameField.setPromptText("Enter username");

	    Label passwordLabel = new Label("Password");
	    PasswordField passwordField = new PasswordField();
	    passwordField.setPromptText("Enter password");

	    Button registerButton =
	            new Button("Create Student");

	    Button backButton =
	            new Button("Back");

	    Label message = new Label();

	    registerButton.setOnAction(e -> {

	        String firstName = firstNameField.getText().trim();
	        String lastName = lastNameField.getText().trim();
	        String username = usernameField.getText().trim();
	        String password = passwordField.getText();

	        if (firstName.isEmpty()
	                || lastName.isEmpty()
	                || username.isEmpty()
	                || password.isEmpty()) {

	            message.setText(
	                    "Please complete all fields."
	            );

	            return;
	        }

	        boolean usernameExists = false;

	        for (Student student : data.getStudentDirectory()) {

	            if (student.getUsername().equalsIgnoreCase(username)) {

	                usernameExists = true;
	                break;
	            }
	        }

	        if (usernameExists) {

	            message.setText(
	                    "That username is already in use."
	            );

	            return;
	        }

	        new Student(
	                firstName,
	                lastName,
	                username,
	                password,
	                data.getStudentDirectory()
	        );

	        DataManager.saveCourseDatabase(data);

	        message.setText(
	                "Student created successfully!"
	        );

	        firstNameField.clear();
	        lastNameField.clear();
	        usernameField.clear();
	        passwordField.clear();
	    });

	    backButton.setOnAction(
	            e -> showAdminDashboard(stage, data, admin)
	    );

	    VBox layout = new VBox(
	            12,
	            title,
	            firstNameLabel,
	            firstNameField,
	            lastNameLabel,
	            lastNameField,
	            usernameLabel,
	            usernameField,
	            passwordLabel,
	            passwordField,
	            registerButton,
	            message,
	            backButton
	    );

	    layout.setAlignment(Pos.CENTER);

	    layout.setMaxWidth(350);

	    StackPane root = new StackPane(layout);

	    Scene scene = new Scene(root, 700, 500);

	    stage.setScene(scene);
	}
	private void showAdminFullCourses(Stage stage,CourseDatabase data,Admin admin) {

	    Label title = new Label("Full Courses");

	    title.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    VBox courses = new VBox(12);
	    courses.setPadding(new Insets(10));

	    boolean foundFullCourse = false;

	    for (Course course : data.getCourseDirectory()) {

	        if (course.isFull()) {

	            foundFullCourse = true;
	            VBox courseCard = createCourseCard(course);
	            courses.getChildren().add(courseCard);
	        }
	    }

	    if (!foundFullCourse) {

	        Label emptyMessage = new Label(
	                "There are currently no full courses."
	        );

	        emptyMessage.setStyle(
	                "-fx-font-size: 16px;"
	        );

	        courses.getChildren().add(emptyMessage);
	    }

	    ScrollPane scrollPane = new ScrollPane(courses);

	    scrollPane.setFitToWidth(true);
	    scrollPane.setPrefHeight(450);

	    Button backButton =
	            new Button("Back to Dashboard");

	    backButton.setOnAction(e ->
	            showAdminDashboard(stage, data, admin)
	    );

	    VBox layout = new VBox(
	            15,
	            title,
	            scrollPane,
	            backButton
	    );

	    layout.setPadding(new Insets(25));
	    layout.setAlignment(Pos.TOP_CENTER);

	    VBox.setVgrow(scrollPane, Priority.ALWAYS);

	    Scene scene = new Scene(layout, 750, 650);

	    stage.setScene(scene);
	}	
	
	private void showCreateCourse(Stage stage,CourseDatabase data,Admin admin) {

	    Label title = new Label("Create Course");

	    title.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    Label courseNameLabel =
	            new Label("Course Name");

	    TextField courseNameField =
	            new TextField();

	    courseNameField.setPromptText(
	            "Enter course name"
	    );

	    Label courseIDLabel =
	            new Label("Course ID");

	    TextField courseIDField =
	            new TextField();

	    courseIDField.setPromptText(
	            "Enter course ID"
	    );

	    Label capacityLabel =
	            new Label("Capacity");

	    TextField capacityField =
	            new TextField();

	    capacityField.setPromptText(
	            "Enter capacity"
	    );

	    Label instructorLabel =
	            new Label("Instructor");

	    TextField instructorField =
	            new TextField();

	    instructorField.setPromptText(
	            "Enter instructor"
	    );

	    Label sectionLabel =
	            new Label("Section");

	    TextField sectionField =
	            new TextField();

	    sectionField.setPromptText(
	            "Enter section number"
	    );

	    Label locationLabel =
	            new Label("Location");

	    TextField locationField =
	            new TextField();

	    locationField.setPromptText(
	            "Enter location"
	    );

	    Button createCourseButton =
	            new Button("Create Course");

	    Button backButton =
	            new Button("Back");

	    Label message = new Label();

	    createCourseButton.setOnAction(e -> {

	        String courseName =
	                courseNameField.getText().trim();

	        String courseID =
	                courseIDField.getText().trim();

	        String capacityText =
	                capacityField.getText().trim();

	        String instructor =
	                instructorField.getText().trim();

	        String sectionText =
	                sectionField.getText().trim();

	        String location =
	                locationField.getText().trim();

	        if (courseName.isEmpty()
	                || courseID.isEmpty()
	                || capacityText.isEmpty()
	                || instructor.isEmpty()
	                || sectionText.isEmpty()
	                || location.isEmpty()) {

	            message.setText(
	                    "Please complete all fields."
	            );

	            return;
	        }

	        try {

	            int capacity =
	                    Integer.parseInt(capacityText);

	            int section =
	                    Integer.parseInt(sectionText);

	            if (capacity <= 0) {

	                message.setText(
	                        "Capacity must be greater than 0."
	                );

	                return;
	            }

	            if (section <= 0) {

	                message.setText(
	                        "Section must be greater than 0."
	                );

	                return;
	            }

	            Admin adminCopy = admin;

	            adminCopy.createCourse(
	                    courseName,
	                    courseID,
	                    capacity,
	                    instructor,
	                    section,
	                    location,
	                    data
	            );

	            DataManager.saveCourseDatabase(data);

	            message.setText(
	                    "Course created successfully!"
	            );

	            courseNameField.clear();
	            courseIDField.clear();
	            capacityField.clear();
	            instructorField.clear();
	            sectionField.clear();
	            locationField.clear();

	        } catch (NumberFormatException ex) {

	            message.setText(
	                    "Capacity and section must be numbers."
	            );
	        }
	    });

	    backButton.setOnAction(e ->
	            showAdminDashboard(
	                    stage,
	                    data,
	                    admin
	            )
	    );

	    VBox layout = new VBox(
	            12,
	            title,
	            courseNameLabel,
	            courseNameField,
	            courseIDLabel,
	            courseIDField,
	            capacityLabel,
	            capacityField,
	            instructorLabel,
	            instructorField,
	            sectionLabel,
	            sectionField,
	            locationLabel,
	            locationField,
	            createCourseButton,
	            message,
	            backButton
	    );

	    layout.setAlignment(Pos.CENTER);
	    layout.setMaxWidth(400);

	    StackPane root =
	            new StackPane(layout);

	    Scene scene =
	            new Scene(root, 700, 650);

	    stage.setScene(scene);
	}
	
	private void showDeleteCourse(Stage stage,CourseDatabase data,Admin admin) {

	    Label title = new Label("Delete Course");

	    title.setStyle(
	            "-fx-font-size: 26px; -fx-font-weight: bold;"
	    );

	    Label subtitle = new Label(
	            "Select a course to view its details."
	    );

	    subtitle.setStyle(
	            "-fx-font-size: 15px;"
	    );

	    VBox courses = new VBox(12);

	    for (Course course : data.getCourseDirectory()) {

	        VBox courseCard = createCourseCard(course);

	        Button selectButton =
	                new Button("Select Course");

	        selectButton.setOnAction(e ->
	                showDeleteCourseConfirmation(
	                        stage,
	                        data,
	                        admin,
	                        course
	                )
	        );

	        courseCard.getChildren().add(selectButton);

	        courses.getChildren().add(courseCard);
	    }

	    if (courses.getChildren().isEmpty()) {

	        Label emptyMessage = new Label(
	                "There are currently no courses."
	        );

	        emptyMessage.setStyle(
	                "-fx-font-size: 16px;"
	        );

	        courses.getChildren().add(emptyMessage);
	    }

	    ScrollPane scrollPane =
	            createCourseListPane(courses);

	    Button backButton =
	            new Button("Back to Dashboard");

	    backButton.setOnAction(e ->
	            showAdminDashboard(
	                    stage,
	                    data,
	                    admin
	            )
	    );

	    VBox layout = new VBox(
	            15,
	            title,
	            subtitle,
	            scrollPane,
	            backButton
	    );

	    layout.setPadding(new Insets(25));
	    layout.setAlignment(Pos.TOP_CENTER);

	    Scene scene = new Scene(layout, 750, 650);

	    stage.setScene(scene);
	}
	
	private void showDeleteCourseConfirmation(
	        Stage stage,
	        CourseDatabase data,
	        Admin admin,
	        Course course) {

	    Label title = new Label(
	            "Delete Course"
	    );

	    title.setStyle(
	            "-fx-font-size: 26px; -fx-font-weight: bold;"
	    );

	    Label warning = new Label(
	            "Are you sure you want to delete this course?"
	    );

	    warning.setStyle(
	            "-fx-font-size: 16px;"
	    );

	    VBox courseCard =
	            createCourseCard(course);

	    Label consequence = new Label(
	            "This will delete all sections of "
	            + course.getCourseName()
	            + " and remove the course from students' schedules."
	    );

	    consequence.setWrapText(true);
	    consequence.setMaxWidth(500);

	    Button deleteCourseButton =
	            new Button("Delete Course");

	    Button cancelButton =
	            new Button("Cancel");

	    Label message = new Label();

	    deleteCourseButton.setOnAction(e -> {

	        admin.deleteCourse(
	                course.getCourseName(),
	                data
	        );

	        DataManager.saveCourseDatabase(data);

	        message.setText(
	                "Course deleted successfully."
	        );

	        showDeleteCourse(
	                stage,
	                data,
	                admin
	        );
	    });

	    cancelButton.setOnAction(e ->
	            showDeleteCourse(
	                    stage,
	                    data,
	                    admin
	            )
	    );

	    VBox buttons = new VBox(
	            10,
	            deleteCourseButton,
	            cancelButton
	    );

	    buttons.setAlignment(Pos.CENTER);

	    VBox layout = new VBox(
	            15,
	            title,
	            warning,
	            courseCard,
	            consequence,
	            buttons,
	            message
	    );

	    layout.setAlignment(Pos.CENTER);
	    layout.setMaxWidth(500);

	    StackPane root =
	            new StackPane(layout);

	    Scene scene =
	            new Scene(root, 800, 650);

	    stage.setScene(scene);
	}
	
	private void showDeleteSection(Stage stage,CourseDatabase data,Admin admin) {

	    Label title = new Label("Delete Section");

	    title.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    Label subtitle = new Label(
	            "Select a section to view its details."
	    );

	    subtitle.setStyle(
	            "-fx-font-size: 16px;"
	    );

	    VBox courses = new VBox(12);

	    for (Course course : data.getCourseDirectory()) {

	        VBox courseCard = createCourseCard(course);

	        Button selectButton =
	                new Button("Select Section " + course.getSection());

	        selectButton.setOnAction(e ->
	                showDeleteSectionConfirmation(
	                        stage,
	                        data,
	                        admin,
	                        course
	                )
	        );

	        courseCard.getChildren().add(selectButton);

	        courses.getChildren().add(courseCard);
	    }

	    if (courses.getChildren().isEmpty()) {

	        Label emptyMessage = new Label(
	                "There are currently no courses."
	        );

	        emptyMessage.setStyle(
	                "-fx-font-size: 16px;"
	        );

	        courses.getChildren().add(emptyMessage);
	    }

	    ScrollPane scrollPane =
	            createCourseListPane(courses);

	    Button backButton =
	            new Button("Back to Dashboard");

	    backButton.setOnAction(e ->
	            showAdminDashboard(
	                    stage,
	                    data,
	                    admin
	            )
	    );

	    VBox layout = new VBox(
	            15,
	            title,
	            subtitle,
	            scrollPane,
	            backButton
	    );

	    layout.setPadding(new Insets(25));
	    layout.setAlignment(Pos.TOP_CENTER);

	    Scene scene = new Scene(layout, 750, 650);

	    stage.setScene(scene);	}
	
	private void showDeleteSectionConfirmation(
	        Stage stage,
	        CourseDatabase data,
	        Admin admin,
	        Course course) {

	    Label title = new Label(
	            "Delete Section"
	    );

	    title.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    Label warning = new Label(
	            "Are you sure you want to delete this section?"
	    );

	    warning.setStyle(
	            "-fx-font-size: 16px;"
	    );

	    VBox courseCard =
	            createCourseCard(course);

	    Label consequence = new Label(
	            "Only Section "
	            + course.getSection()
	            + " of "
	            + course.getCourseName()
	            + " will be deleted."
	    );

	    consequence.setWrapText(true);
	    consequence.setMaxWidth(500);

	    Button deleteButton =
	            new Button("Delete Section");

	    Button cancelButton =
	            new Button("Cancel");

	    deleteButton.setOnAction(e -> {

	        /*
	         * I use the course's identifying information
	         * to delete this specific section.
	         */
	    	admin.deleteSection( course, data);

	        DataManager.saveCourseDatabase(data);

	        showDeleteSection(
	                stage,
	                data,
	                admin
	        );
	    });

	    cancelButton.setOnAction(e ->
	            showDeleteSection(
	                    stage,
	                    data,
	                    admin
	            )
	    );

	    VBox buttons = new VBox(
	            10,
	            deleteButton,
	            cancelButton
	    );

	    buttons.setAlignment(Pos.CENTER);

	    VBox layout = new VBox(
	            15,
	            title,
	            warning,
	            courseCard,
	            consequence,
	            buttons
	    );

	    layout.setAlignment(Pos.CENTER);
	    layout.setMaxWidth(500);

	    StackPane root =
	            new StackPane(layout);

	    Scene scene =
	            new Scene(root, 800, 650);

	    stage.setScene(scene);
	}
	
	private void showEditCourse(Stage stage,CourseDatabase data,Admin admin) {

	    Label title = new Label("Edit Course");
	    title.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    Label subtitle = new Label(
	            "Select a course to edit."
	    );
	    subtitle.setStyle("-fx-font-size: 16px;");

	    VBox courses = new VBox(12);
	    courses.setPadding(new Insets(10));

	    for (Course course : data.getCourseDirectory()) {

	        VBox courseCard = createCourseCard(course);

	        Button selectButton =
	                new Button("Select Course");

	        selectButton.setOnAction(e ->
	                showEditCourseForm(
	                        stage,
	                        data,
	                        admin,
	                        course
	                )
	        );

	        courseCard.getChildren().add(selectButton);
	        courses.getChildren().add(courseCard);
	    }

	    if (courses.getChildren().isEmpty()) {
	        Label emptyMessage = new Label(
	                "There are currently no courses."
	        );
	        emptyMessage.setStyle("-fx-font-size: 16px;");
	        courses.getChildren().add(emptyMessage);
	    }

	    ScrollPane scrollPane =
	            createCourseListPane(courses);

	    Button backButton =
	            new Button("Back to Dashboard");

	    backButton.setOnAction(e ->
	            showAdminDashboard(
	                    stage,
	                    data,
	                    admin
	            )
	    );

	    VBox layout = new VBox(
	            15,
	            title,
	            subtitle,
	            scrollPane,
	            backButton
	    );

	    layout.setPadding(new Insets(25));
	    layout.setAlignment(Pos.TOP_CENTER);

	    Scene scene =
	            new Scene(layout, 750, 650);

	    stage.setScene(scene);
	}
	
	private void showEditCourseForm(Stage stage, CourseDatabase data, Admin admin, Course course) {

	    Label title = new Label("Edit Course");
	    title.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    Label courseNameLabel = new Label(
	            "Course Name: " + course.getCourseName()
	    );

	    Label courseIDLabel = new Label(
	            "Course ID: " + course.getCourseID()
	    );

	    Label capacityLabel = new Label("Maximum Capacity");
	    TextField capacityField = new TextField(
	            String.valueOf(course.getCapacity())
	    );

	    Label instructorLabel = new Label("Instructor");
	    TextField instructorField = new TextField(
	            course.getInstructor()
	    );

	    Label sectionLabel = new Label("Section");
	    TextField sectionField = new TextField(
	            String.valueOf(course.getSection())
	    );

	    Label locationLabel = new Label("Location");
	    TextField locationField = new TextField(
	            course.getLocation()
	    );

	    Label message = new Label();

	    Button saveButton =
	            new Button("Save Changes");

	    Button cancelButton =
	            new Button("Back");

	    saveButton.setOnAction(e -> {

	        String capacityText =
	                capacityField.getText().trim();

	        String instructor =
	                instructorField.getText().trim();

	        String sectionText =
	                sectionField.getText().trim();

	        String location =
	                locationField.getText().trim();

	        if (capacityText.isEmpty()
	                || instructor.isEmpty()
	                || sectionText.isEmpty()
	                || location.isEmpty()) {

	            message.setText(
	                    "Please complete all fields."
	            );
	            return;
	        }

	        try {

	            int capacity =
	                    Integer.parseInt(capacityText);

	            int section =
	                    Integer.parseInt(sectionText);

	            if (capacity <= 0) {
	                message.setText(
	                        "Capacity must be greater than zero."
	                );
	                return;
	            }

	            if (section <= 0) {
	                message.setText(
	                        "Section must be greater than zero."
	                );
	                return;
	            }

	            if (capacity < course.getCurrentEnrollment()) {
	                message.setText(
	                        "Capacity cannot be less than current enrollment."
	                );
	                return;
	            }

	            course.setCapacity(capacity);
	            course.setInstructor(instructor);
	            course.setSection(section);
	            course.setLocation(location);

	            DataManager.saveCourseDatabase(data);

	            message.setText(
	                    "Course updated successfully!"
	            );

	        } catch (NumberFormatException ex) {

	            message.setText(
	                    "Capacity and section must be numbers."
	            );
	        }
	    });

	    cancelButton.setOnAction(e ->
	            showEditCourse(
	                    stage,
	                    data,
	                    admin
	            )
	    );

	    VBox layout = new VBox(
	            12,
	            title,
	            courseNameLabel,
	            courseIDLabel,
	            capacityLabel,
	            capacityField,
	            instructorLabel,
	            instructorField,
	            sectionLabel,
	            sectionField,
	            locationLabel,
	            locationField,
	            saveButton,
	            message,
	            cancelButton
	    );

	    layout.setAlignment(Pos.CENTER);
	    layout.setMaxWidth(400);

	    StackPane root =
	            new StackPane(layout);

	    Scene scene =
	            new Scene(root, 750, 650);

	    stage.setScene(scene);
	}
	
	private void showStudentsInCourse(
	        Stage stage,
	        CourseDatabase data,
	        Admin admin) {

	    Label title = new Label("Students in a Course");
	    title.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    Label subtitle = new Label(
	            "Select a course to view its enrolled students."
	    );
	    subtitle.setStyle("-fx-font-size: 16px;");

	    VBox courses = new VBox(12);
	    courses.setPadding(new Insets(10));

	    for (Course course : data.getCourseDirectory()) {

	        VBox courseCard = createCourseCard(course);

	        Button selectButton =
	                new Button("View Students");

	        selectButton.setOnAction(e ->
	                showEnrolledStudents(
	                        stage,
	                        data,
	                        admin,
	                        course
	                )
	        );

	        courseCard.getChildren().add(selectButton);
	        courses.getChildren().add(courseCard);
	    }

	    if (courses.getChildren().isEmpty()) {

	        Label emptyMessage = new Label(
	                "There are currently no courses."
	        );

	        emptyMessage.setStyle(
	                "-fx-font-size: 16px;"
	        );

	        courses.getChildren().add(emptyMessage);
	    }

	    ScrollPane scrollPane =
	            createCourseListPane(courses);

	    Button backButton =
	            new Button("Back to Dashboard");

	    backButton.setOnAction(e ->
	            showAdminDashboard(
	                    stage,
	                    data,
	                    admin
	            )
	    );

	    VBox layout = new VBox(
	            15,
	            title,
	            subtitle,
	            scrollPane,
	            backButton
	    );

	    layout.setPadding(new Insets(25));
	    layout.setAlignment(Pos.TOP_CENTER);

	    Scene scene =
	            new Scene(layout, 750, 650);

	    stage.setScene(scene);
	}
	
	private void showEnrolledStudents(Stage stage, CourseDatabase data, Admin admin, Course course) {

	    Label title = new Label(
	            "Enrolled Students"
	    );

	    title.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    Label courseTitle = new Label(
	            course.getCourseName()
	            + " | "
	            + course.getCourseID()
	            + " | Section "
	            + course.getSection()
	    );

	    courseTitle.setStyle(
	            "-fx-font-size: 18px; -fx-font-weight: bold;"
	    );

	    Label enrollment = new Label(
	            "Enrollment: "
	            + course.getCurrentEnrollment()
	            + "/"
	            + course.getCapacity()
	    );

	    VBox students = new VBox(10);
	    students.setPadding(new Insets(10));

	    if (course.getEnrolled().isEmpty()) {

	        Label emptyMessage = new Label(
	                "No students are currently enrolled in this course."
	        );

	        emptyMessage.setStyle(
	                "-fx-font-size: 16px;"
	        );

	        students.getChildren().add(emptyMessage);

	    } else {

	    	for (Student student : course.getEnrolled()) {

	    	    VBox studentCard = new VBox(5);
	    	    studentCard.getStyleClass().add("student-card");

	    	    Label nameLabel = new Label(
	    	            student.getFullName()
	    	    );

	    	    nameLabel.getStyleClass().add("student-card-name");

	    	    Label usernameLabel = new Label(
	    	            "Username: " + student.getUsername()
	    	    );

	    	    usernameLabel.getStyleClass().add("student-card-username");

	    	    studentCard.getChildren().addAll(
	    	            nameLabel,
	    	            usernameLabel
	    	    );

	    	    studentCard.setPadding(
	    	            new Insets(12)
	    	    );

	    	    students.getChildren().add(studentCard);
	    	}
	    }

	    ScrollPane scrollPane =
	            createCourseListPane(students);

	    Button backButton =
	            new Button("Back to Courses");

	    backButton.setOnAction(e ->
	            showStudentsInCourse(
	                    stage,
	                    data,
	                    admin
	            )
	    );

	    VBox layout = new VBox(
	            15,
	            title,
	            courseTitle,
	            enrollment,
	            scrollPane,
	            backButton
	    );

	    layout.setPadding(new Insets(25));
	    layout.setAlignment(Pos.TOP_CENTER);

	    Scene scene =
	            new Scene(layout, 750, 650);

	    stage.setScene(scene);
	}
	
	private void showStudentCourses(
	        Stage stage,
	        CourseDatabase data,
	        Admin admin) {

	    Label title = new Label("View Student's Courses");
	    title.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    Label instruction = new Label(
	            "Enter the student's full name:"
	    );
	    instruction.setStyle("-fx-font-size: 16px;");

	    TextField studentNameField = new TextField();
	    studentNameField.setPromptText("First name Last name");
	    studentNameField.setMaxWidth(350);

	    Button searchButton = new Button("View Courses");
	    Label message = new Label();

	    VBox courses = new VBox(12);
	    courses.setPadding(new Insets(10));

	    searchButton.setOnAction(e -> {

	        String fullName =
	                studentNameField.getText().trim();

	        courses.getChildren().clear();
	        message.setText("");

	        if (fullName.isEmpty()) {
	            message.setText(
	                    "Please enter a student's name."
	            );
	            return;
	        }

	        Student foundStudent = null;

	        for (Student student : data.getStudentDirectory()) {
	            if (student.getFullName()
	                    .equalsIgnoreCase(fullName)) {

	                foundStudent = student;
	                break;
	            }
	        }

	        if (foundStudent == null) {
	            message.setText(
	                    "Student not found."
	            );
	            return;
	        }

	        Label studentLabel = new Label(
	                "Courses for " + foundStudent.getFullName()
	        );

	        studentLabel.setStyle(
	                "-fx-font-size: 20px; -fx-font-weight: bold;"
	        );

	        courses.getChildren().add(studentLabel);

	        if (foundStudent.getSchedule().isEmpty()) {

	            Label emptyMessage = new Label(
	                    "This student is not currently registered "
	                    + "for any courses."
	            );

	            emptyMessage.setStyle(
	                    "-fx-font-size: 16px;"
	            );

	            courses.getChildren().add(emptyMessage);

	        } else {

	            for (Course course : foundStudent.getSchedule()) {

	                VBox courseCard =
	                        createCourseCard(course);

	                courses.getChildren().add(courseCard);
	            }
	        }
	    });

	    ScrollPane scrollPane =
	            createCourseListPane(courses);

	    Button backButton =
	            new Button("Back to Dashboard");

	    backButton.setOnAction(e ->
	            showAdminDashboard(
	                    stage,
	                    data,
	                    admin
	            )
	    );

	    VBox layout = new VBox(
	            15,
	            title,
	            instruction,
	            studentNameField,
	            searchButton,
	            message,
	            scrollPane,
	            backButton
	    );

	    layout.setPadding(new Insets(25));
	    layout.setAlignment(Pos.TOP_CENTER);

	    Scene scene =
	            new Scene(layout, 750, 650);

	    stage.setScene(scene);
	}
	
	private void showStudentDashboard(
	        Stage stage,
	        CourseDatabase data,
	        Student student) {

	    Label welcome = new Label(
	            "Welcome, " + student.getFullName() + "!"
	    );

	    welcome.getStyleClass().add("dashboard-title");

	    Label subtitle = new Label(
	            "Student Dashboard"
	    );

	    subtitle.getStyleClass().add("dashboard-subtitle");


	    Button viewAllButton =
	            new Button("View All Courses");

	    Button viewOpenButton =
	            new Button("View Open Courses");

	    Button registerButton =
	            new Button("Register for a Course");

	    Button withdrawButton =
	            new Button("Withdraw from a Course");

	    Button myCoursesButton =
	            new Button("My Courses");

	    Button logoutButton =
	            new Button("Logout");


	    Button[] buttons = {
	            viewAllButton,
	            viewOpenButton,
	            registerButton,
	            withdrawButton,
	            myCoursesButton
	    };


	    for (Button button : buttons) {

	        button.setPrefWidth(270);
	        button.setPrefHeight(42);

	        button.getStyleClass().add("dashboard-button");
	    }


	    logoutButton.setPrefWidth(120);
	    logoutButton.setPrefHeight(36);

	    logoutButton.getStyleClass().add("text-button");


	    // Button actions

	    viewAllButton.setOnAction(e ->
	            showAllCourses(stage, data, student)
	    );

	    viewOpenButton.setOnAction(e ->
	            showOpenCourses(stage, data, student)
	    );

	    registerButton.setOnAction(e ->
	            showRegisterCourse(stage, data, student)
	    );

	    withdrawButton.setOnAction(e ->
	            showWithdrawCourse(stage, data, student)
	    );

	    myCoursesButton.setOnAction(e ->
	            showMyCourses(stage, data, student)
	    );

	    logoutButton.setOnAction(e ->
	            start(stage)
	    );


	    VBox menu = new VBox(
	            10,
	            viewAllButton,
	            viewOpenButton,
	            registerButton,
	            withdrawButton,
	            myCoursesButton
	    );

	    menu.setAlignment(Pos.CENTER);


	    VBox layout = new VBox(
	            18,
	            welcome,
	            subtitle,
	            menu,
	            logoutButton
	    );

	    layout.setPadding(new Insets(35));
	    layout.setAlignment(Pos.TOP_CENTER);

	    Scene scene =
	            new Scene(layout, 750, 650);

	    stage.setScene(scene);
	}	private void showAllCourses(Stage stage, CourseDatabase data, Student student) {

	    Label title = new Label("All Courses");
	    title.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    Label subtitle = new Label(
	            "Browse all courses offered by the university."
	    );
	    subtitle.setStyle("-fx-font-size: 16px;");

	    VBox courses = new VBox(12);
	    courses.setPadding(new Insets(10));

	    for (Course course : data.getCourseDirectory()) {

	        VBox courseCard = createCourseCard(course);

	        courses.getChildren().add(courseCard);
	    }

	    ScrollPane scrollPane = new ScrollPane(courses);
	    scrollPane.setFitToWidth(true);
	    scrollPane.setFitToHeight(false);
	    scrollPane.setPrefHeight(450);

	    Button backButton = new Button("Back to Dashboard");

	    backButton.setOnAction(e ->
	            showStudentDashboard(stage, data, student)
	    );

	    VBox layout = new VBox(
	            15,
	            title,
	            subtitle,
	            scrollPane,
	            backButton
	    );

	    layout.setPadding(new Insets(25));
	    layout.setAlignment(Pos.TOP_CENTER);

	    VBox.setVgrow(scrollPane, Priority.ALWAYS);

	    Scene scene = new Scene(layout, 750, 650);

	    stage.setScene(scene);
	}
	private void showOpenCourses(Stage stage, CourseDatabase data, Student student) {

	    Label title = new Label("Open Courses");
	    title.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    Label subtitle = new Label(
	            "Courses currently accepting new students."
	    );
	    subtitle.setStyle("-fx-font-size: 16px;");

	    VBox courses = new VBox(12);
	    courses.setPadding(new Insets(10));

	    boolean foundOpenCourse = false;

	    for (Course course : data.getCourseDirectory()) {

	        if (!course.isFull()) {
	            foundOpenCourse = true;
	            VBox courseCard = createCourseCard(course);
	            courses.getChildren().add(courseCard);
	        }
	    }

	    if (!foundOpenCourse) {

	        Label noCourses = new Label(
	                "There are currently no open courses."
	        );

	        noCourses.setStyle(
	                "-fx-font-size: 16px;"
	        );

	        courses.getChildren().add(noCourses);
	    }

	    ScrollPane scrollPane = new ScrollPane(courses);
	    scrollPane.setFitToWidth(true);
	    scrollPane.setFitToHeight(false);

	    Button backButton = new Button("Back to Dashboard");

	    backButton.setOnAction(e ->
	            showStudentDashboard(stage, data, student));

	    VBox layout = new VBox(
	            15,
	            title,
	            subtitle,
	            scrollPane,
	            backButton
	    );

	    layout.setPadding(new Insets(25));
	    layout.setAlignment(Pos.TOP_CENTER);

	    VBox.setVgrow(scrollPane, Priority.ALWAYS);

	    Scene scene = new Scene(layout, 750, 650);

	    stage.setScene(scene);
	}
	private void showRegisterCourse(
	        Stage stage,
	        CourseDatabase data,
	        Student student) {

	    Label title = new Label("Register for a Course");
	    title.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    Label subtitle = new Label(
	            "Enter the course name and section you would like to register for."
	    );
	    subtitle.setStyle("-fx-font-size: 16px;");

	    Label courseNameLabel = new Label("Course Name");

	    TextField courseNameField = new TextField();
	    courseNameField.setPromptText("e.g. Fundamental Algorithms");
	    courseNameField.setMaxWidth(300);

	    Label sectionLabel = new Label("Section");

	    TextField sectionField = new TextField();
	    sectionField.setPromptText("e.g. 1");
	    sectionField.setMaxWidth(300);

	    Button findButton = new Button("Find Course");
	    Button registerButton = new Button("Register");
	    Button backButton = new Button("Back to Dashboard");

	    registerButton.setDisable(true);

	    Label courseInfo = new Label();
	    courseInfo.setWrapText(true);
	    courseInfo.setStyle("-fx-font-size: 16px;");

	    Label message = new Label();
	    message.setWrapText(true);

	    final Course[] selectedCourse = {null};

	    findButton.setOnAction(e -> {

	        String courseName = courseNameField.getText().trim();
	        String sectionText = sectionField.getText().trim();

	        if (courseName.isEmpty() || sectionText.isEmpty()) {
	            message.setText(
	                    "Please enter both a course name and section."
	            );
	            return;
	        }

	        int section;

	        try {
	            section = Integer.parseInt(sectionText);
	        } catch (NumberFormatException ex) {
	            message.setText(
	                    "Section must be a number."
	            );
	            return;
	        }

	        Course course = data.findCourse(courseName, section);

	        if (course == null) {
	            selectedCourse[0] = null;
	            registerButton.setDisable(true);
	            courseInfo.setText("");
	            message.setText("Course not found.");
	            return;
	        }

	        selectedCourse[0] = course;

	        courseInfo.setText(
	                "Course: " + course.getCourseName()
	                + "\nCourse ID: " + course.getCourseID()
	                + "\nSection: " + course.getSection()
	                + "\nInstructor: " + course.getInstructor()
	                + "\nLocation: " + course.getLocation()
	                + "\nEnrollment: "
	                + course.getCurrentEnrollment()
	                + " / "
	                + course.getCapacity()
	        );

	        if (course.isFull()) {

	            registerButton.setDisable(true);

	            message.setText(
	                    "This course is currently full."
	            );

	        } else if (course.checkStudent(student)) {

	            registerButton.setDisable(true);

	            message.setText(
	                    "You are already enrolled in this course."
	            );

	        } else {

	            registerButton.setDisable(false);

	            message.setText(
	                    "This course is available for registration."
	            );
	        }
	    });

	    registerButton.setOnAction(e -> {

	        Course course = selectedCourse[0];

	        if (course == null) {
	            message.setText(
	                    "Please find a course first."
	            );
	            return;
	        }

	        course.enroll(student);

	        if (course.checkStudent(student)) {

	            message.setText(
	                    "Successfully registered for "
	                    + course.getCourseName()
	                    + ", Section "
	                    + course.getSection()
	                    + "."
	            );

	            registerButton.setDisable(true);

	            courseInfo.setText(
	                    "Course: " + course.getCourseName()
	                    + "\nCourse ID: " + course.getCourseID()
	                    + "\nSection: " + course.getSection()
	                    + "\nInstructor: " + course.getInstructor()
	                    + "\nLocation: " + course.getLocation()
	                    + "\nEnrollment: "
	                    + course.getCurrentEnrollment()
	                    + " / "
	                    + course.getCapacity()
	            );

	            DataManager.saveCourseDatabase(data);

	        } else {

	            message.setText(
	                    "Registration was unsuccessful."
	            );
	        }
	    });

	    backButton.setOnAction(e ->
	            showStudentDashboard(stage, data, student)
	    );

	    VBox layout = new VBox(
	            12,
	            title,
	            subtitle,
	            courseNameLabel,
	            courseNameField,
	            sectionLabel,
	            sectionField,
	            findButton,
	            courseInfo,
	            registerButton,
	            message,
	            backButton
	    );

	    layout.setPadding(new Insets(25));
	    layout.setAlignment(Pos.TOP_CENTER);

	    Scene scene = new Scene(layout, 750, 650);

	    stage.setScene(scene);
	}
	//lets make this in the same format as the other name cards and scrollable as well.
	private void showMyCourses(
	        Stage stage,
	        CourseDatabase data,
	        Student student) {

	    Label title = new Label("My Courses");

	    title.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    VBox courses = new VBox(12);
	    courses.setPadding(new Insets(10));

	    if (student.getSchedule().isEmpty()) {

	        Label emptyMessage = new Label(
	                "You are not registered for any courses."
	        );

	        emptyMessage.setStyle(
	                "-fx-font-size: 16px;"
	        );

	        courses.getChildren().add(emptyMessage);

	    } else {

	        for (Course course : student.getSchedule()) {

	            VBox courseCard = createCourseCard(course);

	            courses.getChildren().add(courseCard);
	        }
	    }

	    ScrollPane scrollPane =
	            createCourseListPane(courses);

	    Button backButton =
	            new Button("Back to Dashboard");

	    backButton.setOnAction(e ->
	            showStudentDashboard(
	                    stage,
	                    data,
	                    student
	            )
	    );

	    VBox layout = new VBox(
	            15,
	            title,
	            scrollPane,
	            backButton
	    );

	    layout.setPadding(new Insets(25));
	    layout.setAlignment(Pos.TOP_CENTER);

	    VBox.setVgrow(scrollPane, Priority.ALWAYS);

	    Scene scene = new Scene(layout, 750, 650);

	    stage.setScene(scene);
	}
	
	// add control message "to confirm that you want to withdraw from this course, click next, otherwise, click cancel"
	private void showWithdrawCourse(
	        Stage stage,
	        CourseDatabase data,
	        Student student) {

	    Label title = new Label("Withdraw from a Course");
	    title.setStyle(
	            "-fx-font-family: 'Arial';" +
	            "-fx-font-size: 28px;" +
	            "-fx-font-weight: bold;"
	    );

	    Label instruction = new Label(
	            "Select a course to view its details."
	    );
	    instruction.setStyle("-fx-font-size: 16px;");

	    VBox courses = new VBox(12);
	    courses.setPadding(new Insets(10));

	    if (student.getSchedule().isEmpty()) {

	        Label emptyMessage = new Label(
	                "You are not currently registered for any courses."
	        );
	        emptyMessage.setStyle("-fx-font-size: 16px;");
	        courses.getChildren().add(emptyMessage);

	    } else {

	        for (Course course : student.getSchedule()) {

	            VBox courseCard = createCourseCard(course);

	            Button selectButton =
	                    new Button("Select Course");

	            selectButton.setOnAction(e ->
	                    showWithdrawConfirmation(
	                            stage,
	                            data,
	                            student,
	                            course
	                    )
	            );

	            courseCard.getChildren().add(selectButton);
	            courses.getChildren().add(courseCard);
	        }
	    }

	    ScrollPane scrollPane =
	            createCourseListPane(courses);

	    Button backButton =
	            new Button("Back to Dashboard");

	    backButton.setOnAction(e ->
	            showStudentDashboard(
	                    stage,
	                    data,
	                    student
	            )
	    );

	    VBox layout = new VBox(
	            15,
	            title,
	            instruction,
	            scrollPane,
	            backButton
	    );

	    layout.setPadding(new Insets(25));
	    layout.setAlignment(Pos.TOP_CENTER);

	    Scene scene =
	            new Scene(layout, 750, 650);

	    stage.setScene(scene);
	}
	private void showWithdrawConfirmation(
	        Stage stage,
	        CourseDatabase data,
	        Student student,
	        Course course) {

	    Label title = new Label(
	            "Confirm Withdrawal"
	    );

	    title.setStyle(
	            "-fx-font-size: 28px; -fx-font-weight: bold;"
	    );

	    Label warning = new Label(
	            "Are you sure you want to withdraw from this course?"
	    );

	    warning.setStyle(
	            "-fx-font-size: 16px;"
	    );

	    VBox courseCard =
	            createCourseCard(course);

	    Label consequence = new Label(
	            "You will be withdrawn from Section "
	            + course.getSection()
	            + " of "
	            + course.getCourseName()
	            + "."
	    );

	    consequence.setWrapText(true);
	    consequence.setMaxWidth(500);

	    Button withdrawButton =
	            new Button("Withdraw from Course");

	    Button cancelButton =
	            new Button("Cancel");

	    withdrawButton.setOnAction(e -> {

	        course.withdraw(student);

	        DataManager.saveCourseDatabase(data);

	        showWithdrawCourse(
	                stage,
	                data,
	                student
	        );
	    });

	    cancelButton.setOnAction(e ->
	            showWithdrawCourse(
	                    stage,
	                    data,
	                    student
	            )
	    );

	    VBox buttons = new VBox(
	            10,
	            withdrawButton,
	            cancelButton
	    );

	    buttons.setAlignment(Pos.CENTER);

	    VBox layout = new VBox(
	            15,
	            title,
	            warning,
	            courseCard,
	            consequence,
	            buttons
	    );

	    layout.setAlignment(Pos.CENTER);
	    layout.setMaxWidth(500);

	    StackPane root =
	            new StackPane(layout);

	    Scene scene =
	            new Scene(root, 800, 650);

	    stage.setScene(scene);
	}
	
}
