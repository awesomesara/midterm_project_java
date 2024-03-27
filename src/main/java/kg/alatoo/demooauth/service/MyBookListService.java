package kg.alatoo.demooauth.service;


import kg.alatoo.demooauth.entity.Book;
import kg.alatoo.demooauth.entity.MyBookList;
import kg.alatoo.demooauth.repository.BookRepository;
import kg.alatoo.demooauth.repository.MyBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyBookListService {
    @Autowired
    private MyBookRepository mybook;

    public void saveMyBook(MyBookList book){
        mybook.save(book);
    }
    public List<MyBookList> getAllMyBooks(){
        return mybook.findAll();
    }
    public void deleteById(int id) {
        mybook.deleteById(id);
    }
    public MyBookList getById(int id){
        return mybook.getById(id);
    }
}
