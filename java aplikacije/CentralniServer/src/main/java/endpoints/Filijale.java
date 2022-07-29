/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints;

import entiteti.Filijala;
import entiteti.Mesto;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 *
 * @author Iva
 */
@Path("filijala")
public class Filijale {
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "c1Queue")
    private Queue MyQueueRequest;
    @Resource(lookup = "p1cQueue")
    private Queue MyQueueResponse;

    @POST
    @Path("{Naziv}/{Adresa}/{IdMes}")
    public Response createFilijala(@PathParam("Naziv") String naziv, @PathParam("Adresa") String adr, @PathParam("IdMes") int idMes){
        
        try{
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueResponse);
            JMSProducer producer = context.createProducer();
        
            Filijala f = new Filijala();
            f.setNaziv(naziv);
            f.setAdresa(adr);

            ObjectMessage objmsg = context.createObjectMessage(f);
            objmsg.setIntProperty("Mesto", idMes);
            producer.send(MyQueueRequest, objmsg);
            Message msg;
            
	    msg = consumer.receive();
            //consumer.close();
	    //context.close();
            if(msg instanceof TextMessage) {
                if(((TextMessage)msg).getText().equals("Fail"))
                    return Response.status(Response.Status.BAD_REQUEST).entity("Fail").build();
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
    public Response getAllFilijala() {
	try{
	    JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueResponse);
            JMSProducer producer = context.createProducer();

	    TextMessage txtmsg = context.createTextMessage("Sve filijale");
	    producer.send(MyQueueRequest, txtmsg);

	    Message msg = consumer.receive();
	    consumer.close();
	    context.close();
	    if(msg instanceof ObjectMessage) {
		ObjectMessage objmsg = (ObjectMessage) msg;
		Object obj = objmsg.getObject();
		if(obj instanceof ArrayList && objmsg.getStringProperty("Tip").equals("Filijala")) {
		    ArrayList<Filijala> list = (ArrayList<Filijala>) obj;
		    StringBuilder sb = new StringBuilder("IdFil\tNaziv\tAdresa\tIdMes\n");
		    for(Filijala f: list) {
			sb.append(f.toString());
			sb.append('\n');
		    }
		    return Response.status(Response.Status.OK).entity(sb.toString()).build();
		    //return Response.status(Response.Status.OK).entity(new GenericEntity<ArrayList<Filijala>>(list){}).build();
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
