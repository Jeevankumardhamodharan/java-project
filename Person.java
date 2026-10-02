package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Abstract base class representing any person in the ExamVision system.
 * Student, Faculty, and Admin all extend this class, demonstrating
 * Inheritance and Abstraction. Common fields (id, name, email, password)
 * and behaviour are encapsulated here with private fields and
 * protected/public accessors.
 */
public abstract class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String email;
    private String password;
    private final LocalDateTime registeredOn;

    /**
     * Primary constructor used when creating a brand-new person record.
     */
    public Person(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.registeredOn = LocalDateTime.now();
    }

    // ---------- Encapsulation: getters & setters ----------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Password is intentionally not exposed in displayDetails() output
     * for basic data hygiene, but is accessible for authentication checks.
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    /**
     * Checks whether the supplied raw password matches the stored password.
     * Kept simple (plain-text comparison) since this is a console-only,
     * file-based academic project with no external security libraries.
     */
    public boolean checkPassword(String rawPassword) {
        return this.password != null && this.password.equals(rawPassword);
    }

    /**
     * Abstract method forcing every subclass to define how it displays
     * its own details. Demonstrates Abstraction + Method Overriding.
     */
    public abstract String displayDetails();

    @Override
    public String toString() {
        return displayDetails();
    }
}
