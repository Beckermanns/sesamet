import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 4189,
  login: 'w0A&A@6aCvm\\#g\\<rl1V\\KcWxoyO\\izBB',
};

export const sampleWithPartialData: IUser = {
  id: 29072,
  login: 'h',
};

export const sampleWithFullData: IUser = {
  id: 31127,
  login: 'v09KMj',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
