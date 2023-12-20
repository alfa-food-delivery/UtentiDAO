package it.alfasoft.utentidao;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.alfasoft.daogenerico.DaoConnected;
import it.alfasoft.daogenerico.DaoException;

public class UtentiDAO<T,I> extends DaoConnected<Utente,Integer> {

    public UtentiDAO(String tableName) { super(tableName); }
    @Override
    public Utente creaOggetto(ResultSet rs) throws DaoException {
        Utente u = null;
        try{
            u = new Utente(
                    rs.getInt("id_utente"),
                    rs.getString("email"),
                    rs.getString("password_hash")
            );
            return u;
        }catch(Exception sqle){ sqle.printStackTrace(); throw new DaoException();}
    }

    @Override
    public Integer getGeneratedKey(PreparedStatement ps) throws DaoException {
        try{
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e) { e.printStackTrace(); throw new DaoException(); }
    }
    @Override
    public int eseguiUpdate(PreparedStatement ps, Utente elemento) throws DaoException {
        try{
            ps.setString(1, elemento.getEmail() );
            ps.setString(2, elemento.getPasswordHash());
            return ps.executeUpdate();
        }catch (Exception e) { e.printStackTrace(); throw new DaoException(); }
    }

    @Override
    public List<Utente> acquisisciOggettoDaFile(String percorsoFile) throws DaoException {
        File file = new File("C:\\Users\\alebr\\Documents\\FoodDelivery\\FoodDelivery\\DAOs\\UtentiDAO\\src\\main\\resources\\" + percorsoFile);
        try (Scanner sc = new Scanner(file)) {
            List<Utente> listaUtenti = new ArrayList<>();
            while (sc.hasNextLine()) {
                String[] buf = sc.nextLine().split(",");
                Utente c = new Utente(Integer.parseInt(buf[0]),buf[1],buf[2]);
                listaUtenti.add(c);
            }
            return listaUtenti;
        }
        catch (NumberFormatException e) { e.printStackTrace(); throw new DaoException();}
        catch (FileNotFoundException f){ f.printStackTrace(); throw new DaoException();}
    }

    @Override
    public int assegnaCategoria(String s, Integer integer) throws DaoException {
        try {
            // Controlla se il ruolo esiste:
            RuoliDAO<Ruolo, Integer> ruoliDAO = new RuoliDAO<Ruolo, Integer>("food_delivery.ruoli");
            List<Ruolo> ruoliEsistenti = ruoliDAO.read();
            Ruolo role = checkIfCategoryIsAlreadyExistent(ruoliEsistenti, s.toUpperCase());

            if (role != null) {// Il ruolo esiste già
                associazioneCategoria(integer, role);
                return 0;
            } else {// Fai qualcos'altro se il ruolo non esiste
                Ruolo newRole = new Ruolo(ruoliEsistenti.size() + 1, s.toUpperCase(), ("Ruolo per gli utenti " + qg.generateDTOClassName(s)));
                ruoliDAO.create(newRole);
                associazioneCategoria(integer, newRole);
                return 1; // Ritorno desiderato se il ruolo non esisteva
            }
        }catch (Exception e) { e.printStackTrace();throw new DaoException(); }
    }

    public void associazioneCategoria(Integer integer, Ruolo newRole) throws DaoException {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO food_delivery.utenti_ruoli (id_utente,id_ruolo) VALUES (");
        queryBuilder.append(integer + "," + newRole.getId() + ");");
        try(
            Statement stmt = getConnection().createStatement();
        ){
            stmt.executeUpdate(queryBuilder.toString());
        }catch (Exception e) { throw new DaoException(); }
    }

    public Ruolo checkIfCategoryIsAlreadyExistent(List<Ruolo> ruoliEsistenti, String s){
        return ruoliEsistenti.stream()
                .filter(ruolo -> ruolo.getNomeRuolo().equalsIgnoreCase(s))
                .findFirst()
                .orElse(null);
    }

    //exists v1
    //public boolean isAlreadyExistent(List<Ruolo> ruoliEsistenti, String s){
    //    return ruoliEsistenti.stream().anyMatch(ruolo -> ruolo.getNomeRuolo().equals(s.toUpperCase()));
    //}

    @Override
    public List<Object> getValori(Utente elemento) throws DaoException {
        List<Object> valori = new ArrayList<>();
        valori.add(elemento.getId());
        valori.add(elemento.getEmail());
        valori.add(elemento.getPasswordHash());
        return valori;
    }

    //TO DO : to be implemented
    @Override
    public boolean checkOggetto(Integer id, Utente elemento) throws DaoException {
        if(elemento.getEmail()==null){ throw new DaoException("La nuova mail non puo' essere vuota"); }
        if(elemento.getPasswordHash()==null){ throw new DaoException("La nuova password non puo' essere vuota");}
        if(! verificaFormatoEmail(elemento.getEmail())){ throw new DaoException("Formato email non valido");}
        return true;
    }

    public static boolean verificaFormatoEmail(String formatoDouble) {
        Matcher matcher = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(formatoDouble);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
