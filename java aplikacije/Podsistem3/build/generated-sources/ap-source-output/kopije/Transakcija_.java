package kopije;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import kopije.Filijala;
import kopije.Racun;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-02-15T13:14:35")
@StaticMetamodel(Transakcija.class)
public class Transakcija_ { 

    public static volatile SingularAttribute<Transakcija, Integer> iznos;
    public static volatile SingularAttribute<Transakcija, String> svrha;
    public static volatile SingularAttribute<Transakcija, Date> datumVreme;
    public static volatile SingularAttribute<Transakcija, Integer> idT;
    public static volatile SingularAttribute<Transakcija, Integer> redniBr;
    public static volatile SingularAttribute<Transakcija, Filijala> idFil;
    public static volatile SingularAttribute<Transakcija, Racun> racun;
    public static volatile SingularAttribute<Transakcija, Character> tip;

}