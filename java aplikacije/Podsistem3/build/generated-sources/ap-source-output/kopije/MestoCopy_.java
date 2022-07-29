package kopije;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import kopije.FilijalaCopy;
import kopije.KomitentCopy;
import kopije.RacunCopy;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2022-02-19T13:01:11")
@StaticMetamodel(MestoCopy.class)
public class MestoCopy_ { 

    public static volatile SingularAttribute<MestoCopy, Integer> postanskiBr;
    public static volatile ListAttribute<MestoCopy, RacunCopy> racunCopyList;
    public static volatile ListAttribute<MestoCopy, FilijalaCopy> filijalaCopyList;
    public static volatile SingularAttribute<MestoCopy, Integer> idMes;
    public static volatile SingularAttribute<MestoCopy, String> naziv;
    public static volatile ListAttribute<MestoCopy, KomitentCopy> komitentCopyList;

}