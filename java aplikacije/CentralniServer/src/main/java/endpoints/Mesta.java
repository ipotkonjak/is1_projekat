/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints;

import entiteti.Mesto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.persistence.EntityExistsException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 *
 * @author Iva
 */
@Path("mesto")
public class Mesta {
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "c1Queue")
    private Queue MyQueueRequest;
    @Resource(lookup = "p1cQueue")
    private Queue MyQueueResponse;
    
    @POST
    @Path("{Naziv}/{PostBr}")
    public Response createMesto(@PathParam("Naziv") String naziv, @PathParam("PostBr") int postBr){
        
        try{
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueResponse);
            JMSProducer producer = context.createProducer();
        
            Mesto m = new Mesto();
            m.setNaziv(naziv);
            m.setPostanskiBr(postBr);

            ObjectMessage objmsg = context.createObjectMessage(m);
            producer.send(MyQueueRequest, objmsg);
            
            Message msg;
            do{
                msg = consumer.receive();
            }while(msg == null);
            consumer.close();
	    context.close();
            if(msg instanceof TextMessage) {
                if(((TextMessage)msg).getText().equals("Fail"))
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fail").build();
                else
                    return Response.status(Response.Status.CREATED).entity("Success").build();
            }
        
        }
        catch(EntityExistsException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fail").build();
        } catch (JMSException ex) {
            Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fail").build();
    }

    @GET
    public Response getAllMesto() {
	try{
	    JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueResponse);
            JMSProducer producer = context.createProducer();

	    TextMessage txtmsg = context.createTextMessage("Sva mesta");
	    producer.send(MyQueueRequest, txtmsg);

	    Message msg = consumer.receive();
	    consumer.close();
	    context.close();
	    if(msg instanceof ObjectMessage) {
		ObjectMessage objmsg = (ObjectMessage) msg;
		Object obj = objmsg.getObject();
		if(obj instanceof ArrayList && objmsg.getStringProperty("Tip").equals("Mesto")) {
		    ArrayList<Mesto> list = (ArrayList<Mesto>) obj;
		    StringBuilder sb = new StringBuilder("IdMes\tNaziv\tPostBr\n");
		    for(Mesto m: list) {
			sb.append(m.toString());
			sb.append('\n');
		    }
		    return Response.status(Response.Status.OK).entity(sb.toString()).build();
		    //return Response.status(Response.Status.OK).entity(new GenericEntity<ArrayList<Mesto>>(list){}).build();
		}
	    }
	}
	catch(EntityExistsException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fail").build();
        } 
	catch (JMSException ex) {
            Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fail").build();
    }
}
