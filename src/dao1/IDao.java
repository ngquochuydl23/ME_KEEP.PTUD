package dao1;

import java.util.List;

public interface IDao<T, TKey> {
    T layTheoMa(TKey id);

    List<T> layHet();

    T them(T entity);

    void xoa(TKey id);

    T sua(T entity);
}
