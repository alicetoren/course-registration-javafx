package registrationProgram;

import java.util.ArrayList;

public interface AdminInterface {
	void deleteCourse(String name, CourseDatabase cd);
	void createCourse(String courseName, String courseID, int capacity, String instructor, int section, String location, CourseDatabase cd);
	void deleteSection(Course course, CourseDatabase cd);
	void displayCourse(String signature, CourseDatabase cd);
	void viewAllCourses(ArrayList<Course> courseDirectory);
	void viewFullCourses(CourseDatabase cd);
	void viewStudentsInCourse(String signature, CourseDatabase cd);
	void viewStudentCourses(String fullName, ArrayList <Student> studentDirectory);
}

