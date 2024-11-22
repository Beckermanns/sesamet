import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CentroMedicoDetailComponent } from './centro-medico-detail.component';

describe('CentroMedico Management Detail Component', () => {
  let comp: CentroMedicoDetailComponent;
  let fixture: ComponentFixture<CentroMedicoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CentroMedicoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./centro-medico-detail.component').then(m => m.CentroMedicoDetailComponent),
              resolve: { centroMedico: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CentroMedicoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CentroMedicoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load centroMedico on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CentroMedicoDetailComponent);

      // THEN
      expect(instance.centroMedico()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
