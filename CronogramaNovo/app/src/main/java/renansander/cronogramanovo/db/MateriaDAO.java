package renansander.cronogramanovo.db;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import renansander.cronogramanovo.domain.Materias;


public class MateriaDAO {

    private DAOObserver observer;

    private static final String COLLECTION = "materias";
    private static final String DESC = "description";
    private static final String ROOM = "room";
    private static final String WEEK_DAY = "week_day";
    private static final String HOUR = "hour";
    private static final String ACT = "active";

    //para ter acesso a base no firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MateriaDAO(DAOObserver observer){
        this.observer = observer;

    }

    public void loadMaterias(){

        List<Materias> materias = new ArrayList<>();

        db.collection(COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(
                            @NonNull com.google.android.gms.tasks.Task<QuerySnapshot> taskFire) {
                        if(taskFire.isSuccessful()){
                            //taskFire.getResult()//lista de documentos
                            for(QueryDocumentSnapshot doc : taskFire.getResult()){

                                String id = doc.getId();
                                String des = doc.get(DESC, String.class);
                                String room = doc.get(ROOM, String.class);
                                String week_day = doc.get(WEEK_DAY, String.class);
                                String hour = doc.get(HOUR, String.class);
                                Boolean act = doc.get(ACT, Boolean.class);
                                //Object activeValue = doc.get(ACT);
                                //Boolean act = activeValue instanceof Boolean ?
                                //        (Boolean) activeValue : true;
                                Materias materia = new Materias(id, des, room, week_day, hour, act);
                                materia.setActive(act);

                                materias.add(materia);
                            }
                            observer.loadSuccess((materias));
                        }
                    }
                });

    }

    public boolean save(Materias materia){

        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put(DESC, materia.getDescription());
        taskMap.put(ACT, materia.getActive());
        //taskMap.put(DT_CHANGE, task.getDescription());
        db.collection(COLLECTION)//recupera colecao
                .add(materia);//insere doc
        return true;
    }

    public void update(Materias materia){

        Map<String, Object> materiaMap = new HashMap<>();
        materiaMap.put(DESC, materia.getDescription());
        materiaMap.put(ROOM, materia.getRoom());
        materiaMap.put(WEEK_DAY, materia.getWeek_day());
        materiaMap.put(HOUR, materia.getHour());
        materiaMap.put(ACT, materia.getActive());
        //recupera a colecao
        db.collection(COLLECTION)
                .document(materia.getId().toString())//recupera doc
                .update(materiaMap);//altera documento

    }

    public void delete(Materias materia){

        db.collection(COLLECTION).document(materia.getId().toString()).delete();

    }

}
