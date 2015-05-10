package pl.gda.spc.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import pl.gda.spc.DevcrowdDao;
import pl.gda.spc.model.Cell;
import pl.gda.spc.model.Player;
import pl.gda.spc.model.PlayerFieldDefinition;

@Component
public class JdbcDao implements DevcrowdDao {

    private static final String SELECT_PLAYERS = "select * from players";
    private static final String SELECT_CELLS_FOR_PLAYER = "select * from cells where id = ?";
    private static final String SELECT_PLAYER_BY_EMAIL_TEMPLATE = "select * from players where email = ?";
    private static final String SELECT_PLAYER_COUNT_BY_EMAIL_TEMPLATE = "select count(*) from players where email = ?";
    private static final String INSRET_PLAYER_TEPMLATE = "INSERT INTO players (name,email,points) VALUES (?,?,?);";
    private static final String INSRET_CELL_TEPMLATE = "INSERT INTO cells (id,x,y) VALUES (?,?,?)";
    private static final String ADDITIONAL_SQL_VALUES = ",(?,?,?)";

    private CellMapper cellMapper = new CellMapper();

    @Autowired
    private JdbcTemplate template;

    @Override
    public void insertPlayer(String name, String email, int points) {
        template.update(INSRET_PLAYER_TEPMLATE, name, email, points);
    }

    @Override
    public List<Player> getPlayersByEmail(String email) {
        return template.query(SELECT_PLAYER_BY_EMAIL_TEMPLATE, new Object[]{email}, new PlayerMapper());
    }

    @Override
    public Integer getEmailsCount(String email) {
        return template.queryForObject(SELECT_PLAYER_COUNT_BY_EMAIL_TEMPLATE, new Object[]{email}, Integer.class);
    }

    @Override
    public void insertField(Long playerId, PlayerFieldDefinition field) {
        StringBuilder query = buildQuery(field);
        List<Object> queryArgs = buildQueryArgsList(field, playerId);

        template.update(query.toString(), queryArgs.toArray());
    }

    @Override
    public List<Player> getAllPlayers() {
        return template.query(SELECT_PLAYERS, new PlayerMapper());
    }

    @Override
    public List<Cell> getCellsForPlayer(Long playerId) {
        return template.query(SELECT_CELLS_FOR_PLAYER, new Object[]{playerId}, cellMapper);
    }

    private StringBuilder buildQuery(PlayerFieldDefinition field) {
        StringBuilder query = new StringBuilder(INSRET_CELL_TEPMLATE);

        if (field.getCells().size() > 1) {
            for (int i = 1; i < field.getCells().size(); i++) {
                query.append(ADDITIONAL_SQL_VALUES);
            }
        }

        return query;
    }

    private List<Object> buildQueryArgsList(PlayerFieldDefinition field, Long playerId) {
        List<Object> queryArgs = new ArrayList<>();

        field.getCells().stream().forEach(c -> {queryArgs.add(playerId); queryArgs.add(c.getX()); queryArgs.add(c.getY());});

        return queryArgs;
    }

}
