package it.alfasoft.utentidao;

import it.alfasoft.daogenerico.DaoConnected;
import it.alfasoft.daogenerico.DaoException;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RuoliDAO<T,I> extends DaoConnected<Ruolo,Integer> {

    public RuoliDAO(String tableName) { super(tableName); }
    @Override
    public Integer getGeneratedKey(PreparedStatement ps) throws DaoException {
        try{
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e) { e.printStackTrace(); throw new DaoException(); }
    }
    @Override
    public int eseguiUpdate(PreparedStatement ps, Ruolo elemento) throws DaoException {
        try{
            ps.setString(1, elemento.getNomeRuolo().toUpperCase() );
            return ps.executeUpdate();
        }catch (Exception e) { e.printStackTrace(); throw new DaoException(); }
    }

    @Override
    public List<Ruolo> acquisisciOggettoDaFile(String s) throws DaoException {
        File file = new File("C:\\Users\\alebr\\Documents\\FoodDelivery\\FoodDelivery\\DAOs\\UtentiDAO\\src\\main\\resources\\" + s);
        try (Scanner sc = new Scanner(file)) {
            List<Ruolo> listaRuoli = new ArrayList<>();
            while (sc.hasNextLine()) {
                String[] buf = sc.nextLine().split(",");
                Ruolo r = new Ruolo(buf[0].toUpperCase(),buf[1]);
                listaRuoli.add(r);
            }
            return listaRuoli;
        }
        catch (NumberFormatException e) { e.printStackTrace(); throw new DaoException();}
        catch (FileNotFoundException f){ f.printStackTrace(); throw new DaoException();}
    }

    @Override
    public int assegnaCategoria(String s, Integer integer) throws DaoException {
        return 0;
    }

    @Override
    public Ruolo creaOggetto(ResultSet rs) throws DaoException {
        Ruolo r = null;
        try{
            r = new Ruolo(
                    rs.getInt("id_ruolo"),
                    rs.getString("ruolo"),
                    rs.getString("descrizione")
            );
            return r;
        }catch(Exception sqle){ sqle.printStackTrace(); throw new DaoException();}
    }
    @Override
    public boolean checkOggetto(Integer integer, Ruolo ruolo) throws DaoException {
        return true;
    }
    @Override
    public List<Object> getValori(Ruolo elemento) throws DaoException {
        List<Object> valori = new ArrayList<>();
        valori.add(elemento.getId());
        valori.add(elemento.getNomeRuolo());
        valori.add(elemento.getDescrizioneRuolo());
        return valori;
    }
}