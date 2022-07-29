/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem2;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import entiteti.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.persistence.TypedQuery;

/**
 *
 * @author Iva
 */
public class Main {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "c2Queue")
    private static Queue MyQueueRequest;
    @Resource(lookup = "p2cQueue")
    private static Queue MyQueueResponse;
    @Resource(lookup = "pQueue")
    private static Queue MyQueueData;
    /**
     * @param args the command line arguments
     */
    private static void sendData(EntityManager em, JMSContext context, JMSProducer producer, String prop) {
	try{
	    ObjectMessage objmsg = context.createObjectMessage();
	    ArrayList<Racun> listR = new ArrayList<>(em.createNamedQuery("Racun.findAll", Racun.class).getResultList());
	    objmsg.setObject(listR);
	    objmsg.setStringProperty("Tip", "Racun");
	    objmsg.setStringProperty("Namena", prop);
	    producer.send(MyQueueData, objmsg);
	    System.out.println("racun\n");
	    ArrayList<Transakcija> listT = new ArrayList<>(em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList());
	    objmsg.setObject(listT);
	    objmsg.setStringProperty("Tip", "Transakcija");
	    objmsg.setStringProperty("Namena", prop);
	    producer.send(MyQueueData, objmsg);
	    System.out.println("trans\n");
	    
	} catch (JMSException ex) {
	    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public static void main(String[] args) {
        try{
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
            EntityManager em = emf.createEntityManager();
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueRequest);
            JMSProducer producer = context.createProducer();

	    
	    while(true) {
		Message msg = consumer.receive();
		TextMessage txt = context.createTextMessage();
		if(msg instanceof TextMessage) {
		    
		    TextMessage txtmsg = (TextMessage) msg;
		    switch (txtmsg.getText()) {
			case "Komitent":
			    KomitentLite k = new KomitentLite();
			    k.setIdK(txtmsg.getIntProperty("IdK"));
			    k.setAdresa(txtmsg.getStringProperty("Adresa"));
			    k.setNaziv(txtmsg.getStringProperty("Naziv"));
			    k.setSediste(txtmsg.getIntProperty("Sediste"));
			    try{
				em.getTransaction().begin();
				em.persist(k);
				em.flush();
				em.getTransaction().commit();
			    }
			    finally{

				if(em.getTransaction().isActive()){
				    em.getTransaction().rollback();
				}
			    }
			    break;
			case "Promena sedista":
			    int idKom = txtmsg.getIntProperty("Komitent");
			    int idMes = txtmsg.getIntProperty("Sediste");
			    try{
			    KomitentLite kom = em.find(KomitentLite.class, idKom);
                            em.getTransaction().begin();
                            kom.setSediste(idMes);
                            em.flush();
                            em.getTransaction().commit();
			    }
			    finally{

				if(em.getTransaction().isActive()){
				    em.getTransaction().rollback();
				}
			    }
			    break;
			case "Racun":
			    Racun r = em.find(Racun.class, txtmsg.getIntProperty("idR"));
			    if(r == null) {
				txt.setText("Fail");
				producer.send(MyQueueResponse, txt);
				continue;
			    }
			    try{
				em.getTransaction().begin();
				r.setStatus('Z');
				em.flush();
				em.getTransaction().commit();
			    }
			    finally{

				if(em.getTransaction().isActive()){
				    em.getTransaction().rollback();
				    txt.setText("Fail");
				    producer.send(MyQueueResponse, txt);
				    continue;
				}
			    }
			    txt.setText("Success");
			    producer.send(MyQueueResponse, txt);
			    break;
			case "Svi racuni":
			    KomitentLite vlasnik  = em.find(KomitentLite.class, txtmsg.getIntProperty("idK"));
			    if(vlasnik == null) {
				txt.setText("Fail");
				producer.send(MyQueueResponse, txt);
				continue;
			    }
			    List<Racun> listRacuni = vlasnik.getRacunList();
			    ArrayList<Racun> listaR = new ArrayList<>(listRacuni);
			    ObjectMessage objListR = context.createObjectMessage(listaR);
			    objListR.setStringProperty("Tip", "Racun");
			    producer.send(MyQueueResponse, objListR);
			    break;
			case "Sve transakcija":
			    Racun kontekst  = em.find(Racun.class, txtmsg.getIntProperty("idR"));
			    if(kontekst == null) {
				txt.setText("Fail");
				producer.send(MyQueueResponse, txt);
				continue;
			    }
			    List<Transakcija> listTrans = kontekst.getTransakcijaList();
			    ArrayList<Transakcija> listaT = new ArrayList<>(listTrans);
			    ObjectMessage objListT = context.createObjectMessage(listaT);
			    objListT.setStringProperty("Tip", "Transakcija");
			    producer.send(MyQueueResponse, objListT);
			    break;
			case "Svi podaci":
			    sendData(em, context, producer, txtmsg.getStringProperty("Namena"));
			    break;
		    }
		}
		else if (msg instanceof ObjectMessage) {
		    ObjectMessage objmsg = (ObjectMessage) msg;
		    Object obj = objmsg.getObject();

		    if(obj instanceof Racun) {
			Racun r = (Racun) obj;
			int idK = objmsg.getIntProperty("Komitent");

			TypedQuery<KomitentLite> kom = em.createNamedQuery("KomitentLite.findByIdK", KomitentLite.class);
			List<KomitentLite> resultKom = kom.setParameter("idK", idK).getResultList();
			if(resultKom.isEmpty()){
			    txt.setText("Fail");
			    producer.send(MyQueueResponse, txt);
			    continue;
			}
			r.setIdK(resultKom.get(0));

			try{
                            em.getTransaction().begin();
                            em.persist(r);
                            em.flush();
                            em.getTransaction().commit();
                        }
                        finally{
                        
                            if(em.getTransaction().isActive()){
                                em.getTransaction().rollback();
                                txt.setText("Fail");
                                producer.send(MyQueueResponse, txt);
                                continue;
                            }
                        }
			
                        txt.setText("Success");
                        producer.send(MyQueueResponse, txt);
		    }
		    else if(obj instanceof Transakcija) {
			Transakcija t = (Transakcija) obj;
			int idR = objmsg.getIntProperty("Racun");

			Racun r = em.find(Racun.class, idR);
			if(r == null){
			    txt.setText("Fail");
			    producer.send(MyQueueResponse, txt);
			    continue;
			}
			if(t.getTip() == 'I' ) {
			    boolean flag = !(r.getStatus() == 'A');
			    if(objmsg.propertyExists("Provera")) {
				int p = objmsg.getIntProperty("Provera");
				Racun prov = em.find(Racun.class, p);
				flag = flag || (prov == null) || (prov.getStatus() == 'Z');
			    }
			    if(flag) {
				txt.setText("Fail");
				producer.send(MyQueueResponse, txt);
				continue;
			    }
			}
			if(t.getTip() == 'U' && r.getStatus() == 'Z') {
			    txt.setText("Fail");
			    producer.send(MyQueueResponse, txt);
			    continue;
			}
			t.setIdR(r);
			t.setRedniBr(r.getBrTransakcija() + 1);

			try{
                            em.getTransaction().begin();
                            em.persist(t);
			    r.setBrTransakcija(r.getBrTransakcija() + 1);
			    if(t.getTip() == 'I') {
				if(r.getStanje() - t.getIznos() < (-1)*r.getDozvMinus()) {
				    r.setStatus('B');
				}
				r.setStanje(r.getStanje() - t.getIznos());
			    }
			    else if(t.getTip() == 'U') {
				if(r.getStanje() + t.getIznos() >= (-1)*r.getDozvMinus()) {
				    r.setStatus('A');
				}
				r.setStanje(r.getStanje() + t.getIznos());
			    }
                            em.flush();
                            em.getTransaction().commit();
                        }
                        finally{
                        
                            if(em.getTransaction().isActive()){
                                em.getTransaction().rollback();
                                txt.setText("Fail");
                                producer.send(MyQueueResponse, txt);
                                continue;
                            }
                        }
			
                        txt.setText("Success");
                        producer.send(MyQueueResponse, txt);
		    }
		    
		    
		}

		
	    }
	}
	catch(Exception e){}
    }
    
}
