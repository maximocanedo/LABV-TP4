package utn.frgp.tp4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.DateAndTime;
import com.github.javafaker.Faker;

import web.entity.*;
import web.generator.AppointmentGenerator;
import web.generator.DoctorGenerator;
import web.generator.PatientGenerator;
import web.logicImpl.AppointmentLogicImpl;
import web.logicImpl.DoctorLogicImpl;

class AppointmentGeneratorTest {

    @Mock
    private AppointmentLogicImpl mockAppointments;

    @Mock
    private DoctorLogicImpl mockDoctors;

    @Mock
    private DoctorGenerator doctorGenerator;

    @Mock
    private PatientGenerator patientGenerator;

    @Mock
    private Faker faker;

    @Mock
    private DateAndTime dateAndTime;

    private AppointmentGenerator appointmentGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        appointmentGenerator = new AppointmentGenerator();

        appointmentGenerator.setAppointments(mockAppointments);
        appointmentGenerator.setDoctors(mockDoctors);
        appointmentGenerator.setDoctorGenerator(doctorGenerator);
        appointmentGenerator.setPatientGenerator(patientGenerator);
        appointmentGenerator.setFaker(faker);

        when(faker.date()).thenReturn(dateAndTime);
        when(dateAndTime.future(anyInt(), any(TimeUnit.class))).thenReturn(new Date());
    }

    @Test
    void testGenerate() {
        // Mock dependencies
        Patient mockPatient = mock(Patient.class);
        Doctor mockDoctor = mock(Doctor.class);
        Date mockDate = new Date();

        when(patientGenerator.generate()).thenReturn(mockPatient);
        when(doctorGenerator.generate()).thenReturn(mockDoctor);
        when(dateAndTime.future(1280, TimeUnit.DAYS)).thenReturn(mockDate);

        Appointment result = appointmentGenerator.generate();

        assertNotNull(result);
        assertEquals(mockPatient, result.getPatient());
        assertEquals(mockDoctor, result.getAssignedDoctor());
        assertEquals(AppointmentStatus.PENDING, result.getStatus());
        assertEquals("", result.getRemarks());
        assertEquals(mockDate, result.getDate());
    }


    @Test
    void testGenerateForDoctor1234() {
        Patient mockPatient = mock(Patient.class);
        User mockUser = mock(User.class);
        Doctor mockDoctor = mock(Doctor.class);

        web.entity.Optional<Doctor> doctorOptional = new web.entity.Optional<>(mockDoctor); 
        Date mockDate = new Date();

        when(mockDoctors.findByFile(1234, mockUser)).thenReturn(doctorOptional);
        when(doctorGenerator.generate(any(User.class))).thenReturn(mockDoctor);
        when(dateAndTime.future(anyInt(), any(TimeUnit.class))).thenReturn(mockDate);

        IAppointment result = appointmentGenerator.generateForDoctor1234(mockPatient, mockUser);

        assertNotNull(result);
        assertEquals(mockDoctor, result.getAssignedDoctor());
        assertEquals(mockPatient, result.getPatient());
        assertEquals(AppointmentStatus.PENDING, result.getStatus());
        verify(mockDoctors, atLeast(1)).findByFile(1234, mockUser);
        verify(mockAppointments, times(1)).register(any(Appointment.class), eq(mockUser));
    }

}
