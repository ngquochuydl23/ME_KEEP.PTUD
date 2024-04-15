package dao;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T, TKey> {
    T layTheoMa(TKey id);

    List<T> layHet();

    boolean them(T entity) throws SQLException;

    boolean xoa(TKey id);

    boolean sua(T entity);
}
