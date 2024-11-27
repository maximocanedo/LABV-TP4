package utn.frgp.tp4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;

import java.util.*;

import web.daoImpl.DoctorDAOImpl;
import web.daoImpl.ScheduleDAOImpl;
import web.entity.*;
import web.exceptions.NotAllowedException;
import web.exceptions.NotFoundException;
import web.logic.validator.DoctorValidator;
import web.logicImpl.DoctorLogicImpl;
import web.logicImpl.UserPermitLogicImpl;

class DoctorLogicImplTest {

    private DoctorLogicImpl doctorLogic;

    private DoctorDAOImpl mockDoctorDaoImpl;
    private ScheduleDAOImpl mockScheduleDaoImpl;
    private UserPermitLogicImpl mockPermitLogic;
    private DoctorValidator mockDoctorValidator;

    @BeforeEach
    void setUp() {
        mockDoctorDaoImpl = mock(DoctorDAOImpl.class);
        mockScheduleDaoImpl = mock(ScheduleDAOImpl.class);
        mockPermitLogic = mock(UserPermitLogicImpl.class);
        mockDoctorValidator = mock(DoctorValidator.class);

        doctorLogic = new DoctorLogicImpl();
        doctorLogic.setDoctorsrepository(mockDoctorDaoImpl);
        doctorLogic.setPermits(mockPermitLogic);
        doctorLogic.setSchedulesrepository(mockScheduleDaoImpl);
        doctorLogic.setDoctorValidator(mockDoctorValidator);
    }

    @Test
    void testAddDoctorSuccessfully() {
        Doctor doctor = new Doctor();
        doctor.setName("John");
        doctor.setSurname("Doe");

        User user = mock(User.class);
        when(mockPermitLogic.require(user, Permit.CREATE_DOCTOR)).thenReturn(user);

        when(mockDoctorValidator.name("John")).thenReturn("John");
        when(mockDoctorValidator.surname("Doe")).thenReturn("Doe");
        when(mockDoctorDaoImpl.add(any(Doctor.class))).thenReturn(doctor);

        Doctor result = doctorLogic.add(doctor, user);

        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(mockPermitLogic, times(1)).require(user, Permit.CREATE_DOCTOR);
        verify(mockDoctorDaoImpl, times(1)).add(any(Doctor.class));
    }

    @Test
    void testAddDoctorThrowsPermissionError() {
        Doctor doctor = new Doctor();
        User user = mock(User.class);

        doThrow(new NotAllowedException("Permission Denied"))
                .when(mockPermitLogic).require(user, Permit.CREATE_DOCTOR);

        NotAllowedException exception = assertThrows(
                NotAllowedException.class,
                () -> doctorLogic.add(doctor, user)
        );
        assertEquals("Permission Denied", exception.getMessage());
        verify(mockDoctorDaoImpl, never()).add(any(Doctor.class));
    }

    @Test
    void testAddScheduleSuccessfully() {
        int file = 1234;
        User user = mock(User.class);
        Schedule schedule = new Schedule();
        Doctor doctor = new Doctor();
        doctor.setSchedules(new HashSet<>());

        when(mockPermitLogic.inquireDoctorByFile(user, file, Permit.UPDATE_DOCTOR_SCHEDULES)).thenReturn(user);
        when(mockDoctorDaoImpl.findByFile(file)).thenReturn(new web.entity.Optional<>(doctor));
        when(mockScheduleDaoImpl.save(schedule)).thenReturn(schedule);
        Set<Schedule> result = doctorLogic.addSchedule(file, schedule, user);
        assertNotNull(result);
        assertTrue(result.contains(schedule));
        verify(mockScheduleDaoImpl, times(1)).save(schedule);
        verify(mockScheduleDaoImpl, times(1)).link(schedule, doctor);
    }

}
