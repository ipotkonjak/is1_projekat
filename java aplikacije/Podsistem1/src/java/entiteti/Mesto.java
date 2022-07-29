/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "mesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mesto.findAll", query = "SELECT m FROM Mesto m"),
    @NamedQuery(name = "Mesto.findByIdMes", query = "SELECT m FROM Mesto m WHERE m.idMes = :idMes"),
    @NamedQuery(name = "Mesto.findByNaziv", query = "SELECT m FROM Mesto m WHERE m.naziv = :naziv"),
    @NamedQuery(name = "Mesto.findByPostanskiBr", query = "SELECT m FROM Mesto m WHERE m.postanskiBr = :postanskiBr")})
public class Mesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMes")
    private List<Filijala> filijalaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sediste")
    private List<Komitent> komitentList;

    public Mesto() {
    }

    public Mesto(Integer idMes) {
        this.idMes = idMes;
    }

    public Mesto(Integer idMes, String naziv, int postanskiBr) {
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
    public List<Filijala> getFilijalaList() {
        return filijalaList;
    }

    public void setFilijalaList(List<Filijala> filijalaList) {
        this.filijalaList = filijalaList;
    }

    @XmlTransient
    public List<Komitent> getKomitentList() {
        return komitentList;
    }

    public void setKomitentList(List<Komitent> komitentList) {
        this.komitentList = komitentList;
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
        if (!(object instanceof Mesto)) {
            return false;
        }
        Mesto other = (Mesto) object;
        if ((this.idMes == null && other.idMes != null) || (this.idMes != null && !this.idMes.equals(other.idMes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "entiteti.Mesto[ idMes=" + idMes + " ]";
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
