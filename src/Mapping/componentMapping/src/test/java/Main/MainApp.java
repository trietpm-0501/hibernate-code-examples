package Main;



import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Address;
import model.Employee;
import util.HibernateUtil;

public class MainApp {
    public static void main(String[] args) {
        // Khởi tạo Session và Transaction
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Bắt đầu transaction
            transaction = session.beginTransaction();

            // Tạo đối tượng Address
            Address address = new Address();
            address.setStreet("123 Main St");
            address.setCity("Springfield");
            address.setState("IL");
            address.setZipcode("62704");

            // Tạo đối tượng Employee
            Employee employee = new Employee();
            employee.setName("John Doe");
            employee.setSalary(1000.0);
            employee.setAddress(address);

            // Lưu đối tượng Employee (cùng với Address liên quan)
            session.save(employee);

            // Commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Đóng session
            session.close();
        }

        // Đóng SessionFactory
        HibernateUtil.shutdown();
    }
}
