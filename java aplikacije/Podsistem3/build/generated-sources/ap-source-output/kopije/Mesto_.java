package kopije;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import kopije.Filijala;
import kopije.Komitent;
import kopije.Racun;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-02-15T13:14:35")
@StaticMetamodel(Mesto.class)
public class Mesto_ { 

    public static volatile SingularAttribute<Mesto, Integer> postanskiBr;
    public static volatile SingularAttribute<Mesto, Integer> idMes;
    public static volatile ListAttribute<Mesto, Racun> racunList;
    public static volatile SingularAttribute<Mesto, String> naziv;
    public static volatile ListAttribute<Mesto, Filijala> filijalaList;
    public static volatile ListAttribute<Mesto, Komitent> komitentList;

}