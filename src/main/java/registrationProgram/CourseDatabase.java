package registrationProgram;

import java.util.ArrayList;
import java.io.Serializable;

public class CourseDatabase implements Serializable{
	//define data fields
	private ArrayList<Course> courseDirectory;
	private ArrayList<Student> studentDirectory;
	
	//no arguments constructor
	public CourseDatabase() {
		courseDirectory = new ArrayList<>();
		studentDirectory = new ArrayList<>();
	}
	
	//method to add a course to directory
	public void addCourse(Course course) {
		this.courseDirectory.add(course);
	}
	
	//method to remove a course from directory
	public void deleteCourse(Course course) {
		this.courseDirectory.remove(course);
	}
	
	//method to add Student to directory
	public void registerStudent(Student student) {
		this.studentDirectory.add(student);
	}
	
	//method to find course from directory given the course ID and section
	public Course findCourse(String signature) {
	    for (Course course : courseDirectory) {
	        if (course.toString().equalsIgnoreCase(signature)) {
	            return course;
	        }
	    }
	    return null;
	}
	
	//method to find course from directory given course name and section (overloading)
	public Course findCourse(String name, int section) {
	    for (Course course : courseDirectory) {
	        if (course.getCourseName().equalsIgnoreCase(name)
	                && course.getSection() == section) {
	            return course;
	        }
	    }
	    return null;
	}
	
	//method to find student from first and last name
	public Student findStudent(String firstName, String lastName) {
	    for (Student student : studentDirectory) {
	        if (student.getFirstName().equals(firstName)
	                && student.getLastName().equals(lastName)) {
	            return student;
	        }
	    }
	    return null;
	}
	
	//method to sort course directory in order of least to most remaining capacity
	public void sortView() {
	    for (int i = 0; i < courseDirectory.size() - 1; i++) {
	        int minIndex = i;

	        for (int j = i + 1; j < courseDirectory.size(); j++) {
	            int currentRemaining = courseDirectory.get(j).getCapacity()
	                    - courseDirectory.get(j).getCurrentEnrollment();

	            int minimumRemaining = courseDirectory.get(minIndex).getCapacity()
	                    - courseDirectory.get(minIndex).getCurrentEnrollment();

	            if (currentRemaining < minimumRemaining) {
	                minIndex = j;
	            }
	        }

	        if (minIndex != i) {
	            Course temp = courseDirectory.get(i);
	            courseDirectory.set(i, courseDirectory.get(minIndex));
	            courseDirectory.set(minIndex, temp);
	        }
	    }
	}
	
	//getters and setters
	public ArrayList<Course> getCourseDirectory() {
		return courseDirectory;
	}

	public void setCourseDirectory(ArrayList<Course> courseDirectory) {
		this.courseDirectory = courseDirectory;
	}

	public ArrayList<Student> getStudentDirectory() {
		return studentDirectory;
	}

	public void setStudentDirectory(ArrayList<Student> studentDirectory) {
		this.studentDirectory = studentDirectory;
	}
	
	
}
