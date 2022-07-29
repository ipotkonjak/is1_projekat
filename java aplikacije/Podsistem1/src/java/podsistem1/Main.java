/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem1;

import entiteti.Filijala;
import entiteti.Komitent;
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
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Iva
 */
public class Main {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "c1Queue")
    private static Queue MyQueueRequest;
    @Resource(lookup = "p1cQueue")
    private static Queue MyQueueResponse;
    @Resource(lookup = "c2Queue")
    private static Queue MyQueueNeighbor2;
    @Resource(lookup = "pQueue")
    private static Queue MyQueueData;
    /**
     * @param args the command line arguments
     */
    private static void sendData(EntityManager em, JMSContext context, JMSProducer producer, String prop) {
	try{
	    ObjectMessage objmsg = context.createObjectMessage();
	    ArrayList<Mesto> listM = new ArrayList<>(em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList());
	    objmsg.setObject(listM);
	    objmsg.setStringProperty("Tip", "Mesto");
	    objmsg.setStringProperty("Namena", prop);
	    producer.send(MyQueueData, objmsg);
	    System.out.println("mesto\n");
	    ArrayList<Filijala> listF = new ArrayList<>(em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList());
	    objmsg.setObject(listF);
	    objmsg.setStringProperty("Tip", "Filijala");
	    objmsg.setStringProperty("Namena", prop);
	    producer.send(MyQueueData, objmsg);
	    System.out.println("filijala\n");
	    ArrayList<Komitent> listK = new ArrayList<>(em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList());
	    objmsg.setObject(listK);
	    objmsg.setStringProperty("Tip", "Komitent");
	    objmsg.setStringProperty("Namena", prop);
	    System.out.println("komitent\n");
	    producer.send(MyQueueData, objmsg);
	} catch (JMSException ex) {
	    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public static void main(String[] args) {
        try{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
            EntityManager em = emf.createEntityManager();
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueRequest);
            JMSProducer producer = context.createProducer();

            while(true) {
                TextMessage txtmsg = context.createTextMessage();
                Message msg = consumer.receive();
                if(msg instanceof ObjectMessage) {
                    ObjectMessage objmsg = (ObjectMessage) msg;
                    Object obj = objmsg.getObject();
                    
                    if(obj instanceof Mesto) {
                        Mesto m = (Mesto) obj;
                        try{
                            em.getTransaction().begin();
                            em.persist(m);
                            em.flush();
                            em.getTransaction().commit();
                        }
                        finally{
                        
                            if(em.getTransaction().isActive()){
                                em.getTransaction().rollback();
                                txtmsg.setText("Fail");
                                producer.send(MyQueueResponse, txtmsg);
                                continue;
                            }
                        }
                        txtmsg.setText("Success");
                        producer.send(MyQueueResponse, txtmsg);
                    }
                    else if(obj instanceof Filijala) {
                        int idMes = objmsg.getIntProperty("Mesto");
                        TypedQuery<Mesto> q = em.createNamedQuery("Mesto.findByIdMes", Mesto.class);
                        List<Mesto> resultList = q.setParameter("idMes", idMes).getResultList();
                        if(resultList.size() == 0) {
                            txtmsg.setText("Fail");
                            producer.send(MyQueueResponse, txtmsg);
                            continue;
                        }
                        Filijala f = (Filijala) obj;
                        f.setIdMes(resultList.get(0));

                        try{
                            em.getTransaction().begin();
                            em.persist(f);
                            em.flush();
                            em.getTransaction().commit();
                        }
                        finally{
                        
                            if(em.getTransaction().isActive()){
                                em.getTransaction().rollback();
                                txtmsg.setText("Fail");
                                producer.send(MyQueueResponse, txtmsg);
                                continue;
                            }
                        }
                        txtmsg.setText("Success");
                        producer.send(MyQueueResponse, txtmsg);
                    }
		    else if(obj instanceof Komitent) {
			int idMes = objmsg.getIntProperty("Sediste");
                        TypedQuery<Mesto> q = em.createNamedQuery("Mesto.findByIdMes", Mesto.class);
                        List<Mesto> resultList = q.setParameter("idMes", idMes).getResultList();
                        if(resultList.size() == 0) {
                            txtmsg.setText("Fail");
                            producer.send(MyQueueResponse, txtmsg);
                            continue;
                        }
                        Komitent k = (Komitent) obj;
                        k.setSediste(resultList.get(0));

                        try{
                            em.getTransaction().begin();
                            em.persist(k);
                            em.flush();
                            em.getTransaction().commit();
                        }
                        finally{
                        
                            if(em.getTransaction().isActive()){
                                em.getTransaction().rollback();
                                txtmsg.setText("Fail");
                                producer.send(MyQueueResponse, txtmsg);
                                continue;
                            }
                        }
			TextMessage sendCopy = context.createTextMessage("Komitent");
			sendCopy.setIntProperty("IdK", k.getIdK());
			sendCopy.setStringProperty("Adresa", k.getAdresa());
			sendCopy.setStringProperty("Naziv", k.getNaziv());
			sendCopy.setIntProperty("Sediste", k.getSediste().getIdMes());
			producer.send(MyQueueNeighbor2, sendCopy);
                        txtmsg.setText("Success");
                        producer.send(MyQueueResponse, txtmsg);
		    }
                }
		else if(msg instanceof TextMessage) {
		    TextMessage txt = (TextMessage) msg;
		    switch (txt.getText()) {
			case "Promena sedista":
			    int idKom = txt.getIntProperty("Komitent");
			    int idMes = txt.getIntProperty("Sediste");
			    try{
			    Komitent kom = em.find(Komitent.class, idKom);
			    Mesto mes = em.find(Mesto.class, idMes);
			    if(kom == null || mes == null){
				txtmsg.setText("Fail");
				producer.send(MyQueueResponse, txtmsg);
				continue;
			    }
			    /*TypedQuery<Mesto> qM = em.createNamedQuery("Mesto.findByIdMes", Mesto.class);
			    List<Mesto> resultListM = qM.setParameter("idMes", idMes).getResultList();
			    TypedQuery<Komitent> qK = em.createNamedQuery("Komitent.findByIdK", Komitent.class);
			    List<Komitent> resultListK = qK.setParameter("idK", idKom).getResultList();
			    if(resultListK.isEmpty() || resultListM.isEmpty()) {
				txtmsg.setText("Fail");
				producer.send(MyQueueResponse, txtmsg);
				continue;
			    }*/
			    
                            em.getTransaction().begin();
                            kom.setSediste(mes);
                            em.flush();
                            em.getTransaction().commit();
			    }
			    finally{

				if(em.getTransaction().isActive()){
				    em.getTransaction().rollback();
				    txtmsg.setText("Fail");
				    producer.send(MyQueueResponse, txtmsg);
				    continue;
				}
			    }
			    TextMessage sendCopy = context.createTextMessage("Promena sedista");
			    sendCopy.setIntProperty("Komitent", idKom);
			    sendCopy.setIntProperty("Sediste", idMes);
			    producer.send(MyQueueNeighbor2, sendCopy);
			    txtmsg.setText("Success");
			    producer.send(MyQueueResponse, txtmsg);
			    break;
			case "Sva mesta":
			    TypedQuery<Mesto> svaMesta = em.createNamedQuery("Mesto.findAll", Mesto.class);
			    List<Mesto> resultListMesta = svaMesta.getResultList();
			    ArrayList<Mesto> listaM = new ArrayList<>(resultListMesta);
			    ObjectMessage objListM = context.createObjectMessage(listaM);
			    objListM.setStringProperty("Tip", "Mesto");
			    producer.send(MyQueueResponse, objListM);
			    break;
			case "Sve filijale":
			    TypedQuery<Filijala> sveFil = em.createNamedQuery("Filijala.findAll", Filijala.class);
			    List<Filijala> resultListFil = sveFil.getResultList();
			    ArrayList<Filijala> listaF = new ArrayList<>(resultListFil);
			    ObjectMessage objListF = context.createObjectMessage(listaF);
			    objListF.setStringProperty("Tip", "Filijala");
			    producer.send(MyQueueResponse, objListF);
			    break;
			case "Svi komitenti":
			    TypedQuery<Komitent> sveKom = em.createNamedQuery("Komitent.findAll", Komitent.class);
			    List<Komitent> resultListKom = sveKom.getResultList();
			    ArrayList<Komitent> listaK = new ArrayList<>(resultListKom);
			    ObjectMessage objListK = context.createObjectMessage(listaK);
			    objListK.setStringProperty("Tip", "Komitent");
			    producer.send(MyQueueResponse, objListK);
			    break;
			case "Mesto":
			    TypedQuery<Mesto> trazenoMesto = em.createNamedQuery("Mesto.findByIdMes", Mesto.class);
			    List<Mesto> resM = trazenoMesto.setParameter("idMes", txt.getIntProperty("IdMes")).getResultList();
			    if(resM.isEmpty())
				txtmsg.setText("Fail");
			    else txtmsg.setText("Success");
			    producer.send(MyQueueResponse, txtmsg);
			    break;
			case "Filijala":
			    TypedQuery<Filijala> trazenaFil = em.createNamedQuery("Filijala.findByIdFil", Filijala.class);
			    List<Filijala> resF = trazenaFil.setParameter("idFil", txt.getIntProperty("IdFil")).getResultList();
			    if(resF.isEmpty())
				txtmsg.setText("Fail");
			    else txtmsg.setText("Success");
			    producer.send(MyQueueResponse, txtmsg);
			    break;
			case "Svi podaci":
			    sendData(em, context, producer, txt.getStringProperty("Namena"));
			    break;
		    }
		}   
            }
        }
        catch(Exception e) {}
    }
    
}
