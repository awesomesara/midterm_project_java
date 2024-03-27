package kg.alatoo.demooauth.controllers;



import kg.alatoo.demooauth.entity.Book;
import kg.alatoo.demooauth.entity.Borrower;
import kg.alatoo.demooauth.repository.BookRepository;
import kg.alatoo.demooauth.repository.BorrowerRepository;
import kg.alatoo.demooauth.service.BookService;
import kg.alatoo.demooauth.service.BorrowerService;
import kg.alatoo.demooauth.service.MyBookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BController {

    private final
    BorrowerRepository borrowerRepository;
    private final
    BookRepository bookRepository;

    @Autowired
    private MyBookListService myService;

    @Autowired
    BookService bookService;

    @Autowired
    BorrowerService service;

    public BController(BorrowerRepository borrowerRepository, BookRepository bookRepository) {
        this.borrowerRepository = borrowerRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/borrowers")//borrowers
    private String borrowersPagess(Model model){
        model.addAttribute("users", borrowerRepository.findAll());
        System.out.println(borrowerRepository.findAll().toString());
        return "borrower/borrowersPage";
    }


    @RequestMapping(path = {"/search"})
    public String home(Book shop, Model model, String keyword) {
        System.out.println(keyword + "\n\n\n");
        if(keyword!=null) {
            List<Book> list = bookService.getByKeyword(keyword);
            model.addAttribute("list", list);
        }else {
            List<Book> list = bookService.getAllBooks();
            model.addAttribute("list", list);}
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().toString().contains("ADMIN")) {
            return "bookList";
        }
        return "bookListForUsers";
    }


    @RequestMapping(path = {"/borrowers/search"})
    public String hosme(Book shop, Model model, String keyword) {
        if(keyword!=null) {
            List<Borrower> list = service.getByKeyword(keyword);
            model.addAttribute("users", list);
        }else {
            List<Borrower> list = service.getAllBooks();
            model.addAttribute("users", list);}
        return "borrower/borrowersPage";
    }



    @GetMapping("/borrowers/book_register")
    public String bookRegister(Model model){
        model.addAttribute("allBooks", bookRepository.findAll());
        return "borrower/borrowersBookRegister";
    }


    @PostMapping("/borrowers/save")
    public String addBook(@ModelAttribute Borrower b){
        service.save(b);
        return "redirect:/borrowers";
    }



    @RequestMapping("/borrowers/editBook/{id}")
    public String editeBook(@PathVariable Integer id, Model model){
        Borrower b=service.getBookById(id);
        model.addAttribute("book",b);
        model.addAttribute("allBooks",bookRepository.findAll());
        return "borrower/borrowersBookEdit";
    }
    @RequestMapping("/borrowers/deleteBook/{id}")
    public String deleteeBook(@PathVariable Integer id){
        service.deleteById(id);
        return "redirect:/borrowers";
    }
    @RequestMapping("/deleteMyList/{id}")
    public String deleteMyList(@PathVariable("id")int id){
        bookService.save(new Book(id, myService.getById(id).getName(), myService.getById(id).getAuthor(),
                myService.getById(id).getPrice()) );
        myService.deleteById(id);
        return"redirect:/my_books";
    }
}
