package registrationProgram;

import java.util.ArrayList;

public class Admin extends User implements AdminInterface{
	//no need to define data fields - they've all been defined in superclass
	
	//create constructor from superclass
	public Admin(String firstName, String lastName, String username, String password) {
		super(firstName, lastName, username, password);
	}
	
	//implement methods from interface
	//method that creates a new course and adds it to directory
	public void createCourse(String courseName, String courseID, int capacity, String instructor, int section, String location, CourseDatabase cd){
		Course course = new Course(courseName, courseID, capacity, instructor, section, location);
		cd.addCourse(course);
		System.out.println("Course has been successfully added!");
	}
	
	//method that deletes a specific section of a course from the directory (and from students' schedule if necessary)
	@Override
	public void deleteSection(Course course, CourseDatabase cd) {

	    if (course == null) {
	        System.out.println("Course not found.");
	        return;
	    }

	    cd.deleteCourse(course);

	    for (Student student : cd.getStudentDirectory()) {
	        student.getSchedule().remove(course);
	    }

	    System.out.println(
	            "Section has been successfully deleted!"
	    );
	}
	
	//method that deletes all sections of a course by name
	public void deleteCourse(String name, CourseDatabase cd) {
		boolean found = false;
		for (int i = cd.getCourseDirectory().size() - 1; i >= 0; i--) {
	        if (cd.getCourseDirectory().get(i).getCourseName().equalsIgnoreCase(name)) {
	            Course course = cd.getCourseDirectory().get(i);
	            cd.getCourseDirectory().remove(i);
	            found = true;

	            for (Student student : cd.getStudentDirectory()) {
	                student.getSchedule().remove(course);
	            }
	        }
	    }
		if(found) {
			System.out.println("Course has been successfully deleted!");
		}
		else {System.out.println("Course not found.");}
	}
	
	//method that displays the information of a single course given the course's id and section
	public void displayCourse(String signature, CourseDatabase cd) {
		Course course = cd.findCourse(signature);
		if(course==null){
			System.out.println("Course not found");
		}
		else
			course.printCourseAdmin();
	}
	
	//method that displays the information of a single course given the course's name and section (overloading)
	public void displayCourse(String name, int section, CourseDatabase cd) {
		Course course = cd.findCourse(name, section);
		if(course==null){
			System.out.println("Course not found");
		}
		else
			course.printCourseAdmin();
	}
	
	//method that displays the information of all courses
	@Override
	public void viewAllCourses(ArrayList<Course> courseDirectory) {
		for(Course course : courseDirectory) {
			course.printCourseAdmin();
		}
	}
	
	//method that prints all full courses
	public void viewFullCourses(CourseDatabase cd) {
		boolean foundFullCourse = false;
		for (Course course : cd.getCourseDirectory()) {
	        if (course.isFull()) {
	            course.printCourseAdmin();
	            foundFullCourse = true;
	        }
	    }
		if(!foundFullCourse) {System.out.println("There are currently no full courses.");}
	}
	
	//method that shows all the students enrolled in a section of a course
	public void viewStudentsInCourse(String signature, CourseDatabase cd) {
		Course course = cd.findCourse(signature);
	    if (course == null) {
	        System.out.println("Course not found");
	        return;
	    }
	    if (course.getEnrolled().isEmpty()) {
	        System.out.println("No students are enrolled in this course.");
	        return;
	    }
	    for (Student student : course.getEnrolled()) {
	        System.out.println(student.getFullName());
	    }
	}
	
	
	//method that prints all the courses that a given student is enrolled in
	public void viewStudentCourses(String fullName, ArrayList <Student> studentDirectory) {
		for (Student student : studentDirectory) {
	        if (student.getFullName().equalsIgnoreCase(fullName)) {
	            if (student.getSchedule().isEmpty()) {
	                System.out.println("Student is not registered in any courses.");
	                return;
	            }
	            for (Course course : student.getSchedule()) {
	                System.out.println(course.getCourseName());
	            }
	            return;
	        }
	    }
	    System.out.println("Student not found");
	}
	
}

