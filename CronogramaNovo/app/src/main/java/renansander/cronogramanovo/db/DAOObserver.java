package renansander.cronogramanovo.db;

import java.util.List;

import renansander.cronogramanovo.domain.Materias;

public interface DAOObserver {

    default void loadSuccess(List<Materias> materia){};
    default void loadFailure(){};
    default void saveSuccess(){};
    default void saveFailure(){};
    default void updateSuccess(){};
    default void updateFailure(){};
    default void deleteSuccess(){};
    default void deleteFailure(){};
}
