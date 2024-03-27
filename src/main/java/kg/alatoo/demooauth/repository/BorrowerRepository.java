package kg.alatoo.demooauth.repository;

import kg.alatoo.demooauth.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BorrowerRepository extends JpaRepository<Borrower, Integer> {
    @Query(value = "select * from borrower s where s.name like %:keyword% or s.email like %:keyword%", nativeQuery = true)
    List<Borrower> findByKeyword(@Param("keyword") String keyword);
}
