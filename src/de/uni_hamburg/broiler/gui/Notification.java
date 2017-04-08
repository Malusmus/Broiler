package de.uni_hamburg.broiler.gui;

public class Notification {
   private String event;
   private Object payload;
   
   public Notification(String e, Object p)
   {
	   event = e;
	   payload = p;
   }
   
   public String getEvent()
   {
	   return event;
   }
   
   public Object getPayload()
   {
	   return payload;
   }
   
   
}
