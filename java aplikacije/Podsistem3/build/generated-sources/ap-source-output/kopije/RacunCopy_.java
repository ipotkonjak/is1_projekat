package kopije;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import kopije.KomitentCopy;
import kopije.MestoCopy;
import kopije.TransakcijaCopy;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-02-18T20:48:01")
@StaticMetamodel(RacunCopy.class)
public class RacunCopy_ { 

    public static volatile SingularAttribute<RacunCopy, KomitentCopy> idK;
    public static volatile SingularAttribute<RacunCopy, Date> datumVreme;
    public static volatile SingularAttribute<RacunCopy, Integer> stanje;
    public static volatile SingularAttribute<RacunCopy, Integer> idR;
    public static volatile SingularAttribute<RacunCopy, MestoCopy> idMes;
    public static volatile SingularAttribute<RacunCopy, Integer> brTransakcija;
    public static volatile SingularAttribute<RacunCopy, Integer> dozvMinus;
    public static volatile ListAttribute<RacunCopy, TransakcijaCopy> transakcijaCopyList;
    public static volatile SingularAttribute<RacunCopy, Character> status;

}