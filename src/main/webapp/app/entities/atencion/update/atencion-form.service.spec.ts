import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../atencion.test-samples';

import { AtencionFormService } from './atencion-form.service';

describe('Atencion Form Service', () => {
  let service: AtencionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AtencionFormService);
  });

  describe('Service methods', () => {
    describe('createAtencionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAtencionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fecha: expect.any(Object),
            hora: expect.any(Object),
            motivo_consulta: expect.any(Object),
            diagnostico: expect.any(Object),
            tratamiento: expect.any(Object),
            centroMedico: expect.any(Object),
            doctor: expect.any(Object),
            paciente: expect.any(Object),
          }),
        );
      });

      it('passing IAtencion should create a new form with FormGroup', () => {
        const formGroup = service.createAtencionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fecha: expect.any(Object),
            hora: expect.any(Object),
            motivo_consulta: expect.any(Object),
            diagnostico: expect.any(Object),
            tratamiento: expect.any(Object),
            centroMedico: expect.any(Object),
            doctor: expect.any(Object),
            paciente: expect.any(Object),
          }),
        );
      });
    });

    describe('getAtencion', () => {
      it('should return NewAtencion for default Atencion initial value', () => {
        const formGroup = service.createAtencionFormGroup(sampleWithNewData);

        const atencion = service.getAtencion(formGroup) as any;

        expect(atencion).toMatchObject(sampleWithNewData);
      });

      it('should return NewAtencion for empty Atencion initial value', () => {
        const formGroup = service.createAtencionFormGroup();

        const atencion = service.getAtencion(formGroup) as any;

        expect(atencion).toMatchObject({});
      });

      it('should return IAtencion', () => {
        const formGroup = service.createAtencionFormGroup(sampleWithRequiredData);

        const atencion = service.getAtencion(formGroup) as any;

        expect(atencion).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAtencion should not enable id FormControl', () => {
        const formGroup = service.createAtencionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAtencion should disable id FormControl', () => {
        const formGroup = service.createAtencionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
