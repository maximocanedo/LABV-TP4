package logic;

import java.time.LocalDate;
import java.util.List;
import entity.Medico;

public interface IMedicoLogic {

    void signupMedico(Medico medico);

    Medico findById(int id);

    List<Medico> list(int page, int size);

    List<Medico> list();
    
    List<Object[]> listMedicosLegajoAscP2();

    List<Object[]> getTurnosMedicoEnFecha(int legajo, LocalDate fecha);
    
    List<Object[]> getTurnosMedicoEnRangoDeFechas(int legajo, LocalDate fechaInicio, LocalDate fechaFin);
}
