package tn.esprit.msstore.Service;

import com.google.zxing.WriterException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.msstore.Entity.Product;
import tn.esprit.msstore.dtoEntities.ProductDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IProductService {

    public Product ajouterProduit(Product product,String idUser,Long idCategory , Long idSousCategory ,MultipartFile productImage ) throws IOException ;
    public Product modifierProduit(Product product, String idUser,Long idProduct);
    public Product getProductById(Long idProduct);
    public void removeProduct(Long idProduct, String idUser);
    Map<String,Object> findAllProdPaginatedAndSorted(int page , int size);
    public List<Product> getTopRecommendedProducts();
    public void addFavoriProduct(String idClient, Long idProduit);
    public List<Product> getProductsFavoris(String idClient);
    public List<Product> searchProducts(String name, String marque);
    public List<Product> findProductsByCategory(String categoryName);
    public void generateQRCode(Product product, HttpServletResponse response) throws IOException, WriterException;

}
