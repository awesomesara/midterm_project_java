package kg.alatoo.demooauth.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Borrower {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    public void setId(int id) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMyBook() {
        return myBook;
    }

    public void setMyBook(String myBook) {
        this.myBook = myBook;
    }

    public int getId() {
        return id;
    }
    private String name, email, phone, myBook;
}
