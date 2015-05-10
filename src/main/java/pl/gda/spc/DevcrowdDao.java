package pl.gda.spc;

import java.util.List;

import pl.gda.spc.model.Cell;
import pl.gda.spc.model.Player;
import pl.gda.spc.model.PlayerFieldDefinition;

public interface DevcrowdDao {

    void insertPlayer(String name, String email, int points);

    List<Player> getPlayersByEmail(String email);

    Integer getEmailsCount(String email);

    void insertField(Long playerId, PlayerFieldDefinition field);

    List<Player> getAllPlayers();

    List<Cell> getCellsForPlayer(Long playerId);
}
