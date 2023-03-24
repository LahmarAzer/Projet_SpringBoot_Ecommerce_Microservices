package tn.esprit.msstore.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.msstore.Entity.*;
import tn.esprit.msstore.Repository.*;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
@Slf4j
@Service
@AllArgsConstructor
public class IProductServiceIMP implements IProductService {
    CategoryProductRepository categoryProductRepository ;
    UserRepository userRepository;
    FavorisProductRepository favorisProductRepository;
    ProductRepository productRepository;
    CartLineRepository cartLineRepository ;
    SousCategorieRepository sousCategorieRepository ;
    PromotionProductRepository promotionProductRepository;

    public class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }
    @Override
    public void removeProduct(Long idProduct , String idUser) {
        Product product = productRepository.findById(idProduct).orElse(null);
        User user = userRepository.findById(idUser).orElse(null);
        if (product != null && product.getUserProduct().getIdUser().equals(idUser) ){
            productRepository.deleteById(idProduct);
        }else
            log.info("error se produit ");

    }
    @Override
    public Product ajouterProduit(Product product,String idUser,Long idCategory , Long idSousCategory , MultipartFile productImage) throws  IOException , ValidationException {
        User user = userRepository.findById(idUser).orElse(null);
        CategoryProduct categoryProduct = categoryProductRepository.findById(idCategory).orElse(null);
        Sous_CategoryProduct sous_categoryProduct = sousCategorieRepository.findById(idSousCategory).orElse(null);
        if(user != null && categoryProduct != null && sous_categoryProduct != null ) {
            if (product.getNameProduct() == null || product.getNameProduct().isEmpty()) {
                throw new ValidationException("Le nom du produit est obligatoire");
            }
            if (product.getPriceProduct() <= 0) {
                throw new ValidationException("Le prix du produit doit être supérieur à zéro");
            }
            if (product.getQuantityProduct() <= 0) {
                throw new ValidationException("La quantité du produit doit être supérieure à zéro");
            }
            if (product.getReferenceProduct() == null || product.getReferenceProduct().isEmpty()) {
                throw new ValidationException("La référence du produit est obligatoire");
            }
            if (product.getDiscountProduct() < 0.0 || product.getDiscountProduct() > 0.0) {
                throw new ValidationException("La réduction du produit doit être comprise entre 0 et 100");
            }
            if (product.getMarqueProduct() == null || product.getMarqueProduct().isEmpty()) {
                throw new ValidationException("La marque du produit est obligatoire");
            }
            if (!productImage.isEmpty()) {
                product.setImageProduct(productImage.getBytes());
            }
            product.setDateCreationProduct(new Date());
            product.setUserProduct(user);
            product.setCategoryProduct(categoryProduct);
            product.setSousCategorie(sous_categoryProduct);
            return productRepository.save(product);
        }
        return null;
    }

    @Override
    public Product modifierProduit(Product product, String idUser,Long idProduct) {
        Product productUser = productRepository.findById(idProduct).orElse(null);

        if (productUser.getUserProduct().getIdUser().equals(idUser)){
            if (product.getNameProduct() == null || product.getNameProduct().isEmpty()) {
                throw new ValidationException("Le nom du produit est obligatoire");
            }
            if (product.getPriceProduct() <= 0) {
                throw new ValidationException("Le prix du produit doit être supérieur à zéro");
            }
            if (product.getQuantityProduct() <= 0) {
                throw new ValidationException("La quantité du produit doit être supérieure à zéro");
            }
            if (product.getReferenceProduct() == null || product.getReferenceProduct().isEmpty()) {
                throw new ValidationException("La référence du produit est obligatoire");
            }
            if (product.getDiscountProduct() < 0.0 || product.getDiscountProduct() > 0.0) {
                throw new ValidationException("La réduction du produit doit être comprise entre 0 et 100");
            }
            if (product.getMarqueProduct() == null || product.getMarqueProduct().isEmpty()) {
                throw new ValidationException("La marque du produit est obligatoire");
            }
            product.setUserProduct(productUser.getUserProduct());
            product.setSousCategorie(productUser.getSousCategorie());
            product.setCategoryProduct(productUser.getCategoryProduct());
            product.setComment(productUser.getComment());
            product.setImageProduct(productUser.getImageProduct());

            return productRepository.save(product);

        }
        return null;
    }

    @Override
    public Product getProductById(Long idProduct) {
        return productRepository.findById(idProduct).orElse(null);
    }


    @Override
    public Map<String, Object> findAllProdPaginatedAndSorted(int page , int size) {
        try {
            List<Product> produitList = new ArrayList<>();
            Pageable paging= PageRequest.of(page,size);
            Page<Product> pageProduit=this.productRepository.findAll(paging);
            produitList = pageProduit.getContent();
            //Sort sort = Sort.by(Sort.Direction.DESC,"dateCreationProduct");
            Map<String,Object> produits=new HashMap<>();
            produits.put("produits",produitList);
            produits.put("pageCurrent",pageProduit.getNumber());
            produits.put("totalItems",pageProduit.getTotalElements());
            produits.put("totalPage",pageProduit.getTotalPages());
            return produits;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Product> getTopRecommendedProducts() {
        // Sélectionner les produits qui ont été commandés au moins une fois
        List<Long> orderedProductIds = cartLineRepository.findOrderedProductIds();
        // Trier les produits par ordre décroissant de notes moyennes et de quantité commandée
        List<Product> recommendedProducts = productRepository
                .findProductAvgRatingAndTotalQuantity(orderedProductIds);
        // Retourner une sous liste de la liste  => Top 10
        recommendedProducts = recommendedProducts.subList(0, Math.min(recommendedProducts.size(), 10));
        return recommendedProducts;
    }

    //@Scheduled(fixedRate = 75600000) // par jour
    @Scheduled(fixedRate = 30000) // par jour
    public void checkStock() {
        int criticalStockAlerts = 0;
        int lowStockAlerts = 0;
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            if (product.getQuantityProduct() == 0) {
                sendCriticalStockAlert(product);
                criticalStockAlerts++;
            } else if (product.getQuantityProduct() < 10) {
                sendLowStockAlert(product);
                lowStockAlerts++;
            }
        }
        log.info("criticalStockAlerts : " + criticalStockAlerts );
        log.info("lowStockAlerts : " + lowStockAlerts );
    }

    private void sendCriticalStockAlert(Product product) {
        // Envoyer une alerte pour le stock critique
        log.info("Le stock de " + product.getNameProduct() + " est critique (0).");
    }

    private void sendLowStockAlert(Product product) {
        // Envoyer une alerte pour le stock faible
        log.info("Le stock de " + product.getNameProduct() + " est faible (" + product.getQuantityProduct() + ").");
    }

    @Transactional
    @Override
    public void addFavoriProduct(String idClient, Long idProduit) {
        User user = userRepository.findById(idClient).orElse(null);
        Product produit = productRepository.findById(idProduit).orElse(null);
        if ( user != null && produit != null ) {
            FavoriProduct favoriUserProduct = favorisProductRepository.findByUserAndProduct(user, produit);
            if (favoriUserProduct == null) {
                FavoriProduct favoriProduct = new FavoriProduct();
                favoriProduct.setProduct(produit);
                favoriProduct.setUser(user);
                favoriProduct.setDateAjout(new Date());
                favorisProductRepository.save(favoriProduct);
            } else {
                favorisProductRepository.delete(favoriUserProduct);
            }
        }
        log.info("user or product not found ");
    }

    @Override
    public List<Product> getProductsFavoris(String idClient) {
        User user = userRepository.findById(idClient).orElse(null);
        if (user != null ) {
            List<FavoriProduct> favoris = favorisProductRepository.findByUser_IdUser(idClient);
            List<Product> produitsFavoris = new ArrayList<>();
            for (FavoriProduct favori : favoris) {
                produitsFavoris.add(favori.getProduct());
            }
            return produitsFavoris;
        }
        return null;
    }

    @Override
    public List<Product> searchProducts(String name, String marque) {
        List<Product> produits = new ArrayList<>();
         produits =productRepository.findByNameProductContainingIgnoreCaseAndMarqueProductContainingIgnoreCase
                (name, marque);
        if (produits != null){
            return produits;
        }
        return  null;
    }
    @Override
    public List<Product> findProductsByCategory(String categorieNom){
        CategoryProduct categoryProduct = categoryProductRepository.findCategoryProductByNameCategoryProduct(categorieNom);
        if (categoryProduct != null ) {
            return productRepository.findByCategorie(categorieNom);
        }
        return null ;
    }

    @Override
    public void generateQRCode(Product product, HttpServletResponse response) throws IOException, WriterException {

        // les données à inclure dans le code QR
        String qrCodeData = "Product Name: " + product.getNameProduct() +
                ", Price: " + product.getPriceProduct() +
                ", Quantity: " + product.getQuantityProduct();

        // configuration de la taille et des propriétés du code QR
        int size = 250;
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // génération du code QR en utilisant la bibliothèque ZXing
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, size, size, hints);

        // écriture du code QR dans la réponse HTTP en tant qu'image PNG
        response.setContentType("image/png");
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", response.getOutputStream());
    }

}
