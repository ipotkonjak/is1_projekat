package kopije;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import kopije.Mesto;
import kopije.Transakcija;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-02-15T13:14:35")
@StaticMetamodel(Filijala.class)
public class Filijala_ { 

    public static volatile SingularAttribute<Filijala, Integer> idFil;
    public static volatile SingularAttribute<Filijala, String> naziv;
    public static volatile SingularAttribute<Filijala, String> adresa;
    public static volatile SingularAttribute<Filijala, Mesto> mesto;
    public static volatile ListAttribute<Filijala, Transakcija> transakcijaList;

}