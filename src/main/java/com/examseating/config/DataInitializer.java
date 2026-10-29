package com.examseating.config;

import com.examseating.entity.*;
import com.examseating.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.password:admin123}")
    private String adminPassword;

    @Bean
    public CommandLineRunner initData(
            AdminRepository adminRepository,
            StudentRepository studentRepository,
            ExamRepository examRepository,
            RoomRepository roomRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            // Create admin user if none exists
            if (adminRepository.count() == 0) {
                if (adminPassword == null || adminPassword.isBlank()) {
                    throw new IllegalStateException("Set ADMIN_PASSWORD before initializing the first admin account.");
                }
                Admin admin = new Admin(adminUsername, passwordEncoder.encode(adminPassword));
                adminRepository.save(admin);
                System.out.println("✔ Initial admin user created (username: " + adminUsername + ")");
            }

            // Create sample students if none exist
            if (studentRepository.count() == 0) {
                String[] names = {
                    "Aarav Sharma", "Priya Patel", "Rohan Gupta", "Sneha Reddy",
                    "Vikram Singh", "Ananya Das", "Karthik Nair", "Meera Joshi",
                    "Arjun Kumar", "Divya Verma"
                };
                String[] emails = {
                    "aarav@example.com", "priya@example.com", "rohan@example.com", "sneha@example.com",
                    "vikram@example.com", "ananya@example.com", "karthik@example.com", "meera@example.com",
                    "arjun@example.com", "divya@example.com"
                };

                for (int i = 0; i < 10; i++) {
                    String rollNumber = String.format("CS%03d", i + 1);
                    Student student = new Student(rollNumber, names[i], emails[i], "Computer Science", (i % 4) + 1);
                    studentRepository.save(student);
                }
                System.out.println("✔ 10 sample students created (CS001–CS010)");
            }

            // Create sample exams if none exist
            if (examRepository.count() == 0) {
                examRepository.save(new Exam("MATH101", "Mathematics I", "Mathematics", LocalDate.now().plusDays(7), 180));
                examRepository.save(new Exam("PHY101", "Physics I", "Physics", LocalDate.now().plusDays(14), 180));
                examRepository.save(new Exam("CS101", "Intro to CS", "Computer Science", LocalDate.now().plusDays(21), 120));
                System.out.println("✔ 3 sample exams created (MATH101, PHY101, CS101)");
            }

            // Create sample rooms if none exist
            if (roomRepository.count() == 0) {
                roomRepository.save(new Room("HALL-A", "Examination Hall A", 5, 6));
                roomRepository.save(new Room("HALL-B", "Examination Hall B", 4, 8));
                roomRepository.save(new Room("LAB-1", "Computer Lab 1", 3, 10));
                System.out.println("✔ 3 sample rooms created (HALL-A, HALL-B, LAB-1)");
            }
        };
    }
}
