package registrationProgram;

import java.util.ArrayList;

public class Student extends User implements StudentInterface{
	//define data fields not inherited from user
	private ArrayList <Course> schedule;
	
	//student constructor
	public Student() {
		super();
		this.schedule = new ArrayList<>();
	}
	//constructor that sets generic name and password (as defined in superclass), students have the ability to change their login information later
	public Student(String firstName, String lastName, String username, String password, ArrayList<Student> studentDirectory) {
		super(firstName, lastName, username, password);
		studentDirectory.add(this);
		this.schedule = new ArrayList<>();
	}
	
	//implement methods from student interface
	@Override //method to view all courses
	public void viewAllCourses(ArrayList<Course> courseDirectory) {
		for(Course course : courseDirectory) {
			course.printCourseStudent();
		}
	}
	
	//method to view all open courses
	public void viewOpenCourses(ArrayList<Course> courseDirectory) {
		for(Course course: courseDirectory) {
			if(!course.isFull()) {
	            System.out.println("Course name: " + course.getCourseName());
	            System.out.println("Course ID: " + course.getCourseID());
	            System.out.println("Section: " + course.getSection());
	            System.out.println("Remaining capacity: "
	                    + (course.getCapacity() - course.getCurrentEnrollment()));
	            System.out.println();
			}
		}
	}
	
	//method to register in a course
	public void registerCourse(String signature, CourseDatabase cd) {
		Course course = cd.findCourse(signature);
		if(course==null) {
			System.out.println("Course not found");
		}
		else if(course.isFull()) {
			System.out.println("Invalid, this course is full.");
		}
		else if(course.checkStudent(this)) {
			System.out.println("Invalid, you are already enrolled in this course.");
		}
		else {course.enroll(this);}
	}
	
	//method to withdraw from a course
	public void withdrawCourse(String signature, CourseDatabase cd) {
		Course course = cd.findCourse(signature);
		if(course == null) {
			System.out.println("Course not found.");
		}
		else if(!course.checkStudent(this)) {
			System.out.println("Invalid, you are not enrolled in this course.");
		}
		else {course.withdraw(this);}
	}
	
	//method to show all the courses that a student is registered in
	public void viewMyCourses() {
		if (schedule.isEmpty()) {
	        System.out.println("You are not registered for any courses.");
	        return;
	    }

	    for (Course course : schedule) {
	        System.out.println(course);
	    }
	}
	
	//method to determine if a student's schedule is empty
	public boolean isEmpty() {
		return this.schedule.isEmpty();
	}
	
	//create getters and setters
	public ArrayList <Course> getSchedule() {
		return schedule;
	}
	
	public void setSchedule(ArrayList <Course> schedule) {
		this.schedule = schedule;
	}
	
}

