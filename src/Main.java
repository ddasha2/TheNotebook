import java.util.List;
import java.util.Scanner;

public class Main {
    private static PhoneBook phoneBook;
    private static FileStorage storage;
    private static Scanner scanner;

    public static void main(String[] args) {
        phoneBook = new PhoneBook();
        storage = new FileStorage();
        scanner = new Scanner(System.in);

        System.out.println("=== Записная книжка ===");
        phoneBook.setContacts(storage.loadContacts());

        boolean running = true;
        while (running) {
            printMenu();
            int choice = getIntInput("Выберите действие: ");

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    removeContact();
                    break;
                case 3:
                    findByName();
                    break;
                case 4:
                    findByPhone();
                    break;
                case 5:
                    phoneBook.printAllContacts();
                    break;
                case 6:
                    saveAndExit();
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== Меню ===");
        System.out.println("1. Добавить контакт");
        System.out.println("2. Удалить контакт");
        System.out.println("3. Найти по имени");
        System.out.println("4. Найти по телефону");
        System.out.println("5. Показать все контакты");
        System.out.println("6. Сохранить и выйти");
    }

    private static void addContact() {
        System.out.println("\n=== Добавление контакта ===");
        System.out.print("Введите имя: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Имя не может быть пустым!");
            return;
        }

        String phone = "";
        boolean phoneValid = false;
        while (!phoneValid) {
            System.out.print("Введите телефон: ");
            phone = scanner.nextLine().trim();

            if (!Contact.isValidPhone(phone)) {
                System.out.println("Ошибка! Телефон должен начинаться с +7 или 8 и иметь 11 цифр");
                System.out.println("Пример: +79091234567 или 89091234567");
                continue;
            }

            if (phoneBook.isPhoneExists(phone)) {
                Contact existing = phoneBook.findContactByPhone(phone);
                System.out.println("Ошибка! Номер " + phone + " уже существует!");
                System.out.println("Владелец: " + existing.getName());
                continue;
            }
            break;
        }

        String email = "";
        boolean emailValid = false;
        while (!emailValid) {
            System.out.print("Введите email: ");
            email = scanner.nextLine().trim();

            if (Contact.isValidEmail(email)) {
                emailValid = true;
            } else {
                System.out.println("Ошибка! Email должен быть в формате: name@domain.ru");
                System.out.println("Примеры: user@mail.ru, name@gmail.com");
            }
        }

        Contact contact = new Contact(name, phone, email);
        phoneBook.add(contact);
    }

    private static void removeContact() {
        System.out.println("\n=== Удаление контакта ===");
        System.out.print("Введите телефон контакта для удаления: ");
        String phone = scanner.nextLine().trim();
        phoneBook.removeByPhone(phone);
    }

    private static void findByName() {
        System.out.println("\n=== Поиск по имени ===");
        System.out.print("Введите имя (или часть имени): ");
        String name = scanner.nextLine().trim();

        List<Contact> results = phoneBook.findByName(name);
        printSearchResults(results, "имени", name);
    }

    private static void findByPhone() {
        System.out.println("\n=== Поиск по телефону ===");
        System.out.print("Введите номер телефона (или часть номера): ");
        String phone = scanner.nextLine().trim();

        List<Contact> results = phoneBook.findByPhone(phone);
        printSearchResults(results, "телефону", phone);
    }

    private static void printSearchResults(List<Contact> results, String searchType, String query) {
        if (results.isEmpty()) {
            System.out.println("Контакты не найдены по " + searchType + ": " + query);
        } else {
            System.out.println("Найдено " + results.size() + " контакт(ов):");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = Integer.parseInt(scanner.nextLine().trim());
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число.");
            }
        }
    }

    private static void saveAndExit() {
        System.out.println("\n=== Сохранение и выход ===");
        storage.saveContacts(phoneBook.getAllContacts());
        System.out.println("До свидания!");
    }
}
