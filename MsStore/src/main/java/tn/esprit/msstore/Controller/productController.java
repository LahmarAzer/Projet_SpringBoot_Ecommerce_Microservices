package tn.esprit.msstore.Controller;

import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.msstore.Entity.Product;
import tn.esprit.msstore.Repository.ProductRepository;
import tn.esprit.msstore.Service.IProductService;
import tn.esprit.msstore.Service.IProductServiceIMP;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/store/product")
public class productController {
    IProductService iProductService;
    IProductServiceIMP iProductServiceIMP;

    @PostMapping("/favoris/{idClient}/{idProduit}")
    public void ajouterProduitFavori(@PathVariable String idClient, @PathVariable Long idProduit) {
        iProductService.addFavoriProduct(idClient, idProduit);
    }

    @GetMapping("/search/{name}/{marque}")
    public ResponseEntity<List<Product>> searchProducts(
            @PathVariable(required = false) String name, @PathVariable(required = false) String marque) {
        List<Product> products = iProductService.searchProducts(name, marque);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/clients/{idClient}/favoris")
    public List<Product> getProduitsFavorisByClient(@PathVariable String idClient) {
        return iProductService.getProductsFavoris(idClient);
    }

    @GetMapping("/produitsPag/{page}/{size}")
    public Map<String, Object> getPaginatedProducts(@PathVariable int page, @PathVariable int size) {
        return iProductService.findAllProdPaginatedAndSorted(page, size);
    }

    @GetMapping("/getRecommandedProduct")
    public List<Product> GetAllProductsrecommanded() {
        return iProductService.getTopRecommendedProducts();
    }

    @PutMapping("/updateproduct/{idUser}/{idProduct}")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable String idUser, @PathVariable Long idProduct) {
        try {
            iProductService.modifierProduit(product, idUser, idProduct);
            return ResponseEntity.ok("Product updated successfully.");
        } catch (IProductServiceIMP.ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @PostMapping("/addproduit")
    public ResponseEntity<?> addProduct(@RequestParam("idUser") String idUser, @RequestParam("idCategory") Long idCategory,
                                        @RequestParam("idSousCategory") Long idSousCategory,
                                        @RequestParam("descriptionProduct") String descriptionProduct
            , @RequestParam("priceProduct") float priceProduct, @RequestParam("quantityProduct") Long quantityProduct
            , @RequestParam("nameProduct") String nameProduct
            , @RequestParam("referenceProduct") String referenceProduct , @RequestParam("file") MultipartFile file,
                                        @RequestParam("discountProduct") float discountProduct ,
                                        @RequestParam("marqueProduct") String marqueProduct) {
        Product product = new Product();
        product.setDescriptionProduct(descriptionProduct);
        product.setQuantityProduct(quantityProduct);
        product.setPriceProduct(priceProduct);
        product.setNameProduct(nameProduct);
        product.setReferenceProduct(referenceProduct);
        product.setDiscountProduct(discountProduct);
        product.setMarqueProduct(marqueProduct);
        try {
            iProductService.ajouterProduit(product, idUser, idCategory, idSousCategory,file);
            return ResponseEntity.ok("Product added successfully.");
        } catch (IProductServiceIMP.ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @GetMapping("/produits/categorie/{categorieNom}")
    public List<Product> getProduitsByCategorie(@PathVariable String categorieNom) {
        return iProductService.findProductsByCategory(categorieNom);
    }

    @GetMapping("/{productId}/qrcode")
    public void generateQRCode(@PathVariable Long productId, HttpServletResponse response) throws IOException, WriterException {
        Product product = iProductService.getProductById(productId);
        iProductService.generateQRCode(product, response);
    }

    @DeleteMapping("/deleteProduct/{idProduct}/{idUser}")
    public void removeProduct(@PathVariable Long idProduct , @PathVariable String idUser) {
        iProductService.removeProduct(idProduct, idUser);
    }

    @GetMapping("/getproductbyid/{productId}")
    public Product getproductById(@PathVariable Long productId) {
        return  iProductService.getProductById(productId);
    }


}
