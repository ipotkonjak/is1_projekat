/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kopije;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "filijala_copy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FilijalaCopy.findAll", query = "SELECT f FROM FilijalaCopy f"),
    @NamedQuery(name = "FilijalaCopy.findByIdFil", query = "SELECT f FROM FilijalaCopy f WHERE f.idFil = :idFil"),
    @NamedQuery(name = "FilijalaCopy.findByNaziv", query = "SELECT f FROM FilijalaCopy f WHERE f.naziv = :naziv"),
    @NamedQuery(name = "FilijalaCopy.findByAdresa", query = "SELECT f FROM FilijalaCopy f WHERE f.adresa = :adresa")})
public class FilijalaCopy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdFil")
    private Integer idFil;
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
    @OneToMany(mappedBy = "idFil")
    private List<TransakcijaCopy> transakcijaCopyList;
    @JoinColumn(name = "IdMes", referencedColumnName = "IdMes")
    @ManyToOne(optional = false)
    private MestoCopy idMes;

    public FilijalaCopy() {
    }

    public FilijalaCopy(Integer idFil) {
        this.idFil = idFil;
    }

    public FilijalaCopy(Integer idFil, String naziv, String adresa) {
        this.idFil = idFil;
        this.naziv = naziv;
        this.adresa = adresa;
    }

    public Integer getIdFil() {
        return idFil;
    }

    public void setIdFil(Integer idFil) {
        this.idFil = idFil;
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

    @XmlTransient
    public List<TransakcijaCopy> getTransakcijaCopyList() {
        return transakcijaCopyList;
    }

    public void setTransakcijaCopyList(List<TransakcijaCopy> transakcijaCopyList) {
        this.transakcijaCopyList = transakcijaCopyList;
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
        hash += (idFil != null ? idFil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FilijalaCopy)) {
            return false;
        }
        FilijalaCopy other = (FilijalaCopy) object;
        if ((this.idFil == null && other.idFil != null) || (this.idFil != null && !this.idFil.equals(other.idFil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "kopije.FilijalaCopy[ idFil=" + idFil + " ]";
	StringBuilder sb = new StringBuilder();
	sb.append(idFil);
	sb.append('\t');
	sb.append(naziv);
	sb.append('\t');
	sb.append(adresa);
	if(adresa.length() < 8)
	    sb.append('\t');
	sb.append('\t');
	sb.append(idMes.getIdMes());
	return sb.toString();
    }
    
}
