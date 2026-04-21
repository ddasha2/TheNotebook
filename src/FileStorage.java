import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private static final String FILE_NAME = "contacts.csv";

    public void saveContacts(List<Contact> contacts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Contact contact : contacts) {
                writer.write(contact.toCSV());
                writer.newLine();
            }
            System.out.println("Контакты успешно сохранены в файл: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении контактов: " + e.getMessage());
        }
    }

    public List<Contact> loadContacts() {
        List<Contact> contacts = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("Файл с контактами не найден. Будет создан новый при сохранении.");
            return contacts;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Contact contact = Contact.fromCSV(line);
                    if (contact != null) {
                        contacts.add(contact);
                    }
                }
            }
            System.out.println("Загружено " + contacts.size() + " контактов из файла: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке контактов: " + e.getMessage());
        }
        return contacts;
    }
}