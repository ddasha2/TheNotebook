import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }

        String cleanPhone = phone.replaceAll("[\\s-]", "");
        String phonePattern = "^(\\+7|8)\\d{10}$";

        return cleanPhone.matches(phonePattern);
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailPattern);
    }

    @Override
    public String toString() {
        return String.format("Имя: %-20s | Телефон: %-12s | Email: %-20s", name, phone, email);
    }

    public String toCSV() {
        return name + "," + phone + "," + email;
    }

    public static Contact fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length == 3) {
            return new Contact(parts[0].trim(), parts[1].trim(), parts[2].trim());
        }
        return null;
    }
}