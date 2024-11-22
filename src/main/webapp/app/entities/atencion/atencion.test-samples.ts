import dayjs from 'dayjs/esm';

import { IAtencion, NewAtencion } from './atencion.model';

export const sampleWithRequiredData: IAtencion = {
  id: 17379,
};

export const sampleWithPartialData: IAtencion = {
  id: 19478,
  motivo_consulta: 'unto',
  tratamiento: 'reword',
};

export const sampleWithFullData: IAtencion = {
  id: 22226,
  fecha: dayjs('2024-11-20'),
  hora: 'that some among',
  motivo_consulta: 'towards',
  diagnostico: 'pace pointed',
  tratamiento: 'unfortunate frenetically',
};

export const sampleWithNewData: NewAtencion = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
