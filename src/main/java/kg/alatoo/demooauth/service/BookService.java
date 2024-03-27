package kg.alatoo.demooauth.service;

import kg.alatoo.demooauth.entity.Book;
import kg.alatoo.demooauth.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {
    @Autowired
    public BookRepository bRepo;
    public void save(Book b){
        bRepo.save(b);
    }
    public List<Book> getAllBook(){
        return bRepo.findAll();
    }


    public Book getBookById(int id){
        return bRepo.findById(id).get();
    }
    public void deleteById(int id){
        bRepo.deleteById(id);
    }

    public List<Book> getByKeyword(String keyword){
        return bRepo.findByKeyword(keyword);
    }
    public List<Book> getAllBooks(){
        List<Book> list = (List<Book>) bRepo.findAll();
        return list;
    }
}
