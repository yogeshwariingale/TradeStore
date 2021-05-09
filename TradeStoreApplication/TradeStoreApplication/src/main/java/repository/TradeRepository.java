package repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.main.bean.Trade;

public interface TradeRepository extends CrudRepository<Trade, Long> {
	@Query("from trade trade where :tradeId = lab.tradeId")
	public Trade findByTradeId(@Param("tradeId") String tradeId);
	
}