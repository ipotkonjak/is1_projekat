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
@Table(name = "komitent_lite")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KomitentLite.findAll", query = "SELECT k FROM KomitentLite k"),
    @NamedQuery(name = "KomitentLite.findByIdK", query = "SELECT k FROM KomitentLite k WHERE k.idK = :idK"),
    @NamedQuery(name = "KomitentLite.findByNaziv", query = "SELECT k FROM KomitentLite k WHERE k.naziv = :naziv"),
    @NamedQuery(name = "KomitentLite.findByAdresa", query = "SELECT k FROM KomitentLite k WHERE k.adresa = :adresa"),
    @NamedQuery(name = "KomitentLite.findBySediste", query = "SELECT k FROM KomitentLite k WHERE k.sediste = :sediste")})
public class KomitentLite implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Column(name = "Sediste")
    private int sediste;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idK")
    private List<Racun> racunList;

    public KomitentLite() {
    }

    public KomitentLite(Integer idK) {
        this.idK = idK;
    }

    public KomitentLite(Integer idK, String naziv, String adresa, int sediste) {
        this.idK = idK;
        this.naziv = naziv;
        this.adresa = adresa;
        this.sediste = sediste;
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

    public int getSediste() {
        return sediste;
    }

    public void setSediste(int sediste) {
        this.sediste = sediste;
    }

    @XmlTransient
    public List<Racun> getRacunList() {
        return racunList;
    }

    public void setRacunList(List<Racun> racunList) {
        this.racunList = racunList;
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
        if (!(object instanceof KomitentLite)) {
            return false;
        }
        KomitentLite other = (KomitentLite) object;
        if ((this.idK == null && other.idK != null) || (this.idK != null && !this.idK.equals(other.idK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.KomitentLite[ idK=" + idK + " ]";
    }
    
}
