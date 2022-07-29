/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kopije;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Iva
 */
@Entity
@Table(name = "transakcija_copy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransakcijaCopy.findAll", query = "SELECT t FROM TransakcijaCopy t"),
    @NamedQuery(name = "TransakcijaCopy.findByIdT", query = "SELECT t FROM TransakcijaCopy t WHERE t.idT = :idT"),
    @NamedQuery(name = "TransakcijaCopy.findByRedniBr", query = "SELECT t FROM TransakcijaCopy t WHERE t.redniBr = :redniBr"),
    @NamedQuery(name = "TransakcijaCopy.findBySvrha", query = "SELECT t FROM TransakcijaCopy t WHERE t.svrha = :svrha"),
    @NamedQuery(name = "TransakcijaCopy.findByTip", query = "SELECT t FROM TransakcijaCopy t WHERE t.tip = :tip"),
    @NamedQuery(name = "TransakcijaCopy.findByIznos", query = "SELECT t FROM TransakcijaCopy t WHERE t.iznos = :iznos"),
    @NamedQuery(name = "TransakcijaCopy.findByDatumVreme", query = "SELECT t FROM TransakcijaCopy t WHERE t.datumVreme = :datumVreme")})
public class TransakcijaCopy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdT")
    private Integer idT;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RedniBr")
    private int redniBr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Svrha")
    private String svrha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Tip")
    private Character tip;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Iznos")
    private int iznos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVreme;
    @JoinColumn(name = "IdFil", referencedColumnName = "IdFil")
    @ManyToOne
    private FilijalaCopy idFil;
    @JoinColumn(name = "IdR", referencedColumnName = "IdR")
    @ManyToOne(optional = false)
    private RacunCopy idR;

    public TransakcijaCopy() {
    }

    public TransakcijaCopy(Integer idT) {
        this.idT = idT;
    }

    public TransakcijaCopy(Integer idT, int redniBr, String svrha, Character tip, int iznos, Date datumVreme) {
        this.idT = idT;
        this.redniBr = redniBr;
        this.svrha = svrha;
        this.tip = tip;
        this.iznos = iznos;
        this.datumVreme = datumVreme;
    }

    public Integer getIdT() {
        return idT;
    }

    public void setIdT(Integer idT) {
        this.idT = idT;
    }

    public int getRedniBr() {
        return redniBr;
    }

    public void setRedniBr(int redniBr) {
        this.redniBr = redniBr;
    }

    public String getSvrha() {
        return svrha;
    }

    public void setSvrha(String svrha) {
        this.svrha = svrha;
    }

    public Character getTip() {
        return tip;
    }

    public void setTip(Character tip) {
        this.tip = tip;
    }

    public int getIznos() {
        return iznos;
    }

    public void setIznos(int iznos) {
        this.iznos = iznos;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
    }

    public FilijalaCopy getIdFil() {
        return idFil;
    }

    public void setIdFil(FilijalaCopy idFil) {
        this.idFil = idFil;
    }

    public RacunCopy getIdR() {
        return idR;
    }

    public void setIdR(RacunCopy idR) {
        this.idR = idR;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idT != null ? idT.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransakcijaCopy)) {
            return false;
        }
        TransakcijaCopy other = (TransakcijaCopy) object;
        if ((this.idT == null && other.idT != null) || (this.idT != null && !this.idT.equals(other.idT))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "kopije.TransakcijaCopy[ idT=" + idT + " ]";
	StringBuilder sb = new StringBuilder();
	sb.append(idT);
	sb.append('\t');
	sb.append(idR.getIdR());
	sb.append('\t');
	sb.append(redniBr);
	sb.append('\t');
	sb.append(tip);
	sb.append('\t');
	sb.append(idFil.getIdFil());
	sb.append('\t');
	sb.append(iznos);
	sb.append('\t');
	sb.append(datumVreme);
	sb.append('\t');
	sb.append(svrha);
	return sb.toString();
    }
    
}
