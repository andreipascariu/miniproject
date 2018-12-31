package ro.andrei.miniproject.repository;

import java.util.ArrayList;

public interface IRepository {

    boolean create(Object object);
    boolean delete(int id);
    ArrayList<?> getAll();
    Object getById(int id);

}
