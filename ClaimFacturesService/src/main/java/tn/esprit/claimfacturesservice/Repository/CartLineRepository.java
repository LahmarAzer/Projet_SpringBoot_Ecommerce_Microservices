package tn.esprit.claimfacturesservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.claimfacturesservice.Entities.CartLine;

import java.util.List;

import static com.sun.mail.imap.SortTerm.FROM;
import static org.hibernate.hql.internal.antlr.HqlTokenTypes.WHERE;
import static org.hibernate.loader.Loader.SELECT;

public interface CartLineRepository extends JpaRepository<CartLine,Long> {
    @Query("SELECT DISTINCT cl.product.idProduct FROM CartLine cl WHERE cl.cart.cartStatus = 'CONFIRMED'")
    List<Long> findOrderedProductIds();
//cartline teb3a fournisseur 1

    @Query("SELECT cartl FROM CartLine cartl WHERE cartl.cart.cartStatus='CONFIRMED' and cartl.cart.user.idUser= :userId")
    List<CartLine> getss(Long userId);
}
