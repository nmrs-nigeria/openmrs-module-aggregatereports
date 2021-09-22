/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.dataquality;

//import org.openmrs.event.EventListener;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
//import org.openmrs.event.Event.Action;

import org.springframework.stereotype.Component;

/**
 * @author lordmaul
 */
@Component("dataquality.NMRSEventListener")
public class NMRSEventListener //implements EventListener 
{
	
	private int createdCount = 0;
	
	private int updatedCount = 0;
	
	private int purgedCount = 0;
	
	public int getCreatedCount() {
		return createdCount;
	}
	
	public int getPurgedCount() {
		return purgedCount;
	}
	
	/*@Override
	public void onMessage(Message message) {
		try {
			System.out
			        .println("Messaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaage typeeeeeeeeeeeeeeeeeeeeeee:::typeeeeeeeeeeeeeeeeeeeeeee:::typeeeeeeeeeeeeeeeeeeeeeee:::typeeeeeeeeeeeeeeeeeeeeeee:::");
			MapMessage mapMessage = (MapMessage) message;
			if (Action.CREATED.toString().equals(mapMessage.getString("action")))
				createdCount++;
			else if (Action.UPDATED.toString().equals(mapMessage.getString("action")))
				updatedCount++;
			else if (Action.PURGED.toString().equals(mapMessage.getString("action")))
				purgedCount++;
			
			//..... Keep counts for more event actions
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ooops! some error occurred");
		}
	}*/
	
}
