package kg.alatoo.demooauth.apis;


import kg.alatoo.demooauth.entity.Book;
import kg.alatoo.demooauth.entity.Borrower;
import kg.alatoo.demooauth.entity.MyBookList;
import kg.alatoo.demooauth.repository.BookRepository;
import kg.alatoo.demooauth.repository.BorrowerRepository;
import kg.alatoo.demooauth.service.BookService;
import kg.alatoo.demooauth.service.BorrowerService;
import kg.alatoo.demooauth.service.MyBookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiDoc {
    @Autowired
    BookService bookService;
    @Autowired
    MyBookListService myBookListService;


    @GetMapping("/book_register")
    public String bookRegister(){
        return "bookRegister";
    }

    @GetMapping("/available_books")
    public List<Book> getAllBook() {
        return bookService.getAllBook();
    }
    @PostMapping("/save")
    public String addBook(@ModelAttribute Book b){
        bookService.save(b);
        return "redirect:/available_books";
    }
    @GetMapping("/my_books")
    public String getMyBooks(Model model){
        List<MyBookList>list=myBookListService.getAllMyBooks();
        model.addAttribute("book",list);
        return "MyBooks";
    }
    @RequestMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id")int id){
        Book b= bookService.getBookById(id);
        MyBookList mb=new MyBookList(b.getId(), b.getName(), b.getAuthor(),b.getPrice(), b.getImageName());
        myBookListService.saveMyBook(mb);
        bookService.deleteById(b.getId());
        return "redirect:/my_books";
    }

    @RequestMapping("/editBook/{id}")
    public String editBook(@PathVariable("id")int id, Model model){
        Book b=bookService.getBookById(id);
        model.addAttribute("book",b);
        return"bookEdit";
    }
    @RequestMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id")int id){
        bookService.deleteById(id);
        return "redirect:/available_books";
    }






    public ApiDoc(BorrowerRepository borrowerRepository, BookRepository bookRepository) {
        this.borrowerRepository = borrowerRepository;
        this.bookRepository = bookRepository;
    }

/*    @GetMapping(value = "/",headers = {})
    public String index() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(principal.getClass().getName());
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) principal;
        System.out.println(oAuth2User.toString());
        return "index";
    }*/
    private final
    BorrowerRepository borrowerRepository;
    private final
    BookRepository bookRepository;


    @Autowired
    BorrowerService service;
    @Autowired
    private MyBookListService serviceq;




    @RequestMapping(path = {"/search"})
    public String home(Book shop, Model model, String keyword) {
        System.out.println(keyword + "\n\n\n");
        if(keyword!=null) {
            List<Book> list = bookService.getByKeyword(keyword);
            model.addAttribute("book", list);
        }else {
            List<Book> list = bookService.getAllBooks();
            model.addAttribute("book", list);}
        return "bookList";
    }


    @RequestMapping(path = {"/borrowers/search"})
    public String hosme(Book shop, Model model, String keyword) {
        if(keyword!=null) {
            List<Borrower> list = service.getByKeyword(keyword);
            model.addAttribute("users", list);
        }else {
            List<Borrower> list = service.getAllBooks();
            model.addAttribute("users", list);}
        return "borrowersPages/borrowersPage";
    }

    @GetMapping("/borrowers")
    private String BorrowersPage(Model model){
        model.addAttribute("users", borrowerRepository.findAll());
        return "borrowersPages/borrowersPage";
    }

    @GetMapping("/borrowers/book_register")
    public String bookRegister(Model model){
        model.addAttribute("allBooks", bookRepository.findAll());
        return "/borrowersPages/borrowersBookRegister";
    }
    @RequestMapping("/deleteMyList/{id}")
    public String deleteMyList(@PathVariable("id")int id){
        bookService.save(new Book(id, serviceq.getById(id).getName(), serviceq.getById(id).getAuthor(),
                serviceq.getById(id).getPrice()) );
        serviceq.deleteById(id);
        return"redirect:/my_books";
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
        return "borrowersPages/borrowersBookEdit";
    }
    @RequestMapping("/borrowers/deleteBook/{id}")
    public String deleteeBook(@PathVariable Integer id){
        service.deleteById(id);
        return "redirect:/borrowers";
    }
}
