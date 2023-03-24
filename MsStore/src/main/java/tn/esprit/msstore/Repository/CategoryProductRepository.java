package tn.esprit.msstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.msstore.Entity.CategoryProduct;

public interface CategoryProductRepository extends JpaRepository<CategoryProduct,Long> {
    CategoryProduct findCategoryProductByNameCategoryProduct(String categoryName);
}
