/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints;

import java.util.ArrayList;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 *
 * @author Iva
 */
@Path("podaci")
public class Podaci {
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "c3Queue")
    private Queue MyQueueRequest;
    @Resource(lookup = "p3cQueue")
    private Queue MyQueueResponse;

    @GET
    @Path("kopije")
    public Response getBackUp(){
	try{
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueResponse);
            JMSProducer producer = context.createProducer();
        
            TextMessage txtmsg = context.createTextMessage("Svi podaci");
            producer.send(MyQueueRequest, txtmsg);
            
            Message msg;
            do{
                msg = consumer.receive();
            }while(msg == null);
	    consumer.close();
	    context.close();
            
            if(msg instanceof ObjectMessage) {
		ObjectMessage objmsg = (ObjectMessage) msg;
		Object obj = objmsg.getObject();
                if(obj instanceof ArrayList) {
		    ArrayList<Object> list = (ArrayList<Object>) obj;
		    StringBuilder sb = new StringBuilder();
		    for(Object o: list) {
			sb.append(o.toString());
			sb.append('\n');
		    }
		    return Response.status(Response.Status.OK).entity(sb.toString()).build();
		    //return Response.status(Response.Status.OK).entity(new GenericEntity<ArrayList<Object>>(list){}).build();
		}
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
    @Path("razlika")
    public Response getDifference(){
	try{
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueResponse);
            JMSProducer producer = context.createProducer();
        
            TextMessage txtmsg = context.createTextMessage("Razlika");
            producer.send(MyQueueRequest, txtmsg);
            
            Message msg;
            do{
                msg = consumer.receive();
            }while(msg == null);
	    consumer.close();
	    context.close();
            
            if(msg instanceof ObjectMessage) {
		ObjectMessage objmsg = (ObjectMessage) msg;
		Object obj = objmsg.getObject();
                if(obj instanceof ArrayList) {
		    ArrayList<Object> list = (ArrayList<Object>) obj;
		    StringBuilder sb = new StringBuilder();
		    for(Object o: list) {
			sb.append(o.toString());
			sb.append('\n');
		    }
		    return Response.status(Response.Status.OK).entity(sb.toString()).build();
		}
            }
        
        }
        catch(EntityExistsException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fail").build();
        } catch (JMSException ex) {
            Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fail").build();
    }
}
