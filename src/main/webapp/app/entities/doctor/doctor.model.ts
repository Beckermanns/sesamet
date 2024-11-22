export interface IDoctor {
  id: number;
  rut_doctor?: string | null;
  nombre?: string | null;
  apellido_paterno?: string | null;
  apellido_materno?: string | null;
  telefono?: number | null;
  correo?: string | null;
  especialidad?: string | null;
}

export type NewDoctor = Omit<IDoctor, 'id'> & { id: null };
