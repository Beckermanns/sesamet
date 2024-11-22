import { IDoctor, NewDoctor } from './doctor.model';

export const sampleWithRequiredData: IDoctor = {
  id: 14356,
};

export const sampleWithPartialData: IDoctor = {
  id: 7551,
  rut_doctor: 'besides',
  apellido_paterno: 'among provided',
  telefono: 15971,
  correo: 'ah pastel bravely',
};

export const sampleWithFullData: IDoctor = {
  id: 28506,
  rut_doctor: 'jovially',
  nombre: 'nor revitalise lest',
  apellido_paterno: 'anesthetize muted yum',
  apellido_materno: 'meanwhile bloom curly',
  telefono: 11834,
  correo: 'weary',
  especialidad: 'almost given statement',
};

export const sampleWithNewData: NewDoctor = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
