package kopije;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import kopije.MestoCopy;
import kopije.TransakcijaCopy;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-02-19T13:01:11")
@StaticMetamodel(FilijalaCopy.class)
public class FilijalaCopy_ { 

    public static volatile SingularAttribute<FilijalaCopy, MestoCopy> idMes;
    public static volatile SingularAttribute<FilijalaCopy, Integer> idFil;
    public static volatile SingularAttribute<FilijalaCopy, String> naziv;
    public static volatile SingularAttribute<FilijalaCopy, String> adresa;
    public static volatile ListAttribute<FilijalaCopy, TransakcijaCopy> transakcijaCopyList;

}