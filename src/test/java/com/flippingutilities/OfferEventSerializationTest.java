package com.flippingutilities;

import com.flippingutilities.model.OfferEvent;
import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OfferEventSerializationTest
{
	private final Gson gson = new Gson();

	@Test
	public void listedPriceIsPersistedSeparatelyFromExecutionPrice()
	{
		OfferEvent offer = new OfferEvent();
		offer.setPrice(91);
		offer.setListedPrice(94);

		String json = gson.toJson(offer);
		assertTrue(json.contains("\"p\":91"));
		assertTrue(json.contains("\"lp\":94"));

		OfferEvent restored = gson.fromJson(json, OfferEvent.class);
		assertEquals(91, restored.getPreTaxPrice());
		assertEquals(94, restored.getListedPrice());
	}

	@Test
	public void oldAccountFilesDefaultListedPriceToUnknown()
	{
		OfferEvent restored = gson.fromJson("{\"p\":91}", OfferEvent.class);
		assertEquals(91, restored.getPreTaxPrice());
		assertEquals(0, restored.getListedPrice());
	}
}
