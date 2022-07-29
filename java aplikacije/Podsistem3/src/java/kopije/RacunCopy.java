/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kopije;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Iva
 */
@Entity
@Table(name = "racun_copy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RacunCopy.findAll", query = "SELECT r FROM RacunCopy r"),
    @NamedQuery(name = "RacunCopy.findByIdR", query = "SELECT r FROM RacunCopy r WHERE r.idR = :idR"),
    @NamedQuery(name = "RacunCopy.findByStanje", query = "SELECT r FROM RacunCopy r WHERE r.stanje = :stanje"),
    @NamedQuery(name = "RacunCopy.findByDozvMinus", query = "SELECT r FROM RacunCopy r WHERE r.dozvMinus = :dozvMinus"),
    @NamedQuery(name = "RacunCopy.findByDatumVreme", query = "SELECT r FROM RacunCopy r WHERE r.datumVreme = :datumVreme"),
    @NamedQuery(name = "RacunCopy.findByStatus", query = "SELECT r FROM RacunCopy r WHERE r.status = :status"),
    @NamedQuery(name = "RacunCopy.findByBrTransakcija", query = "SELECT r FROM RacunCopy r WHERE r.brTransakcija = :brTransakcija")})
public class RacunCopy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdR")
    private Integer idR;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Stanje")
    private int stanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DozvMinus")
    private int dozvMinus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVreme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Status")
    private Character status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BrTransakcija")
    private int brTransakcija;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idR")
    private List<TransakcijaCopy> transakcijaCopyList;
    @JoinColumn(name = "IdK", referencedColumnName = "IdK")
    @ManyToOne(optional = false)
    private KomitentCopy idK;
    @JoinColumn(name = "IdMes", referencedColumnName = "IdMes")
    @ManyToOne(optional = false)
    private MestoCopy idMes;

    public RacunCopy() {
    }

    public RacunCopy(Integer idR) {
        this.idR = idR;
    }

    public RacunCopy(Integer idR, int stanje, int dozvMinus, Date datumVreme, Character status, int brTransakcija) {
        this.idR = idR;
        this.stanje = stanje;
        this.dozvMinus = dozvMinus;
        this.datumVreme = datumVreme;
        this.status = status;
        this.brTransakcija = brTransakcija;
    }

    public Integer getIdR() {
        return idR;
    }

    public void setIdR(Integer idR) {
        this.idR = idR;
    }

    public int getStanje() {
        return stanje;
    }

    public void setStanje(int stanje) {
        this.stanje = stanje;
    }

    public int getDozvMinus() {
        return dozvMinus;
    }

    public void setDozvMinus(int dozvMinus) {
        this.dozvMinus = dozvMinus;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public int getBrTransakcija() {
        return brTransakcija;
    }

    public void setBrTransakcija(int brTransakcija) {
        this.brTransakcija = brTransakcija;
    }

    @XmlTransient
    public List<TransakcijaCopy> getTransakcijaCopyList() {
        return transakcijaCopyList;
    }

    public void setTransakcijaCopyList(List<TransakcijaCopy> transakcijaCopyList) {
        this.transakcijaCopyList = transakcijaCopyList;
    }

    public KomitentCopy getIdK() {
        return idK;
    }

    public void setIdK(KomitentCopy idK) {
        this.idK = idK;
    }

    public MestoCopy getIdMes() {
        return idMes;
    }

    public void setIdMes(MestoCopy idMes) {
        this.idMes = idMes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idR != null ? idR.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RacunCopy)) {
            return false;
        }
        RacunCopy other = (RacunCopy) object;
        if ((this.idR == null && other.idR != null) || (this.idR != null && !this.idR.equals(other.idR))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "kopije.RacunCopy[ idR=" + idR + " ]";
	StringBuilder sb = new StringBuilder();
	sb.append(idR);
	sb.append('\t');
	sb.append(idK.getIdK());
	sb.append('\t');
	sb.append(idMes.getIdMes());
	sb.append('\t');
	sb.append(stanje);
	sb.append('\t');
	sb.append(dozvMinus);
	sb.append('\t');
	sb.append(datumVreme);
	sb.append('\t');
	sb.append(status);
	sb.append('\t');
	sb.append(brTransakcija);
	return sb.toString();
    }
    
}
