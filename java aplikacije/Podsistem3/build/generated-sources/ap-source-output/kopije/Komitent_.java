package kopije;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import kopije.Mesto;
import kopije.Racun;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-02-15T13:14:35")
@StaticMetamodel(Komitent.class)
public class Komitent_ { 

    public static volatile SingularAttribute<Komitent, Integer> idK;
    public static volatile SingularAttribute<Komitent, Mesto> sediste;
    public static volatile ListAttribute<Komitent, Racun> racunList;
    public static volatile SingularAttribute<Komitent, String> naziv;
    public static volatile SingularAttribute<Komitent, String> adresa;

}