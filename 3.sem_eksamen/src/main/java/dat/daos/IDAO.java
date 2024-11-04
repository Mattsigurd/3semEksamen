package dat.daos;

import dat.entities.Trip;

import java.time.LocalDate;
import java.util.List;

public interface IDAO <T> {
    List<T> getAll();
    T getById(int id);
    T create(T t);
    T update(int id, T update);

    void delete(T t);

}
