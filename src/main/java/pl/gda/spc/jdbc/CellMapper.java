package pl.gda.spc.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pl.gda.spc.model.Cell;

public class CellMapper implements RowMapper<Cell> {

    @Override
    public Cell mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Cell(resultSet.getInt("x"), resultSet.getInt("y"));
    }

}
