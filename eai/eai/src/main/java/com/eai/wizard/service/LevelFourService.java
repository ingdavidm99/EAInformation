package com.eai.wizard.service;

import java.math.BigDecimal;
import java.util.List;

import com.eai.wizard.model.LevelFour;
import com.eai.wizard.model.ViewLevelThree;

public interface LevelFourService{

	public void save(LevelFour levelFour);
	
	public void save(List<ViewLevelThree> viewLevelThreeList, BigDecimal usdToCo, BigDecimal shippingUsd, BigDecimal shippingCop);
	
	public LevelFour findOne(Integer idLevel4);
	
	public BigDecimal priceValue(BigDecimal usdToCop, String priceUSD, BigDecimal shippingUsd, BigDecimal shippingCop);
}