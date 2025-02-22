package br.com.nlw.events.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.nlw.events.model.Event;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;
import br.com.nlw.events.dto.SubscriptionRankingItem;

public interface SubscriptionRepo extends CrudRepository<Subscription, Integer>{
    final String queryRanking = "SELECT user_name, COUNT(subscription_number) AS quantidade, indication_user_id FROM tbl_subscription ts JOIN tbl_user tu ON ts.indication_user_id = tu.user_id WHERE indication_user_id IS NOT NULL AND event_id = :eventId GROUP BY indication_user_id ORDER BY quantidade DESC";

    public Subscription findByEventAndSubscriber(Event event, User user);

    @Query(value = queryRanking, nativeQuery = true)
    public List<SubscriptionRankingItem> generateRanking(@Param("eventId") Integer eventId);
}
