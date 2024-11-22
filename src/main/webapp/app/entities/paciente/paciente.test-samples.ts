import { IPaciente, NewPaciente } from './paciente.model';

export const sampleWithRequiredData: IPaciente = {
  id: 11577,
};

export const sampleWithPartialData: IPaciente = {
  id: 28175,
  rut_paciente: 'privilege',
  nombres: 'bend equally scornful',
  telefono: 11874,
};

export const sampleWithFullData: IPaciente = {
  id: 20695,
  rut_paciente: 'simplistic because underneath',
  nombres: 'phew',
  apellido_paterno: 'forager',
  apellido_materno: 'slide',
  telefono: 8622,
  correo: 'secret concerning',
  direccion: 'an finally ripe',
  comuna: 'silk',
};

export const sampleWithNewData: NewPaciente = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
