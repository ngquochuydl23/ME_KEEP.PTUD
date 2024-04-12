package dao;

import java.util.List;

public interface IDao<T, TKey> {
    T layTheoMa(TKey id);

    List<T> layHet();

    boolean them(T entity);

    boolean xoa(TKey id);

    boolean sua(T entity);
}
