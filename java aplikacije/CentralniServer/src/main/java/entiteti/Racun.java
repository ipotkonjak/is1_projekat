/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "racun")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Racun.findAll", query = "SELECT r FROM Racun r"),
    @NamedQuery(name = "Racun.findByIdR", query = "SELECT r FROM Racun r WHERE r.idR = :idR"),
    @NamedQuery(name = "Racun.findByIdMes", query = "SELECT r FROM Racun r WHERE r.idMes = :idMes"),
    @NamedQuery(name = "Racun.findByStanje", query = "SELECT r FROM Racun r WHERE r.stanje = :stanje"),
    @NamedQuery(name = "Racun.findByDozvMinus", query = "SELECT r FROM Racun r WHERE r.dozvMinus = :dozvMinus"),
    @NamedQuery(name = "Racun.findByDatumVreme", query = "SELECT r FROM Racun r WHERE r.datumVreme = :datumVreme"),
    @NamedQuery(name = "Racun.findByStatus", query = "SELECT r FROM Racun r WHERE r.status = :status"),
    @NamedQuery(name = "Racun.findByBrTransakcija", query = "SELECT r FROM Racun r WHERE r.brTransakcija = :brTransakcija")})
public class Racun implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdR")
    private Integer idR;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdMes")
    private int idMes;
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
    private List<Transakcija> transakcijaList;
    @JoinColumn(name = "IdK", referencedColumnName = "IdK")
    @ManyToOne(optional = false)
    private KomitentLite idK;

    public Racun() {
    }

    public Racun(Integer idR) {
        this.idR = idR;
    }

    public Racun(Integer idR, int idMes, int stanje, int dozvMinus, Date datumVreme, Character status, int brTransakcija) {
        this.idR = idR;
        this.idMes = idMes;
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

    public int getIdMes() {
        return idMes;
    }

    public void setIdMes(int idMes) {
        this.idMes = idMes;
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
    public List<Transakcija> getTransakcijaList() {
        return transakcijaList;
    }

    public void setTransakcijaList(List<Transakcija> transakcijaList) {
        this.transakcijaList = transakcijaList;
    }

    public KomitentLite getIdK() {
        return idK;
    }

    public void setIdK(KomitentLite idK) {
        this.idK = idK;
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
        if (!(object instanceof Racun)) {
            return false;
        }
        Racun other = (Racun) object;
        if ((this.idR == null && other.idR != null) || (this.idR != null && !this.idR.equals(other.idR))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "entiteti.Racun[ idR=" + idR + " ]";
	StringBuilder sb = new StringBuilder();
	sb.append(idR);
	sb.append('\t');
	sb.append(idK.getIdK());
	sb.append('\t');
	sb.append(idMes);
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
