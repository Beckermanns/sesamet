export interface IPaciente {
  id: number;
  rut_paciente?: string | null;
  nombres?: string | null;
  apellido_paterno?: string | null;
  apellido_materno?: string | null;
  telefono?: number | null;
  correo?: string | null;
  direccion?: string | null;
  comuna?: string | null;
}

export type NewPaciente = Omit<IPaciente, 'id'> & { id: null };
