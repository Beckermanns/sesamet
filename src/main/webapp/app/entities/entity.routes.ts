import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'sesametApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'centro-medico',
    data: { pageTitle: 'sesametApp.centroMedico.home.title' },
    loadChildren: () => import('./centro-medico/centro-medico.routes'),
  },
  {
    path: 'doctor',
    data: { pageTitle: 'sesametApp.doctor.home.title' },
    loadChildren: () => import('./doctor/doctor.routes'),
  },
  {
    path: 'paciente',
    data: { pageTitle: 'sesametApp.paciente.home.title' },
    loadChildren: () => import('./paciente/paciente.routes'),
  },
  {
    path: 'atencion',
    data: { pageTitle: 'sesametApp.atencion.home.title' },
    loadChildren: () => import('./atencion/atencion.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
