import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IDoctor, NewDoctor } from '../doctor.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDoctor for edit and NewDoctorFormGroupInput for create.
 */
type DoctorFormGroupInput = IDoctor | PartialWithRequiredKeyOf<NewDoctor>;

type DoctorFormDefaults = Pick<NewDoctor, 'id'>;

type DoctorFormGroupContent = {
  id: FormControl<IDoctor['id'] | NewDoctor['id']>;
  rut_doctor: FormControl<IDoctor['rut_doctor']>;
  nombre: FormControl<IDoctor['nombre']>;
  apellido_paterno: FormControl<IDoctor['apellido_paterno']>;
  apellido_materno: FormControl<IDoctor['apellido_materno']>;
  telefono: FormControl<IDoctor['telefono']>;
  correo: FormControl<IDoctor['correo']>;
  especialidad: FormControl<IDoctor['especialidad']>;
};

export type DoctorFormGroup = FormGroup<DoctorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DoctorFormService {
  createDoctorFormGroup(doctor: DoctorFormGroupInput = { id: null }): DoctorFormGroup {
    const doctorRawValue = {
      ...this.getFormDefaults(),
      ...doctor,
    };
    return new FormGroup<DoctorFormGroupContent>({
      id: new FormControl(
        { value: doctorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      rut_doctor: new FormControl(doctorRawValue.rut_doctor),
      nombre: new FormControl(doctorRawValue.nombre),
      apellido_paterno: new FormControl(doctorRawValue.apellido_paterno),
      apellido_materno: new FormControl(doctorRawValue.apellido_materno),
      telefono: new FormControl(doctorRawValue.telefono),
      correo: new FormControl(doctorRawValue.correo),
      especialidad: new FormControl(doctorRawValue.especialidad),
    });
  }

  getDoctor(form: DoctorFormGroup): IDoctor | NewDoctor {
    return form.getRawValue() as IDoctor | NewDoctor;
  }

  resetForm(form: DoctorFormGroup, doctor: DoctorFormGroupInput): void {
    const doctorRawValue = { ...this.getFormDefaults(), ...doctor };
    form.reset(
      {
        ...doctorRawValue,
        id: { value: doctorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DoctorFormDefaults {
    return {
      id: null,
    };
  }
}
