package io.lightfeather.springtemplate;

import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;

public class Employee {

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    private String email;

    private String phone;

    @NonNull
    private String supervisor;

    public Employee(){}

    public Employee(String firstName, String lastName, String email, String phone, String supervisor){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.supervisor = supervisor;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("First name: ").append(this.firstName).append(", ");
        s.append("Last name: ").append(this.lastName).append(", ");
        s.append("Email: ").append(this.email).append(", ");
        s.append("Phone: ").append(this.phone).append(", ");
        s.append("Supervisor: ").append(this.supervisor);
        return s.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getSupervisor() {
        return supervisor;
    }
}
