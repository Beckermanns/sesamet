import dayjs from 'dayjs/esm';
import { ICentroMedico } from 'app/entities/centro-medico/centro-medico.model';
import { IDoctor } from 'app/entities/doctor/doctor.model';
import { IPaciente } from 'app/entities/paciente/paciente.model';

export interface IAtencion {
  id: number;
  fecha?: dayjs.Dayjs | null;
  hora?: string | null;
  motivo_consulta?: string | null;
  diagnostico?: string | null;
  tratamiento?: string | null;
  centroMedico?: ICentroMedico | null;
  doctor?: IDoctor | null;
  paciente?: IPaciente | null;
}

export type NewAtencion = Omit<IAtencion, 'id'> & { id: null };
