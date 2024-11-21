import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '82854f99-cf9d-485c-bef9-13a9f9f54c3b',
};

export const sampleWithPartialData: IAuthority = {
  name: 'ce6301ea-ac17-4160-b4b9-db601310a5b0',
};

export const sampleWithFullData: IAuthority = {
  name: '7ade7871-2af5-4223-abd9-c520dfb0d440',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
