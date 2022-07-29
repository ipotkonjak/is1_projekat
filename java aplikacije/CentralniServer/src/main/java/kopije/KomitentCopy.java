/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kopije;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Iva
 */
@Entity
@Table(name = "komitent_copy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KomitentCopy.findAll", query = "SELECT k FROM KomitentCopy k"),
    @NamedQuery(name = "KomitentCopy.findByIdK", query = "SELECT k FROM KomitentCopy k WHERE k.idK = :idK"),
    @NamedQuery(name = "KomitentCopy.findByNaziv", query = "SELECT k FROM KomitentCopy k WHERE k.naziv = :naziv"),
    @NamedQuery(name = "KomitentCopy.findByAdresa", query = "SELECT k FROM KomitentCopy k WHERE k.adresa = :adresa")})
public class KomitentCopy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdK")
    private Integer idK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Adresa")
    private String adresa;
    @JoinColumn(name = "Sediste", referencedColumnName = "IdMes")
    @ManyToOne(optional = false)
    private MestoCopy sediste;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idK")
    private List<RacunCopy> racunCopyList;

    public KomitentCopy() {
    }

    public KomitentCopy(Integer idK) {
        this.idK = idK;
    }

    public KomitentCopy(Integer idK, String naziv, String adresa) {
        this.idK = idK;
        this.naziv = naziv;
        this.adresa = adresa;
    }

    public Integer getIdK() {
        return idK;
    }

    public void setIdK(Integer idK) {
        this.idK = idK;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public MestoCopy getSediste() {
        return sediste;
    }

    public void setSediste(MestoCopy sediste) {
        this.sediste = sediste;
    }

    @XmlTransient
    public List<RacunCopy> getRacunCopyList() {
        return racunCopyList;
    }

    public void setRacunCopyList(List<RacunCopy> racunCopyList) {
        this.racunCopyList = racunCopyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idK != null ? idK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KomitentCopy)) {
            return false;
        }
        KomitentCopy other = (KomitentCopy) object;
        if ((this.idK == null && other.idK != null) || (this.idK != null && !this.idK.equals(other.idK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "kopije.KomitentCopy[ idK=" + idK + " ]";
	StringBuilder sb = new StringBuilder();
	sb.append(idK);
	sb.append('\t');
	sb.append(naziv);
	sb.append('\t');
	sb.append(adresa);
	if(adresa.length() < 8)
	    sb.append('\t');
	if(adresa.length() < 16)
	    sb.append('\t');
	sb.append('\t');
	sb.append(sediste.getIdMes());
	return sb.toString();
    }
    
}
