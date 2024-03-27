package kg.alatoo.demooauth.controllers;

import kg.alatoo.demooauth.bookdto.BookDTO;
import kg.alatoo.demooauth.entity.Book;
import kg.alatoo.demooauth.entity.MyBookList;
import kg.alatoo.demooauth.entity.User;
import kg.alatoo.demooauth.repository.RoleRepository;
import kg.alatoo.demooauth.repository.UserRepository;
import kg.alatoo.demooauth.service.BookService;
import kg.alatoo.demooauth.service.MyBookListService;
import kg.alatoo.demooauth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class MainController {

    public  static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";


    @Autowired
    private UserService userService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/") public String homePage(){

        if (userService.CreateFirstAdmin()){

        }
        else {
            System.out.println("Not working");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getAuthorities()+"   ---   "+auth.getName());

        if (auth.getAuthorities().toString().contains("ADMIN")) {
            return "home";
        }
        if (auth.getAuthorities().toString().contains("USER")) {
            return "homeForUsers";
        }

        return "homeL";

    }
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/login")
    public String loginPage(){

        return "login";
    }
    @GetMapping("/register") public String registerPage(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.isAuthenticated()) {
            return "register";
        }

        return "registerL";
    }
    @PostMapping("/register")
    public String postRegister(@ModelAttribute User user){
        userService.createUser(user);
        return "login";
    }

    @Autowired
    public BookService service;

    @Autowired
    PasswordEncoder encoder;


    @Autowired
    private MyBookListService myBookListService;



    @GetMapping("/book_register")
    public String bookRegister(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("bookDTO", new BookDTO());

        if (auth.getAuthorities().toString().contains("ADMIN")) {
            return "admin/bookRegister";
        }
        return "homeForUsers";
    }
    @PostMapping("/book_register")
    public String productAddPost(@ModelAttribute("productDTO") BookDTO bookDTO,
                                 @RequestParam("cover-images") MultipartFile file,
                                 @RequestParam("imgName") String imgName) throws IOException {

        Book product = new Book();
        product.setAuthor(bookDTO.getAuthor());
        product.setName(bookDTO.getName());
        product.setPrice(bookDTO.getPrice());


        String imageUUID;

        if (!file.isEmpty()) {
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());

        } else {
            imageUUID = imgName;
        }

        product.setImageName(imageUUID);
        service.save(product);

        return "redirect:/available_books";
    }
    List<String> listSort = new ArrayList<>();

    @GetMapping("/webphoto/{photoName}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable String photoName) throws IOException {
        Path photo = Path.of("cover-images", photoName);
        byte [] bytes = Files.readAllBytes(photo);
        return ResponseEntity.ok().header("Content-Type", MediaType.IMAGE_PNG.toString()).body(bytes);
    }


    @RequestMapping(path = {"/available_books"})
    public String getAllBook(Model model, String keyword) {
        listSort.clear();
        listSort.add("By Author Asc");listSort.add("By Author Desc");listSort.add("By Name Asc");listSort.add("By Name Desc");
        listSort.add("By Prize Asc");listSort.add("By Prize Desk");listSort.add("By Id Desc");
        model.addAttribute("listS", listSort);

        if(keyword!=null) {
            switch (keyword){
                case "By Author Asc":
                    model.addAttribute("list", service.bRepo.findByOrderByAuthorAsc());
                    break;
                case "By Author Desc":
                    model.addAttribute("list", service.bRepo.findByOrderByAuthorDesc());
                    break;
                case "By Name Asc":
                    model.addAttribute("list", service.bRepo.findByOrderByNameAsc());
                    break;
                case "By Name Desc":
                    model.addAttribute("list", service.bRepo.findByOrderByNameDesc());
                    break;
                case "By Prize Asc":
                    model.addAttribute("list", service.bRepo.findByOrderByPriceAsc());
                    break;
                case "By Prize Desc":
                    model.addAttribute("list", service.bRepo.findByOrderByPriceDesc());
                    break;
            }


        }else {

            model.addAttribute("list", service.getAllBooks());
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().toString().contains("ADMIN")) {
            return "bookList";
        }

        return "bookListForUsers";
    }
    @PostMapping("/save")
    public String addBook(@ModelAttribute Book b){
        service.save(b);
        return "redirect:/available_books";
    }
    @GetMapping("/my_books")
    public String getMyBooks(Model model){
        List<MyBookList>list=myBookListService.getAllMyBooks();
        model.addAttribute("book",list);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().toString().contains("ADMIN")) {
            return "admin/MyBooks";
        }

        return "MyBooksForUsers";
    }
    @RequestMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id")int id){
        Book b= service.getBookById(id);
        MyBookList mb=new MyBookList(b.getId(), b.getName(), b.getAuthor(),b.getPrice(), b.getImageName());
        myBookListService.saveMyBook(mb);
        service.deleteById(b.getId());
        return "redirect:/my_books";
    }


    @RequestMapping("/editBook/{id}")
    public String editBook(@PathVariable("id")int id, Model model){
        Book b=service.getBookById(id);
        model.addAttribute("book",b);
        return"admin/bookEdit";
    }
    @RequestMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id")int id){
        Book b = service.getBookById(id);

        service.deleteById(id);
        return "redirect:/available_books";
    }

}
