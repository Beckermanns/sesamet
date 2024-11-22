import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IAtencion, NewAtencion } from '../atencion.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAtencion for edit and NewAtencionFormGroupInput for create.
 */
type AtencionFormGroupInput = IAtencion | PartialWithRequiredKeyOf<NewAtencion>;

type AtencionFormDefaults = Pick<NewAtencion, 'id'>;

type AtencionFormGroupContent = {
  id: FormControl<IAtencion['id'] | NewAtencion['id']>;
  fecha: FormControl<IAtencion['fecha']>;
  hora: FormControl<IAtencion['hora']>;
  motivo_consulta: FormControl<IAtencion['motivo_consulta']>;
  diagnostico: FormControl<IAtencion['diagnostico']>;
  tratamiento: FormControl<IAtencion['tratamiento']>;
  centroMedico: FormControl<IAtencion['centroMedico']>;
  doctor: FormControl<IAtencion['doctor']>;
  paciente: FormControl<IAtencion['paciente']>;
};

export type AtencionFormGroup = FormGroup<AtencionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AtencionFormService {
  createAtencionFormGroup(atencion: AtencionFormGroupInput = { id: null }): AtencionFormGroup {
    const atencionRawValue = {
      ...this.getFormDefaults(),
      ...atencion,
    };
    return new FormGroup<AtencionFormGroupContent>({
      id: new FormControl(
        { value: atencionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fecha: new FormControl(atencionRawValue.fecha),
      hora: new FormControl(atencionRawValue.hora),
      motivo_consulta: new FormControl(atencionRawValue.motivo_consulta),
      diagnostico: new FormControl(atencionRawValue.diagnostico),
      tratamiento: new FormControl(atencionRawValue.tratamiento),
      centroMedico: new FormControl(atencionRawValue.centroMedico, {
        validators: [Validators.required],
      }),
      doctor: new FormControl(atencionRawValue.doctor, {
        validators: [Validators.required],
      }),
      paciente: new FormControl(atencionRawValue.paciente, {
        validators: [Validators.required],
      }),
    });
  }

  getAtencion(form: AtencionFormGroup): IAtencion | NewAtencion {
    return form.getRawValue() as IAtencion | NewAtencion;
  }

  resetForm(form: AtencionFormGroup, atencion: AtencionFormGroupInput): void {
    const atencionRawValue = { ...this.getFormDefaults(), ...atencion };
    form.reset(
      {
        ...atencionRawValue,
        id: { value: atencionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AtencionFormDefaults {
    return {
      id: null,
    };
  }
}
