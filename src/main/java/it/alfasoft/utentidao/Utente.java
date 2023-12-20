package it.alfasoft.utentidao;

import it.alfasoft.daogenerico.IDto;

import java.util.Set;

public class Utente implements IDto<Integer> {
    private Integer idUtente;
    private String email;
    private String passwordHash;
    private Ruolo ruolo;

    public Utente(){};

    public Utente(String email, String passwordHash){
        this.email = email;
        this.passwordHash = passwordHash;
    }
    public Utente(int idUtente, String email, String passwordHash){
        this.idUtente = idUtente;
        this.email = email;
        this.passwordHash = passwordHash;
    }
    //GETTERS
    @Override
    public Integer getId(){ return this.idUtente; }
    public String getEmail(){
        return this.email;
    }
    public String getPasswordHash(){
        return this.passwordHash;
    }
    public Ruolo getRuolo(){
        return this.ruolo;
    }

    public void setIdUtente(Integer idUtente){ this.idUtente = idUtente; }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }
    public void setRuolo(Ruolo ruolo){
        this.ruolo = ruolo;
    }

}
