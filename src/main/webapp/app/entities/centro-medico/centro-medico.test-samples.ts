import { ICentroMedico, NewCentroMedico } from './centro-medico.model';

export const sampleWithRequiredData: ICentroMedico = {
  id: 20429,
};

export const sampleWithPartialData: ICentroMedico = {
  id: 24909,
  region: 'archive toward and',
  comuna: 'incidentally',
};

export const sampleWithFullData: ICentroMedico = {
  id: 23074,
  centro: 'furthermore',
  region: 'publicity decisive allocation',
  comuna: 'whose ha',
  direccion: 'above per thick',
};

export const sampleWithNewData: NewCentroMedico = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
