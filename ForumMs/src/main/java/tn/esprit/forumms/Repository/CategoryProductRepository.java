package tn.esprit.forumms.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.forumms.Entity.CategoryProduct;

public interface CategoryProductRepository extends JpaRepository<CategoryProduct,Long> {

}
