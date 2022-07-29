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
@Table(name = "mesto_copy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MestoCopy.findAll", query = "SELECT m FROM MestoCopy m"),
    @NamedQuery(name = "MestoCopy.findByIdMes", query = "SELECT m FROM MestoCopy m WHERE m.idMes = :idMes"),
    @NamedQuery(name = "MestoCopy.findByNaziv", query = "SELECT m FROM MestoCopy m WHERE m.naziv = :naziv"),
    @NamedQuery(name = "MestoCopy.findByPostanskiBr", query = "SELECT m FROM MestoCopy m WHERE m.postanskiBr = :postanskiBr")})
public class MestoCopy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdMes")
    private Integer idMes;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PostanskiBr")
    private int postanskiBr;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sediste")
    private List<KomitentCopy> komitentCopyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMes")
    private List<RacunCopy> racunCopyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMes")
    private List<FilijalaCopy> filijalaCopyList;

    public MestoCopy() {
    }

    public MestoCopy(Integer idMes) {
        this.idMes = idMes;
    }

    public MestoCopy(Integer idMes, String naziv, int postanskiBr) {
        this.idMes = idMes;
        this.naziv = naziv;
        this.postanskiBr = postanskiBr;
    }

    public Integer getIdMes() {
        return idMes;
    }

    public void setIdMes(Integer idMes) {
        this.idMes = idMes;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getPostanskiBr() {
        return postanskiBr;
    }

    public void setPostanskiBr(int postanskiBr) {
        this.postanskiBr = postanskiBr;
    }

    @XmlTransient
    public List<KomitentCopy> getKomitentCopyList() {
        return komitentCopyList;
    }

    public void setKomitentCopyList(List<KomitentCopy> komitentCopyList) {
        this.komitentCopyList = komitentCopyList;
    }

    @XmlTransient
    public List<RacunCopy> getRacunCopyList() {
        return racunCopyList;
    }

    public void setRacunCopyList(List<RacunCopy> racunCopyList) {
        this.racunCopyList = racunCopyList;
    }

    @XmlTransient
    public List<FilijalaCopy> getFilijalaCopyList() {
        return filijalaCopyList;
    }

    public void setFilijalaCopyList(List<FilijalaCopy> filijalaCopyList) {
        this.filijalaCopyList = filijalaCopyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMes != null ? idMes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MestoCopy)) {
            return false;
        }
        MestoCopy other = (MestoCopy) object;
        if ((this.idMes == null && other.idMes != null) || (this.idMes != null && !this.idMes.equals(other.idMes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "kopije.MestoCopy[ idMes=" + idMes + " ]";
	StringBuilder sb = new StringBuilder();
	sb.append(idMes);
	sb.append('\t');
	sb.append(naziv);
	if(naziv.length() < 8)
	    sb.append('\t');
	sb.append('\t');
	sb.append(postanskiBr);
	return sb.toString();
    }
    
}
