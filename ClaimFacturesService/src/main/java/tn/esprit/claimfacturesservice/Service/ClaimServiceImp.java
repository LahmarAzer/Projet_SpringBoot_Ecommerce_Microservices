package tn.esprit.claimfacturesservice.Service;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.claimfacturesservice.Entities.*;
import tn.esprit.claimfacturesservice.Repository.*;
import tn.esprit.claimfacturesservice.dtoEntities.MailRequest;
import tn.esprit.claimfacturesservice.dtoEntities.MailResponse;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@AllArgsConstructor
@Service
@Slf4j
public class ClaimServiceImp implements ClaimService {
    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration config;
    private final ClaimRepo claimRepo;

    private final ProductRepository productRepo;


    private final CartLineRepository cartLineRepository;

    private final  UserRepository userRepo;

    private final  FactureRepository factureRepo;

    private final DeliveryRepository deliveryRepository;

    private final EmailService emailService;


    @Override
    public Claim addClaim(Claim claim) {
        claim.setDateCreationClaim(new Date());
        claim.setStatusClaim(StatusClaim.NONTRAITE);
        return claimRepo.save(claim);
    }


    @Override
    public Claim UpdateClaim(Claim claim) {
        return claimRepo.save(claim);
    }

    @Override
    public Boolean DeleteClaim(Long id) {
        if (claimRepo.existsById(id)) {
            claimRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
     public Claim retrieveclaimById(Long id) {
        return claimRepo.findById(id).orElse(null);
    }

    @Override
    public List<Claim> retrieveAllclaim() {
        return claimRepo.findAll();
    }

    @Override
    public Claim UpdateClaimStatut(Long idClaim, StatusClaim newStatut) {
        Claim claim = claimRepo.findById(idClaim).orElse(null);
        claim.setStatusClaim(newStatut);
        return claimRepo.save(claim);
    }

    @Override
    public boolean isOwner(long idclaim) {
        User user = claimRepo.findById(idclaim).get().getUserclaim();
        Facture facture = claimRepo.findById(idclaim).get().getFacture();
        if(user!=null && facture!=null) {
            if (user.getIdUser() == facture.getUser().getIdUser()) {
                return true;
            }
            return false;
   }return false ;
    }
    public String isPurchase(String refProduct, String idUser) {
        User user = userRepo.findById(idUser).orElse(null);
        List<Facture> factures = user.getFactureList();
        Product productRef = productRepo.findByReferenceProduct(refProduct);
        if (productRef != null) {
            for (Facture facture : factures) {
                List<Delivery> deliveries = new ArrayList<>();
                deliveries.add(facture.getDelivery());
                for (Delivery delivery : deliveries) {
                    List<Cart> carts = new ArrayList<>();
                    carts.add(delivery.getCart());
                    for (Cart cart : carts) {
                        List<CartLine> cartLines = new ArrayList<>();
                        cartLines.addAll(cart.getCartLines());
                        for (CartLine cartLine : cartLines) {
                            List<Product> products = new ArrayList<>();
                            products.add(cartLine.getProduct());
                            for (Product product : products) {
                                if (product.getReferenceProduct() != refProduct) {
                                }
                                return ("this product"+ " " + product.getNameProduct() +" "+ " has been purchased by the user "+ " " + user.getFirstName() + " " + user.getLastName());
//                                return ("this product Savon with reference 147m has been purchased by the Eya zidi ");
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    public Boolean DateValideClaim(Long Idclaim, Long Iddelivery) {
        Delivery delivery= deliveryRepository.findById(Iddelivery).orElse(null);
        Claim claim = claimRepo.findById(Idclaim).orElse(null);
        Date deliveryDate = delivery.getDeliverytime();
        Date claimDate = claim.getDateCreationClaim();
        Long diff = claimDate.getTime() - deliveryDate.getTime();
        float res = (diff / (1000 * 60 * 60 * 24));
      if(delivery != null && claim!=null ){
        if (res >= 3 && (claimDate.after(deliveryDate))) {
             // return (true +""+ res);

            return false;
        }
       return true;
      }
        // return ("false" + ""+ res);
        return false;
    }

    @Override
    public String isClaimValid(Claim claim, String invoiceNumber) {
        long claimId= claimRepo.save(claim).getIdClaim();
       // Claim claim = claimRepo.findById(claimId).orElse(null);
        Facture facture = factureRepo.findByReference(invoiceNumber);
        long iddelivery = facture.getDelivery().getId_Delivery();
        log.info("temchi0");


        DeliveryMen men = factureRepo.findByReference(invoiceNumber).getDelivery().getDeliveryMen();

        // if (facture != null && facture.getClaims().equals(claim)) {
        if (claim.getCategoryClaim().equals(CategoryClaim.PRODUCTCLAIM)) {

            if (DateValideClaim(claimId, iddelivery) && isOwner(claimId)) {


                claimRepo.save(claim); return ("saved") ;
            } else { claimRepo.delete(claim);  return ("deleted");}


        } else if (claim.getCategoryClaim().equals(CategoryClaim.DELIVERYCLAIM)) {
            claim.setDeliveryMen(men);
            claimRepo.save(claim);
            return ("claim saved with affect a claim to deliveryMen ");
        }

        else if (claim.getCategoryClaim().equals(CategoryClaim.SERVICECLAIM))
            claimRepo.save(claim);

        else claimRepo.delete(claim);
        return ("claim saved");
    }


    public int getnbrproduitVendus(String idUser){
        User user=userRepo.findById(idUser).orElse(null);
        List<Product>products=user.getProductListUser();
        List<CartLine>cartLines = new ArrayList<>();
        int productsSold=0;
        for (Product product:products) {
            for (CartLine cartLine:product.getCartLines()) {
                Objects.requireNonNull(cartLines).add(cartLine);
            }
        }

        for (CartLine cartLine: Objects.requireNonNull(cartLines)) {
            if (cartLine.getCart().getCartStatus().equals(CartStatus.CONFIRMED)){
                productsSold=productsSold+cartLine.getQuantity();
            }
        }
        return productsSold;
    }


    @Override
    @Transactional
    public String banUser(String supplierId) {
        User user=userRepo.findById(supplierId).orElse(null);
        List<Claim> claims  = user.getClaimList();
        System.out.println("user claim "+user.getClaimList());


        int nbcart= getnbrproduitVendus( supplierId);
        int nbclaims =claims.size();
       if(nbcart > 0){
         if (nbclaims <= (nbcart*20)/100){
             int nba= user.getNbrAvertissment();
             if (nba <3){
                 //mail tanbih
                    nba++;
                    log.info("user have "+ nba + "avertissment");
                    user.setNbrAvertissment(nba);
                    userRepo.save(user);
                 return("user have "+ nba + "avertissment");

             }else {
                 user.setBanned(true);
                 userRepo.save(user);
                 System.out.println("User Banned");

             }return("user is banned");

          }
       }return("user didn't have a lot of claim");
    }



public MailResponse sendEmail(MailRequest request, Map<String, Object> model) {
    MailResponse response = new MailResponse();
    MimeMessage message = sender.createMimeMessage();
    try {
        // set mediaType
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        // add attachment
        helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

        Template t = config.getTemplate("email-template.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(request.getTo());
        helper.setText(html, true);
        helper.setSubject(request.getSubject());
        helper.setFrom(request.getFrom());
        sender.send(message);

        response.setMessage("mail send to : " + request.getTo());
        response.setStatus(Boolean.TRUE);

    } catch (MessagingException | IOException | TemplateException e) {
        response.setMessage("Mail Sending failure : "+e.getMessage());
        response.setStatus(Boolean.FALSE);
    }

    return response;
}


}

















