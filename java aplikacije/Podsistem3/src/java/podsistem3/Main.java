/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem3;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import kopije.*;

/**
 *
 * @author Iva
 */
public class Main {
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "c3Queue")
    private static Queue MyQueueRequest;
    @Resource(lookup = "p3cQueue")
    private static Queue MyQueueResponse;
    @Resource(lookup = "pQueue")
    private static Queue MyQueueData;
    @Resource(lookup = "c1Queue")
    private static Queue MyQueueP1;
    @Resource(lookup = "c2Queue")
    private static Queue MyQueueP2;

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
    /**
     * @param args the command line arguments
     */
    static class MyTask extends TimerTask {
	private ArrayList<entiteti.Mesto> listaM;
	private ArrayList<entiteti.Filijala> listaF;
	private ArrayList<entiteti.Komitent> listaK;
	private ArrayList<entiteti.Racun> listaR;
	private ArrayList<entiteti.Transakcija> listaT;
	public void run() {
	    EntityManager em = emf.createEntityManager();
	    em.clear();
	    JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(MyQueueData, "Namena='Rutina'");
	    try{
	    TextMessage txtmsg = context.createTextMessage("Svi podaci");
	    txtmsg.setStringProperty("Namena", "Rutina");
	    producer.send(MyQueueP1, txtmsg);
	    producer.send(MyQueueP2, txtmsg);
	    System.out.println("update\n");
	    
		for(int i = 0; i != 31; ) {
		    Message msg = consumer.receive();
		    if(msg instanceof ObjectMessage) {
			ObjectMessage objmsg = (ObjectMessage) msg;
			switch (objmsg.getStringProperty("Tip")) {
			    case "Mesto":
				listaM = (ArrayList<entiteti.Mesto>) objmsg.getObject();
				i |= 1;
				break;
			    case "Filijala":
				listaF = (ArrayList<entiteti.Filijala>) objmsg.getObject();
				i |= 2;
				break;
			    case "Komitent":
				listaK = (ArrayList<entiteti.Komitent>) objmsg.getObject();
				i |= 4;
				break;
			    case "Racun":
				listaR = (ArrayList<entiteti.Racun>) objmsg.getObject();
				i |= 8;
				break;
			    case "Transakcija":
				listaT = (ArrayList<entiteti.Transakcija>) objmsg.getObject();
				i |= 16;
				break;
			}
		    }
		}
		System.out.println("ok");
		try{
		    em.getTransaction().begin();
		    for(entiteti.Mesto m: listaM)
			if(em.find(MestoCopy.class, m.getIdMes()) == null) {
			    MestoCopy mes = new MestoCopy();
			    mes.setIdMes(m.getIdMes());
			    mes.setNaziv(m.getNaziv());
			    mes.setPostanskiBr(m.getPostanskiBr());
			    em.persist(mes);
			}
		    em.getTransaction().commit();

		    em.getTransaction().begin();
		    for(entiteti.Filijala f: listaF)
			if(em.find(FilijalaCopy.class, f.getIdFil()) == null) {
			    FilijalaCopy fil = new FilijalaCopy();
			    fil.setIdFil(f.getIdFil());
			    fil.setNaziv(f.getNaziv());
			    fil.setAdresa(f.getAdresa());
			    fil.setIdMes(em.find(MestoCopy.class, f.getIdMes().getIdMes()));
			    em.persist(fil);
			}
		    em.flush();
		    em.getTransaction().commit();

		    em.getTransaction().begin();
		    for(entiteti.Komitent k: listaK){
			KomitentCopy pom = em.find(KomitentCopy.class, k.getIdK());
			if( pom == null) {
			    pom = new KomitentCopy();
			    pom.setIdK(k.getIdK());
			    pom.setAdresa(k.getAdresa());
			    pom.setNaziv(k.getNaziv());
			    pom.setSediste(em.find(MestoCopy.class, k.getSediste().getIdMes()));
			    em.persist(pom);
			}
			else if(!pom.getSediste().getIdMes().equals(k.getSediste().getIdMes()))pom.setSediste(em.find(MestoCopy.class, k.getSediste().getIdMes()));
		    }
		    em.flush();
		    em.getTransaction().commit();

		    em.getTransaction().begin();
		    for(entiteti.Racun r: listaR) {
			RacunCopy pom = em.find(RacunCopy.class, r.getIdR());
			if(pom == null) {
			    pom = new RacunCopy();
			    pom.setIdR(r.getIdR());
			    pom.setIdK(em.find(KomitentCopy.class, r.getIdK().getIdK()));
			    pom.setIdMes(em.find(MestoCopy.class, r.getIdMes()));
			    pom.setDatumVreme(r.getDatumVreme());
			    pom.setDozvMinus(r.getDozvMinus());
			    pom.setBrTransakcija(r.getBrTransakcija());
			    pom.setStanje(r.getStanje());
			    pom.setStatus(r.getStatus());
			    em.persist(pom);
			}
			else {
			    pom.setStanje(r.getStanje());
			    pom.setStatus(r.getStatus());
			    pom.setBrTransakcija(r.getBrTransakcija());
			}
		    }
		    em.flush();
		    em.getTransaction().commit();

		    em.getTransaction().begin();
		    for(entiteti.Transakcija t: listaT) {
			TransakcijaCopy pom = em.find(TransakcijaCopy.class, t.getIdT());
			if(pom == null) {
			    pom = new TransakcijaCopy();
			    pom.setIdT(t.getIdT());
			    pom.setIdR(em.find(RacunCopy.class, t.getIdR().getIdR()));
			    pom.setIdFil(em.find(FilijalaCopy.class, t.getIdFil()));
			    pom.setDatumVreme(t.getDatumVreme());
			    pom.setIznos(t.getIznos());
			    pom.setRedniBr(t.getRedniBr());
			    pom.setSvrha(t.getSvrha());
			    pom.setTip(t.getTip());
			    em.persist(pom);
			}
			
		    }
		    em.flush();
		    em.getTransaction().commit();
		}
		finally{
		    if(em.getTransaction().isActive())
			em.getTransaction().rollback();
		    consumer.close();
		    context.close();
		    em.close();
		}
		

		
	    } catch (JMSException ex) {
		Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	    }


	}
    }
    
    private static void getDifference(EntityManager em, JMSContext context, JMSProducer producer) {
	ArrayList<entiteti.Mesto> listaM = null;
	ArrayList<entiteti.Filijala> listaF = null;
	ArrayList<entiteti.Komitent> listaK = null;
	ArrayList<entiteti.Racun> listaR = null;
	ArrayList<entiteti.Transakcija> listaT = null;
	ArrayList<Object> objListMiss = new ArrayList<>();
	ArrayList<Object> objListDiff = new ArrayList<>();
	objListMiss.add("Nedostaje");
	objListDiff.add("Razlika");
	em.clear();
	JMSConsumer consumer = context.createConsumer(MyQueueData, "Namena='Zadatak'");
	try{
	TextMessage txtmsg = context.createTextMessage("Svi podaci");
	txtmsg.setStringProperty("Namena", "Zadatak");
	producer.send(MyQueueP1, txtmsg);
	producer.send(MyQueueP2, txtmsg);

	
	    for(int i = 0; i != 31; ) {
		Message msg = consumer.receive();
		if(msg instanceof ObjectMessage) {
		    ObjectMessage objmsg = (ObjectMessage) msg;
		    switch (objmsg.getStringProperty("Tip")) {
			case "Mesto":
			    listaM = (ArrayList<entiteti.Mesto>) objmsg.getObject();
			    i |= 1;
			    break;
			case "Filijala":
			    listaF = (ArrayList<entiteti.Filijala>) objmsg.getObject();
			    i |= 2;
			    break;
			case "Komitent":
			    listaK = (ArrayList<entiteti.Komitent>) objmsg.getObject();
			    i |= 4;
			    break;
			case "Racun":
			    listaR = (ArrayList<entiteti.Racun>) objmsg.getObject();
			    i |= 8;
			    break;
			case "Transakcija":
			    listaT = (ArrayList<entiteti.Transakcija>) objmsg.getObject();
			    i |= 16;
			    break;
		    }
		}
	    }
	    for(entiteti.Mesto m: listaM)
		if(em.find(MestoCopy.class, m.getIdMes()) == null) {
		    m.setFilijalaList(null);
		    m.setKomitentList(null);
		    objListMiss.add(m);
		}

	    for(entiteti.Filijala f: listaF)
		if(em.find(FilijalaCopy.class, f.getIdFil()) == null) {
		    f.getIdMes().setFilijalaList(null);
		    f.getIdMes().setKomitentList(null);
		    objListMiss.add(f);
		}

	    for(entiteti.Komitent k: listaK){
		KomitentCopy pom = em.find(KomitentCopy.class, k.getIdK());
		if( pom == null) {
		    k.getSediste().setFilijalaList(null);
		    k.getSediste().setKomitentList(null);
		    objListMiss.add(k);
		}
		else if(!pom.getSediste().getIdMes().equals(k.getSediste().getIdMes())) {
		    k.getSediste().setFilijalaList(null);
		    k.getSediste().setKomitentList(null);
		    pom.setRacunCopyList(null);
		    pom.getSediste().setFilijalaCopyList(null);
		    pom.getSediste().setKomitentCopyList(null);
		    pom.getSediste().setRacunCopyList(null);
		    objListDiff.add(k);
		    objListDiff.add(pom);
		}
	    }

	    for(entiteti.Racun r: listaR) {
		RacunCopy pom = em.find(RacunCopy.class, r.getIdR());
		if(pom == null) {
		    r.setTransakcijaList(null);
		    r.getIdK().setRacunList(null);
		    objListMiss.add(r);
		}
		else if(pom.getBrTransakcija() != r.getBrTransakcija() || pom.getStanje() != r.getStanje() || !pom.getStatus().equals(r.getStatus())) {
		    r.setTransakcijaList(null);
		    r.getIdK().setRacunList(null);
		    pom.setTransakcijaCopyList(null);
		    pom.getIdK().setRacunCopyList(null);
		    pom.getIdMes().setFilijalaCopyList(null);
		    pom.getIdMes().setKomitentCopyList(null);
		    pom.getIdMes().setRacunCopyList(null);
		    objListDiff.add(r);
		    objListDiff.add(pom);
		}
	    }

	    for(entiteti.Transakcija t: listaT) {
		TransakcijaCopy pom = em.find(TransakcijaCopy.class, t.getIdT());
		if(pom == null) {
		    t.getIdR().setTransakcijaList(null);
		    objListMiss.add(t);
		}

	    }
	    consumer.close();
	    objListMiss.addAll(objListDiff);
	    ObjectMessage objmsg = context.createObjectMessage(objListMiss);
	    producer.send(MyQueueResponse, objmsg);

	} catch (JMSException ex) {
	    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	}



    }

    public static void main(String[] args) {
        try{
	    EntityManager em = emf.createEntityManager();
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(MyQueueRequest);
            JMSProducer producer = context.createProducer();
	    
	    Timer timer = new Timer();
	    timer.schedule(new MyTask(), 0, 120000);

	    while(true) {
		Message msg = consumer.receive();
		if(msg instanceof TextMessage) {
		    
		    TextMessage txtmsg = (TextMessage) msg;
		    switch (txtmsg.getText()) {
			
			case "Svi podaci":
			    em.clear();
			    ObjectMessage objList = context.createObjectMessage();
			    TypedQuery<MestoCopy> qM = em.createNamedQuery("MestoCopy.findAll", MestoCopy.class);
			    List<MestoCopy> lM = qM.getResultList();
			    for(MestoCopy m: lM) {
				m.setKomitentCopyList(null);
				m.setFilijalaCopyList(null);
				m.setRacunCopyList(null);
			    }
			    TypedQuery<FilijalaCopy> qF = em.createNamedQuery("FilijalaCopy.findAll", FilijalaCopy.class);
			    List<FilijalaCopy> lF = qF.getResultList();
			    for(FilijalaCopy f: lF) {
				f.setTransakcijaCopyList(null);
			    }
			    TypedQuery<KomitentCopy> qK = em.createNamedQuery("KomitentCopy.findAll", KomitentCopy.class);
			    List<KomitentCopy> lK = qK.getResultList();
			    for(KomitentCopy k: lK) {
				k.setRacunCopyList(null);
			    }
			    TypedQuery<RacunCopy> qR = em.createNamedQuery("RacunCopy.findAll", RacunCopy.class);
			    List<RacunCopy> lR = qR.getResultList();
			    for(RacunCopy r: lR) {
				r.setTransakcijaCopyList(null);
			    }
			    TypedQuery<TransakcijaCopy> qT = em.createNamedQuery("TransakcijaCopy.findAll", TransakcijaCopy.class);

			    ArrayList<Object> arrList = new ArrayList<>();
			    arrList.addAll(lM);
			    arrList.addAll(qF.getResultList());
			    arrList.addAll(qK.getResultList());
			    arrList.addAll(qR.getResultList());
			    arrList.addAll(qT.getResultList());
			    objList.setObject(arrList);
			    producer.send(MyQueueResponse, objList);
			    break;
			case "Razlika":
			    getDifference(em, context, producer);
			    break;
			
		    }
		}
	    }
	    
	}
	catch(Exception e){}
    }
    
}
