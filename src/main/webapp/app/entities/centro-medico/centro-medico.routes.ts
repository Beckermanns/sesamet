import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import CentroMedicoResolve from './route/centro-medico-routing-resolve.service';

const centroMedicoRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/centro-medico.component').then(m => m.CentroMedicoComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/centro-medico-detail.component').then(m => m.CentroMedicoDetailComponent),
    resolve: {
      centroMedico: CentroMedicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/centro-medico-update.component').then(m => m.CentroMedicoUpdateComponent),
    resolve: {
      centroMedico: CentroMedicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/centro-medico-update.component').then(m => m.CentroMedicoUpdateComponent),
    resolve: {
      centroMedico: CentroMedicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default centroMedicoRoute;
