package br.com.nlw.events.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nlw.events.dto.SubscriptionRankingItem;
import br.com.nlw.events.dto.SubscriptionResponse;
import br.com.nlw.events.exception.EventNotFoundException;
import br.com.nlw.events.exception.SubscriptionConflictException;
import br.com.nlw.events.exception.UserIndicatorNotFoundException;
import br.com.nlw.events.model.Event;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;
import br.com.nlw.events.repository.EventRepo;
import br.com.nlw.events.repository.SubscriptionRepo;
import br.com.nlw.events.repository.UserRepo;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepo evtRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubscriptionRepo subRepo;

    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId) {
        // recuperar o evento pelo nome
        Event evt = evtRepo.findByPrettyName(eventName);
        if (evt == null) {
            throw new EventNotFoundException("Evento " + eventName + " não existe.");
        }

        User userRec = userRepo.findByEmail(user.getEmail());
        if (userRec == null) {
            userRec = userRepo.save(user);
        }

        User indicator = null;
        if (userId != null) {
            indicator = userRepo.findById(userId).orElse(null);
            if (indicator == null) {
                throw new UserIndicatorNotFoundException("Usuário " + userId + " indicador não existe");
            }
        }

        Subscription subs = new Subscription();

        subs.setEvent(evt);
        subs.setSubscriber(userRec);
        subs.setIndication(indicator);

        Subscription tmpSub = subRepo.findByEventAndSubscriber(evt, userRec);
        if (tmpSub != null) {
            throw new SubscriptionConflictException(
                    "Inscrição já existente para o usuário " + userRec.getName() + " no evento " + evt.getTitle());
        }

        Subscription result = subRepo.save(subs);

        var formatedDesignation = "http//codecraft.com/subscription/" + result.getEvent().getPrettyName() + "/"
                + result.getSubscriber().getId();

        return new SubscriptionResponse(result.getSubscriptionNumber(),
                formatedDesignation);
    }

    public List<SubscriptionRankingItem> getCompleteRanking(String prettyName) {
        Event evt = evtRepo.findByPrettyName(prettyName);
        if (evt == null) {
            throw new EventNotFoundException("Ranking do evento " + prettyName + " não existe.");
        }
        return subRepo.generateRanking(evt.getEventId());
    }
}
