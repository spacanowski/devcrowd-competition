package pl.gda.spc.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pl.gda.spc.model.Player;

public class PlayerMapper implements RowMapper<Player> {

    @Override
    public Player mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Player result = new Player();

        result.setId(resultSet.getLong("id"));
        result.setName(resultSet.getString("name"));
        result.setEmail(resultSet.getString("email"));
        result.setPoints(resultSet.getInt("points"));
//        result.setDisqualified(resultSet.getBoolean("disqualified"));

        return result;
    }

}
