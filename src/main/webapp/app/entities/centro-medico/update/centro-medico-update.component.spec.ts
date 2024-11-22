import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CentroMedicoService } from '../service/centro-medico.service';
import { ICentroMedico } from '../centro-medico.model';
import { CentroMedicoFormService } from './centro-medico-form.service';

import { CentroMedicoUpdateComponent } from './centro-medico-update.component';

describe('CentroMedico Management Update Component', () => {
  let comp: CentroMedicoUpdateComponent;
  let fixture: ComponentFixture<CentroMedicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let centroMedicoFormService: CentroMedicoFormService;
  let centroMedicoService: CentroMedicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CentroMedicoUpdateComponent],
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
      .overrideTemplate(CentroMedicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CentroMedicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    centroMedicoFormService = TestBed.inject(CentroMedicoFormService);
    centroMedicoService = TestBed.inject(CentroMedicoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const centroMedico: ICentroMedico = { id: 456 };

      activatedRoute.data = of({ centroMedico });
      comp.ngOnInit();

      expect(comp.centroMedico).toEqual(centroMedico);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentroMedico>>();
      const centroMedico = { id: 123 };
      jest.spyOn(centroMedicoFormService, 'getCentroMedico').mockReturnValue(centroMedico);
      jest.spyOn(centroMedicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centroMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centroMedico }));
      saveSubject.complete();

      // THEN
      expect(centroMedicoFormService.getCentroMedico).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(centroMedicoService.update).toHaveBeenCalledWith(expect.objectContaining(centroMedico));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentroMedico>>();
      const centroMedico = { id: 123 };
      jest.spyOn(centroMedicoFormService, 'getCentroMedico').mockReturnValue({ id: null });
      jest.spyOn(centroMedicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centroMedico: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centroMedico }));
      saveSubject.complete();

      // THEN
      expect(centroMedicoFormService.getCentroMedico).toHaveBeenCalled();
      expect(centroMedicoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentroMedico>>();
      const centroMedico = { id: 123 };
      jest.spyOn(centroMedicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centroMedico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(centroMedicoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
