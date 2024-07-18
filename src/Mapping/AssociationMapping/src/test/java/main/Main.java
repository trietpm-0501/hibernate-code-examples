package main;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.Course;
import model.Student;

import org.hibernate.Transaction;

import java.util.HashSet;

public class Main {
    private static SessionFactory factory;

    public static void main(String[] args) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        Main main = new Main();

        // Create example data
        main.addExampleData();
    }

    public void addExampleData() {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // Create courses
            Course course1 = new Course();
            course1.setTitle("Math");

            Course course2 = new Course();
            course2.setTitle("Science");

            // Create students
            Student student1 = new Student();
            student1.setName("Alice");

            Student student2 = new Student();
            student2.setName("Bob");

            // Set associations
            student1.setCourses(new HashSet<Course>() {{
                add(course1);
                add(course2);
            }});

            student2.setCourses(new HashSet<Course>() {{
                add(course1);
            }});

            course1.setStudents(new HashSet<Student>() {{
                add(student1);
                add(student2);
            }});

            course2.setStudents(new HashSet<Student>() {{
                add(student1);
            }});

            // Save entities
            session.save(student1);
            session.save(student2);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
