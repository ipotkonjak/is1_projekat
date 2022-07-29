/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints;

import entiteti.Racun;
import entiteti.Transakcija;
import java.util.ArrayList;
import java.util.Formatter;
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
@Path("transakcija")
public class Transakcije {
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
    @Path("{idRPos}/{idRPri}/{idFil}/{Iznos}/{Svrha}")
    public Response createTransakcija(@PathParam("idRPos") int IdR1, @PathParam("idRPri") int IdR2,@PathParam("idFil") int IdFil, @PathParam("Iznos") int iznos, @PathParam("Svrha") String svrha){
	try{
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueResponse);
	    JMSConsumer consumer2 = context.createConsumer(MyQueueValidation);
            JMSProducer producer = context.createProducer();

	    TextMessage checkMsg = context.createTextMessage("Filijala");
	    checkMsg.setIntProperty("IdFil", IdFil);

	    producer.send(MyQueueCheck, checkMsg);
	    Message resp = consumer2.receive();
	    consumer2.close();
	    if(resp instanceof TextMessage) {
                if(((TextMessage)resp).getText().equals("Fail")) {
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
        
            Transakcija t = new Transakcija();
	    t.setIdFil(IdFil);
	    t.setIznos(iznos);
	    t.setSvrha(svrha);
	    t.setTip('I');
	    t.setDatumVreme(new java.util.Date());

            ObjectMessage objmsg = context.createObjectMessage(t);
	    objmsg.setIntProperty("Racun", IdR1);
	    objmsg.setIntProperty("Provera", IdR2);
            producer.send(MyQueueRequest, objmsg);
            
            Message msg;
            do{
                msg = consumer.receive();
            }while(msg == null);
            
            if(msg instanceof TextMessage) {
                if(((TextMessage)msg).getText().equals("Fail"))
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fail").build();
                else {
		    t = new Transakcija();
		    t.setIdFil(IdFil);
		    t.setIznos(iznos);
		    t.setSvrha(svrha);
		    t.setTip('U');
		    t.setDatumVreme(new java.util.Date());
		    
		    objmsg = context.createObjectMessage(t);
		    objmsg.setIntProperty("Racun", IdR2);
		    producer.send(MyQueueRequest, objmsg);
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
	    }
        }
        catch(EntityExistsException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fail").build();
        } catch (JMSException ex) {
            Logger.getLogger(Mesta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fail").build();
    }

    @POST
    @Path("uplata/{idR}/{idFil}/{Iznos}/{Svrha}")
    public Response createUplata(@PathParam("idR") int IdR,@PathParam("idFil") int IdFil, @PathParam("Iznos") int iznos, @PathParam("Svrha") String svrha){

	try{
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueResponse);
	    JMSConsumer consumer2 = context.createConsumer(MyQueueValidation);
            JMSProducer producer = context.createProducer();

	    TextMessage checkMsg = context.createTextMessage("Filijala");
	    checkMsg.setIntProperty("IdFil", IdFil);

	    producer.send(MyQueueCheck, checkMsg);
	    Message resp = consumer2.receive();
	    consumer2.close();
	    if(resp instanceof TextMessage) {
                if(((TextMessage)resp).getText().equals("Fail")) {
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
        
            Transakcija t = new Transakcija();
	    t.setIdFil(IdFil);
	    t.setIznos(iznos);
	    t.setSvrha(svrha);
	    t.setTip('U');
	    t.setDatumVreme(new java.util.Date());

            ObjectMessage objmsg = context.createObjectMessage(t);
	    objmsg.setIntProperty("Racun", IdR);
            producer.send(MyQueueRequest, objmsg);
            
            Message msg;
            do{
                msg = consumer.receive();
            }while(msg == null);
            //consumer.close();
	    //context.close();
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

    @POST
    @Path("isplata/{idR}/{idFil}/{Iznos}/{Svrha}")
    public Response createIsplata(@PathParam("idR") int IdR,@PathParam("idFil") int IdFil, @PathParam("Iznos") int iznos, @PathParam("Svrha") String svrha){

	try{
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueResponse);
	    JMSConsumer consumer2 = context.createConsumer(MyQueueValidation);
            JMSProducer producer = context.createProducer();

	    TextMessage checkMsg = context.createTextMessage("Filijala");
	    checkMsg.setIntProperty("IdFil", IdFil);

	    producer.send(MyQueueCheck, checkMsg);
	    Message resp = consumer2.receive();
	    consumer2.close();
	    if(resp instanceof TextMessage) {
                if(((TextMessage)resp).getText().equals("Fail")) {
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
        
            Transakcija t = new Transakcija();
	    t.setIdFil(IdFil);
	    t.setIznos(iznos);
	    t.setSvrha(svrha);
	    t.setTip('I');
	    t.setDatumVreme(new java.util.Date());

            ObjectMessage objmsg = context.createObjectMessage(t);
	    objmsg.setIntProperty("Racun", IdR);
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
    @Path("{idR}")
    public Response getAllTransakcija(@PathParam("idR") int IdR){
	try{
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueResponse);
            JMSProducer producer = context.createProducer();
        
            TextMessage txtmsg = context.createTextMessage("Sve transakcija");
	    txtmsg.setIntProperty("idR", IdR);
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
                if(obj instanceof ArrayList && objmsg.getStringProperty("Tip").equals("Transakcija")) {
		    ArrayList<Transakcija> list = (ArrayList<Transakcija>) obj;
		    //Formatter form = new Formatter();
		    StringBuilder sb = new StringBuilder("IdT\tIdR\tRdBr\tTip\tIdFil\tIznos\tDatumVreme\tSvrha\n");
		    for(Transakcija t: list) {
			sb.append(t.toString());
			sb.append('\n');
		    }
		    return Response.status(Response.Status.OK).entity(sb.toString()).build();
		    //return Response.status(Response.Status.OK).entity(new GenericEntity<ArrayList<Transakcija>>(list){}).build();
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
