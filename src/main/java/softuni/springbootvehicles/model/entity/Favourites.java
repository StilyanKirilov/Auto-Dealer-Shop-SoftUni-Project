package softuni.springbootvehicles.model.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Favourites {
    private Map<Long, Offer> favs = new ConcurrentHashMap<>();

    public void addOffer(Offer offer) {
        this.favs.put(offer.getId(), offer);
    }

    public Offer getOfferById(Long offerId) {
        return this.favs.get(offerId);
    }

    public Offer removeOfferById(Long offerId) {
        return this.favs.remove(offerId);
    }

    public Set<Long> getAllOffersIds() {
        return this.favs.keySet();
    }

    public Collection<Offer> getAllOffers(){
        return this.favs.values();
    }
}
