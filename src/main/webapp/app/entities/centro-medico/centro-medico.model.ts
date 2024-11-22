export interface ICentroMedico {
  id: number;
  centro?: string | null;
  region?: string | null;
  comuna?: string | null;
  direccion?: string | null;
}

export type NewCentroMedico = Omit<ICentroMedico, 'id'> & { id: null };
