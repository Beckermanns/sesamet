import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICentroMedico, NewCentroMedico } from '../centro-medico.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICentroMedico for edit and NewCentroMedicoFormGroupInput for create.
 */
type CentroMedicoFormGroupInput = ICentroMedico | PartialWithRequiredKeyOf<NewCentroMedico>;

type CentroMedicoFormDefaults = Pick<NewCentroMedico, 'id'>;

type CentroMedicoFormGroupContent = {
  id: FormControl<ICentroMedico['id'] | NewCentroMedico['id']>;
  centro: FormControl<ICentroMedico['centro']>;
  region: FormControl<ICentroMedico['region']>;
  comuna: FormControl<ICentroMedico['comuna']>;
  direccion: FormControl<ICentroMedico['direccion']>;
};

export type CentroMedicoFormGroup = FormGroup<CentroMedicoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CentroMedicoFormService {
  createCentroMedicoFormGroup(centroMedico: CentroMedicoFormGroupInput = { id: null }): CentroMedicoFormGroup {
    const centroMedicoRawValue = {
      ...this.getFormDefaults(),
      ...centroMedico,
    };
    return new FormGroup<CentroMedicoFormGroupContent>({
      id: new FormControl(
        { value: centroMedicoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      centro: new FormControl(centroMedicoRawValue.centro),
      region: new FormControl(centroMedicoRawValue.region),
      comuna: new FormControl(centroMedicoRawValue.comuna),
      direccion: new FormControl(centroMedicoRawValue.direccion),
    });
  }

  getCentroMedico(form: CentroMedicoFormGroup): ICentroMedico | NewCentroMedico {
    return form.getRawValue() as ICentroMedico | NewCentroMedico;
  }

  resetForm(form: CentroMedicoFormGroup, centroMedico: CentroMedicoFormGroupInput): void {
    const centroMedicoRawValue = { ...this.getFormDefaults(), ...centroMedico };
    form.reset(
      {
        ...centroMedicoRawValue,
        id: { value: centroMedicoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CentroMedicoFormDefaults {
    return {
      id: null,
    };
  }
}
