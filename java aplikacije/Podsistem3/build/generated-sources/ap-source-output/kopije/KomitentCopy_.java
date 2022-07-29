package kopije;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import kopije.MestoCopy;
import kopije.RacunCopy;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-02-19T13:01:11")
@StaticMetamodel(KomitentCopy.class)
public class KomitentCopy_ { 

    public static volatile ListAttribute<KomitentCopy, RacunCopy> racunCopyList;
    public static volatile SingularAttribute<KomitentCopy, Integer> idK;
    public static volatile SingularAttribute<KomitentCopy, MestoCopy> sediste;
    public static volatile SingularAttribute<KomitentCopy, String> naziv;
    public static volatile SingularAttribute<KomitentCopy, String> adresa;

}