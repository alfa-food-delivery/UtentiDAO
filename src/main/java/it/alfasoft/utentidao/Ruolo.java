package it.alfasoft.utentidao;

import it.alfasoft.daogenerico.IDto;

public class Ruolo implements IDto<Integer> {
    private Integer idRuolo;
    private String nomeRuolo;
    private String descrizioneRuolo;

    public Ruolo(){};

    public Ruolo(String nomeRuolo) {
        this.nomeRuolo = nomeRuolo;
    }
    public Ruolo(int idRuolo, String nomeRuolo){
        this.idRuolo = idRuolo;
        this.nomeRuolo = nomeRuolo;
    }
    public Ruolo(String nomeRuolo, String descrizioneRuolo){
        this.nomeRuolo = nomeRuolo;
        this.descrizioneRuolo = descrizioneRuolo;
    }

    public Ruolo(int idRuolo, String nomeRuolo, String descrizioneRuolo){
        this.idRuolo = idRuolo;
        this.nomeRuolo = nomeRuolo;
        this.descrizioneRuolo = descrizioneRuolo;
    }

    public Integer getId() { return this.idRuolo; }
    public String getNomeRuolo() { return this.nomeRuolo;}
    public String getDescrizioneRuolo(){ return this.descrizioneRuolo;}
    public void setIdRuolo(int idRuolo) { this.idRuolo = idRuolo;}
    public void setNomeRuolo(String nomeRuolo) { this.nomeRuolo = nomeRuolo;}
    public void setDescrizioneRuolo(String descrizioneRuolo){ this.descrizioneRuolo = descrizioneRuolo;}
}
