package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GUI extends Application {

	final Insets insets = new Insets(10, 10, 10, 10);
	final int vgap = 8, hgap = 5;

	String loginID, loginLN, loginFN, loginM;
	int loginA;
	int w = 60;
	Stage stage;
	Scene loginScene, studentScene, adminScene, staffScene, instructorScene, passwordScene;
	List<Scene> sceneList = new ArrayList<Scene>();
	TextField usernameTF, queryTF, dropcrnTF, enrollcrnTF, idTF, crnTF;
	TextArea ta = new TextArea();
	Text msgText;
	PasswordField passwordField, passwordTF, confirmPasswordTF;
	Label loginMessage, changePassMessage;
	ResultSet queryResult, actualSet;
	BorderPane studentPane, staffPane, instructorPane, adminPane, expertPane, enrollPane, dropPane,
			adminViewStudentPane, instructorViewSectionsPane, deletePane;
	VBox passwordPane, msgLabel;
	TableView<Student> studentsTable;
	TableView<Instructor> instructorsTable;
	TableView<Staff> staffTable;
	TableView<Course> coursesTable;
	TableView<Section> sectionsTable;
	TableView<Grade> gradesTable;
	TableView<FA> faTable;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;

		ta.setEditable(false);
		ta.setFocusTraversable(false);

		createLoginScene();

		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		stage.setX(screenBounds.getWidth() / 2 - 282.5);
		stage.setY(screenBounds.getHeight() / 2 - 250);

		stage.setResizable(true);
		stage.setMinWidth(581);
		stage.setMinHeight(500);
		stage.setTitle("College System");
		stage.show();

	}

	private void createLoginScene() {
		GridPane loginPane = new GridPane();
		loginPane.setVgap(vgap);
		loginPane.setHgap(hgap);
		loginPane.setAlignment(Pos.CENTER);
		loginPane.setPadding(insets);

		loginMessage = new Label("Please enter your username and password");
		loginMessage.setStyle("-fx-font-style: italic");
		Label usernameLabel = new Label("Username: ");
		Label passwordLabel = new Label(" Password: ");
		int width = 225;
		usernameTF = new TextField();
		usernameTF.setPromptText("ID");
		usernameTF.setMinWidth(width);
		passwordField = new PasswordField();
		passwordField.setPromptText("password");
		passwordField.setMinWidth(width);
		Button loginButton = new Button("Login");
		loginButton.setMinWidth(width);
		loginButton.setOnAction(e -> loginButtonClicked());

		loginPane.add(loginMessage, 1, 0);
		loginPane.add(usernameLabel, 0, 1);
		loginPane.add(passwordLabel, 0, 2);
		loginPane.add(usernameTF, 1, 1);
		loginPane.add(passwordField, 1, 2);
		loginPane.add(loginButton, 1, 3);

		if (sceneList.size() == 0) {
			loginScene = new Scene(loginPane, 565, 500);
			loginScene.setOnKeyPressed(e -> loginEnterPressed(e));
			sceneList.add(loginScene);
			stage.setScene(loginScene);
		} else {
			loginScene = new Scene(loginPane, (sceneList.get((sceneList.size() - 1))).getWidth(),
					(sceneList.get((sceneList.size() - 1))).getHeight());
			loginScene.setOnKeyPressed(e -> loginEnterPressed(e));
			sceneList.add(loginScene);
			stage.setScene(loginScene);

		}

	}

	private void createStudentScene() {
		studentPane = new BorderPane();
		studentPane.setPadding(insets);
		VBox buttonsPane = new VBox(5);
		buttonsPane.setAlignment(Pos.CENTER);
		buttonsPane.setPadding(new Insets(8, 0, 0, 0));
		HBox buttonPane1 = new HBox(hgap);
		HBox buttonPane2 = new HBox(hgap);
		buttonPane1.setAlignment(Pos.CENTER);
		buttonPane2.setAlignment(Pos.CENTER);
		BorderPane headerPane = new BorderPane();
		headerPane.setPadding(new Insets(0, 0, 8, 0));
		HBox nameidPane = new HBox(8);
		nameidPane.setAlignment(Pos.CENTER);

		int width = 178;

		Button coursesButton = new Button("Current Sections");
		Button enrollButton = new Button("Enroll");
		Button dropButton = new Button("Drop");
		Button backButton = new Button("Sign out");
		Button gradesButton = new Button("Grades");
		Button faButton = new Button("Finacial Aid");
		Button changePasswordButton = new Button("Change Password");
		Button expertButton = new Button("Expert");
		Button viewDetailsButton = new Button("Details");
		viewDetailsButton.setMinWidth(w);
		backButton.setMinWidth(w);
		expertButton.setMinWidth(w);
		gradesButton.setMinWidth(width);
		coursesButton.setMinWidth(width);
		enrollButton.setMinWidth(width);
		dropButton.setMinWidth(width);
		faButton.setMinWidth(width);
		changePasswordButton.setMinWidth(width);

		TextFlow nameTextFlow = new TextFlow();
		TextFlow idTextFlow = new TextFlow();
		TextFlow majorTextFlow = new TextFlow();

		Text nameText = new Text("Name:");
		nameText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		Text studentNameText = new Text(" " + loginFN + " " + loginLN);
		nameTextFlow.getChildren().addAll(nameText, studentNameText);

		Text idText = new Text("ID:");
		idText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		Text studentIdText = new Text(" " + loginID);
		idTextFlow.getChildren().addAll(idText, studentIdText);

		Text majorText = new Text("Major:");
		majorText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		Text studentMajorText = new Text(" " + loginM);
		majorTextFlow.getChildren().addAll(majorText, studentMajorText);

		backButton.setOnAction(e -> backButtonClicked());
		backButton.setOnKeyPressed(e -> backEnterPressed(e));

		expertButton.setTextFill(Color.RED);
		expertButton.setOnAction(e -> createExpertScene());
		expertButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				createExpertScene();
		});

		viewDetailsButton.setOnAction(e -> {
			createStudentListTable("SELECT * FROM STUDENT WHERE studentID = \"" + loginID + "\"");
			studentPane.setCenter(studentsTable);
		});
		viewDetailsButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				createStudentListTable("SELECT * FROM STUDENT WHERE studentID = \"" + loginID + "\"");
				studentPane.setCenter(studentsTable);
			}
		});

		enrollButton.setOnAction(e -> student_enrollButtonClicked());
		enrollButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				student_enrollButtonClicked();
		});

		dropButton.setOnAction(e -> student_dropButtonClicked());
		dropButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				student_dropButtonClicked();
		});

		coursesButton.setOnAction(e -> student_currentSectionsButtonClicked());
		coursesButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				student_currentSectionsButtonClicked();
		});

		gradesButton.setOnAction(e -> {
			createGradesListTable(
					"Select isinstanceof, coursetitle, credits, term , sectionyear, grade from STUDENT_SECTION S , Section se , COURSE C WHERE se.isinstanceof = C.courseID AND S.crn = se.crn AND studentID =\""
							+ loginID + "\"");
			studentPane.setCenter(gradesTable);
		});
		gradesButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				createGradesListTable(
						"Select isinstanceof, coursetitle, credits, term , sectionyear, grade from STUDENT_SECTION S , Section se , COURSE C WHERE se.isinstanceof = C.courseID AND S.crn = se.crn AND studentID =\""
								+ loginID + "\"");
				studentPane.setCenter(gradesTable);
			}
		});

		faButton.setOnAction(e -> {
			createFAListTable(
					"select SS.studentID, SS.manager, S.firstname, S.lastname, SS.term, SS.contractyear, SS.percentage, SS.salary, SS.location from STUDENT_STAFF SS, STAFF S where SS.manager = S.staffID AND studentID=\""
							+ loginID + "\"");
			studentPane.setCenter(faTable);
		});
		faButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				createFAListTable(
						"select SS.studentID, SS.manager, S.firstname, S.lastname, SS.term, SS.contractyear, SS.percentage, SS.salary, SS.location from STUDENT_STAFF SS, STAFF S where SS.manager = S.staffID AND studentID=\""
								+ loginID + "\"");
				studentPane.setCenter(faTable);
			}
		});

		changePasswordButton.setOnAction(e -> createPasswordScene());
		changePasswordButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				createPasswordScene();
		});

		HBox backPane = new HBox(hgap);
		backPane.setAlignment(Pos.TOP_RIGHT);
		backPane.getChildren().addAll(viewDetailsButton, backButton);

		nameidPane.getChildren().addAll(nameTextFlow, idTextFlow, majorTextFlow);
		headerPane.setLeft(nameidPane);
		headerPane.setRight(backPane);
		buttonPane1.getChildren().addAll(coursesButton, enrollButton, dropButton);
		buttonPane2.getChildren().addAll(gradesButton, faButton, changePasswordButton);
		buttonsPane.getChildren().addAll(buttonPane1, buttonPane2);

		createStudentListTable("SELECT * FROM STUDENT WHERE studentID = \"" + loginID + "\"");

		studentPane.setBottom(buttonsPane);
		studentPane.setTop(headerPane);
		studentPane.setCenter(studentsTable);

		studentScene = new Scene(studentPane, (sceneList.get((sceneList.size() - 1))).getWidth(),
				(sceneList.get((sceneList.size() - 1))).getHeight());
		studentScene.setOnKeyPressed(e -> backspacePressed(e));
		sceneList.add(studentScene);
		stage.setScene(studentScene);
	}

	private void createAdminScene() {

		adminPane = new BorderPane();
		adminPane.setPadding(insets);
		BorderPane headerPane = new BorderPane();
		headerPane.setPadding(new Insets(0, 0, 8, 0));
		HBox nameidPane = new HBox(8);
		nameidPane.setAlignment(Pos.CENTER);
		GridPane buttonPane = new GridPane();
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.setPadding(new Insets(8, 0, 0, 0));
		buttonPane.setVgap(vgap);
		buttonPane.setHgap(hgap);

		Button backButton = new Button("Sign out");
		Button expertButton = new Button("Expert");
		Button viewDetailsButton = new Button("Details");
		backButton.setMinWidth(w);
		expertButton.setMinWidth(w);
		viewDetailsButton.setMinWidth(w);

		HBox backPane = new HBox(hgap);
		backPane.setAlignment(Pos.TOP_RIGHT);
		backPane.getChildren().addAll(expertButton, viewDetailsButton, backButton);

		backButton.setOnAction(e -> backButtonClicked());
		backButton.setOnKeyPressed(e -> backEnterPressed(e));

		viewDetailsButton.setOnAction(e -> admin_viewDetailsButtonClicked());
		viewDetailsButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				admin_viewDetailsButtonClicked();
		});

		expertButton.setTextFill(Color.RED);
		expertButton.setOnAction(e -> createExpertScene());
		expertButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				createExpertScene();
		});

		ObservableList<String> studentsList = FXCollections.observableArrayList("View All Students",
				"View Student's Courses", "Add Student", "Delete Student", "View FA Students");
		ComboBox<String> studentsComboBox = new ComboBox<>(studentsList);
		studentsComboBox.setValue("Student");
		studentsComboBox.setOnAction(e -> {

			if (studentsList.indexOf(studentsComboBox.getValue()) == 0)
				admin_viewAllStudentsButtonClicked();
			if (studentsList.indexOf(studentsComboBox.getValue()) == 1)
				admin_viewStudentButtonClicked();
			if (studentsList.indexOf(studentsComboBox.getValue()) == 2)
				admin_addStudentButtonClicked();
			if (studentsList.indexOf(studentsComboBox.getValue()) == 3)
				admin_deleteStudentButtonClicked();
			if (studentsList.indexOf(studentsComboBox.getValue()) == 4)
				admin_viewFAStudentsButtonClicked();
			Platform.runLater(() -> studentsComboBox.setValue("Student"));
		});

		ObservableList<String> instructorsList = FXCollections.observableArrayList("View All Instructors",
				"View Instructor's Courses", "Add Instructor", "Delete Instructor", "Change Instructor's Salary");
		ComboBox<String> instructorsComboBox = new ComboBox<>(instructorsList);
		instructorsComboBox.setValue("Instructor");
		instructorsComboBox.setOnAction(e -> {

			if (instructorsList.indexOf(instructorsComboBox.getValue()) == 0)
				admin_viewAllInstructorsButtonClicked();
			if (instructorsList.indexOf(instructorsComboBox.getValue()) == 1)
				admin_viewInstructorButtonClicked();
			if (instructorsList.indexOf(instructorsComboBox.getValue()) == 2)
				admin_addInstructorButtonClicked();
			if (instructorsList.indexOf(instructorsComboBox.getValue()) == 3)
				admin_deleteInstructorButtonClicked();
			if (instructorsList.indexOf(instructorsComboBox.getValue()) == 4)
				admin_changeSalaryButtonClicked();
			Platform.runLater(() -> instructorsComboBox.setValue("Instructor"));
		});

		ObservableList<String> coursesList = FXCollections.observableArrayList("View Courses", "View course's Sections",
				"Add Course", "Delete Course");
		ComboBox<String> coursesComboBox = new ComboBox<>(coursesList);
		coursesComboBox.setValue("Course");
		coursesComboBox.setOnAction(e -> {

			if (coursesList.indexOf(coursesComboBox.getValue()) == 0)
				admin_viewAllCoursesButtonClicked();
			if (coursesList.indexOf(coursesComboBox.getValue()) == 1)
				admin_viewCourseButtonClicked();
			if (coursesList.indexOf(coursesComboBox.getValue()) == 2)
				admin_addCourseButtonClicked();
			if (coursesList.indexOf(coursesComboBox.getValue()) == 3)
				admin_deleteCourseButtonClicked();
			Platform.runLater(() -> coursesComboBox.setValue("Course"));
		});

		ObservableList<String> sectionsList = FXCollections.observableArrayList("View All Section",
				"View Section's Students", "Add Section", "Delete Section");
		ComboBox<String> sectionsComboBox = new ComboBox<>(sectionsList);
		sectionsComboBox.setValue("Section");
		sectionsComboBox.setOnAction(e -> {

			if (sectionsList.indexOf(sectionsComboBox.getValue()) == 0)
				admin_viewAllSectionsButtonClicked();
			if (sectionsList.indexOf(sectionsComboBox.getValue()) == 1)
				admin_viewSectionsButtonClicked();
			if (sectionsList.indexOf(sectionsComboBox.getValue()) == 2)
				admin_addSectionButtonClicked();
			if (sectionsList.indexOf(sectionsComboBox.getValue()) == 3)
				admin_deleteSectionButtonClicked();
			Platform.runLater(() -> sectionsComboBox.setValue("Section"));
		});

		ObservableList<String> staffList = FXCollections.observableArrayList("View All Staff", "View Staff's Students",
				"Add Staff", "Delete Staff", "Change Staff's status", "Change Staff's Salary");
		ComboBox<String> staffComboBox = new ComboBox<>(staffList);
		staffComboBox.setValue("Staff");
		staffComboBox.setOnAction(e -> {

			if (staffList.indexOf(staffComboBox.getValue()) == 0)
				admin_viewAllStaffButtonClicked();
			if (staffList.indexOf(staffComboBox.getValue()) == 1)
				admin_viewStaffButtonClicked();
			if (staffList.indexOf(staffComboBox.getValue()) == 2)
				admin_addStaffButtonClicked();
			if (staffList.indexOf(staffComboBox.getValue()) == 3)
				admin_deleteStaffButtonClicked();
			if (staffList.indexOf(staffComboBox.getValue()) == 4)
				admin_changeAdminButtonClicked();
			if (staffList.indexOf(staffComboBox.getValue()) == 5)
				admin_changeSalaryButtonClicked();
			Platform.runLater(() -> staffComboBox.setValue("Staff"));
		});

		buttonPane.add(studentsComboBox, 0, 0);
		buttonPane.add(instructorsComboBox, 1, 0);
		buttonPane.add(coursesComboBox, 2, 0);
		buttonPane.add(sectionsComboBox, 3, 0);
		buttonPane.add(staffComboBox, 4, 0);

		TextFlow nameTextFlow = new TextFlow();
		TextFlow idTextFlow = new TextFlow();

		Text nameText = new Text("Name:");
		nameText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		Text adminNameText = new Text(" " + loginFN + " " + loginLN);
		nameTextFlow.getChildren().addAll(nameText, adminNameText);

		Text idText = new Text("ID:");
		idText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		Text adminIdText = new Text(" " + loginID);
		idTextFlow.getChildren().addAll(idText, adminIdText);

		nameidPane.getChildren().addAll(nameTextFlow, idTextFlow);
		headerPane.setLeft(nameidPane);
		headerPane.setRight(backPane);

		createStaffListTable("SELECT * FROM STAFF WHERE staffID = \"" + loginID + "\"");

		adminPane.setTop(headerPane);
		adminPane.setCenter(staffTable);
		adminPane.setBottom(buttonPane);

		adminScene = new Scene(adminPane, (sceneList.get((sceneList.size() - 1))).getWidth(),
				(sceneList.get((sceneList.size() - 1))).getHeight());
		adminScene.setOnKeyPressed(e -> backspacePressed(e));
		sceneList.add(adminScene);
		stage.setScene(adminScene);

	}

	private void createStaffScene() {
		staffPane = new BorderPane();
		staffPane.setPadding(insets);
		BorderPane headerPane = new BorderPane();
		headerPane.setPadding(new Insets(0, 0, 8, 0));
		HBox nameidPane = new HBox(8);
		nameidPane.setAlignment(Pos.CENTER);
		HBox buttonsPane = new HBox(hgap);
		buttonsPane.setAlignment(Pos.CENTER);
		buttonsPane.setPadding(new Insets(8, 0, 0, 0));

		int width = 110;
		Button backButton = new Button("Sign out");
		Button detailsButton = new Button("View Details");
		Button changePasswordButton = new Button("Change Password");
		Button viewFAStudents = new Button("View FA Students");
		Button expertButton = new Button("Expert");
		backButton.setMinWidth(w);
		expertButton.setMinWidth(w);
		detailsButton.setMinWidth(width);
		viewFAStudents.setMinWidth(width);
		changePasswordButton.setMinWidth(width);

		viewFAStudents.setOnAction(e -> staff_viewFAStudentsButtonClicked());
		viewFAStudents.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				staff_viewFAStudentsButtonClicked();
		});

		detailsButton.setOnAction(e -> staff_detailsButtonClicked());
		detailsButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				staff_detailsButtonClicked();
		});

		expertButton.setTextFill(Color.RED);
		expertButton.setOnAction(e -> createExpertScene());
		expertButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				createExpertScene();
		});
		backButton.setOnAction(e -> backButtonClicked());
		backButton.setOnKeyPressed(e -> backEnterPressed(e));

		changePasswordButton.setMinWidth(width);
		changePasswordButton.setOnAction(e -> createPasswordScene());
		changePasswordButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				createPasswordScene();
		});

		HBox backPane = new HBox(hgap);
		backPane.setAlignment(Pos.TOP_RIGHT);
		backPane.getChildren().addAll(backButton);

		buttonsPane.getChildren().addAll(detailsButton, viewFAStudents, changePasswordButton);

		TextFlow nameTextFlow = new TextFlow();
		TextFlow idTextFlow = new TextFlow();

		Text nameText = new Text("Name:");
		nameText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		Text staffNameText = new Text(" " + loginFN + " " + loginLN);
		nameTextFlow.getChildren().addAll(nameText, staffNameText);

		Text idText = new Text("ID:");
		idText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		Text staffIdText = new Text(" " + loginID);
		idTextFlow.getChildren().addAll(idText, staffIdText);

		nameidPane.getChildren().addAll(nameTextFlow, idTextFlow);
		headerPane.setLeft(nameidPane);
		headerPane.setRight(backPane);

		staff_detailsButtonClicked();
		staffPane.setTop(headerPane);
		staffPane.setBottom(buttonsPane);

		staffScene = new Scene(staffPane, (sceneList.get((sceneList.size() - 1))).getWidth(),
				(sceneList.get((sceneList.size() - 1))).getHeight());
		staffScene.setOnKeyPressed(e -> backspacePressed(e));
		sceneList.add(staffScene);
		stage.setScene(staffScene);

	}

	private void createInstructorScene() {
		instructorPane = new BorderPane();
		instructorPane.setPadding(insets);
		BorderPane headerPane = new BorderPane();
		headerPane.setPadding(new Insets(0, 0, 8, 0));
		HBox nameidPane = new HBox(8);
		nameidPane.setAlignment(Pos.CENTER);
		VBox buttonsPane = new VBox(5);
		buttonsPane.setPadding(new Insets(8, 0, 0, 0));
		HBox buttonPane1 = new HBox(hgap);
		HBox buttonPane2 = new HBox(hgap);
		buttonPane1.setAlignment(Pos.CENTER);
		buttonPane2.setAlignment(Pos.CENTER);
		buttonsPane.setAlignment(Pos.CENTER);

		Button backButton = new Button("Sign out");
		backButton.setOnAction(e -> backButtonClicked());
		backButton.setOnKeyPressed(e -> backEnterPressed(e));

		int width = 178;
		Button viewAdviseesButton = new Button("View Advisees");
		Button viewCoursesButton = new Button("View Courses");
		Button viewSectionsButton = new Button("View Sections");
		Button viewAllSectionsButton = new Button("View All Sections");
		Button viewStudentsEnrolledButton = new Button("View Students");
		Button changePasswordButton = new Button("Change Password");
		Button expertButton = new Button("Expert");
		Button viewDetailsButton = new Button("Details");
		viewDetailsButton.setMinWidth(w);
		backButton.setMinWidth(w);
		expertButton.setMinWidth(w);
		viewAdviseesButton.setMinWidth(width);
		viewCoursesButton.setMinWidth(width);
		viewSectionsButton.setMinWidth(width);
		viewAllSectionsButton.setMinWidth(width);
		viewStudentsEnrolledButton.setMinWidth(width);
		changePasswordButton.setMinWidth(width);

		viewCoursesButton.setOnAction(e -> instructor_viewCourseButtonClicked());
		viewCoursesButton.setOnKeyTyped(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				instructor_viewCourseButtonClicked();
			}
		});

		viewSectionsButton.setOnAction(e -> instructor_viewSectionButtonClicked());
		viewSectionsButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				instructor_viewSectionButtonClicked();
			}
		});

		viewAllSectionsButton.setOnAction(e -> instuctor_viewAllSectionButtonClicked());
		viewAllSectionsButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				instuctor_viewAllSectionButtonClicked();
			}
		});

		viewStudentsEnrolledButton.setOnAction(e -> instructor_viewStudentsButtonClicked());
		viewStudentsEnrolledButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				instructor_viewStudentsButtonClicked();
			}
		});

		viewDetailsButton.setOnAction(e -> {
			createInstructorListTable("SELECT * FROM FACULTY WHERE facultyID = \"" + loginID + "\"");
			instructorPane.setCenter(instructorsTable);
		});
		viewDetailsButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				createInstructorListTable("SELECT * FROM FACULTY WHERE facultyID = \"" + loginID + "\"");
				instructorPane.setCenter(instructorsTable);
			}
		});

		viewAdviseesButton.setOnAction(e -> instructor_viewAdviseesButtonClicked());
		viewAdviseesButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				instructor_viewAdviseesButtonClicked();
		});

		changePasswordButton.setOnAction(e -> createPasswordScene());
		changePasswordButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				createPasswordScene();
		});

		expertButton.setTextFill(Color.RED);
		expertButton.setOnAction(e -> createExpertScene());
		expertButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				createExpertScene();
		});

		buttonPane1.getChildren().addAll(viewCoursesButton, viewSectionsButton, viewStudentsEnrolledButton);
		buttonPane2.getChildren().addAll(viewAllSectionsButton, viewAdviseesButton, changePasswordButton);
		buttonsPane.getChildren().addAll(buttonPane1, buttonPane2);

		HBox backPane = new HBox(hgap);
		backPane.setAlignment(Pos.TOP_RIGHT);
		backPane.getChildren().addAll(viewDetailsButton, backButton);

		TextFlow nameTextFlow = new TextFlow();
		TextFlow idTextFlow = new TextFlow();

		Text nameText = new Text("Name:");
		nameText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		Text insNameText = new Text(" " + loginFN + " " + loginLN);
		nameTextFlow.getChildren().addAll(nameText, insNameText);

		Text idText = new Text("ID:");
		idText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		Text insIdText = new Text(" " + loginID);
		idTextFlow.getChildren().addAll(idText, insIdText);

		nameidPane.getChildren().addAll(nameTextFlow, idTextFlow);
		headerPane.setLeft(nameidPane);
		headerPane.setRight(backPane);

		createInstructorListTable("SELECT * FROM FACULTY WHERE facultyID = \"" + loginID + "\"");

		instructorPane.setTop(headerPane);
		instructorPane.setCenter(instructorsTable);
		instructorPane.setBottom(buttonsPane);

		instructorScene = new Scene(instructorPane, (sceneList.get((sceneList.size() - 1))).getWidth(),
				(sceneList.get((sceneList.size() - 1))).getHeight());
		sceneList.add(instructorScene);
		instructorScene.setOnKeyPressed(e -> backspacePressed(e));
		stage.setScene(instructorScene);

	}

	private void createPasswordScene() {

		BorderPane mainPane = new BorderPane();
		mainPane.setPadding(insets);
		passwordPane = new VBox(vgap);
		passwordPane.setAlignment(Pos.CENTER);

		Button backButton = new Button("Back");
		backButton.setOnAction(e -> backButtonClicked());
		backButton.setOnKeyPressed(e -> backEnterPressed(e));

		Button signOutButton = new Button("Sign out");
		signOutButton.setOnAction(e -> createLoginScene());
		signOutButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				createLoginScene();
		});
		backButton.setMinWidth(w);
		signOutButton.setMinWidth(w);

		changePassMessage = new Label("Please enter a new password and confirm it");
		changePassMessage.setStyle("-fx-font-style: italic");

		HBox backPane = new HBox(hgap);
		backPane.setAlignment(Pos.TOP_RIGHT);
		backPane.getChildren().addAll(backButton, signOutButton);

		Button submitButton = new Button("Submit");

		passwordTF = new PasswordField();
		passwordTF.setPromptText("Enter New Password");
		confirmPasswordTF = new PasswordField();
		confirmPasswordTF.setPromptText("Re-enter New Password");
		passwordTF.setMaxWidth(225);
		confirmPasswordTF.setMaxWidth(225);
		submitButton.setMinWidth(225);

		passwordPane.getChildren().addAll(changePassMessage, passwordTF, confirmPasswordTF, submitButton);

		mainPane.setCenter(passwordPane);
		mainPane.setTop(backPane);
		passwordScene = new Scene(mainPane, (sceneList.get((sceneList.size() - 1))).getWidth(),
				(sceneList.get((sceneList.size() - 1))).getHeight());

		passwordScene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.BACK_SPACE)
				backspacePressed(e);
		});

		if (loginID.matches("S\\d{4}")) {
			submitButton.setOnAction(e -> student_submitButtonClicked());
			submitButton.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					student_submitButtonClicked();
			});
			passwordTF.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					student_submitButtonClicked();
			});
			confirmPasswordTF.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					student_submitButtonClicked();
			});

		} else if (loginID.matches("I\\d{4}")) {
			submitButton.setOnAction(e -> instructor_submitButtonClicked());
			submitButton.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					instructor_submitButtonClicked();
			});
			passwordTF.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					instructor_submitButtonClicked();
			});
			confirmPasswordTF.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					instructor_submitButtonClicked();
			});

		} else if (loginID.matches("ST\\d{4}") && loginA == 1) {
			submitButton.setOnAction(e -> admin_submitButtonClicked());
			submitButton.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					admin_submitButtonClicked();
			});
			passwordTF.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					admin_submitButtonClicked();
			});
			confirmPasswordTF.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					admin_submitButtonClicked();
			});

		} else {
			submitButton.setOnAction(e -> staff_submitButtonClicked());
			submitButton.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					staff_submitButtonClicked();
			});
			passwordTF.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					staff_submitButtonClicked();
			});
			confirmPasswordTF.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					staff_submitButtonClicked();
			});

		}

		sceneList.add(passwordScene);
		stage.setScene(passwordScene);

	}

	private void createExpertScene() {
		expertPane = new BorderPane();
		expertPane.setPadding(insets);
		BorderPane headerPane = new BorderPane();
		headerPane.setPadding(new Insets(0, 0, 8, 0));
		HBox nameidPane = new HBox(8);
		nameidPane.setAlignment(Pos.CENTER);
		HBox backPane = new HBox(hgap);
		backPane.setAlignment(Pos.TOP_RIGHT);
		HBox queryPane = new HBox(hgap);
		queryPane.setPadding(new Insets(8, 0, 0, 0));

		msgLabel = new VBox(vgap);
		msgLabel.setAlignment(Pos.CENTER);
		Label msgLabel1 = new Label("Wrong query !");
		Label msgLabel2 = new Label("Please enter a valid one");
		msgLabel1.setStyle("-fx-font-size: 40; -fx-text-fill: red");
		msgLabel2.setStyle("-fx-font-size: 40; -fx-text-fill: red");
		msgLabel.getChildren().addAll(msgLabel1, msgLabel2);

		TextFlow nameTextFlow = new TextFlow();
		TextFlow idTextFlow = new TextFlow();

		Text nameText = new Text("Name:");
		nameText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		Text adminNameText = new Text(" " + loginFN + " " + loginLN);
		nameTextFlow.getChildren().addAll(nameText, adminNameText);

		Text idText = new Text("ID:");
		idText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		Text adminIdText = new Text(" " + loginID);
		idTextFlow.getChildren().addAll(idText, adminIdText);

		if (loginID.matches("S\\d{4}")) {
			TextFlow majorTextFlow = new TextFlow();
			Text majorText = new Text("Major:");
			majorText.setStyle("-fx-font-weight: bold; -fx-underline: true");
			Text studentMajorText = new Text(" " + loginM);
			majorTextFlow.getChildren().addAll(majorText, studentMajorText);
			nameidPane.getChildren().addAll(nameTextFlow, idTextFlow, majorTextFlow);
		} else
			nameidPane.getChildren().addAll(nameTextFlow, idTextFlow);

		Button backButton = new Button("Back");
		backButton.setOnAction(e -> backButtonClicked());
		backButton.setOnKeyPressed(e -> backEnterPressed(e));

		Button signOutButton = new Button("Sign out");
		signOutButton.setOnAction(e -> createLoginScene());
		signOutButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				createLoginScene();
		});
		backButton.setMinWidth(w);
		signOutButton.setMinWidth(w);

		backPane.getChildren().addAll(backButton, signOutButton);

		headerPane.setRight(backPane);
		headerPane.setLeft(nameidPane);

		queryTF = new TextField();
		queryTF.setPromptText("Enter your query here");
		queryPane.setHgrow(queryTF, Priority.ALWAYS);

		Button querySubmitButton = new Button("Submit");
		Button clearButton = new Button("Clear");
		querySubmitButton.setMinWidth(w);
		clearButton.setMinWidth(w);
		queryPane.getChildren().addAll(queryTF, querySubmitButton, clearButton);

		querySubmitButton.setOnAction(e -> expert_querySubmitButtonClicked());
		querySubmitButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				expert_querySubmitButtonClicked();
		});

		clearButton.setOnAction(e -> queryTF.clear());
		clearButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				queryTF.clear();
		});

		queryTF.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				expert_querySubmitButtonClicked();
		});

		expertPane.setTop(headerPane);
		expertPane.setCenter(ta);
		expertPane.setBottom(queryPane);

		Scene expertScene = new Scene(expertPane, (sceneList.get((sceneList.size() - 1))).getWidth(),
				(sceneList.get((sceneList.size() - 1))).getHeight());
		sceneList.add(expertScene);
		stage.setScene(expertScene);
	}

	private void admin_viewAllStudentsButtonClicked() {
		createStudentListTable("SELECT * FROM STUDENT");
		adminPane.setCenter(studentsTable);

		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}
	}

	private void admin_viewStudentButtonClicked() {
		try {

			VBox vb = new VBox(2);
			vb.setAlignment(Pos.CENTER);
			adminViewStudentPane = new BorderPane();
			HBox buttonsPane = new HBox(hgap);
			buttonsPane.setAlignment(Pos.CENTER);
			buttonsPane.setPadding(insets);
			Button submitButton = new Button("Submit");
			idTF = new TextField();
			idTF.setPromptText("Student ID");
			buttonsPane.getChildren().addAll(idTF, submitButton);
			msgText = new Text("");
			msgText.setStyle("-fx-font-style: italic");
			vb.getChildren().addAll(msgText, buttonsPane);
			adminViewStudentPane.setBottom(vb);

			idTF.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					admin_viewStudent_submitButtonPressed();
			});

			submitButton.setOnAction(e -> admin_viewStudent_submitButtonPressed());
			submitButton.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					admin_viewStudent_submitButtonPressed();
			});

			Student item = studentsTable.getSelectionModel().getSelectedItem();
			createGradesListTable(
					"Select isinstanceof, coursetitle, credits, term , sectionyear, grade from STUDENT_SECTION S , Section se , COURSE C WHERE se.isinstanceof = C.courseID AND S.crn = se.crn AND studentID =\""
							+ item.getId() + "\"");

			adminViewStudentPane.setCenter(gradesTable);
			adminPane.setCenter(adminViewStudentPane);

			try {
				studentsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				instructorsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				coursesTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				sectionsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				staffTable.getItems().clear();
			} catch (Exception e1) {

			}

		} catch (Exception e) {
			TableView<Grade> table = new TableView<>();
			adminViewStudentPane.setCenter(table);
			adminPane.setCenter(adminViewStudentPane);
			msgText.setText("Nothing is selected!");
			msgText.setFill(Color.RED);

			try {
				studentsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				instructorsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				coursesTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				sectionsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				staffTable.getItems().clear();
			} catch (Exception e1) {

			}

		}

	}

	private void admin_viewStudent_submitButtonPressed() {

		try {
			createGradesListTable(
					"Select isinstanceof, coursetitle, credits, term , sectionyear, grade from STUDENT_SECTION S , Section se , COURSE C WHERE se.isinstanceof = C.courseID AND S.crn = se.crn AND studentID =\""
							+ idTF.getText() + "\"");
			gradesTable.getItems().get(0);
			adminViewStudentPane.setCenter(gradesTable);
			msgText.setText("");
		} catch (Exception e) {
			TableView<Section> table = new TableView<>();
			adminViewStudentPane.setCenter(table);
			msgText.setText("Wrong Student ID! Please enter a valid one");
			msgText.setFill(Color.RED);
		}

	}

	private void admin_viewAllInstructorsButtonClicked() {
		createInstructorListTable("SELECT * FROM FACULTY");
		adminPane.setCenter(instructorsTable);
		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}

		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}
	}

	private void admin_viewInstructorButtonClicked() {
		try {
			VBox vb = new VBox(2);
			vb.setAlignment(Pos.CENTER);
			adminViewStudentPane = new BorderPane();
			HBox buttonsPane = new HBox(hgap);
			buttonsPane.setAlignment(Pos.CENTER);
			buttonsPane.setPadding(insets);
			Button submitButton = new Button("Submit");
			idTF = new TextField();
			idTF.setPromptText("Instructor ID");
			buttonsPane.getChildren().addAll(idTF, submitButton);
			msgText = new Text("");
			msgText.setStyle("-fx-font-style: italic");
			vb.getChildren().addAll(msgText, buttonsPane);
			adminViewStudentPane.setBottom(vb);

			idTF.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					admin_viewInstructor_submitButtonPressed();
			});

			submitButton.setOnAction(e -> admin_viewInstructor_submitButtonPressed());
			submitButton.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					admin_viewInstructor_submitButtonPressed();
			});

			Instructor item = instructorsTable.getSelectionModel().getSelectedItem();
			createSectionsListTable("SELECT * FROM SECTION WHERE taughtby ='" + item.getId() + "'");

			adminViewStudentPane.setCenter(sectionsTable);
			adminPane.setCenter(adminViewStudentPane);

			try {
				studentsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				instructorsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				coursesTable.getItems().clear();
			} catch (Exception e1) {

			}

			try {
				staffTable.getItems().clear();
			} catch (Exception e1) {

			}
		} catch (Exception e) {
			TableView<Instructor> table = new TableView<>();
			adminViewStudentPane.setCenter(table);
			adminPane.setCenter(adminViewStudentPane);
			msgText.setText("Nothing is selected!");
			msgText.setFill(Color.RED);

			try {
				studentsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				instructorsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				coursesTable.getItems().clear();
			} catch (Exception e1) {

			}

			try {
				staffTable.getItems().clear();
			} catch (Exception e1) {

			}

		}
	}

	private void admin_viewInstructor_submitButtonPressed() {

		try {
			createSectionsListTable("SELECT * FROM SECTION WHERE taughtby ='" + idTF.getText() + "'");
			sectionsTable.getItems().get(0);
			adminViewStudentPane.setCenter(sectionsTable);
			msgText.setText("");
		} catch (Exception e) {
			TableView<Section> table = new TableView<>();
			adminViewStudentPane.setCenter(table);
			msgText.setText("Wrong Instructor ID! Please enter a valid one");
			msgText.setFill(Color.RED);
		}

	}

	private void admin_viewAllCoursesButtonClicked() {
		createCourseListTable("SELECT * FROM COURSE");
		adminPane.setCenter(coursesTable);
		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}
	}

	private void admin_viewCourseButtonClicked() {
		try {
			VBox vb = new VBox(2);
			vb.setAlignment(Pos.CENTER);
			adminViewStudentPane = new BorderPane();
			HBox buttonsPane = new HBox(hgap);
			buttonsPane.setAlignment(Pos.CENTER);
			buttonsPane.setPadding(insets);
			Button submitButton = new Button("Submit");
			idTF = new TextField();
			idTF.setPromptText("Course ID");
			buttonsPane.getChildren().addAll(idTF, submitButton);
			msgText = new Text("");
			msgText.setStyle("-fx-font-style: italic");
			vb.getChildren().addAll(msgText, buttonsPane);
			adminViewStudentPane.setBottom(vb);

			idTF.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					admin_viewCourse_submitButtonPressed();
			});

			submitButton.setOnAction(e -> admin_viewCourse_submitButtonPressed());
			submitButton.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					admin_viewCourse_submitButtonPressed();
			});

			Course item = coursesTable.getSelectionModel().getSelectedItem();
			createSectionsListTable("SELECT * FROM SECTION WHERE isinstanceof ='" + item.getCourseid() + "'");

			adminViewStudentPane.setCenter(sectionsTable);
			adminPane.setCenter(adminViewStudentPane);

			try {
				studentsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				instructorsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				coursesTable.getItems().clear();
			} catch (Exception e1) {

			}

			try {
				staffTable.getItems().clear();
			} catch (Exception e1) {

			}
		} catch (Exception e) {
			TableView<Section> table = new TableView<>();
			adminViewStudentPane.setCenter(table);
			adminPane.setCenter(adminViewStudentPane);
			msgText.setText("Nothing is selected!");
			msgText.setFill(Color.RED);
			try {
				studentsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				instructorsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				coursesTable.getItems().clear();
			} catch (Exception e1) {

			}

			try {
				staffTable.getItems().clear();
			} catch (Exception e1) {

			}

		}
	}

	private void admin_viewCourse_submitButtonPressed() {

		try {
			createSectionsListTable("SELECT * FROM SECTION WHERE isinstanceof ='" + idTF.getText() + "'");
			sectionsTable.getItems().get(0);
			adminViewStudentPane.setCenter(sectionsTable);
			msgText.setText("");
		} catch (Exception e) {
			TableView<Section> table = new TableView<>();
			adminViewStudentPane.setCenter(table);
			msgText.setText("Wrong Course ID! Please enter a valid one");
			msgText.setFill(Color.RED);
		}

	}

	private void admin_viewAllSectionsButtonClicked() {
		createSectionsListTable("SELECT * FROM SECTION");
		adminPane.setCenter(sectionsTable);
		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}

		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}
	}

	private void admin_viewSectionsButtonClicked() {
		try {
			VBox vb = new VBox(2);
			vb.setAlignment(Pos.CENTER);
			adminViewStudentPane = new BorderPane();
			HBox buttonsPane = new HBox(hgap);
			buttonsPane.setAlignment(Pos.CENTER);
			buttonsPane.setPadding(insets);
			Button submitButton = new Button("Submit");
			idTF = new TextField();
			idTF.setPromptText("CRN");
			buttonsPane.getChildren().addAll(idTF, submitButton);
			msgText = new Text("");
			msgText.setStyle("-fx-font-style: italic");
			vb.getChildren().addAll(msgText, buttonsPane);
			adminViewStudentPane.setBottom(vb);

			idTF.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					admin_viewSection_submitButtonPressed();
			});

			submitButton.setOnAction(e -> admin_viewSection_submitButtonPressed());
			submitButton.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					admin_viewSection_submitButtonPressed();
			});

			Section item = sectionsTable.getSelectionModel().getSelectedItem();
			createStudentListTable("SELECT Student.* FROM STUDENT_SECTION JOIN STUDENT on crn =" + item.getCrn()
					+ " AND student.studentID = student_section.studentID");

			adminViewStudentPane.setCenter(studentsTable);
			adminPane.setCenter(adminViewStudentPane);

			try {
				instructorsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				coursesTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				sectionsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				staffTable.getItems().clear();
			} catch (Exception e1) {

			}
		} catch (Exception e) {
			TableView<Student> table = new TableView<>();
			adminViewStudentPane.setCenter(table);
			adminPane.setCenter(adminViewStudentPane);
			msgText.setText("Nothing is selected!");
			msgText.setFill(Color.RED);

			try {
				instructorsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				coursesTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				sectionsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				staffTable.getItems().clear();
			} catch (Exception e1) {

			}

		}
	}

	private void admin_viewSection_submitButtonPressed() {

		try {
			createStudentListTable("SELECT Student.* FROM STUDENT_SECTION JOIN STUDENT on crn =" + idTF.getText()
					+ " AND student.studentID = student_section.studentID");
			studentsTable.getItems().get(0);
			adminViewStudentPane.setCenter(studentsTable);
			msgText.setText("");
		} catch (Exception e) {
			TableView<Section> table = new TableView<>();
			adminViewStudentPane.setCenter(table);
			msgText.setText("Wrong Section CRN! Please enter a valid one");
			msgText.setFill(Color.RED);
		}

	}

	private void admin_viewAllStaffButtonClicked() {
		createStaffListTable("SELECT * FROM STAFF");
		adminPane.setCenter(staffTable);
		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
	}

	private void admin_viewStaffButtonClicked() {

		try {
			VBox vb = new VBox(2);
			vb.setAlignment(Pos.CENTER);
			adminViewStudentPane = new BorderPane();
			HBox buttonsPane = new HBox(hgap);
			buttonsPane.setAlignment(Pos.CENTER);
			buttonsPane.setPadding(insets);
			Button submitButton = new Button("Submit");
			idTF = new TextField();
			idTF.setPromptText("Staff ID");
			buttonsPane.getChildren().addAll(idTF, submitButton);
			msgText = new Text("");
			msgText.setStyle("-fx-font-style: italic");
			vb.getChildren().addAll(msgText, buttonsPane);
			adminViewStudentPane.setBottom(vb);

			idTF.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					admin_viewStaff_submitButtonClicked();
			});

			submitButton.setOnAction(e -> admin_viewStaff_submitButtonClicked());
			submitButton.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					admin_viewStaff_submitButtonClicked();
			});

			Staff item = staffTable.getSelectionModel().getSelectedItem();

			BorderPane fasPane = new BorderPane();
			VBox buttonPane = new VBox(vgap);
			buttonPane.setPadding(insets);
			buttonPane.setAlignment(Pos.CENTER);
			Button viewStudent = new Button("View Selected Student");
			buttonPane.getChildren().add(viewStudent);
			createFAListTable(
					"select SS.studentID, SS.manager, S.firstname, S.lastname, SS.term, SS.contractyear, SS.percentage, SS.salary, SS.location from STUDENT_STAFF SS, STUDENT S where SS.studentID = S.studentID AND manager='"
							+ item.getStaffid() + "'");
			fasPane.setCenter(faTable);
			fasPane.setBottom(buttonPane);

			viewStudent.setOnAction(e -> admin_viewStaff_viewStudentButtonClicked());

			viewStudent.setOnKeyTyped(e -> {
				if (e.getCode() == KeyCode.ENTER)
					admin_viewStaff_viewStudentButtonClicked();
			});

			adminViewStudentPane.setCenter(fasPane);
			adminPane.setCenter(adminViewStudentPane);

			try {
				studentsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				instructorsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				coursesTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				sectionsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				staffTable.getItems().clear();
			} catch (Exception e1) {

			}
		} catch (Exception e) {
			TableView<Student> table = new TableView<>();
			adminViewStudentPane.setCenter(table);
			adminPane.setCenter(adminViewStudentPane);
			msgText.setText("Nothing is selected!");
			msgText.setFill(Color.RED);
			try {
				studentsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				instructorsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				coursesTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				sectionsTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				staffTable.getItems().clear();
			} catch (Exception e1) {

			}

		}
	}

	private void admin_viewStaff_submitButtonClicked() {

		try {
			BorderPane fasPane = new BorderPane();
			VBox buttonPane = new VBox(vgap);
			buttonPane.setPadding(insets);
			buttonPane.setAlignment(Pos.CENTER);
			Button viewStudent = new Button("View Selected Student");
			buttonPane.getChildren().add(viewStudent);
			createFAListTable(
					"select SS.studentID, SS.manager, S.firstname, S.lastname, SS.term, SS.contractyear, SS.percentage, SS.salary, SS.location from STUDENT_STAFF SS, STUDENT S where SS.studentID = S.studentID AND manager='"
							+ idTF.getText() + "'");
			fasPane.setCenter(faTable);
			fasPane.setBottom(buttonPane);

			viewStudent.setOnAction(e -> admin_viewStaff_viewStudentButtonClicked());

			viewStudent.setOnKeyTyped(e -> {
				if (e.getCode() == KeyCode.ENTER)
					admin_viewStaff_viewStudentButtonClicked();
			});

			faTable.getItems().get(0);
			adminViewStudentPane.setCenter(fasPane);
			msgText.setText("");
		} catch (Exception e) {
			TableView<Section> table = new TableView<>();
			adminViewStudentPane.setCenter(table);
			msgText.setText("Wrong Staff ID! Please enter a valid one");
			msgText.setFill(Color.RED);
		}

	}

	private void admin_viewStaff_viewStudentButtonClicked() {
		try {
			FA item = faTable.getSelectionModel().getSelectedItem();
			createStudentListTable("SELECT * FROM STUDENT WHERE studentID='" + item.getId() + "'");
			adminViewStudentPane.setCenter(studentsTable);
		} catch (Exception e) {
			msgText.setText("Nothing is selected!");
			msgText.setFill(Color.RED);
		}

	}

	private void admin_submitButtonClicked() {

		if (passwordTF.getText().equals(confirmPasswordTF.getText()) && !((passwordTF.getText()).equals(""))) {
			query("UPDATE staff set passwd =\"" + passwordTF.getText() + "\" where staffID=\"" + loginID + "\"");
			changePassMessage.setText("Password changed");
			changePassMessage.setTextFill(Color.BLUE);
		} else {
			changePassMessage.setText("Passwords do not match! Please try again");
			changePassMessage.setTextFill(Color.RED);

		}
	}

	private void admin_changeAdminButtonClicked() {

		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}
		VBox changeAdminPane = new VBox(vgap);
		changeAdminPane.setAlignment(Pos.CENTER);
		Text msgText = new Text("Please enter staff ID and new admin status");
		msgText.setStyle("-fx-font-style: italic");

		TextField idTF = new TextField();
		idTF.setPromptText("ID");
		idTF.setMaxWidth(150);

		TextField adminStatusTF = new TextField();
		adminStatusTF.setPromptText("0/1");
		adminStatusTF.setMaxWidth(150);

		Button submitButton = new Button("Submit");
		submitButton.setMinWidth(150);

		Button clearButton = new Button("Clear");
		clearButton.setMinWidth(150);
		clearButton.setOnAction(e -> {
			msgText.setText("Please enter staff ID and new admin status");
			msgText.setFill(Color.BLACK);
			idTF.clear();
			adminStatusTF.clear();
		});
		clearButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				msgText.setText("Please enter staff ID and new admin status");
				msgText.setFill(Color.BLACK);
				idTF.clear();
				adminStatusTF.clear();

			}

		});

		submitButton.setOnAction(e -> {

			try {
				String id = idTF.getText();
				if (id.matches("ST\\d{4}")) {

					createStaffListTable("Select * from staff");

					int staffCounter = staffTable.getItems().size();

					for (int i = 0; i <= staffCounter; i++) {
						if (i < staffCounter) {
							if (id.equals(staffTable.getItems().get(i).getStaffid())) {

								String q = "UPDATE (staff) SET admin =" + adminStatusTF.getText()
										+ " WHERE (staffID = '" + idTF.getText() + "')";
								PreparedStatement ps = getConnection().prepareStatement(q);
								ps.executeUpdate();

								msgText.setText("Admin status changed");
								msgText.setFill(Color.BLUE);
								i = staffCounter;
							}
						} else if (i == staffCounter)
							throw new Exception();
					}

				} else
					throw new Exception();
			} catch (Exception e1) {
				msgText.setText("Wrong staff ID or admin status format! Please try again");
				msgText.setFill(Color.RED);

				e1.printStackTrace();
			}

		});

		changeAdminPane.getChildren().addAll(msgText, idTF, adminStatusTF, submitButton, clearButton);
		adminPane.setCenter(changeAdminPane);

	}

	private void admin_changeSalaryButtonClicked() {

		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}
		VBox changeSalaryPane = new VBox(vgap);
		changeSalaryPane.setAlignment(Pos.CENTER);
		Text msgText = new Text("Please enter ID and new salary");
		msgText.setStyle("-fx-font-style: italic");

		TextField idTF = new TextField();
		idTF.setPromptText("ID");
		idTF.setMaxWidth(150);

		TextField salaryTF = new TextField();
		salaryTF.setPromptText("Salary");
		salaryTF.setMaxWidth(150);

		Button submitButton = new Button("Submit");
		submitButton.setMinWidth(150);

		Button clearButton = new Button("Clear");
		clearButton.setMinWidth(150);
		clearButton.setOnAction(e -> {
			msgText.setText("Please enter ID and new salary");
			msgText.setFill(Color.BLACK);
			idTF.clear();
			salaryTF.clear();
		});
		clearButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				msgText.setText("Please enter ID and new salary");
				msgText.setFill(Color.BLACK);
				idTF.clear();
				salaryTF.clear();

			}

		});

		submitButton.setOnAction(e -> {

			try {
				String id = idTF.getText();
				if (id.matches("ST\\d{4}")) {

					createStaffListTable("Select * from staff");

					int staffCounter = staffTable.getItems().size();

					for (int i = 0; i <= staffCounter; i++) {
						if (i < staffCounter) {
							if (id.equals(staffTable.getItems().get(i).getStaffid())) {

								String q = "UPDATE (staff) SET salary =" + salaryTF.getText() + " WHERE (staffID = '"
										+ idTF.getText() + "')";
								PreparedStatement ps = getConnection().prepareStatement(q);
								ps.executeUpdate();

								msgText.setText("Salary changed");
								msgText.setFill(Color.BLUE);
								i = staffCounter;
							}
						} else if (i == staffCounter)
							throw new Exception();
					}

				} else if (id.matches("I\\d{4}")) {
					createInstructorListTable("Select * from faculty");
					int insCounter = instructorsTable.getItems().size();
					for (int i = 0; i <= insCounter; i++) {
						if (i < insCounter) {
							if (id.equals(instructorsTable.getItems().get(i).getId())) {

								String q = "UPDATE (Faculty) SET salary =" + salaryTF.getText()
										+ " WHERE (facultyID = '" + idTF.getText() + "')";
								PreparedStatement ps = getConnection().prepareStatement(q);
								ps.executeUpdate();

								msgText.setText("Salary changed");
								msgText.setFill(Color.BLUE);
								i = insCounter;
							}
						} else if (i == insCounter)
							throw new Exception();

					}

				} else
					throw new Exception();

				try {
					staffTable.getItems().clear();
					instructorsTable.getItems().clear();
				} catch (Exception e1) {

				}

			} catch (Exception e1) {
				e1.printStackTrace();
				msgText.setText("Wrong ID or salary format! Please try again");
				msgText.setFill(Color.RED);

			}

		});

		changeSalaryPane.getChildren().addAll(msgText, idTF, salaryTF, submitButton, clearButton);
		adminPane.setCenter(changeSalaryPane);

	}

	private void admin_deleteStudentButtonClicked() {

		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}

		deletePane = new BorderPane();
		VBox deletePane1 = new VBox(vgap);
		deletePane1.setAlignment(Pos.CENTER);
		deletePane1.setPadding(new Insets(vgap, 0, hgap, 0));

		createStudentListTable("SELECT * FROM STUDENT");
		Button deleteSelectedStudentButton = new Button("Delete Selected Student");
		deleteSelectedStudentButton.setMinWidth(325);

		deleteSelectedStudentButton.setOnAction(e -> admin_deleteSelectedStudentButtonClicked());
		deleteSelectedStudentButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				admin_deleteSelectedStudentButtonClicked();
		});

		int width = 160;
		HBox deleteManuallyPane = new HBox(hgap);
		deleteManuallyPane.setAlignment(Pos.CENTER);
		dropcrnTF = new TextField();
		dropcrnTF.setPromptText("Student ID");
		dropcrnTF.setMinWidth(width);
		Button deleteManuallyButton = new Button("Delete Manually");
		deleteManuallyButton.setMinWidth(width);

		deleteManuallyButton.setOnAction(e -> admin_deleteStudentManuallyButtonClicked());
		deleteManuallyButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				admin_deleteStudentManuallyButtonClicked();
		});

		deleteManuallyPane.getChildren().addAll(dropcrnTF, deleteManuallyButton);

		deletePane1.getChildren().addAll(deleteSelectedStudentButton, deleteManuallyPane);

		deletePane.setCenter(studentsTable);
		deletePane.setBottom(deletePane1);

		adminPane.setCenter(deletePane);

	}

	private void admin_deleteSelectedStudentButtonClicked() {
		try {
			Student item = studentsTable.getSelectionModel().getSelectedItem();
			query("DELETE FROM STUDENT WHERE studentID = '" + item.getId() + "'");

			createStudentListTable("SELECT * FROM STUDENT");
			deletePane.setCenter(studentsTable);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void admin_deleteStudentManuallyButtonClicked() {
		try {
			String id = dropcrnTF.getText();

			if (id.matches("S\\d{4}")) {
				query("DELETE FROM STUDENT WHERE studentID = '" + id + "'");

				createStudentListTable("SELECT * FROM STUDENT");
				deletePane.setCenter(studentsTable);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void admin_deleteInstructorButtonClicked() {

		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}

		deletePane = new BorderPane();
		VBox deletePane1 = new VBox(vgap);
		deletePane1.setAlignment(Pos.CENTER);
		deletePane1.setPadding(new Insets(vgap, 0, hgap, 0));

		createInstructorListTable("SELECT * FROM Faculty");
		Button deleteSelectedStudentButton = new Button("Delete Selected Instructor");
		deleteSelectedStudentButton.setMinWidth(325);

		deleteSelectedStudentButton.setOnAction(e -> admin_deleteSelectedInstructorButtonClicked());
		deleteSelectedStudentButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				admin_deleteSelectedInstructorButtonClicked();
		});

		int width = 160;
		HBox deleteManuallyPane = new HBox(hgap);
		deleteManuallyPane.setAlignment(Pos.CENTER);
		dropcrnTF = new TextField();
		dropcrnTF.setPromptText("Instructor ID");
		dropcrnTF.setMinWidth(width);
		Button deleteManuallyButton = new Button("Delete Manually");
		deleteManuallyButton.setMinWidth(width);

		deleteManuallyButton.setOnAction(e -> admin_deleteInstructorManuallyButtonClicked());
		deleteManuallyButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				admin_deleteInstructorManuallyButtonClicked();
		});

		deleteManuallyPane.getChildren().addAll(dropcrnTF, deleteManuallyButton);

		deletePane1.getChildren().addAll(deleteSelectedStudentButton, deleteManuallyPane);

		deletePane.setCenter(instructorsTable);
		deletePane.setBottom(deletePane1);

		adminPane.setCenter(deletePane);
	}

	private void admin_deleteSelectedInstructorButtonClicked() {
		try {
			Instructor item = instructorsTable.getSelectionModel().getSelectedItem();
			query("DELETE FROM Faculty WHERE facultyID = '" + item.getId() + "'");

			createInstructorListTable("SELECT * FROM Faculty");
			deletePane.setCenter(instructorsTable);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void admin_deleteInstructorManuallyButtonClicked() {
		String id = dropcrnTF.getText();

		if (id.matches("I\\d{4}")) {
			query("DELETE FROM Faculty WHERE facultyID = '" + id + "'");

			createInstructorListTable("SELECT * FROM Faculty");
			deletePane.setCenter(instructorsTable);
		}

	}

	private void admin_deleteCourseButtonClicked() {

		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}

		deletePane = new BorderPane();
		VBox deletePane1 = new VBox(vgap);
		deletePane1.setAlignment(Pos.CENTER);
		deletePane1.setPadding(new Insets(vgap, 0, hgap, 0));

		createCourseListTable("SELECT * FROM Course");
		Button deleteSelectedStudentButton = new Button("Delete Selected Course");
		deleteSelectedStudentButton.setMinWidth(325);

		deleteSelectedStudentButton.setOnAction(e -> admin_deleteSelectedCourseButtonClicked());
		deleteSelectedStudentButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				admin_deleteSelectedCourseButtonClicked();
		});

		int width = 160;
		HBox deleteManuallyPane = new HBox(hgap);
		deleteManuallyPane.setAlignment(Pos.CENTER);
		dropcrnTF = new TextField();
		dropcrnTF.setPromptText("Course ID");
		dropcrnTF.setMinWidth(width);
		Button deleteManuallyButton = new Button("Delete Manually");
		deleteManuallyButton.setMinWidth(width);

		deleteManuallyButton.setOnAction(e -> admin_deleteCourseManuallyButtonClicked());
		deleteManuallyButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				admin_deleteCourseManuallyButtonClicked();
		});

		deleteManuallyPane.getChildren().addAll(dropcrnTF, deleteManuallyButton);

		deletePane1.getChildren().addAll(deleteSelectedStudentButton, deleteManuallyPane);

		deletePane.setCenter(coursesTable);
		deletePane.setBottom(deletePane1);

		adminPane.setCenter(deletePane);
	}

	private void admin_deleteSelectedCourseButtonClicked() {
		try {
			Course item = coursesTable.getSelectionModel().getSelectedItem();
			query("DELETE FROM Course WHERE courseID = '" + item.getCourseid() + "'");

			createCourseListTable("SELECT * FROM Course");
			deletePane.setCenter(coursesTable);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void admin_deleteCourseManuallyButtonClicked() {
		String id = dropcrnTF.getText();

		if (id.matches("[A-Z]{3}\\s\\d{3}")) {
			query("DELETE FROM Course WHERE courseID = '" + id + "'");

			createCourseListTable("SELECT * FROM Course");
			deletePane.setCenter(coursesTable);
		}

	}

	private void admin_deleteSectionButtonClicked() {

		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}

		deletePane = new BorderPane();
		VBox deletePane1 = new VBox(vgap);
		deletePane1.setAlignment(Pos.CENTER);
		deletePane1.setPadding(new Insets(vgap, 0, hgap, 0));

		createSectionsListTable("SELECT * FROM section");
		Button deleteSelectedStudentButton = new Button("Delete Selected Section");
		deleteSelectedStudentButton.setMinWidth(325);

		deleteSelectedStudentButton.setOnAction(e -> admin_deleteSelectedSectionButtonClicked());
		deleteSelectedStudentButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				admin_deleteSelectedSectionButtonClicked();
		});

		int width = 160;
		HBox deleteManuallyPane = new HBox(hgap);
		deleteManuallyPane.setAlignment(Pos.CENTER);
		dropcrnTF = new TextField();
		dropcrnTF.setPromptText("CRN");
		dropcrnTF.setMinWidth(width);
		Button deleteManuallyButton = new Button("Delete Manually");
		deleteManuallyButton.setMinWidth(width);

		deleteManuallyButton.setOnAction(e -> admin_deleteSectionManuallyButtonClicked());
		deleteManuallyButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				admin_deleteSectionManuallyButtonClicked();
		});

		deleteManuallyPane.getChildren().addAll(dropcrnTF, deleteManuallyButton);

		deletePane1.getChildren().addAll(deleteSelectedStudentButton, deleteManuallyPane);

		deletePane.setCenter(sectionsTable);
		deletePane.setBottom(deletePane1);

		adminPane.setCenter(deletePane);
	}

	private void admin_deleteSelectedSectionButtonClicked() {
		try {
			Section item = sectionsTable.getSelectionModel().getSelectedItem();
			query("DELETE FROM section WHERE crn = '" + item.getCrn() + "'");

			createSectionsListTable("SELECT * FROM section");
			deletePane.setCenter(sectionsTable);
		} catch (Exception e) {

		}

	}

	private void admin_deleteSectionManuallyButtonClicked() {
		String id = dropcrnTF.getText();

		if (id.matches("\\d{6}")) {
			query("DELETE FROM section WHERE crn = '" + id + "'");

			createSectionsListTable("SELECT * FROM section");
			deletePane.setCenter(sectionsTable);
		}

	}

	private void admin_deleteStaffButtonClicked() {

		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}
		deletePane = new BorderPane();
		VBox deletePane1 = new VBox(vgap);
		deletePane1.setAlignment(Pos.CENTER);
		deletePane1.setPadding(new Insets(vgap, 0, hgap, 0));

		createStaffListTable("SELECT * FROM staff");
		Button deleteSelectedStudentButton = new Button("Delete Selected Staff");
		deleteSelectedStudentButton.setMinWidth(325);

		deleteSelectedStudentButton.setOnAction(e -> admin_deleteSelectedStaffButtonClicked());
		deleteSelectedStudentButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				admin_deleteSelectedStaffButtonClicked();
		});

		int width = 160;
		HBox deleteManuallyPane = new HBox(hgap);
		deleteManuallyPane.setAlignment(Pos.CENTER);
		dropcrnTF = new TextField();
		dropcrnTF.setPromptText("Staff ID");
		dropcrnTF.setMinWidth(width);
		Button deleteManuallyButton = new Button("Delete Manually");
		deleteManuallyButton.setMinWidth(width);

		deleteManuallyButton.setOnAction(e -> admin_deleteStaffManuallyButtonClicked());
		deleteManuallyButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				admin_deleteStaffManuallyButtonClicked();
		});

		deleteManuallyPane.getChildren().addAll(dropcrnTF, deleteManuallyButton);

		deletePane1.getChildren().addAll(deleteSelectedStudentButton, deleteManuallyPane);

		deletePane.setCenter(staffTable);
		deletePane.setBottom(deletePane1);

		adminPane.setCenter(deletePane);
	}

	private void admin_deleteSelectedStaffButtonClicked() {
		try {
			Staff item = staffTable.getSelectionModel().getSelectedItem();
			query("DELETE FROM staff WHERE staffID = '" + item.getStaffid() + "'");

			createStaffListTable("SELECT * FROM staff");
			deletePane.setCenter(staffTable);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void admin_deleteStaffManuallyButtonClicked() {
		String id = dropcrnTF.getText();

		if (id.matches("ST\\d{4}")) {
			query("DELETE FROM staff WHERE staffID = '" + id + "'");

			createStaffListTable("SELECT * FROM staff");
			deletePane.setCenter(staffTable);
		}
	}

	private void admin_addInstructorButtonClicked() {

		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}

		GridPane addStudentPane = new GridPane();
		addStudentPane.setAlignment(Pos.CENTER);
		addStudentPane.setVgap(vgap);
		addStudentPane.setHgap(hgap);

		Text idText = new Text("Instructor ID:");
		idText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField idTF = new TextField();
		idTF.setPromptText("I0000");

		Text fnText = new Text("First Name:");
		fnText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField fnTF = new TextField();
		fnTF.setPromptText("First Name");

		Text lnText = new Text("Last Name:");
		lnText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField lnTF = new TextField();
		lnTF.setPromptText("Last Name");

		Text sexText = new Text("Sex:");
		sexText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField sexTF = new TextField();
		sexTF.setPromptText("M or F");

		Text dobText = new Text("Dob:");
		dobText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField dobTF = new TextField();
		dobTF.setPromptText("YYYY-MM-DD");

		Text cgpaText = new Text("Salary:");
		cgpaText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField salaryTF = new TextField();
		salaryTF.setPromptText("Salary");

		Text mgpaText = new Text("Employment:");
		mgpaText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField employmentTF = new TextField();
		employmentTF.setPromptText("Full/Part Time");

		Text emailText = new Text("Email:");
		emailText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField emailTF = new TextField();
		emailTF.setPromptText("Email");

		Text passwordText = new Text("Password:");
		passwordText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField passwordTF = new TextField();
		passwordTF.setPromptText("Password");

		Text programText = new Text("Office:");
		programText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField officeTF = new TextField();
		officeTF.setPromptText("Room Number");

		Text advisorText = new Text("Teaches in:");
		advisorText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField teachesinTF = new TextField();
		teachesinTF.setPromptText("Department ID");

		Button gap = new Button();
		gap.setMinWidth(20);
		gap.setVisible(false);
		gap.setFocusTraversable(false);
		Button clearButton = new Button("Clear");
		clearButton.setMinWidth(150);
		clearButton.setOnAction(e -> {
			idTF.clear();
			fnTF.clear();
			lnTF.clear();
			sexTF.clear();
			dobTF.clear();
			salaryTF.clear();
			employmentTF.clear();
			emailTF.clear();
			passwordTF.clear();
			officeTF.clear();
			teachesinTF.clear();
			msgText.setText("");
		});
		clearButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				idTF.clear();
				fnTF.clear();
				lnTF.clear();
				sexTF.clear();
				dobTF.clear();
				salaryTF.clear();
				employmentTF.clear();
				emailTF.clear();
				passwordTF.clear();
				officeTF.clear();
				teachesinTF.clear();
				msgText.setText("");
			}

		});

		msgText = new Text("");
		msgText.setStyle("-fx-font-style: italic");

		Button submitButton = new Button("Submit");
		submitButton.setMinWidth(150);
		submitButton.setOnAction(e -> {

			if (idTF.getText().matches("I\\d{4}")) {
				try {
					String q = "INSERT INTO FACULTY VALUES('" + idTF.getText() + "','" + fnTF.getText() + "','"
							+ lnTF.getText() + "','" + sexTF.getText() + "','" + dobTF.getText() + "','"
							+ salaryTF.getText() + "','" + employmentTF.getText() + "','" + emailTF.getText() + "','"
							+ passwordTF.getText() + "','" + officeTF.getText() + "','" + teachesinTF.getText() + "')";

					PreparedStatement ps = getConnection().prepareStatement(q);
					ps.executeUpdate();
					msgText.setText("New instructor added");
					msgText.setFill(Color.BLUE);

				} catch (SQLException e1) {
					msgText.setText("Error adding new instructor! Please try again");
					msgText.setFill(Color.RED);
					e1.printStackTrace();
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			} else {
				msgText.setText("Wrong ID format! It should be I0000");
				msgText.setFill(Color.RED);
			}

		});
		submitButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {

				if (idTF.getText().matches("I\\d{4}")) {
					try {
						String q = "INSERT INTO FACULTY VALUES('" + idTF.getText() + "','" + fnTF.getText() + "','"
								+ lnTF.getText() + "','" + sexTF.getText() + "','" + dobTF.getText() + "','"
								+ salaryTF.getText() + "','" + employmentTF.getText() + "','" + emailTF.getText()
								+ "','" + passwordTF.getText() + "','" + officeTF.getText() + "','"
								+ teachesinTF.getText() + "')";

						PreparedStatement ps = getConnection().prepareStatement(q);
						ps.executeUpdate();
						msgText.setText("New instructor added");
						msgText.setFill(Color.BLUE);

					} catch (SQLException e1) {
						msgText.setText("Error adding new instructor! Please try again");
						msgText.setFill(Color.RED);
						e1.printStackTrace();
					} catch (Exception e1) {

						e1.printStackTrace();
					}
				} else {
					msgText.setText("Wrong ID format! It should be I0000");
					msgText.setFill(Color.RED);
				}

			}
		});

		addStudentPane.add(idText, 0, 0);
		addStudentPane.add(idTF, 1, 0);
		addStudentPane.add(fnText, 0, 1);
		addStudentPane.add(fnTF, 1, 1);
		addStudentPane.add(lnText, 0, 2);
		addStudentPane.add(lnTF, 1, 2);
		addStudentPane.add(sexText, 0, 3);
		addStudentPane.add(sexTF, 1, 3);
		addStudentPane.add(dobText, 0, 4);
		addStudentPane.add(dobTF, 1, 4);
		addStudentPane.add(cgpaText, 0, 5);
		addStudentPane.add(salaryTF, 1, 5);
		addStudentPane.add(gap, 2, 0);
		addStudentPane.add(mgpaText, 3, 0);
		addStudentPane.add(employmentTF, 4, 0);
		addStudentPane.add(emailText, 3, 1);
		addStudentPane.add(emailTF, 4, 1);
		addStudentPane.add(passwordText, 3, 2);
		addStudentPane.add(passwordTF, 4, 2);
		addStudentPane.add(programText, 3, 3);
		addStudentPane.add(officeTF, 4, 3);
		addStudentPane.add(advisorText, 3, 4);
		addStudentPane.add(teachesinTF, 4, 4);
		addStudentPane.add(clearButton, 4, 7);
		addStudentPane.add(submitButton, 4, 6);
		addStudentPane.add(msgText, 1, 6, 3, 2);

		adminPane.setCenter(addStudentPane);
	}

	private void admin_addStudentButtonClicked() {

		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}

		GridPane addStudentPane = new GridPane();
		addStudentPane.setAlignment(Pos.CENTER);
		addStudentPane.setVgap(vgap);
		addStudentPane.setHgap(hgap);

		Text idText = new Text("Student ID:");
		idText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField idTF = new TextField();
		idTF.setPromptText("S0000");

		Text fnText = new Text("First Name:");
		fnText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField fnTF = new TextField();
		fnTF.setPromptText("First Name");

		Text lnText = new Text("Last Name:");
		lnText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField lnTF = new TextField();
		lnTF.setPromptText("Last Name");

		Text sexText = new Text("Sex:");
		sexText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField sexTF = new TextField();
		sexTF.setPromptText("M or F");

		Text dobText = new Text("Dob:");
		dobText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField dobTF = new TextField();
		dobTF.setPromptText("YYYY-MM-DD");

		Text cgpaText = new Text("CGPA:");
		cgpaText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField cgpaTF = new TextField();
		cgpaTF.setPromptText("CGPA");

		Text mgpaText = new Text("MGPA:");
		mgpaText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField mgpaTF = new TextField();
		mgpaTF.setPromptText("MGPA");

		Text minorText = new Text("Minor:");
		minorText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField minorTF = new TextField();
		minorTF.setPromptText("Minor");

		Text emailText = new Text("Email:");
		emailText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField emailTF = new TextField();
		emailTF.setPromptText("Email");

		Text passwordText = new Text("Password:");
		passwordText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField passwordTF = new TextField();
		passwordTF.setPromptText("Password");

		Text programText = new Text("Program:");
		programText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField programTF = new TextField();
		programTF.setPromptText("B.E.COE");

		Text advisorText = new Text("Advisor ID:");
		advisorText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField advisorTF = new TextField();
		advisorTF.setPromptText("I0000");

		Button gap = new Button();
		gap.setMinWidth(20);
		gap.setVisible(false);
		gap.setFocusTraversable(false);
		Button clearButton = new Button("Clear");
		clearButton.setMinWidth(150);
		clearButton.setOnAction(e -> {
			idTF.clear();
			fnTF.clear();
			lnTF.clear();
			sexTF.clear();
			dobTF.clear();
			cgpaTF.clear();
			mgpaTF.clear();
			minorTF.clear();
			emailTF.clear();
			passwordTF.clear();
			programTF.clear();
			advisorTF.clear();
			msgText.setText("");
		});
		clearButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				idTF.clear();
				fnTF.clear();
				lnTF.clear();
				sexTF.clear();
				dobTF.clear();
				cgpaTF.clear();
				mgpaTF.clear();
				minorTF.clear();
				emailTF.clear();
				passwordTF.clear();
				programTF.clear();
				advisorTF.clear();
				msgText.setText("");
			}

		});

		msgText = new Text("");
		msgText.setStyle("-fx-font-style: italic");

		Button submitButton = new Button("Submit");
		submitButton.setMinWidth(150);
		submitButton.setOnAction(e -> {

			if (idTF.getText().matches("S\\d{4}")) {
				try {
					String q = "INSERT INTO STUDENT VALUES('" + idTF.getText() + "','" + fnTF.getText() + "','"
							+ lnTF.getText() + "','" + sexTF.getText() + "','" + dobTF.getText() + "',"
							+ cgpaTF.getText() + "," + mgpaTF.getText() + ",'" + minorTF.getText() + "','"
							+ emailTF.getText() + "','" + passwordTF.getText() + "','" + programTF.getText() + "','"
							+ advisorTF.getText() + "')";

					PreparedStatement ps = getConnection().prepareStatement(q);
					ps.executeUpdate();
					msgText.setText("New student added");
					msgText.setFill(Color.BLUE);

				} catch (SQLException e1) {
					msgText.setText("Error adding new student! Please try again");
					msgText.setFill(Color.RED);

				} catch (Exception e1) {

				}
			} else {
				msgText.setText("Wrong ID format! It should be S0000");
				msgText.setFill(Color.RED);
			}
		});

		submitButton.setOnKeyPressed(e -> {
			if (idTF.getText().matches("S\\d{4}")) {
				try {
					String q = "INSERT INTO STUDENT VALUES('" + idTF.getText() + "','" + fnTF.getText() + "','"
							+ lnTF.getText() + "','" + sexTF.getText() + "','" + dobTF.getText() + "',"
							+ cgpaTF.getText() + "," + mgpaTF.getText() + ",'" + minorTF.getText() + "','"
							+ emailTF.getText() + "','" + passwordTF.getText() + "','" + programTF.getText() + "','"
							+ advisorTF.getText() + "')";

					PreparedStatement ps = getConnection().prepareStatement(q);
					ps.executeUpdate();
					msgText.setText("New student added");
					msgText.setFill(Color.BLUE);

				} catch (SQLException e1) {
					msgText.setText("Error adding new student! Please try again");
					msgText.setFill(Color.RED);
					e1.printStackTrace();
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			} else {
				msgText.setText("Wrong ID format! It should be like S0000");
				msgText.setFill(Color.RED);
			}

		});

		addStudentPane.add(idText, 0, 0);
		addStudentPane.add(idTF, 1, 0);
		addStudentPane.add(fnText, 0, 1);
		addStudentPane.add(fnTF, 1, 1);
		addStudentPane.add(lnText, 0, 2);
		addStudentPane.add(lnTF, 1, 2);
		addStudentPane.add(sexText, 0, 3);
		addStudentPane.add(sexTF, 1, 3);
		addStudentPane.add(dobText, 0, 4);
		addStudentPane.add(dobTF, 1, 4);
		addStudentPane.add(cgpaText, 0, 5);
		addStudentPane.add(cgpaTF, 1, 5);
		addStudentPane.add(gap, 2, 0);
		addStudentPane.add(mgpaText, 3, 0);
		addStudentPane.add(mgpaTF, 4, 0);
		addStudentPane.add(minorText, 3, 1);
		addStudentPane.add(minorTF, 4, 1);
		addStudentPane.add(emailText, 3, 2);
		addStudentPane.add(emailTF, 4, 2);
		addStudentPane.add(passwordText, 3, 3);
		addStudentPane.add(passwordTF, 4, 3);
		addStudentPane.add(programText, 3, 4);
		addStudentPane.add(programTF, 4, 4);
		addStudentPane.add(advisorText, 3, 5);
		addStudentPane.add(advisorTF, 4, 5);
		addStudentPane.add(clearButton, 4, 9);
		addStudentPane.add(submitButton, 4, 8);
		addStudentPane.add(msgText, 1, 8, 3, 2);

		adminPane.setCenter(addStudentPane);
	}

	private void admin_addCourseButtonClicked() {

		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}

		GridPane addStudentPane = new GridPane();
		addStudentPane.setAlignment(Pos.CENTER);
		addStudentPane.setVgap(vgap);
		addStudentPane.setHgap(hgap);

		Text idText = new Text("Course ID:");
		idText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField idTF = new TextField();
		idTF.setPromptText("AAA 111");

		Text fnText = new Text("Credits:");
		fnText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField creditsTF = new TextField();
		creditsTF.setPromptText("Number of credits");

		Text lnText = new Text("Title:");
		lnText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField titleTF = new TextField();
		titleTF.setPromptText("Course title");

		Text sexText = new Text("Level");
		sexText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField levelTF = new TextField();
		levelTF.setPromptText("Undergraduate/Graduate");

		msgText = new Text("");
		msgText.setStyle("-fx-font-style: italic");

		Button clearButton = new Button("Clear");
		clearButton.setMinWidth(150);
		clearButton.setOnAction(e -> {
			idTF.clear();
			creditsTF.clear();
			titleTF.clear();
			levelTF.clear();
			msgText.setText("");
		});
		clearButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				idTF.clear();
				creditsTF.clear();
				titleTF.clear();
				levelTF.clear();
				msgText.setText("");
			}

		});

		Button submitButton = new Button("Submit");
		submitButton.setMinWidth(150);
		submitButton.setOnAction(e -> {

			if (idTF.getText().matches("[A-Z]{3}\\s\\d{3}")) {
				try {
					String q = "INSERT INTO COURSE VALUES('" + idTF.getText() + "','" + creditsTF.getText() + "','"
							+ titleTF.getText() + "','" + levelTF.getText() + "')";

					PreparedStatement ps = getConnection().prepareStatement(q);
					ps.executeUpdate();
					msgText.setText("New Course added");
					msgText.setFill(Color.BLUE);

				} catch (SQLException e1) {
					msgText.setText("Error adding new course!\nPlease try again");
					msgText.setFill(Color.RED);

				} catch (Exception e1) {

				}
			} else {
				msgText.setText("Wrong ID format!\nIt should be like AAA 111");
				msgText.setFill(Color.RED);
			}
		});
		submitButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (idTF.getText().matches("[A-Z]{3}\\s\\d{3}")) {
					try {
						String q = "INSERT INTO COURSE VALUES('" + idTF.getText() + "','" + creditsTF.getText() + "','"
								+ titleTF.getText() + "','" + levelTF.getText() + "')";

						PreparedStatement ps = getConnection().prepareStatement(q);
						ps.executeUpdate();
						msgText.setText("New Course added");
						msgText.setFill(Color.BLUE);

					} catch (SQLException e1) {
						msgText.setText("Error adding new course!\nPlease try again");
						msgText.setFill(Color.RED);

					} catch (Exception e1) {

					}
				} else {
					msgText.setText("Wrong ID format!\nIt should be like AAA 111");
					msgText.setFill(Color.RED);
				}
			}

		});
		addStudentPane.add(idText, 0, 0);
		addStudentPane.add(idTF, 1, 0);
		addStudentPane.add(fnText, 0, 1);
		addStudentPane.add(creditsTF, 1, 1);
		addStudentPane.add(lnText, 0, 2);
		addStudentPane.add(titleTF, 1, 2);
		addStudentPane.add(sexText, 0, 3);
		addStudentPane.add(levelTF, 1, 3);
		addStudentPane.add(clearButton, 1, 7);
		addStudentPane.add(submitButton, 1, 6);
		addStudentPane.add(msgText, 1, 8);

		adminPane.setCenter(addStudentPane);
	}

	private void admin_addSectionButtonClicked() {

		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}

		GridPane addStudentPane = new GridPane();
		addStudentPane.setAlignment(Pos.CENTER);
		addStudentPane.setVgap(vgap);
		addStudentPane.setHgap(hgap);

		Text idText = new Text("CRN:");
		idText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField crnTF = new TextField();
		crnTF.setPromptText("CRN");

		Text fnText = new Text("Instance of:");
		fnText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField instanceofTF = new TextField();
		instanceofTF.setPromptText("Course ID");

		Text lnText = new Text("Term:");
		lnText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField termTF = new TextField();
		termTF.setPromptText("Fall/Spring");

		Text sexText = new Text("Year:");
		sexText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField yearTF = new TextField();
		yearTF.setPromptText("Year");

		Text dobText = new Text("Sched:");
		dobText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField schedTF = new TextField();
		schedTF.setPromptText("MWF/TR");

		Text mgpaText = new Text("Start Time:");
		mgpaText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField stimeTF = new TextField();
		stimeTF.setPromptText("HH:MM:SS");

		Text minorText = new Text("End Time:");
		minorText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField etimeTF = new TextField();
		etimeTF.setPromptText("HH:MM:SS");

		Text emailText = new Text("Capacity:");
		emailText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField capacityTF = new TextField();
		capacityTF.setPromptText("Capacity");

		Text passwordText = new Text("Taught by:");
		passwordText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField taughtbyTF = new TextField();
		taughtbyTF.setPromptText("Instructor ID");

		Text programText = new Text("In room:");
		programText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField inroomTF = new TextField();
		inroomTF.setPromptText("Room ID");

		Button gap = new Button();
		gap.setMinWidth(20);
		gap.setVisible(false);
		gap.setFocusTraversable(false);
		Button clearButton = new Button("Clear");
		clearButton.setMinWidth(150);
		clearButton.setOnAction(e -> {
			crnTF.clear();
			instanceofTF.clear();
			termTF.clear();
			yearTF.clear();
			schedTF.clear();
			stimeTF.clear();
			etimeTF.clear();
			capacityTF.clear();
			taughtbyTF.clear();
			inroomTF.clear();
			msgText.setText("");
		});
		clearButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				crnTF.clear();
				instanceofTF.clear();
				termTF.clear();
				yearTF.clear();
				schedTF.clear();
				stimeTF.clear();
				etimeTF.clear();
				capacityTF.clear();
				taughtbyTF.clear();
				inroomTF.clear();
				msgText.setText("");
			}

		});

		msgText = new Text("");
		msgText.setStyle("-fx-font-style: italic");

		Button submitButton = new Button("Submit");
		submitButton.setMinWidth(150);
		submitButton.setOnAction(e -> {
			if (crnTF.getText().matches("\\d{6}")) {
				try {
					String q = "INSERT INTO SECTION VALUES('" + crnTF.getText() + "','" + instanceofTF.getText() + "','"
							+ termTF.getText() + "','" + yearTF.getText() + "','" + schedTF.getText() + "','"
							+ stimeTF.getText() + "','" + etimeTF.getText() + "','" + capacityTF.getText() + "','"
							+ taughtbyTF.getText() + "','" + inroomTF.getText() + "')";

					PreparedStatement ps = getConnection().prepareStatement(q);
					ps.executeUpdate();
					msgText.setText("New section added");
					msgText.setFill(Color.BLUE);

				} catch (SQLException e1) {
					msgText.setText("Error adding new section! Please try again");
					msgText.setFill(Color.RED);

				} catch (Exception e1) {

				}
			} else {
				msgText.setText("Wrong CRN format! It should be like 111111");
				msgText.setFill(Color.RED);
			}
		});
		submitButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {

				if (crnTF.getText().matches("\\d{6}")) {
					try {
						String q = "INSERT INTO SECTION VALUES('" + crnTF.getText() + "','" + instanceofTF.getText()
								+ "','" + termTF.getText() + "','" + yearTF.getText() + "','" + schedTF.getText()
								+ "','" + stimeTF.getText() + "','" + etimeTF.getText() + "','" + capacityTF.getText()
								+ "','" + taughtbyTF.getText() + "','" + inroomTF.getText() + "')";

						PreparedStatement ps = getConnection().prepareStatement(q);
						ps.executeUpdate();
						msgText.setText("New section added");
						msgText.setFill(Color.BLUE);

					} catch (SQLException e1) {
						msgText.setText("Error adding new section! Please try again");
						msgText.setFill(Color.RED);

					} catch (Exception e1) {

					}
				} else {
					msgText.setText("Wrong CRN format! It should be like 111111");
					msgText.setFill(Color.RED);
				}
			}
		});
		addStudentPane.add(idText, 0, 0);
		addStudentPane.add(crnTF, 1, 0);
		addStudentPane.add(fnText, 0, 1);
		addStudentPane.add(instanceofTF, 1, 1);
		addStudentPane.add(lnText, 0, 2);
		addStudentPane.add(termTF, 1, 2);
		addStudentPane.add(sexText, 0, 3);
		addStudentPane.add(yearTF, 1, 3);
		addStudentPane.add(dobText, 0, 4);
		addStudentPane.add(schedTF, 1, 4);
		addStudentPane.add(gap, 2, 0);
		addStudentPane.add(mgpaText, 3, 0);
		addStudentPane.add(stimeTF, 4, 0);
		addStudentPane.add(minorText, 3, 1);
		addStudentPane.add(etimeTF, 4, 1);
		addStudentPane.add(emailText, 3, 2);
		addStudentPane.add(capacityTF, 4, 2);
		addStudentPane.add(passwordText, 3, 3);
		addStudentPane.add(taughtbyTF, 4, 3);
		addStudentPane.add(programText, 3, 4);
		addStudentPane.add(inroomTF, 4, 4);
		addStudentPane.add(clearButton, 4, 9);
		addStudentPane.add(submitButton, 4, 8);
		addStudentPane.add(msgText, 1, 8, 3, 2);

		adminPane.setCenter(addStudentPane);

	}

	private void admin_addStaffButtonClicked() {

		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}

		GridPane addStudentPane = new GridPane();
		addStudentPane.setAlignment(Pos.CENTER);
		addStudentPane.setVgap(vgap);
		addStudentPane.setHgap(hgap);

		Text idText = new Text("Staff ID:");
		idText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField idTF = new TextField();
		idTF.setPromptText("ST0000");

		Text fnText = new Text("First Name:");
		fnText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField fnTF = new TextField();
		fnTF.setPromptText("First Name");

		Text lnText = new Text("Last Name:");
		lnText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField lnTF = new TextField();
		lnTF.setPromptText("Last Name");

		Text sexText = new Text("Sex:");
		sexText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField sexTF = new TextField();
		sexTF.setPromptText("M or F");

		Text dobText = new Text("Dob:");
		dobText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField dobTF = new TextField();
		dobTF.setPromptText("YYYY-MM-DD");

		Text cgpaText = new Text("Salary:");
		cgpaText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField salaryTF = new TextField();
		salaryTF.setPromptText("Salary");

		Text mgpaText = new Text("Employment:");
		mgpaText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField employmentTF = new TextField();
		employmentTF.setPromptText("Full/Part Time");

		Text emailText = new Text("Email:");
		emailText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField emailTF = new TextField();
		emailTF.setPromptText("Email");

		Text passwordText = new Text("Password:");
		passwordText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField passwordTF = new TextField();
		passwordTF.setPromptText("Password");

		Text programText = new Text("Admin:");
		programText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField adminTF = new TextField();
		adminTF.setPromptText("1 or 0");

		Text advisorText = new Text("Works in:");
		advisorText.setStyle("-fx-font-weight: bold; -fx-underline: true");
		TextField worksinTF = new TextField();
		worksinTF.setPromptText("Department ID");

		Button gap = new Button();
		gap.setMinWidth(20);
		gap.setVisible(false);
		gap.setFocusTraversable(false);
		Button clearButton = new Button("Clear");
		clearButton.setMinWidth(150);
		clearButton.setOnAction(e -> {
			idTF.clear();
			fnTF.clear();
			lnTF.clear();
			sexTF.clear();
			dobTF.clear();
			salaryTF.clear();
			employmentTF.clear();
			emailTF.clear();
			passwordTF.clear();
			adminTF.clear();
			worksinTF.clear();
			msgText.setText("");
		});
		clearButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				idTF.clear();
				fnTF.clear();
				lnTF.clear();
				sexTF.clear();
				dobTF.clear();
				salaryTF.clear();
				employmentTF.clear();
				emailTF.clear();
				passwordTF.clear();
				adminTF.clear();
				worksinTF.clear();
				msgText.setText("");
			}

		});

		msgText = new Text("");
		msgText.setStyle("-fx-font-style: italic");

		Button submitButton = new Button("Submit");
		submitButton.setMinWidth(150);
		submitButton.setOnAction(e -> {

			if (idTF.getText().matches("ST\\d{4}")) {
				try {
					String q = "INSERT INTO STAFF VALUES('" + idTF.getText() + "','" + fnTF.getText() + "','"
							+ lnTF.getText() + "','" + sexTF.getText() + "','" + dobTF.getText() + "','"
							+ salaryTF.getText() + "','" + employmentTF.getText() + "','" + emailTF.getText() + "','"
							+ passwordTF.getText() + "','" + adminTF.getText() + "','" + worksinTF.getText() + "')";

					PreparedStatement ps = getConnection().prepareStatement(q);
					ps.executeUpdate();
					msgText.setText("New staff added");
					msgText.setFill(Color.BLUE);

				} catch (SQLException e1) {
					msgText.setText("Error adding new staff! Please try again");
					msgText.setFill(Color.RED);
					e1.printStackTrace();
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			} else {
				msgText.setText("Wrong ID format! It should be ST0000");
				msgText.setFill(Color.RED);
			}

		});
		submitButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {

				if (idTF.getText().matches("ST\\d{4}")) {
					try {
						String q = "INSERT INTO STAFF VALUES('" + idTF.getText() + "','" + fnTF.getText() + "','"
								+ lnTF.getText() + "','" + sexTF.getText() + "','" + dobTF.getText() + "','"
								+ salaryTF.getText() + "','" + employmentTF.getText() + "','" + emailTF.getText()
								+ "','" + passwordTF.getText() + "','" + adminTF.getText() + "','" + worksinTF.getText()
								+ "')";

						PreparedStatement ps = getConnection().prepareStatement(q);
						ps.executeUpdate();
						msgText.setText("New staff added");
						msgText.setFill(Color.BLUE);

					} catch (SQLException e1) {
						msgText.setText("Error adding new staff! Please try again");
						msgText.setFill(Color.RED);
						e1.printStackTrace();
					} catch (Exception e1) {

						e1.printStackTrace();
					}
				} else {
					msgText.setText("Wrong ID format! It should be ST0000");
					msgText.setFill(Color.RED);
				}
			}
		});

		addStudentPane.add(idText, 0, 0);
		addStudentPane.add(idTF, 1, 0);
		addStudentPane.add(fnText, 0, 1);
		addStudentPane.add(fnTF, 1, 1);
		addStudentPane.add(lnText, 0, 2);
		addStudentPane.add(lnTF, 1, 2);
		addStudentPane.add(sexText, 0, 3);
		addStudentPane.add(sexTF, 1, 3);
		addStudentPane.add(dobText, 0, 4);
		addStudentPane.add(dobTF, 1, 4);
		addStudentPane.add(cgpaText, 0, 5);
		addStudentPane.add(salaryTF, 1, 5);
		addStudentPane.add(gap, 2, 0);
		addStudentPane.add(mgpaText, 3, 0);
		addStudentPane.add(employmentTF, 4, 0);
		addStudentPane.add(emailText, 3, 1);
		addStudentPane.add(emailTF, 4, 1);
		addStudentPane.add(passwordText, 3, 2);
		addStudentPane.add(passwordTF, 4, 2);
		addStudentPane.add(programText, 3, 3);
		addStudentPane.add(adminTF, 4, 3);
		addStudentPane.add(advisorText, 3, 4);
		addStudentPane.add(worksinTF, 4, 4);
		addStudentPane.add(clearButton, 4, 7);
		addStudentPane.add(submitButton, 4, 6);
		addStudentPane.add(msgText, 1, 6, 3, 2);

		adminPane.setCenter(addStudentPane);

	}

	private void admin_viewFAStudentsButtonClicked() {

		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			staffTable.getItems().clear();
		} catch (Exception e1) {

		}

		BorderPane fasPane = new BorderPane();
		VBox buttonPane = new VBox(vgap);
		buttonPane.setPadding(insets);
		buttonPane.setAlignment(Pos.CENTER);
		Button viewStudent = new Button("View Selected Student");
		buttonPane.getChildren().add(viewStudent);
		createFAListTable(
				"select SS.studentID, SS.manager, S.firstname, S.lastname, SS.term, SS.contractyear, SS.percentage, SS.salary, SS.location from STUDENT_STAFF SS, STUDENT S where SS.studentID = S.studentID AND manager='"
						+ loginID + "'");
		fasPane.setCenter(faTable);
		fasPane.setBottom(buttonPane);

		adminPane.setCenter(fasPane);

		viewStudent.setOnAction(e -> admin_viewFAS_viewStudentButtonClicked());

		viewStudent.setOnKeyTyped(e -> {
			if (e.getCode() == KeyCode.ENTER)
				staff_viewFAS_viewStudentButtonClicked();
		});
	}

	private void admin_viewFAS_viewStudentButtonClicked() {
		try {
			FA item = faTable.getSelectionModel().getSelectedItem();
			createStudentListTable("SELECT * FROM STUDENT WHERE studentID='" + item.getId() + "'");
			adminPane.setCenter(studentsTable);
		} catch (Exception e) {

		}

	}

	private void admin_viewDetailsButtonClicked() {
		createStaffListTable("SELECT * FROM STAFF WHERE staffID = \"" + loginID + "\"");
		VBox vb = new VBox(vgap);
		vb.setAlignment(Pos.CENTER);
		Button changePasswordButton = new Button("Change Your Password");
		changePasswordButton.setOnAction(e -> createPasswordScene());
		changePasswordButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				createPasswordScene();
		});

		vb.getChildren().addAll(staffTable, changePasswordButton);
		adminPane.setCenter(vb);
		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			instructorsTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e1) {

		}
		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}
	}

	private void instructor_submitButtonClicked() {

		if (passwordTF.getText().equals(confirmPasswordTF.getText()) && !((passwordTF.getText()).equals(""))) {

			query("UPDATE faculty set passwd =\"" + passwordTF.getText() + "\" where facultyID=\"" + loginID + "\"");
			changePassMessage.setText("Password changed");
			changePassMessage.setTextFill(Color.BLUE);
		} else {
			changePassMessage.setText("Passwords do not match! Please try again");
			changePassMessage.setTextFill(Color.RED);

		}
	}

	private void instructor_viewCourseButtonClicked() {
		createCourseListTable(
				"select DISTINCT co.* from section S , FACULTY_COURSE C , Course co where S.isinstanceof = C.courseID and S.isinstanceof = co.courseID and term = 'Fall' and sectionyear = 2016 AND facultyID ='"
						+ loginID + "'");
		instructorPane.setCenter(coursesTable);

		try {
			sectionsTable.getItems().clear();
		} catch (Exception e) {

		}
		try {
			studentsTable.getItems().clear();
		} catch (Exception e) {

		}
	}

	private void instructor_viewSectionButtonClicked() {
		try {
			instructorViewSectionsPane = new BorderPane();
			VBox vb = new VBox(2);
			vb.setAlignment(Pos.CENTER);
			HBox buttonsPane = new HBox(hgap);
			buttonsPane.setAlignment(Pos.CENTER);
			buttonsPane.setPadding(insets);
			Button submitButton = new Button("Submit");
			idTF = new TextField();
			idTF.setPromptText("Course ID");
			buttonsPane.getChildren().addAll(idTF, submitButton);
			msgText = new Text("");
			msgText.setStyle("-fx-font-style: italic");
			vb.getChildren().addAll(msgText, buttonsPane);
			instructorViewSectionsPane.setBottom(vb);

			idTF.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					instructor_viewSections_submitButtonPressed();
			});

			submitButton.setOnAction(e -> instructor_viewSections_submitButtonPressed());
			submitButton.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					instructor_viewSections_submitButtonPressed();
			});

			Course item = coursesTable.getSelectionModel().getSelectedItem();
			createSectionsListTable("Select * from section where isinstanceof='" + item.getCourseid()
					+ "' AND term = 'FALL' AND sectionyear = 2016");

			instructorViewSectionsPane.setCenter(sectionsTable);
			instructorPane.setCenter(instructorViewSectionsPane);

			try {
				coursesTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				studentsTable.getItems().clear();
			} catch (Exception e1) {

			}

		} catch (Exception e) {
			TableView<Section> table = new TableView<>();
			instructorViewSectionsPane.setCenter(table);
			instructorPane.setCenter(instructorViewSectionsPane);
			try {
				coursesTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				studentsTable.getItems().clear();
			} catch (Exception e1) {

			}

		}
	}

	private void instructor_viewSections_submitButtonPressed() {

		try {

			try {
				sectionsTable.getItems().clear();
			} catch (Exception e1) {

			}

			createCourseListTable(
					"select DISTINCT co.* from section S , FACULTY_COURSE C , Course co where S.isinstanceof = C.courseID and S.isinstanceof = co.courseID and term = 'Fall' and sectionyear = 2016 AND facultyID ='"
							+ loginID + "'");

			int numberOfRows = coursesTable.getItems().size();

			for (int i = 0; i < numberOfRows; i++) {
				if (idTF.getText().equals(coursesTable.getItems().get(i).getCourseid())) {
					createSectionsListTable("Select * from section where isinstanceof='" + idTF.getText()
							+ "' AND term = 'FALL' AND sectionyear = 2016");

				}

			}
			try {
				coursesTable.getItems().clear();
			} catch (Exception e) {

			}

			sectionsTable.getItems().get(0);
			instructorViewSectionsPane.setCenter(sectionsTable);
			msgText.setText("");

		} catch (Exception e) {

			TableView<Section> table = new TableView<>();
			instructorViewSectionsPane.setCenter(table);
			msgText.setText("Wrong Course ID! Please enter a valid one");
			msgText.setFill(Color.RED);

		}
	}

	private void instructor_viewStudentsButtonClicked() {

		try {
			instructorViewSectionsPane = new BorderPane();
			VBox vb = new VBox(2);
			vb.setAlignment(Pos.CENTER);
			HBox buttonsPane = new HBox(hgap);
			buttonsPane.setAlignment(Pos.CENTER);
			buttonsPane.setPadding(insets);
			Button submitButton = new Button("Submit");
			idTF = new TextField();
			idTF.setPromptText("Section CRN");
			buttonsPane.getChildren().addAll(idTF, submitButton);
			msgText = new Text("");
			msgText.setStyle("-fx-font-style: italic");
			vb.getChildren().addAll(msgText, buttonsPane);
			instructorViewSectionsPane.setBottom(vb);

			idTF.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					instructor_viewStudents_submitButtonPressed();
			});

			submitButton.setOnAction(e -> instructor_viewStudents_submitButtonPressed());
			submitButton.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					instructor_viewStudents_submitButtonPressed();
			});

			Section item = sectionsTable.getSelectionModel().getSelectedItem();

			createStudentListTable("SELECT Student.* FROM STUDENT_SECTION JOIN STUDENT on crn =" + item.getCrn()
					+ " AND student.studentID = student_section.studentID");

			instructorViewSectionsPane.setCenter(studentsTable);
			instructorPane.setCenter(instructorViewSectionsPane);

			try {
				coursesTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				sectionsTable.getItems().clear();
			} catch (Exception e1) {

			}

		} catch (Exception e) {
			TableView<Section> table = new TableView<>();
			instructorViewSectionsPane.setCenter(table);
			instructorPane.setCenter(instructorViewSectionsPane);

			try {
				coursesTable.getItems().clear();
			} catch (Exception e1) {

			}
			try {
				sectionsTable.getItems().clear();
			} catch (Exception e1) {

			}

		}
	}

	private void instructor_viewStudents_submitButtonPressed() {

		try {
			studentsTable.getItems().clear();
		} catch (Exception e1) {

		}

		try {
			createSectionsListTable(
					"select DISTINCT S.* from section S , FACULTY_COURSE C , Course co where S.isinstanceof = C.courseID and S.isinstanceof = co.courseID and term = 'Fall' and sectionyear = 2016 AND facultyID ='"
							+ loginID + "'");

			int numberOfRows = sectionsTable.getItems().size();

			for (int i = 0; i < numberOfRows; i++) {
				if (idTF.getText().equals(sectionsTable.getItems().get(i).getCrn())) {
					createStudentListTable("SELECT Student.* FROM STUDENT_SECTION JOIN STUDENT on crn ="
							+ idTF.getText() + " AND student.studentID = student_section.studentID");

				}

			}
			try {
				sectionsTable.getItems().clear();
			} catch (Exception e) {

			}

			studentsTable.getItems().get(0);
			instructorViewSectionsPane.setCenter(studentsTable);

			msgText.setText("");

		} catch (Exception e) {
			TableView<Section> table = new TableView<>();
			instructorViewSectionsPane.setCenter(table);
			msgText.setText("Wrong Section CRN! Please enter a valid one");
			msgText.setFill(Color.RED);

		}
	}

	private void instuctor_viewAllSectionButtonClicked() {

		createSectionsListTable("SELECT * FROM SECTION WHERE taughtby ='" + loginID + "'");
		instructorPane.setCenter(sectionsTable);

		try {
			studentsTable.getItems().clear();
		} catch (Exception e) {

		}
		try {
			coursesTable.getItems().clear();
		} catch (Exception e) {

		}

	}

	private void instructor_viewAdviseesButtonClicked() {
		BorderPane advisorPane = new BorderPane();
		msgText = new Text("");
		VBox buttonPane = new VBox(vgap);
		buttonPane.setPadding(insets);
		buttonPane.setAlignment(Pos.CENTER);
		Button viewStudentCourses = new Button("View Student Courses");
		buttonPane.getChildren().addAll(msgText, viewStudentCourses);
		createStudentListTable("SELECT * FROM STUDENT WHERE advisor ='" + loginID + "'");
		advisorPane.setCenter(studentsTable);
		advisorPane.setBottom(buttonPane);

		instructorPane.setCenter(advisorPane);

		viewStudentCourses.setOnAction(e -> instructor_viewAdviseeCoursesButtonClicked());

		viewStudentCourses.setOnKeyTyped(e -> {
			if (e.getCode() == KeyCode.ENTER)
				instructor_viewAdviseeCoursesButtonClicked();
		});

	}

	private void instructor_viewAdviseeCoursesButtonClicked() {
		try {
			Student item = studentsTable.getSelectionModel().getSelectedItem();
			createGradesListTable(
					"Select isinstanceof, coursetitle, credits, term , sectionyear, grade from STUDENT_SECTION S , Section se , COURSE C WHERE se.isinstanceof = C.courseID AND S.crn = se.crn AND studentID =\""
							+ item.getId() + "\"");
			instructorPane.setCenter(gradesTable);
		} catch (Exception e) {
			msgText.setText("Nothing is selected!");
			msgText.setStyle("-fx-font-style: italic");
			msgText.setFill(Color.RED);

		}

	}

	private void student_submitButtonClicked() {
		if (passwordTF.getText().equals(confirmPasswordTF.getText()) && !((passwordTF.getText()).equals(""))) {

			query("UPDATE student set passwd =\"" + passwordTF.getText() + "\" where studentID=\"" + loginID + "\"");
			changePassMessage.setText("Password changed");
			changePassMessage.setTextFill(Color.BLUE);
		} else {
			changePassMessage.setText("Passwords do not match! Please try again");
			changePassMessage.setTextFill(Color.RED);

		}

	}

	private void student_currentSectionsButtonClicked() {
		createSectionsListTable(
				"Select  se.* from STUDENT_SECTION S , Section se  WHERE S.crn = se.crn AND grade = 'In Progress' AND studentID=\""
						+ loginID + "\"");
		studentPane.setCenter(sectionsTable);
	}

	private void student_dropButtonClicked() {
		msgText = new Text("");
		dropPane = new BorderPane();
		VBox dropPane1 = new VBox(vgap);
		dropPane1.setAlignment(Pos.CENTER);
		dropPane1.setPadding(new Insets(vgap, 0, hgap, 0));

		createSectionsListTable(
				"Select  se.* from STUDENT_SECTION S , Section se  WHERE S.crn = se.crn AND grade = 'In Progress' AND studentID=\""
						+ loginID + "\"");
		Button dropSelectedCourseButton = new Button("Drop Selected Section");
		dropSelectedCourseButton.setMinWidth(325);

		dropSelectedCourseButton.setOnAction(e -> student_dropSelectedCourseButtonClicked());
		dropSelectedCourseButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				student_dropSelectedCourseButtonClicked();
		});

		int width = 160;
		HBox dropManuallyPane = new HBox(hgap);
		dropManuallyPane.setAlignment(Pos.CENTER);
		dropcrnTF = new TextField();
		dropcrnTF.setPromptText("CRN");
		dropcrnTF.setMinWidth(width);
		Button dropManuallyButton = new Button("Drop Manually");
		dropManuallyButton.setMinWidth(width);

		dropcrnTF.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				student_dropManuallyButtonClicked();
		});

		dropManuallyButton.setOnAction(e -> student_dropManuallyButtonClicked());
		dropManuallyButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				student_dropManuallyButtonClicked();
		});

		dropManuallyPane.getChildren().addAll(dropcrnTF, dropManuallyButton);

		dropPane1.getChildren().addAll(msgText, dropSelectedCourseButton, dropManuallyPane);

		dropPane.setCenter(sectionsTable);
		dropPane.setBottom(dropPane1);

		studentPane.setCenter(dropPane);

	}

	private void student_dropSelectedCourseButtonClicked() {

		try {
			Section item = sectionsTable.getSelectionModel().getSelectedItem();

			query("DELETE FROM STUDENT_SECTION WHERE studentID='" + loginID + "' AND crn = '" + item.getCrn() + "'");

			ObservableList<Section> sectionSelected, allSectionss;
			allSectionss = sectionsTable.getItems();
			sectionSelected = sectionsTable.getSelectionModel().getSelectedItems();

			sectionSelected.forEach(allSectionss::remove);
			msgText.setText("Section Dropped!");
			msgText.setStyle("-fx-font-style: italic");
			msgText.setFill(Color.BLUE);
		} catch (Exception e) {
			msgText.setText("Nothing is Selected!");
			msgText.setStyle("-fx-font-style: italic");
			msgText.setFill(Color.RED);
		}

	}

	private void student_dropManuallyButtonClicked() {
		String crn = dropcrnTF.getText();
		try {
			if (crn.matches("\\d{6}")) {
				query("DELETE FROM STUDENT_SECTION WHERE studentID='" + loginID + "' AND crn = " + crn);

				createSectionsListTable(
						"Select  se.* from STUDENT_SECTION S , Section se  WHERE S.crn = se.crn AND grade = 'In Progress' AND studentID=\""
								+ loginID + "\"");
				dropPane.setCenter(sectionsTable);
				msgText.setText("Section Dropped!");
				msgText.setStyle("-fx-font-style: italic");
				msgText.setFill(Color.BLUE);

			} else {
				msgText.setText("Wrong CRN!");
				msgText.setStyle("-fx-font-style: italic");
				msgText.setFill(Color.RED);
			}
		} catch (Exception e) {
			msgText.setText("Wrong CRN!");
			msgText.setStyle("-fx-font-style: italic");
			msgText.setFill(Color.RED);
		}

	}

	private void student_enrollButtonClicked() {

		try {
			sectionsTable.getItems().clear();
		} catch (Exception e1) {

		}

		enrollPane = new BorderPane();
		VBox enrollPane1 = new VBox(vgap);
		enrollPane1.setPadding(new Insets(vgap, 0, hgap, 0));
		createCourseListTable(
				"SELECT distinct courseID,credits, coursetitle, courselevel FROM section S , COURSE C where S.isinstanceof = C.courseID and term = 'fall' and sectionyear = 2016 "
						+ "and c.courseID not in (Select  se.isinstanceof from STUDENT_SECTION S , Section se WHERE S.crn = se.crn AND grade = 'In Progress' AND studentID='"
						+ loginID + "')");

		int width = 160;
		HBox buttonsPane = new HBox(hgap);
		buttonsPane.setAlignment(Pos.CENTER);
		Button viewSectionsButton = new Button("View Course Sections");
		viewSectionsButton.setMinWidth(width);
		Button registerInSelectedSectionButton = new Button("Register In Seleted Section");
		registerInSelectedSectionButton.setMinWidth(width);
		buttonsPane.getChildren().addAll(viewSectionsButton, registerInSelectedSectionButton);

		registerInSelectedSectionButton.setOnAction(e -> student_enroll_registerSelectedSectionButtonClicked());
		registerInSelectedSectionButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				student_enroll_registerSelectedSectionButtonClicked();
		});

		HBox registerPane = new HBox(hgap);
		registerPane.setAlignment(Pos.CENTER);
		enrollcrnTF = new TextField();
		enrollcrnTF.setPromptText("CRN");
		enrollcrnTF.setMinWidth(width);
		Button registerManuallyButton = new Button("Register Manually");
		registerManuallyButton.setMinWidth(width);
		registerPane.getChildren().addAll(enrollcrnTF, registerManuallyButton);

		enrollcrnTF.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				student_enroll_registerManuallyButtonClicked();
		});
		registerManuallyButton.setOnAction(e -> student_enroll_registerManuallyButtonClicked());
		registerManuallyButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				student_enroll_registerManuallyButtonClicked();

		});
		msgText = new Text("");
		enrollPane1.setAlignment(Pos.CENTER);
		enrollPane1.getChildren().addAll(msgText, buttonsPane, registerPane);

		enrollPane.setCenter(coursesTable);
		enrollPane.setBottom(enrollPane1);

		studentPane.setCenter(enrollPane);

		viewSectionsButton.setOnAction(e -> student_enroll_viewSectionsButtonClicked());
		viewSectionsButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				student_enroll_viewSectionsButtonClicked();
		});
	}

	private void student_enroll_viewSectionsButtonClicked() {
		try {
			Course item = coursesTable.getSelectionModel().getSelectedItem();
			createSectionsListTable("select * from section where isinstanceof =\"" + item.getCourseid() + "\"");
			enrollPane.setCenter(sectionsTable);
			msgText.setText("");

		} catch (Exception e) {
			msgText.setText("No Ccourse is selected!");
			msgText.setStyle("-fx-font-style: italic");
			msgText.setFill(Color.RED);
		}
	}

	private void student_enroll_registerSelectedSectionButtonClicked() {
		try {
			Section item = sectionsTable.getSelectionModel().getSelectedItem();
			int occupiedCapacity = 0;
			int capacity = item.getCapacity();
			String crn = item.getCrn();
		

			query("SELECT COUNT(*) FROM STUDENT_SECTION WHERE crn = " + crn);

			while (queryResult.next()) {
				occupiedCapacity = (int) queryResult.getLong(1);
			}
			if (occupiedCapacity < capacity) {
				query("INSERT INTO STUDENT_SECTION VALUES ('" + loginID + "'," + crn + ", 'In Progress')");
				student_currentSectionsButtonClicked();
			} else {
				msgText.setStyle("-fx-font-style: italic");
				msgText.setFill(Color.RED);
				msgText.setText("Section is full!");
			}
		} catch (SQLException e) {
		} catch (Exception e) {
			msgText.setText("No section is selected!");
			msgText.setStyle("-fx-font-style: italic");
			msgText.setFill(Color.RED);

		}

	}

	private void student_enroll_registerManuallyButtonClicked() {
		try {
			String crn = enrollcrnTF.getText();
			if (crn.matches("\\d{6}")) {

				createSectionsListTable(
						"SELECT * FROM SECTION WHERE term = 'fall' and sectionyear = 2016 and crn not in (Select  se.crn from STUDENT_SECTION S , Section se  WHERE S.crn = se.crn AND grade = 'In Progress' AND studentID=\""
								+ loginID
								+ "\" ) and isinstanceof not in(Select  se.isinstanceof from STUDENT_SECTION S , Section se  WHERE S.crn = se.crn AND grade = 'In Progress' AND studentID=\""
								+ loginID + "\" )");
				int numberOfRows = sectionsTable.getItems().size();
				for (int i = 0; i < numberOfRows; i++) {
					if (enrollcrnTF.getText().equals(sectionsTable.getItems().get(i).getCrn())) {

						query("SELECT capacity FROM SECTION WHERE crn =" + crn);
						int capacity = 0;
						int occupiedCapacity = 0;

						while (queryResult.next()) {
							capacity = Integer.parseInt(queryResult.getString("capacity"));
						}

						query("SELECT COUNT(*) FROM STUDENT_SECTION WHERE crn = " + crn);

						while (queryResult.next()) {
							occupiedCapacity = (int) queryResult.getLong(1);
						}
						if (occupiedCapacity < capacity) {
							query("INSERT INTO STUDENT_SECTION VALUES ('" + loginID + "'," + crn + ", 'In Progress')");
							student_currentSectionsButtonClicked();
							i = numberOfRows;
							
						} else {
							msgText.setText("Section is full!");
							msgText.setStyle("-fx-font-style: italic");
							msgText.setFill(Color.RED);
							i = numberOfRows;
						}
					} else {
						msgText.setText("Course already regestered or not available this semester!");
						msgText.setStyle("-fx-font-style: italic");
						msgText.setFill(Color.RED);
					}
				}
			} else {
				msgText.setText("Wrong CRN!");
				msgText.setStyle("-fx-font-style: italic");
				msgText.setFill(Color.RED);
			}
		} catch (SQLException e) {
		} catch (Exception e) {
			msgText.setText("Wrong CRN!");
			msgText.setStyle("-fx-font-style: italic");
			msgText.setFill(Color.RED);

		}
	}

	private void staff_viewFAStudentsButtonClicked() {
		msgText = new Text("");
		BorderPane fasPane = new BorderPane();
		VBox buttonPane = new VBox(vgap);
		buttonPane.setPadding(insets);
		buttonPane.setAlignment(Pos.CENTER);
		Button viewStudent = new Button("View Selected Student");
		buttonPane.getChildren().addAll(msgText, viewStudent);
		createFAListTable(
				"select SS.studentID, SS.manager, S.firstname, S.lastname, SS.term, SS.contractyear, SS.percentage, SS.salary, SS.location from STUDENT_STAFF SS, STUDENT S where SS.studentID = S.studentID AND manager='"
						+ loginID + "'");
		fasPane.setCenter(faTable);
		fasPane.setBottom(buttonPane);

		staffPane.setCenter(fasPane);

		viewStudent.setOnAction(e -> staff_viewFAS_viewStudentButtonClicked());

		viewStudent.setOnKeyTyped(e -> {
			if (e.getCode() == KeyCode.ENTER)
				staff_viewFAS_viewStudentButtonClicked();
		});
	}

	private void staff_viewFAS_viewStudentButtonClicked() {
		try {
			FA item = faTable.getSelectionModel().getSelectedItem();
			createStudentListTable("SELECT * FROM STUDENT WHERE studentID='" + item.getId() + "'");
			staffPane.setCenter(studentsTable);
		} catch (Exception e) {
			msgText.setText("Nothing is selected!");
			msgText.setStyle("-fx-font-style: italic");
			msgText.setFill(Color.RED);
		}

	}

	private void staff_submitButtonClicked() {

		if (passwordTF.getText().equals(confirmPasswordTF.getText()) && !((passwordTF.getText()).equals(""))) {

			query("UPDATE staff set passwd =\"" + passwordTF.getText() + "\" where staffID=\"" + loginID + "\"");
			changePassMessage.setText("Password changed");
			changePassMessage.setTextFill(Color.BLUE);
		} else {
			changePassMessage.setText("Passwords do not match! Please try again");
			changePassMessage.setTextFill(Color.RED);

		}
	}

	private void staff_detailsButtonClicked() {
		createStaffListTable("SELECT * FROM STAFF WHERE staffID = \"" + loginID + "\"");
		staffPane.setCenter(staffTable);
	}

	private void expert_querySubmitButtonClicked() {
		try {
			String q = queryTF.getText();
			PreparedStatement ps = getConnection().prepareStatement(q);
			if ((q.startsWith("INSERT")) || (q.startsWith("UPDATE")) || (q.startsWith("DELETE")))
				ps.executeUpdate();
			else
				queryResult = ps.executeQuery();

			ResultSetMetaData rsmd = queryResult.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			StringBuilder sb = new StringBuilder();
			String columnsNames = "";

			for (int i = 1; i <= numberOfColumns; i++)
				columnsNames += String.format("%-24s", rsmd.getColumnName(i));

			sb.append(columnsNames + "\n");
			while (queryResult.next()) {
				String columnsValues = "";
				for (int i = 1; i <= numberOfColumns; i++) {
					columnsValues += String.format("%-24s", queryResult.getString(i));
				}
				sb.append(columnsValues + "\n");
			}
			TextArea resultArea = new TextArea(sb.toString());
			resultArea.setEditable(false);
			resultArea.setFocusTraversable(false);
			resultArea.setFont(new Font("Courier New", 12));
			expertPane.setCenter(resultArea);

		} catch (SQLException e) {
			expertPane.setCenter(msgLabel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loginButtonClicked() {
		// validate username and password and determine if student or instructor
		// or staff
		String username = usernameTF.getText();
		String password = passwordField.getText();

		try {
			if (username.matches("S\\d{4}")) {

				query("SELECT * FROM STUDENT WHERE studentID = \"" + username + "\"");

				if (queryResult.next()) {
					if (queryResult.getString("passwd").equals(password)) {
						usernameTF.clear();
						passwordField.clear();
						loginID = queryResult.getString("studentID");
						loginFN = queryResult.getString("firstname");
						loginLN = queryResult.getString("lastname");
						loginM = queryResult.getString("enrolledin");
						createStudentScene();
					}
				}

			} else if (username.matches("I\\d{4}")) {

				query("SELECT * FROM FACULTY WHERE facultyID = \"" + username + "\"");

				if (queryResult.next()) {
					if (queryResult.getString("passwd").equals(password)) {
						usernameTF.clear();
						passwordField.clear();
						loginID = queryResult.getString("facultyID");
						loginFN = queryResult.getString("firstname");
						loginLN = queryResult.getString("lastname");
						createInstructorScene();
					}
				}

			} else if (username.matches("ST\\d{4}")) {

				query("SELECT * FROM STAFF WHERE staffID = \"" + username + "\"");

				if (queryResult.next()) {
					if (queryResult.getString("passwd").equals(password)) {
						usernameTF.clear();
						passwordField.clear();

						loginID = queryResult.getString("staffID");
						loginFN = queryResult.getString("firstname");
						loginLN = queryResult.getString("lastname");
						loginA = queryResult.getInt("admin");

						if (loginA == 1)
							createAdminScene();
						else
							createStaffScene();

					}
				}

			}
			loginMessage.setText("Wrong username or password! ");
			loginMessage.setTextFill(Color.RED);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void backButtonClicked() {
		int s = sceneList.size() - 2;
		if (sceneList.get(s) == loginScene) {
			createLoginScene();
			sceneList.remove(s);
			sceneList.remove(s);
		} else if (sceneList.get(s) == studentScene) {
			Node n = studentPane.getCenter();
			createStudentScene();
			studentPane.setCenter(n);
			sceneList.remove(s);
			sceneList.remove(s);
		} else if (sceneList.get(s) == instructorScene) {
			Node n = instructorPane.getCenter();
			createInstructorScene();
			instructorPane.setCenter(n);
			sceneList.remove(s);
			sceneList.remove(s);
		} else if (sceneList.get(s) == adminScene) {
			Node n = adminPane.getCenter();
			createAdminScene();
			adminPane.setCenter(n);
			sceneList.remove(s);
			sceneList.remove(s);
		} else if (sceneList.get(s) == staffScene) {
			Node n = staffPane.getCenter();
			createStaffScene();
			staffPane.setCenter(n);
			sceneList.remove(s);
			sceneList.remove(s);
		}
		loginMessage.setText("Please enter your username and password");
		loginMessage.setTextFill(Color.BLACK);

	}

	private void backspacePressed(KeyEvent e) {

		if (e.getCode() == KeyCode.BACK_SPACE)
			backButtonClicked();
	}

	private void backEnterPressed(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER)
			backButtonClicked();

	}

	private void loginEnterPressed(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER)
			loginButtonClicked();

	}

	private TableView<Student> createStudentListTable(String q) {

		query(q);
		studentsTable = new TableView<>();
		studentsTable.setItems(getStudents());

		int width = 100;
		TableColumn<Student, String> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		idColumn.setMinWidth(width - 1);

		TableColumn<Student, String> fnColumn = new TableColumn<>("First Name");
		fnColumn.setCellValueFactory(new PropertyValueFactory<>("fname"));
		fnColumn.setMinWidth(width);

		TableColumn<Student, String> lnColumn = new TableColumn<>("Last Name");
		lnColumn.setCellValueFactory(new PropertyValueFactory<>("lname"));
		lnColumn.setMinWidth(width);

		TableColumn<Student, String> sexColumn = new TableColumn<>("Sex");
		sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
		sexColumn.setMinWidth(width);

		TableColumn<Student, String> dobColumn = new TableColumn<>("DOB");
		dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
		dobColumn.setMinWidth(width);

		TableColumn<Student, Double> cgpaColumn = new TableColumn<>("CGPA");
		cgpaColumn.setCellValueFactory(new PropertyValueFactory<>("cgpa"));
		cgpaColumn.setMinWidth(width);

		TableColumn<Student, Double> mgpaColumn = new TableColumn<>("Major GPA");
		mgpaColumn.setCellValueFactory(new PropertyValueFactory<>("majorgpa"));
		mgpaColumn.setMinWidth(width);

		TableColumn<Student, String> minorColumn = new TableColumn<>("Minor");
		minorColumn.setCellValueFactory(new PropertyValueFactory<>("minor"));
		minorColumn.setMinWidth(width);

		TableColumn<Student, String> emailColumn = new TableColumn<>("Email");
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		emailColumn.setMinWidth(width);

		TableColumn<Student, String> passColumn = new TableColumn<>("Password");
		passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
		passColumn.setMinWidth(width);

		TableColumn<Student, String> programColumn = new TableColumn<>("Program");
		programColumn.setCellValueFactory(new PropertyValueFactory<>("program"));
		programColumn.setMinWidth(width);

		TableColumn<Student, String> advisorColumn = new TableColumn<>("Advisor");
		advisorColumn.setCellValueFactory(new PropertyValueFactory<>("advisor"));
		advisorColumn.setMinWidth(width);

		studentsTable.getColumns().addAll(idColumn, fnColumn, lnColumn, sexColumn, dobColumn, cgpaColumn, mgpaColumn,
				minorColumn, emailColumn, passColumn, programColumn, advisorColumn);

		return studentsTable;
	}

	private TableView<Instructor> createInstructorListTable(String q) {
		query(q);
		instructorsTable = new TableView<>();
		instructorsTable.setItems(getInstructors());

		int width = 100;
		TableColumn<Instructor, String> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		idColumn.setMinWidth(width);

		TableColumn<Instructor, String> fnColumn = new TableColumn<>("First Name");
		fnColumn.setCellValueFactory(new PropertyValueFactory<>("fname"));
		fnColumn.setMinWidth(width);

		TableColumn<Instructor, String> lnColumn = new TableColumn<>("Last Name");
		lnColumn.setCellValueFactory(new PropertyValueFactory<>("lanme"));
		lnColumn.setMinWidth(width);

		TableColumn<Instructor, String> sexColumn = new TableColumn<>("Sex");
		sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
		sexColumn.setMinWidth(width);

		TableColumn<Instructor, String> dobColumn = new TableColumn<>("DOB");
		dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
		dobColumn.setMinWidth(width);

		TableColumn<Instructor, Double> salaryColumn = new TableColumn<>("Salary");
		salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
		salaryColumn.setMinWidth(width);

		TableColumn<Instructor, String> empColumn = new TableColumn<>("Employment");
		empColumn.setCellValueFactory(new PropertyValueFactory<>("employment"));
		empColumn.setMinWidth(width);

		TableColumn<Instructor, String> emailColumn = new TableColumn<>("Email");
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		emailColumn.setMinWidth(width);

		TableColumn<Instructor, String> passColumn = new TableColumn<>("Password");
		passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
		passColumn.setMinWidth(width);

		TableColumn<Instructor, String> officeColumn = new TableColumn<>("Office");
		officeColumn.setCellValueFactory(new PropertyValueFactory<>("office"));
		officeColumn.setMinWidth(width);

		TableColumn<Instructor, String> depCodeColumn = new TableColumn<>("Dep Code");
		depCodeColumn.setCellValueFactory(new PropertyValueFactory<>("depcode"));
		depCodeColumn.setMinWidth(width);

		instructorsTable.getColumns().addAll(idColumn, fnColumn, lnColumn, sexColumn, dobColumn, salaryColumn,
				empColumn, emailColumn, passColumn, officeColumn, depCodeColumn);

		return instructorsTable;
	}

	private TableView<Section> createSectionsListTable(String q) {
		query(q);
		sectionsTable = new TableView<>();
		sectionsTable.setItems(getSections());

		int width = 100;
		TableColumn<Section, String> crnColumn = new TableColumn<>("Crn");
		crnColumn.setCellValueFactory(new PropertyValueFactory<>("crn"));
		crnColumn.setMinWidth(width);

		TableColumn<Section, String> courseidColumn = new TableColumn<>("Course ID");
		courseidColumn.setCellValueFactory(new PropertyValueFactory<>("courseid"));
		courseidColumn.setMinWidth(width);

		TableColumn<Section, String> termColumn = new TableColumn<>("Term");
		termColumn.setCellValueFactory(new PropertyValueFactory<>("term"));
		termColumn.setMinWidth(width);

		TableColumn<Section, Integer> yearColumn = new TableColumn<>("Year");
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
		yearColumn.setMinWidth(width);

		TableColumn<Section, String> schedColumn = new TableColumn<>("Days");
		schedColumn.setCellValueFactory(new PropertyValueFactory<>("sched"));
		schedColumn.setMinWidth(width);

		TableColumn<Section, String> startingTimeColumn = new TableColumn<>("Starting Time");
		startingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		startingTimeColumn.setMinWidth(width);

		TableColumn<Section, String> endingTimeColumn = new TableColumn<>("Ending Time");
		endingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
		endingTimeColumn.setMinWidth(width);

		TableColumn<Section, Integer> capacityColumn = new TableColumn<>("Capacity");
		capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
		capacityColumn.setMinWidth(width);

		TableColumn<Section, Integer> actualColumn = new TableColumn<>("Actual");
		actualColumn.setCellValueFactory(new PropertyValueFactory<>("actual"));
		actualColumn.setMinWidth(width);

		TableColumn<Section, Integer> remainingColumn = new TableColumn<>("Remaining");
		remainingColumn.setCellValueFactory(new PropertyValueFactory<>("remaining"));
		remainingColumn.setMinWidth(width);

		TableColumn<Section, String> instructoridColumn = new TableColumn<>("Taugh By");
		instructoridColumn.setCellValueFactory(new PropertyValueFactory<>("taughtBy"));
		instructoridColumn.setMinWidth(width);

		TableColumn<Section, String> roomColumn = new TableColumn<>("Room");
		roomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));
		roomColumn.setMinWidth(width);

		sectionsTable.getColumns().addAll(crnColumn, courseidColumn, termColumn, yearColumn, schedColumn,
				startingTimeColumn, endingTimeColumn, capacityColumn, actualColumn, remainingColumn, instructoridColumn,
				roomColumn);

		return sectionsTable;
	}

	private TableView<Staff> createStaffListTable(String q) {
		query(q);
		staffTable = new TableView<>();
		staffTable.setItems(getStaff());

		int width = 100;
		TableColumn<Staff, String> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("staffid"));
		idColumn.setMinWidth(width);

		TableColumn<Staff, String> fnColumn = new TableColumn<>("First Name");
		fnColumn.setCellValueFactory(new PropertyValueFactory<>("fname"));
		fnColumn.setMinWidth(width);

		TableColumn<Staff, String> lnColumn = new TableColumn<>("Last Name");
		lnColumn.setCellValueFactory(new PropertyValueFactory<>("lname"));
		lnColumn.setMinWidth(width);

		TableColumn<Staff, String> sexColumn = new TableColumn<>("Sex");
		sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
		sexColumn.setMinWidth(width);

		TableColumn<Staff, String> dobColumn = new TableColumn<>("DOB");
		dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
		dobColumn.setMinWidth(width);

		TableColumn<Staff, Double> salaryColumn = new TableColumn<>("Salary");
		salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
		salaryColumn.setMinWidth(width);

		TableColumn<Staff, String> empColumn = new TableColumn<>("Employment");
		empColumn.setCellValueFactory(new PropertyValueFactory<>("employment"));
		empColumn.setMinWidth(width);

		TableColumn<Staff, String> emailColumn = new TableColumn<>("Email");
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		emailColumn.setMinWidth(width);

		TableColumn<Staff, String> passColumn = new TableColumn<>("Password");
		passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
		passColumn.setMinWidth(width);

		TableColumn<Staff, Integer> adminColumn = new TableColumn<>("Admin");
		adminColumn.setCellValueFactory(new PropertyValueFactory<>("admin"));
		adminColumn.setMinWidth(width);

		TableColumn<Staff, String> depidColumn = new TableColumn<>("Dep ID");
		depidColumn.setCellValueFactory(new PropertyValueFactory<>("depid"));
		depidColumn.setMinWidth(width);

		staffTable.getColumns().addAll(idColumn, fnColumn, lnColumn, sexColumn, dobColumn, salaryColumn, empColumn,
				emailColumn, passColumn, adminColumn, depidColumn);

		return staffTable;
	}

	private TableView<Course> createCourseListTable(String q) {
		query(q);
		coursesTable = new TableView<>();
		coursesTable.setItems(getCourses());

		int width = 120;
		TableColumn<Course, String> idColumn = new TableColumn<>("Course ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("courseid"));
		idColumn.setMinWidth(width);

		TableColumn<Course, String> titleColumn = new TableColumn<>("Title");
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		titleColumn.setMinWidth(width);

		TableColumn<Course, Integer> creditsColumn = new TableColumn<>("Credits");
		creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
		creditsColumn.setMinWidth(width);

		TableColumn<Course, String> levelColumn = new TableColumn<>("Level");
		levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
		levelColumn.setMinWidth(width);

		coursesTable.getColumns().addAll(idColumn, creditsColumn, titleColumn, levelColumn);

		return coursesTable;
	}

	private TableView<Grade> createGradesListTable(String q) {
		query(q);
		gradesTable = new TableView<>();
		gradesTable.setItems(getGrades());

		int width = 120;
		TableColumn<Grade, String> idColumn = new TableColumn<>("Course ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));
		idColumn.setMinWidth(width);

		TableColumn<Grade, String> titleColumn = new TableColumn<>("Title");
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		titleColumn.setMinWidth(width);

		TableColumn<Grade, String> creditsColumn = new TableColumn<>("Credits");
		creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
		creditsColumn.setMinWidth(width);

		TableColumn<Grade, String> termColumn = new TableColumn<>("Term");
		termColumn.setCellValueFactory(new PropertyValueFactory<>("term"));
		termColumn.setMinWidth(width);

		TableColumn<Grade, String> sectionyearColumn = new TableColumn<>("Year");
		sectionyearColumn.setCellValueFactory(new PropertyValueFactory<>("sectionyear"));
		sectionyearColumn.setMinWidth(width);

		TableColumn<Grade, String> graderColumn = new TableColumn<>("Grade");
		graderColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
		graderColumn.setMinWidth(width);

		gradesTable.getColumns().addAll(idColumn, titleColumn, creditsColumn, termColumn, sectionyearColumn,
				graderColumn);

		return gradesTable;
	}

	private TableView<FA> createFAListTable(String q) {

		query(q);
		faTable = new TableView<>();
		faTable.setItems(getFA());

		int width = 120;
		TableColumn<FA, String> idColumn = new TableColumn<>("Student ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		idColumn.setMinWidth(width);

		TableColumn<FA, String> managerColumn = new TableColumn<>("Manager ID");
		managerColumn.setCellValueFactory(new PropertyValueFactory<>("manager"));
		managerColumn.setMinWidth(width);

		TableColumn<FA, String> fnColumn = new TableColumn<>("First Name");
		fnColumn.setCellValueFactory(new PropertyValueFactory<>("fn"));
		fnColumn.setMinWidth(width);

		TableColumn<FA, String> lnColumn = new TableColumn<>("Last Name");
		lnColumn.setCellValueFactory(new PropertyValueFactory<>("ln"));
		lnColumn.setMinWidth(width);

		TableColumn<FA, String> termColumn = new TableColumn<>("Term");
		termColumn.setCellValueFactory(new PropertyValueFactory<>("term"));
		termColumn.setMinWidth(width);

		TableColumn<FA, String> yearColumn = new TableColumn<>("Contract Year");
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
		yearColumn.setMinWidth(width);

		TableColumn<FA, String> percentageColumn = new TableColumn<>("Percentage");
		percentageColumn.setCellValueFactory(new PropertyValueFactory<>("percentage"));
		percentageColumn.setMinWidth(width);

		TableColumn<FA, Double> salaryColumn = new TableColumn<>("Salary");
		salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
		salaryColumn.setMinWidth(width);

		TableColumn<FA, Double> locationColumn = new TableColumn<>("Location");
		locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
		locationColumn.setMinWidth(width);

		faTable.getColumns().addAll(idColumn, managerColumn, fnColumn, lnColumn, termColumn, yearColumn,
				percentageColumn, salaryColumn, locationColumn);

		return faTable;
	}

	private ObservableList<Student> getStudents() {
		ObservableList<Student> students = FXCollections.observableArrayList();
		try {
			while (queryResult.next())
				students.add(new Student(queryResult.getString(1), queryResult.getString(2), queryResult.getString(3),
						queryResult.getString(4), queryResult.getString(5), queryResult.getDouble(6),
						queryResult.getDouble(7), queryResult.getString(8), queryResult.getString(9),
						queryResult.getString(10), queryResult.getString(11), queryResult.getString(12)));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return students;
	}

	private ObservableList<Grade> getGrades() {
		ObservableList<Grade> grades = FXCollections.observableArrayList();

		try {
			while (queryResult.next())
				grades.add(new Grade(queryResult.getString(1), queryResult.getString(2), queryResult.getString(3),
						queryResult.getString(4), queryResult.getString(5), queryResult.getString(6)));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return grades;
	}

	private ObservableList<Instructor> getInstructors() {
		ObservableList<Instructor> instructors = FXCollections.observableArrayList();

		try {
			while (queryResult.next())
				instructors.add(new Instructor(queryResult.getString(1), queryResult.getString(2),
						queryResult.getString(3), queryResult.getString(4), queryResult.getString(5),
						queryResult.getDouble(6), queryResult.getString(7), queryResult.getString(8),
						queryResult.getString(9), queryResult.getString(10), queryResult.getString(11)));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return instructors;

	}

	private ObservableList<Staff> getStaff() {
		ObservableList<Staff> staff = FXCollections.observableArrayList();
		try {
			while (queryResult.next())
				staff.add(new Staff(queryResult.getString(1), queryResult.getString(2), queryResult.getString(3),
						queryResult.getString(4), queryResult.getString(5), queryResult.getDouble(6),
						queryResult.getString(7), queryResult.getString(8), queryResult.getString(9),
						queryResult.getInt(10), queryResult.getString(11)));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return staff;
	}

	private ObservableList<Course> getCourses() {
		ObservableList<Course> courses = FXCollections.observableArrayList();
		try {
			while (queryResult.next())
				courses.add(new Course(queryResult.getString(1), queryResult.getInt(2), queryResult.getString(3),
						queryResult.getString(4)));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return courses;

	}

	private ObservableList<Section> getSections() {
		ObservableList<Section> sections = FXCollections.observableArrayList();
		try {
			while (queryResult.next()) {
				queryActual("SELECT COUNT(*) FROM STUDENT_SECTION WHERE crn = '" + queryResult.getString(1) + "'");
				int capacity = queryResult.getInt(8);
				int actual= 0;
				if(actualSet.next())
				actual = (int) actualSet.getLong(1);
				int remaining = capacity - actual;
				sections.add(new Section(queryResult.getString(1), queryResult.getString(2), queryResult.getString(3),
						queryResult.getInt(4), queryResult.getString(5), queryResult.getString(6),
						queryResult.getString(7), queryResult.getInt(8), actual, remaining, queryResult.getString(9),
						queryResult.getString(10)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sections;

	}

	private ObservableList<FA> getFA() {
		ObservableList<FA> fa = FXCollections.observableArrayList();

		try {
			while (queryResult.next())
				fa.add(new FA(queryResult.getString(1), queryResult.getString(2), queryResult.getString(3),
						queryResult.getString(4), queryResult.getString(5), queryResult.getString(6),
						queryResult.getString(7), queryResult.getString(8), queryResult.getString(9)));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return fa;
	}

	private Connection getConnection() throws Exception {
		try {
			String url = "jdbc:mysql://localhost:3306/university?useSSL=false";
			String username = "root";
			String password = "947200";
			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}

	private void query(String q) {
		try {
			PreparedStatement ps = getConnection().prepareStatement(q);
			if ((q.startsWith("INSERT")) || (q.startsWith("UPDATE")) || (q.startsWith("DELETE")))
				ps.executeUpdate();
			else
				queryResult = ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void queryActual(String q) {
		try {

			PreparedStatement ps = getConnection().prepareStatement(q);
			if ((q.startsWith("INSERT")) || (q.startsWith("UPDATE")) || (q.startsWith("DELETE")))
				ps.executeUpdate();
			else
				actualSet = ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
