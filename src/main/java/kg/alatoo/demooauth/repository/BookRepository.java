package kg.alatoo.demooauth.repository;

import kg.alatoo.demooauth.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    @Query(value = "select * from book s where s.name like %:keyword% or s.author like %:keyword%", nativeQuery = true)
    List<Book> findByKeyword(@Param("keyword") String keyword);


    List<Book> findByOrderByAuthorAsc();
    List<Book> findByOrderByAuthorDesc();
    List<Book> findByOrderByNameAsc();
    List<Book> findByOrderByNameDesc();
    List<Book> findByOrderByPriceAsc();
    List<Book> findByOrderByPriceDesc();
   /* private int id;
    private String author;
    private String name;
    private String price;*/



}
