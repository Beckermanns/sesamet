import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPaciente, NewPaciente } from '../paciente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaciente for edit and NewPacienteFormGroupInput for create.
 */
type PacienteFormGroupInput = IPaciente | PartialWithRequiredKeyOf<NewPaciente>;

type PacienteFormDefaults = Pick<NewPaciente, 'id'>;

type PacienteFormGroupContent = {
  id: FormControl<IPaciente['id'] | NewPaciente['id']>;
  rut_paciente: FormControl<IPaciente['rut_paciente']>;
  nombres: FormControl<IPaciente['nombres']>;
  apellido_paterno: FormControl<IPaciente['apellido_paterno']>;
  apellido_materno: FormControl<IPaciente['apellido_materno']>;
  telefono: FormControl<IPaciente['telefono']>;
  correo: FormControl<IPaciente['correo']>;
  direccion: FormControl<IPaciente['direccion']>;
  comuna: FormControl<IPaciente['comuna']>;
};

export type PacienteFormGroup = FormGroup<PacienteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PacienteFormService {
  createPacienteFormGroup(paciente: PacienteFormGroupInput = { id: null }): PacienteFormGroup {
    const pacienteRawValue = {
      ...this.getFormDefaults(),
      ...paciente,
    };
    return new FormGroup<PacienteFormGroupContent>({
      id: new FormControl(
        { value: pacienteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      rut_paciente: new FormControl(pacienteRawValue.rut_paciente),
      nombres: new FormControl(pacienteRawValue.nombres),
      apellido_paterno: new FormControl(pacienteRawValue.apellido_paterno),
      apellido_materno: new FormControl(pacienteRawValue.apellido_materno),
      telefono: new FormControl(pacienteRawValue.telefono),
      correo: new FormControl(pacienteRawValue.correo),
      direccion: new FormControl(pacienteRawValue.direccion),
      comuna: new FormControl(pacienteRawValue.comuna),
    });
  }

  getPaciente(form: PacienteFormGroup): IPaciente | NewPaciente {
    return form.getRawValue() as IPaciente | NewPaciente;
  }

  resetForm(form: PacienteFormGroup, paciente: PacienteFormGroupInput): void {
    const pacienteRawValue = { ...this.getFormDefaults(), ...paciente };
    form.reset(
      {
        ...pacienteRawValue,
        id: { value: pacienteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PacienteFormDefaults {
    return {
      id: null,
    };
  }
}
