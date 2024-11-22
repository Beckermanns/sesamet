import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../centro-medico.test-samples';

import { CentroMedicoFormService } from './centro-medico-form.service';

describe('CentroMedico Form Service', () => {
  let service: CentroMedicoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CentroMedicoFormService);
  });

  describe('Service methods', () => {
    describe('createCentroMedicoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCentroMedicoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            centro: expect.any(Object),
            region: expect.any(Object),
            comuna: expect.any(Object),
            direccion: expect.any(Object),
          }),
        );
      });

      it('passing ICentroMedico should create a new form with FormGroup', () => {
        const formGroup = service.createCentroMedicoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            centro: expect.any(Object),
            region: expect.any(Object),
            comuna: expect.any(Object),
            direccion: expect.any(Object),
          }),
        );
      });
    });

    describe('getCentroMedico', () => {
      it('should return NewCentroMedico for default CentroMedico initial value', () => {
        const formGroup = service.createCentroMedicoFormGroup(sampleWithNewData);

        const centroMedico = service.getCentroMedico(formGroup) as any;

        expect(centroMedico).toMatchObject(sampleWithNewData);
      });

      it('should return NewCentroMedico for empty CentroMedico initial value', () => {
        const formGroup = service.createCentroMedicoFormGroup();

        const centroMedico = service.getCentroMedico(formGroup) as any;

        expect(centroMedico).toMatchObject({});
      });

      it('should return ICentroMedico', () => {
        const formGroup = service.createCentroMedicoFormGroup(sampleWithRequiredData);

        const centroMedico = service.getCentroMedico(formGroup) as any;

        expect(centroMedico).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICentroMedico should not enable id FormControl', () => {
        const formGroup = service.createCentroMedicoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCentroMedico should disable id FormControl', () => {
        const formGroup = service.createCentroMedicoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
