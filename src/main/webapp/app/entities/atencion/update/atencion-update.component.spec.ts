import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ICentroMedico } from 'app/entities/centro-medico/centro-medico.model';
import { CentroMedicoService } from 'app/entities/centro-medico/service/centro-medico.service';
import { IDoctor } from 'app/entities/doctor/doctor.model';
import { DoctorService } from 'app/entities/doctor/service/doctor.service';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { IAtencion } from '../atencion.model';
import { AtencionService } from '../service/atencion.service';
import { AtencionFormService } from './atencion-form.service';

import { AtencionUpdateComponent } from './atencion-update.component';

describe('Atencion Management Update Component', () => {
  let comp: AtencionUpdateComponent;
  let fixture: ComponentFixture<AtencionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let atencionFormService: AtencionFormService;
  let atencionService: AtencionService;
  let centroMedicoService: CentroMedicoService;
  let doctorService: DoctorService;
  let pacienteService: PacienteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AtencionUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AtencionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AtencionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    atencionFormService = TestBed.inject(AtencionFormService);
    atencionService = TestBed.inject(AtencionService);
    centroMedicoService = TestBed.inject(CentroMedicoService);
    doctorService = TestBed.inject(DoctorService);
    pacienteService = TestBed.inject(PacienteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CentroMedico query and add missing value', () => {
      const atencion: IAtencion = { id: 456 };
      const centroMedico: ICentroMedico = { id: 23191 };
      atencion.centroMedico = centroMedico;

      const centroMedicoCollection: ICentroMedico[] = [{ id: 9327 }];
      jest.spyOn(centroMedicoService, 'query').mockReturnValue(of(new HttpResponse({ body: centroMedicoCollection })));
      const additionalCentroMedicos = [centroMedico];
      const expectedCollection: ICentroMedico[] = [...additionalCentroMedicos, ...centroMedicoCollection];
      jest.spyOn(centroMedicoService, 'addCentroMedicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ atencion });
      comp.ngOnInit();

      expect(centroMedicoService.query).toHaveBeenCalled();
      expect(centroMedicoService.addCentroMedicoToCollectionIfMissing).toHaveBeenCalledWith(
        centroMedicoCollection,
        ...additionalCentroMedicos.map(expect.objectContaining),
      );
      expect(comp.centroMedicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Doctor query and add missing value', () => {
      const atencion: IAtencion = { id: 456 };
      const doctor: IDoctor = { id: 20921 };
      atencion.doctor = doctor;

      const doctorCollection: IDoctor[] = [{ id: 23113 }];
      jest.spyOn(doctorService, 'query').mockReturnValue(of(new HttpResponse({ body: doctorCollection })));
      const additionalDoctors = [doctor];
      const expectedCollection: IDoctor[] = [...additionalDoctors, ...doctorCollection];
      jest.spyOn(doctorService, 'addDoctorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ atencion });
      comp.ngOnInit();

      expect(doctorService.query).toHaveBeenCalled();
      expect(doctorService.addDoctorToCollectionIfMissing).toHaveBeenCalledWith(
        doctorCollection,
        ...additionalDoctors.map(expect.objectContaining),
      );
      expect(comp.doctorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Paciente query and add missing value', () => {
      const atencion: IAtencion = { id: 456 };
      const paciente: IPaciente = { id: 15973 };
      atencion.paciente = paciente;

      const pacienteCollection: IPaciente[] = [{ id: 13693 }];
      jest.spyOn(pacienteService, 'query').mockReturnValue(of(new HttpResponse({ body: pacienteCollection })));
      const additionalPacientes = [paciente];
      const expectedCollection: IPaciente[] = [...additionalPacientes, ...pacienteCollection];
      jest.spyOn(pacienteService, 'addPacienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ atencion });
      comp.ngOnInit();

      expect(pacienteService.query).toHaveBeenCalled();
      expect(pacienteService.addPacienteToCollectionIfMissing).toHaveBeenCalledWith(
        pacienteCollection,
        ...additionalPacientes.map(expect.objectContaining),
      );
      expect(comp.pacientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const atencion: IAtencion = { id: 456 };
      const centroMedico: ICentroMedico = { id: 27383 };
      atencion.centroMedico = centroMedico;
      const doctor: IDoctor = { id: 25848 };
      atencion.doctor = doctor;
      const paciente: IPaciente = { id: 8556 };
      atencion.paciente = paciente;

      activatedRoute.data = of({ atencion });
      comp.ngOnInit();

      expect(comp.centroMedicosSharedCollection).toContain(centroMedico);
      expect(comp.doctorsSharedCollection).toContain(doctor);
      expect(comp.pacientesSharedCollection).toContain(paciente);
      expect(comp.atencion).toEqual(atencion);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAtencion>>();
      const atencion = { id: 123 };
      jest.spyOn(atencionFormService, 'getAtencion').mockReturnValue(atencion);
      jest.spyOn(atencionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atencion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: atencion }));
      saveSubject.complete();

      // THEN
      expect(atencionFormService.getAtencion).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(atencionService.update).toHaveBeenCalledWith(expect.objectContaining(atencion));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAtencion>>();
      const atencion = { id: 123 };
      jest.spyOn(atencionFormService, 'getAtencion').mockReturnValue({ id: null });
      jest.spyOn(atencionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atencion: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: atencion }));
      saveSubject.complete();

      // THEN
      expect(atencionFormService.getAtencion).toHaveBeenCalled();
      expect(atencionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAtencion>>();
      const atencion = { id: 123 };
      jest.spyOn(atencionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ atencion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(atencionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCentroMedico', () => {
      it('Should forward to centroMedicoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(centroMedicoService, 'compareCentroMedico');
        comp.compareCentroMedico(entity, entity2);
        expect(centroMedicoService.compareCentroMedico).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDoctor', () => {
      it('Should forward to doctorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(doctorService, 'compareDoctor');
        comp.compareDoctor(entity, entity2);
        expect(doctorService.compareDoctor).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePaciente', () => {
      it('Should forward to pacienteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pacienteService, 'comparePaciente');
        comp.comparePaciente(entity, entity2);
        expect(pacienteService.comparePaciente).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
