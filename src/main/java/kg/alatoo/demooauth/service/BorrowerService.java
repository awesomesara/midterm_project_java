package kg.alatoo.demooauth.service;


import kg.alatoo.demooauth.entity.Borrower;
import kg.alatoo.demooauth.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerService {

    @Autowired
    public BorrowerRepository borrowerRepository;
    public void save(Borrower b){
        borrowerRepository.save(b);
    }
    public List<Borrower> getAllBook(){
        return borrowerRepository.findAll();
    }


    public Borrower getBookById(Integer id){
        return borrowerRepository.findById(id).get();
    }
    public void deleteById(Integer id){
        borrowerRepository.deleteById(id);
    }

    public List<Borrower> getByKeyword(String keyword){
        return borrowerRepository.findByKeyword(keyword);
    }
    public List<Borrower> getAllBooks(){
        List<Borrower> list = (List<Borrower>) borrowerRepository.findAll();
        return list;
    }
}
