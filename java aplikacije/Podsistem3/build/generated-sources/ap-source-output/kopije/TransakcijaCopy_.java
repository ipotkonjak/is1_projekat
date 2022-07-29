package kopije;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import kopije.FilijalaCopy;
import kopije.RacunCopy;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-02-19T13:01:11")
@StaticMetamodel(TransakcijaCopy.class)
public class TransakcijaCopy_ { 

    public static volatile SingularAttribute<TransakcijaCopy, Integer> iznos;
    public static volatile SingularAttribute<TransakcijaCopy, String> svrha;
    public static volatile SingularAttribute<TransakcijaCopy, Date> datumVreme;
    public static volatile SingularAttribute<TransakcijaCopy, RacunCopy> idR;
    public static volatile SingularAttribute<TransakcijaCopy, Integer> idT;
    public static volatile SingularAttribute<TransakcijaCopy, Integer> redniBr;
    public static volatile SingularAttribute<TransakcijaCopy, FilijalaCopy> idFil;
    public static volatile SingularAttribute<TransakcijaCopy, Character> tip;

}