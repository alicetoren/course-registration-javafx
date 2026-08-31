package registrationProgram;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataManager {

    private static final String DATA_FILE = "CourseRegistrationData.ser";

    public static CourseDatabase loadCourseDatabase() {

        // First try to load previously saved application data.
        try (ObjectInputStream in =
                new ObjectInputStream(new FileInputStream(DATA_FILE))) {

            return (CourseDatabase) in.readObject();

        } catch (IOException | ClassNotFoundException e) {

            // No saved database exists yet, so load the initial CSV data.
            System.out.println(
                    "No saved database found. Loading courses from FullCourses.csv."
            );
        }

        CourseDatabase data = new CourseDatabase();

        try (InputStream input =
                DataManager.class.getResourceAsStream("/FullCourses.csv")) {

            if (input == null) {
                System.out.println("FullCourses.csv could not be found.");
                return data;
            }

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(input));

            // Skip the CSV header.
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                String[] values = line.split(",");

                Course course = new Course(
                        values[0],
                        values[1],
                        Integer.parseInt(values[2]),
                        values[5],
                        Integer.parseInt(values[6]),
                        values[7]
                );

                data.addCourse(course);
            }

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static void saveCourseDatabase(CourseDatabase data) {

        try (ObjectOutputStream out =
                new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {

            out.writeObject(data);

            System.out.println("Course registration data saved.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}