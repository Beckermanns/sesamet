import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICentroMedico } from '../centro-medico.model';
import { CentroMedicoService } from '../service/centro-medico.service';

const centroMedicoResolve = (route: ActivatedRouteSnapshot): Observable<null | ICentroMedico> => {
  const id = route.params.id;
  if (id) {
    return inject(CentroMedicoService)
      .find(id)
      .pipe(
        mergeMap((centroMedico: HttpResponse<ICentroMedico>) => {
          if (centroMedico.body) {
            return of(centroMedico.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default centroMedicoResolve;
