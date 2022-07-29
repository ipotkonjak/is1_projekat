package entiteti;

import entiteti.KomitentLite;
import entiteti.Transakcija;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-02-18T20:48:01")
@StaticMetamodel(Racun.class)
public class Racun_ { 

    public static volatile SingularAttribute<Racun, KomitentLite> idK;
    public static volatile SingularAttribute<Racun, Date> datumVreme;
    public static volatile SingularAttribute<Racun, Integer> stanje;
    public static volatile SingularAttribute<Racun, Integer> idR;
    public static volatile SingularAttribute<Racun, Integer> idMes;
    public static volatile SingularAttribute<Racun, Integer> brTransakcija;
    public static volatile SingularAttribute<Racun, Integer> dozvMinus;
    public static volatile ListAttribute<Racun, Transakcija> transakcijaList;
    public static volatile SingularAttribute<Racun, Character> status;

}