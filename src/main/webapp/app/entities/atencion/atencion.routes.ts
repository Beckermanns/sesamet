import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AtencionResolve from './route/atencion-routing-resolve.service';

const atencionRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/atencion.component').then(m => m.AtencionComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/atencion-detail.component').then(m => m.AtencionDetailComponent),
    resolve: {
      atencion: AtencionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/atencion-update.component').then(m => m.AtencionUpdateComponent),
    resolve: {
      atencion: AtencionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/atencion-update.component').then(m => m.AtencionUpdateComponent),
    resolve: {
      atencion: AtencionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default atencionRoute;
