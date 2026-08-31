package registrationProgram;

import java.util.ArrayList;
import java.io.Serializable;

public class Course implements Serializable{
	
	//define data fields
	private String courseName; 
	private String courseID;
	private int capacity;
	private int currentEnrollment;
	private ArrayList<Student> enrolled;
	private String instructor;
	private int section;
	private String location;
	
	
	//constructor that takes all data fields (we assume that all courses start empty - they must be populated manually)
	public Course(String courseName, String courseID, int capacity, String instructor, int section, String location) {
		this.courseName = courseName;
		this.courseID = courseID;
		this.capacity = capacity;
		this.currentEnrollment = 0;
		this.enrolled = new ArrayList<>();
		this.instructor = instructor;
		this.section = section;
		this.location = location;
	}
	
	//method to enroll a student
	public void enroll(Student student) {
		if(student==null) {
			System.out.println("Invalid student.");
			return;
		}
		if(isFull()) {
			System.out.println("Invalid, this course is full.");
			return;
		}
		if(checkStudent(student)) {
			System.out.println("Student is already enrolled in this course.");
			return;
		}
		this.enrolled.add(student);
		this.currentEnrollment++;
		student.getSchedule().add(this);
		System.out.println("Student successfully enrolled!");
	}
	
	//method to withdraw a student
	public void  withdraw(Student student) {
		if(student ==  null || !checkStudent(student)) {
			System.out.println("Invalid, student is not enrolled in this course.");
			return;
		}
		this.enrolled.remove(student);
		this.currentEnrollment--;
		student.getSchedule().remove(this);
		System.out.println("Student successfully withdrawn!");
	}
	
	//method that checks if a given student is in a course
	public boolean checkStudent(Student student) {
		return enrolled.contains(student);
	}
	
	//method to determine if a class is full
	public boolean isFull() {
		return currentEnrollment >= capacity;
	}
	
	//method that create a string signature for each course
	@Override
	public String toString() {
		return courseID + " " + section;
	}
	
	//method that prints all information of a course (for an admin)
	public void printCourseAdmin() {
		System.out.println("Course name: " + this.getCourseName());
		System.out.println("Course ID: " + this.getCourseID());
		System.out.println("Current enrollment: " + this.getCurrentEnrollment());
		System.out.println("Max capacity: " + this.getCapacity());
		System.out.println("Section: " + this.getSection());
		System.out.println();
	}
	
	//method that prints all information of a course (for a student)
	public void printCourseStudent() {
		System.out.println("Course name: " + this.getCourseName());
		System.out.println("Course ID: " + this.getCourseID());
		System.out.println("Section: " + this.getSection());
		System.out.println("Remaining capacity: " + (this.getCapacity() - this.getCurrentEnrollment()));
		System.out.println();
	}
	
	
	//getters and setters
		public String getCourseName() {
			return courseName;
		}

		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}

		public String getCourseID() {
			return courseID;
		}

		public void setCourseID(String courseID) {
			this.courseID = courseID;
		}

		public int getCapacity() {
			return capacity;
		}

		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}

		public int getCurrentEnrollment() {
			return currentEnrollment;
		}

		public void setCurrentEnrollment(int currentEnrollment) {
			this.currentEnrollment = currentEnrollment;
		}

		public ArrayList<Student> getEnrolled() {
			return enrolled;
		}

		public void setEnrolled(ArrayList<Student> enrolled) {
			this.enrolled = enrolled;
		}

		public String getInstructor() {
			return instructor;
		}

		public void setInstructor(String instructor) {
			this.instructor = instructor;
		}

		public int getSection() {
			return section;
		}

		public void setSection(int section) {
			this.section = section;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

}
