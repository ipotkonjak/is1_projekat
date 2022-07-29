/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints;

import entiteti.Racun;
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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 *
 * @author Iva
 */
@Path("racun")
public class Racuni {
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "c1Queue")
    private Queue MyQueueCheck;
    @Resource(lookup = "p1cQueue")
    private Queue MyQueueValidation;
    @Resource(lookup = "c2Queue")
    private Queue MyQueueRequest;
    @Resource(lookup = "p2cQueue")
    private Queue MyQueueResponse;

    @POST
    @Path("{idK}/{idMes}/{Minus}")
    public Response createRacun(@PathParam("idK") int IdK, @PathParam("idMes") int IdMes, @PathParam("Minus") int dozvMinus){
	try{
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueResponse);
	    JMSConsumer consumer2 = context.createConsumer(MyQueueValidation);
            JMSProducer producer = context.createProducer();

	    TextMessage checkMsg = context.createTextMessage("Mesto");
	    checkMsg.setIntProperty("IdMes", IdMes);

	    producer.send(MyQueueCheck, checkMsg);
	    Message resp = consumer2.receive();
	    consumer2.close();
	    if(resp instanceof TextMessage) {
                if(((TextMessage)resp).getText().equals("Fail")){
		    consumer.close();
		    context.close();
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fail").build();
		}
            }
	    else{
		consumer.close();
		context.close();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fail").build();
	    }
        
            Racun r = new Racun();
	    r.setIdMes(IdMes);
	    r.setBrTransakcija(0);
	    r.setDozvMinus(dozvMinus);
	    r.setStanje(0);
	    r.setStatus('A');
	    r.setDatumVreme(new java.util.Date());

            ObjectMessage objmsg = context.createObjectMessage(r);
	    objmsg.setIntProperty("Komitent", IdK);
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

    @PUT
    @Path("{idR}")
    public Response shutDownRacun(@PathParam("idR") int IdR){
	try{
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueResponse);
            JMSProducer producer = context.createProducer();
        
            TextMessage txtmsg = context.createTextMessage("Racun");
	    txtmsg.setIntProperty("idR", IdR);
            producer.send(MyQueueRequest, txtmsg);
            
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
                    return Response.status(Response.Status.OK).entity("Success").build();
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
    @Path("{idK}")
    public Response getAllRacun(@PathParam("idK") int IdK){
	try{
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueResponse);
            JMSProducer producer = context.createProducer();
        
            TextMessage txtmsg = context.createTextMessage("Svi racuni");
	    txtmsg.setIntProperty("idK", IdK);
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
                if(obj instanceof ArrayList && objmsg.getStringProperty("Tip").equals("Racun")) {
		    ArrayList<Racun> list = (ArrayList<Racun>) obj;
		    StringBuilder sb = new StringBuilder("IdR\tIdK\tIdMes\tStanje\tDozvMinus\tDatumVreme\tStatus\tBrTransakcija\n");
		    for(Racun r: list) {
			sb.append(r.toString());
			sb.append('\n');
		    }
		    return Response.status(Response.Status.OK).entity(sb.toString()).build();
		    //return Response.status(Response.Status.OK).entity(new GenericEntity<ArrayList<Racun>>(list){}).build();
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
