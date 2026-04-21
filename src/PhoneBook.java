import java.util.*;

public class PhoneBook {
    private List<Contact> contacts;

    public PhoneBook() {
        contacts = new ArrayList<>();
    }

    public void add(Contact contact) {

        for (Contact existing : contacts) {
            if (existing.getPhone().equals(contact.getPhone())) {
                System.out.println("Ошибка! Контакт с таким номером телефона уже существует:");
                return;
            }
        }

        contacts.add(contact);
        sortContacts();
        System.out.println("Контакт успешно добавлен!");
    }

    private void sortContacts() {
        contacts.sort(Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER));
    }

    public boolean isPhoneExists(String phone) {
        for (Contact existing : contacts) {
            if (existing.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeByPhone(String phone) {
        Iterator<Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.getPhone().equals(phone)) {
                iterator.remove();
                System.out.println("Контакт с номером '" + phone + "' удален!");
                System.out.println("Имя: " + contact.getName());
                return true;
            }
        }
        System.out.println("Контакт с номером '" + phone + "' не найден!");
        return false;
    }

    public List<Contact> findByName(String name) {
        List<Contact> result = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(contact);
            }
        }
        return result;
    }

    public List<Contact> findByPhone(String phone) {
        List<Contact> result = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getPhone().contains(phone)) {
                result.add(contact);
            }
        }
        return result;
    }

    public Contact findContactByPhone(String phone) {
        for (Contact contact : contacts) {
            if (contact.getPhone().equals(phone)) {
                return contact;
            }
        }
        return null;
    }

    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }

    public void printAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("Записная книжка пуста!");
            return;
        }
        System.out.println("\n=== Все контакты ===");
        int i = 1;
        for (Contact contact : contacts) {
            System.out.println((i++) + ". " + contact);
        }
        System.out.println("Всего контактов: " + contacts.size());
    }

    public void setContacts(List<Contact> contactsList) {
        this.contacts.clear();
        this.contacts.addAll(contactsList);
    }
}