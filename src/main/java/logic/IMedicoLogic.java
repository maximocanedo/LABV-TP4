package logic;

import java.util.List;
import entity.Medico;

public interface IMedicoLogic {

    void signupMedico(Medico medico);

    Medico findById(int id);

    List<Medico> list(int page, int size);

    List<Medico> list();

}
