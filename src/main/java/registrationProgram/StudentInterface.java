package registrationProgram;

import java.util.ArrayList;

public interface StudentInterface {
	void viewAllCourses(ArrayList<Course> courseDirectory);
	void viewOpenCourses(ArrayList<Course> courseDirectory);
	void registerCourse(String signature, CourseDatabase cd);
	void withdrawCourse(String signature, CourseDatabase cd);
	boolean isEmpty();
	void viewMyCourses();
	
}
