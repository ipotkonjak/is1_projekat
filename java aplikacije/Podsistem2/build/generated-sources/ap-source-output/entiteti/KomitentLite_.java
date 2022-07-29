package entiteti;

import entiteti.Racun;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-02-14T09:51:08")
@StaticMetamodel(KomitentLite.class)
public class KomitentLite_ { 

    public static volatile SingularAttribute<KomitentLite, Integer> idK;
    public static volatile SingularAttribute<KomitentLite, Integer> sediste;
    public static volatile ListAttribute<KomitentLite, Racun> racunList;
    public static volatile SingularAttribute<KomitentLite, String> naziv;
    public static volatile SingularAttribute<KomitentLite, String> adresa;

}