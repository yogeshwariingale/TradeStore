package com.main.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.main.bean.Trade;

import repository.TradeRepository;

@Controller
public class TradeController {
	
	 @Autowired
	  TradeRepository er;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	 
	/**
	 * Serice is created to Store Trade Data
	 * @param tradeId
	 * @param version
	 * @param counterPartyId
	 * @param bookId
	 * @param maturityDate
	 * @param createdDate
	 * @param expired
	 * @return
	 */
		  @ResponseBody
		 @RequestMapping(value="/saveTrade", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
		    public String bookSlot(@RequestParam("tradeId") String tradeId,@RequestParam("version") String version,
		    		@RequestParam("counterPartyId") String counterPartyId,@RequestParam("bookId") String bookId,@RequestParam("MaturityDate") String maturityDate,
		    		@RequestParam("createdDate") String createdDate,@RequestParam("expired") String expired){
		  try {
			  Trade tradeDB = er.findByTradeId(tradeId);
			 
			  Trade trade = new Trade();
			  trade.setTradeId(tradeId);
			  trade.setBookId(bookId);
			  trade.setCreatedDate(sdf.parse(createdDate));
			  trade.setCounterPartyId(counterPartyId);
			  trade.setMaturityDate(sdf.parse(maturityDate));
			  trade.setVersion(Integer.parseInt(version));
			  if(tradeDB != null) {
				  if(isValid(trade,tradeDB)){
					  trade.setId(tradeDB.getId());
					  er.save(trade);
				  }
			  }else {
				  if(isValid(trade,null)){
					er.save(trade);  
				  }
			  }
		      }catch(Exception e) {
		    	  System.out.println(e.getMessage());
		    	  return "FAIL : "+e.getMessage();
		      }
		        return "SUCCESS";
		    }
		    
		    
		    
	private boolean isValid(Trade trade,Trade tradeDB) {
		if(  tradeDB != null && (trade.getVersion() < (tradeDB).getVersion())) {
			return false;
		}
	
		if(trade.getMaturityDate().compareTo(new Date())  < 0) {
			return false;
		}
		
		return false;
	}

}
