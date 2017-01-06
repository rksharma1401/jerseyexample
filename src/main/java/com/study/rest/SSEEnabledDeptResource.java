/**
 * 
 */
package com.study.rest;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.MediaType;

/**
 * @author ravikant.sharma
 * 05-Jan-2017
 */
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature; 

@Path("departments/events")
@Singleton
public class SSEEnabledDeptResource {

    

    private static ArrayList<String> modifiedDepts = new 
        ArrayList<String>();
    private static final SseBroadcaster broadcaster = new 
        SseBroadcaster();
   
    // Client subscribes to SSEs by calling 
    // this RESTful web service method.
    // The lastEventId param copies Last-Event-ID HTTP header
    // field sent by the client. This is used for replaying
    // missed events, if any
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput manageSSEEvents(
        @HeaderParam(SseFeature.LAST_EVENT_ID_HEADER) 
        @DefaultValue("-1") int lastEventId) {

        EventOutput eventOutput = new EventOutput();
        if (lastEventId > 0) {
            replayMissedUpdates(lastEventId, eventOutput);
        }
        if (!broadcaster.add(eventOutput)) {
             // Let's try to force a 5-s delayed client 
             // reconnect attempt
            throw new ServiceUnavailableException(5L);
        }
        return eventOutput;
    }
  //Replay all missed events since lastEventId
  private void replayMissedUpdates(final int lastEventId, 
       final EventOutput eventOutput) {
    try {
      for (int i = lastEventId; 
                     i < modifiedDepts.size(); i++) {
        eventOutput.write(createItemEvent(i, 
                           modifiedDepts.get(i)));
      }
    } catch (IOException ex) {
      throw new InternalServerErrorException
                     ("Error replaying missed events", ex);
    }
  }

   //This method generates an SSE whenever any client
    //invokes it to modify the department resource 
    //identified by path parameter
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") String name ) { 
    	modifiedDepts.add(name);
        final int modifiedListIndex = modifiedDepts.size()+1;
        // Broadcasting an un-named SSE with payload
        // as the name of the newly added item in data
        broadcaster.broadcast(createItemEvent
            (modifiedListIndex, name));
        
    }
    // A helper method to create OutboundEvent
    private OutboundEvent createItemEvent(final int eventId,
        final String name) {
         return 
             new OutboundEvent.Builder()
            .id(String.valueOf(eventId))
            .data(String.class, name).build();
    }
}

